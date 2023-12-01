package com.talentwood.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.util.Patterns
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
import com.talentwood.databinding.FragmentForgetPassBinding
import com.talentwood.utils.gone
import com.talentwood.utils.isVisible
import com.talentwood.utils.setBackgroundTint
import com.talentwood.utils.showErrorToast
import com.talentwood.utils.visible
import kotlinx.coroutines.launch

class ForgetPassFragment : Fragment() {
    private var _binding: FragmentForgetPassBinding? = null
    private lateinit var mCountDownTimer: CountDownTimer
    private val INTERVAL: Long = 1000
    private val MILLI_SECS: Long = 150000
    private val SECS: Long = 60
    private val binding: FragmentForgetPassBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgetPassBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                setOnClickListener()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            requireActivity(),
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    _binding?.let { binding__ ->
                        if (binding__.group2.isVisible()) {
                            binding__.group2.gone()
                            binding__.group.visible()
                            binding__.sendOtp.text = getString(R.string.send_otp)
                            binding__.back.setBackgroundTint(R.color.btncolor)
                            binding__.back.text = getString(R.string.back)
                            binding__.tvTimer.text = ""
                            cancelTimer()
                        } else {
                            isEnabled = false
                            activity?.onBackPressedDispatcher!!.onBackPressed()
                        }
                    }?: kotlin.run {
                        isEnabled = false
                    //    activity?.onBackPressedDispatcher!!.onBackPressed()
                    }
                }
            })
    }

    private fun setOnClickListener() {
        with(binding) {
            sendOtp.setOnClickListener {
                if (sendOtp.text.toString() == getString(R.string.enter_otp)) {
                    if (otpView.otp?.length == 4) {
                        findNavController().navigate(R.id.forget_to_reset)
                    } else showErrorToast(requireContext(), "Please Enter valid OTP")
                } else {
                    if (etEmail.text?.isEmpty() == true) {
                        showErrorToast(requireContext(), "Please Enter Email or Mobile Number")
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                        showErrorToast(
                            requireContext(),
                            "Please Enter valid Email or Mobile Number"
                        )
                    } else {
                        group2.visible()
                        group.gone()
                        sendOtp.text = getString(R.string.enter_otp)
                        back.text = getString(R.string.resend_otp)
                        back.setBackgroundTint(R.color.border)
                        startTimer()
                    }
                }
            }
            back.setOnClickListener {
                if (sendOtp.text.toString() == getString(R.string.enter_otp)) {
                    group2.gone()
                    group.visible()
                    back.setBackgroundTint(R.color.btncolor)
                    sendOtp.text = getString(R.string.send_otp)
                    back.text = getString(R.string.back)
                    binding.tvTimer.text = ""
                    cancelTimer()
                } else findNavController().navigateUp()
            }

        }
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(MILLI_SECS, INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / INTERVAL / SECS
                val seconds = millisUntilFinished / INTERVAL % SECS
                val timeResult = "${minutes.toString().padStart(2, '0')}:" +
                        seconds.toString().padStart(2, '0')
                binding.tvTimer.text = timeResult
            }

            override fun onFinish() {
                binding.tvTimer.text = ""
            }
        }.start()
    }

    private fun cancelTimer() {
        if (::mCountDownTimer.isInitialized) {
            mCountDownTimer.cancel()
        }
    }

    override fun onDestroyView() {
        cancelTimer()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}