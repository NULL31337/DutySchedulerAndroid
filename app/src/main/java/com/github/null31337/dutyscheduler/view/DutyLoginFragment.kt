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
import com.github.null31337.dutyscheduler.databinding.FragmentLoginBinding
import com.github.null31337.dutyscheduler.model.DutySend
import com.github.null31337.dutyscheduler.model.DutyStatus.*

class DutyLoginFragment : Fragment() {
  private lateinit var binding: FragmentLoginBinding
  private val viewModel: DutyViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    super.onCreateView(inflater, container, savedInstanceState)

    binding = FragmentLoginBinding.inflate(inflater, container, false)

    init()

    return binding.root
  }

  private fun init() {
    binding.login.setOnClickListener {
      viewModel.login(binding.secretCode.text.toString()).observe(viewLifecycleOwner) {
        if (it) {
          findNavController().navigateUp()
        } else {
          binding.secretCode.error = "Wrong code"
        }
      }
    }

    binding.register.setOnClickListener {
      viewModel.generateCode().observe(viewLifecycleOwner) {
        binding.secretCodeText.text = it
      }
    }

    binding.back.setOnClickListener {
      findNavController().navigateUp()
    }
  }
}