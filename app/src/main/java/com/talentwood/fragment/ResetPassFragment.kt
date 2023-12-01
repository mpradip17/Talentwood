package com.talentwood.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.talentwood.R
import com.talentwood.databinding.FragmentResetPassBinding
import com.talentwood.utils.meetsRequirements
import com.talentwood.utils.setOnClickWithDebounce
import com.talentwood.utils.showErrorToast
import kotlinx.coroutines.launch

class ResetPassFragment : Fragment() {
    private var _binding: FragmentResetPassBinding? = null

    private val binding: FragmentResetPassBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                setOnClickListener()
            }
        }
    }

    private fun setOnClickListener() {
        with(binding) {
            sendOtp.setOnClickWithDebounce {
                Log.e("etPass","etPass"+etPass.text?.toString())
                if (etPass.text.isNullOrEmpty()) {
                    showErrorToast(requireContext(), "Please Enter Password")
                }else if (etConfirmPass.text.isNullOrEmpty()) {
                    showErrorToast(requireContext(), "Please Enter Confirm Password")
                }else if (etPass.text?.toString()?.meetsRequirements == false) {
                    showErrorToast(requireContext(), "Please Enter Valid Password")
                }else if (etConfirmPass.text?.toString()?.meetsRequirements == false) {
                    showErrorToast(requireContext(), "Please Enter Valid Confirm Password")
                }else if (etPass.text?.toString() != etConfirmPass.text?.toString()) {
                   showErrorToast(requireContext(), "Password Doesn't match")
                }else {
                    findNavController().navigate(R.id.forget_to_reset)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}