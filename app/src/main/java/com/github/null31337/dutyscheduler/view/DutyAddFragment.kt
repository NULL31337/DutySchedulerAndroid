package com.github.null31337.dutyscheduler.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.github.null31337.dutyscheduler.R
import com.github.null31337.dutyscheduler.databinding.FragmentAddDutyBinding
import com.github.null31337.dutyscheduler.model.DutySend
import com.github.null31337.dutyscheduler.model.DutyStatus.*

class DutyAddFragment : Fragment() {
  lateinit var binding: FragmentAddDutyBinding
  private val viewModel: DutyViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)

    binding = FragmentAddDutyBinding.inflate(inflater, container, false)

    init()

    return binding.root
  }

  private fun init() {
    binding.addDutyButton.setOnClickListener {

      when {
        binding.name.text.isBlank() -> {
          binding.name.error = "Name is required"
        }
        binding.description.text.isBlank() -> {
          binding.description.error = "Description is required"
        }
        else -> {
          val name = binding.name.text.toString()
          val description = binding.description.text.toString()
          val deadline = with(binding.deadline) {
            "${dayOfMonth}.${month + 1}.${year}"
          }

          val status = when (binding.status.checkedRadioButtonId) {
            R.id.radio_done -> DONE
            R.id.radio_in_progress -> IN_PROGRESS
            R.id.radio_todo -> TODO
            else -> TODO
          }

          val duty = DutySend(name, description, deadline, status)

          viewModel.addDuty(duty)
          findNavController().navigateUp()
        }
      }
    }
  }
}