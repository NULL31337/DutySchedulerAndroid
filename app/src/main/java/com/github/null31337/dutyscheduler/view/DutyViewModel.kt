package com.github.null31337.dutyscheduler.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.null31337.dutyscheduler.model.DutyReceive
import com.github.null31337.dutyscheduler.model.DutySend
import com.github.null31337.dutyscheduler.repository.DutyRepository
import com.github.null31337.dutyscheduler.repository.DutyRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DutyViewModel(private val repository: DutyRepository = DutyRepositoryImpl()) : ViewModel() {
  private val duties = MutableLiveData<SortedSet<DutyReceive>>(sortedSetOf())
  var userId: Long = 0

  private val formatter = SimpleDateFormat("dd.MM.yyyy")
  private val comparator = Comparator<DutyReceive> { a, b ->
    when {
      a.status != b.status -> a.status.ordinal - b.status.ordinal
      else -> if (formatter.parse(a.deadline).before(formatter.parse(b.deadline))) -1 else 1
    }
  }

  init {
    viewModelScope.launch(Dispatchers.IO) {
      duties.postValue(repository.getAllDuties(userId).toSortedSet(comparator))
      while(true) {
        duties.postValue(repository.getAllDuties(userId).toSortedSet(comparator))
        Thread.sleep(1000)
      }
    }
  }

  fun addDuty(duty: DutySend) {
    viewModelScope.launch(Dispatchers.IO) {
      duties.postValue(
        duties.value?.plus(
          DutyReceive(
            repository.postDuty(userId, duty),
            duty.name,
            duty.description,
            duty.deadline,
            duty.status
          )
        )?.toSortedSet(comparator)
      )
    }
  }

  fun duties() = duties

  fun generateCode(): LiveData<String> {
    val liveData = MutableLiveData<String>()
    viewModelScope.launch(Dispatchers.IO) {
//      liveData.postValue("ABOBA")
      liveData.postValue(repository.generateCode(userId))
    }
    return liveData
  }

  fun login(code: String): LiveData<Boolean> {
    val liveData = MutableLiveData<Boolean>()
    viewModelScope.launch(Dispatchers.IO) {
      val userIdTmp = repository.login(code)
      userIdTmp?.let {
        userId = it
        liveData.postValue(true)
      } ?: liveData.postValue(false)
      duties.postValue(repository.getAllDuties(userId).toSortedSet(comparator))
    }
    return liveData
  }
}