package com.talentwood.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.talentwood.R
import com.talentwood.databinding.FragmentRegisterBinding
import com.talentwood.utils.gone
import com.talentwood.utils.setOnClickWithDebounce
import com.talentwood.utils.visible
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null

    private val binding: FragmentRegisterBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
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
                    _binding?.let {
                        if (it.etGender.isShowing) {
                            it.etGender.dismiss()
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
            register.setOnClickWithDebounce {
                findNavController().navigateUp()
            }
            buttonNext.setOnClickWithDebounce {
                if (buttonNext.text == requireContext().getString(R.string.submit)) {
                    findNavController().navigate(R.id.register_to_success)
                    return@setOnClickWithDebounce
                }
                register1.gone()
                register2.visible()
                buttonPrevious.visible()
                noAccount.gone()
                register.gone()
                buttonNext.text = requireContext().getString(R.string.submit)
            }
            buttonPrevious.setOnClickWithDebounce {
                register1.visible()
                register2.gone()
                buttonPrevious.gone()
                noAccount.visible()
                register.visible()
                buttonNext.text = requireContext().getString(R.string.next)
            }
            etGender.apply {
                setOnSpinnerItemSelectedListener<String> { _, _, _, _ ->
                }
                setOnSpinnerOutsideTouchListener { _, _ ->
                    dismiss()
                }
            }
            etRole.apply {
                setOnSpinnerOutsideTouchListener { _, _ ->
                    dismiss()
                }

                etDistrict.apply {
                    setOnSpinnerOutsideTouchListener { _, _ ->
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun contextDrawable(@DrawableRes resource: Int): Drawable? {
        return ContextCompat.getDrawable(requireContext(), resource)
    }
}