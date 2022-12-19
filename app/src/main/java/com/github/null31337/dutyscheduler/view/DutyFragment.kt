package com.github.null31337.dutyscheduler.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.github.null31337.dutyscheduler.R
import com.github.null31337.dutyscheduler.databinding.FragmentDutyBinding
import com.github.null31337.dutyscheduler.duty.DutyAdapter

class DutyFragment : Fragment() {
  private lateinit var binding: FragmentDutyBinding
  private val viewModel: DutyViewModel by activityViewModels()
  private lateinit var myAdapter: DutyAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentDutyBinding.inflate(inflater, container, false)

    init()

    return binding.root
  }

  override fun onSaveInstanceState(outState: Bundle) {
    outState.putLong("userId", viewModel.userId)
    super.onSaveInstanceState(outState)
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    if (savedInstanceState != null) {
      viewModel.userId = savedInstanceState.getLong("userId")
    }
  }

  private fun init() {
    initRecyclerView()

    binding.addNote.setOnClickListener {
      findNavController().navigate(R.id.action_dutyFragment_to_dutyAddFragment)
    }

    binding.login.setOnClickListener {
      findNavController().navigate(R.id.action_dutyFragment_to_dutyLoginFragment)
    }

    viewModel.duties().observe(viewLifecycleOwner) {
      myAdapter.submitList(it.toList())
    }

  }

  private fun initRecyclerView() {
    myAdapter = DutyAdapter()
    binding.container.apply {
      layoutManager = GridLayoutManager(context, 2)
      this.adapter = myAdapter
    }
  }
}