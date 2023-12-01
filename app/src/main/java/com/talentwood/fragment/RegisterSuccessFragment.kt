package com.talentwood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.talentwood.R
import com.talentwood.databinding.RegisterSuccessBinding
import com.talentwood.utils.gone
import com.talentwood.utils.setOnClickWithDebounce
import com.talentwood.utils.visible
import kotlinx.coroutines.launch

class RegisterSuccessFragment : Fragment() {

    private lateinit var binding: RegisterSuccessBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = RegisterSuccessBinding.inflate(inflater, container, false)
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
            buttonNext.setOnClickWithDebounce {
                findNavController().navigate(R.id.success_to_login)
            }
        }
    }
}