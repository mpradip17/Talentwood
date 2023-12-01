package com.talentwood.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.gson.Gson
import com.talentwood.R
import com.talentwood.databinding.FragmentSliderBinding
import com.talentwood.utils.backgroundDrawable
import com.talentwood.utils.setColor
import com.talentwood.utils.setOnClickWithDebounce


class SliderFragment : Fragment() {
    private var _binding: FragmentSliderBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSliderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickWithDebounce {
            val result = binding.btnNext.text.toString()
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
        }
    }

    fun initializeView(position: Int) {
        Log.e("initializeView","initializeView"+position)
        with(binding){
            when (position) {
                0 -> {
                    ivIcon.backgroundDrawable(R.drawable.oscar)
                    tvTitle.text = getString(R.string.showcasing_talent)
                    tvDesc.text = getString(R.string.intro_desc_1)
                    tvCircle1.setColor(R.color.yellow)
                    tvCircle2.setColor(R.color.grey)
                    tvCircle3.setColor(R.color.grey)
                    tvCircle4.setColor(R.color.grey)
                    btnNext.text= getString(R.string.next)
                }

                1 -> {
                    binding.ivIcon.backgroundDrawable(R.drawable.money)
                    tvTitle.text = getString(R.string.dual_rewards)
                    tvDesc.text = getString(R.string.intro_desc_2)
                    tvCircle1.setColor(R.color.grey)
                    tvCircle2.setColor(R.color.yellow)
                    tvCircle3.setColor(R.color.grey)
                    tvCircle4.setColor(R.color.grey)
                    btnNext.text= getString(R.string.next)
                }

                2 -> {
                    ivIcon.backgroundDrawable(R.drawable.exp)
                    tvTitle.text = getString(R.string.engaging_experience)
                    tvDesc.text = getString(R.string.intro_desc_3)
                    tvCircle1.setColor(R.color.grey)
                    tvCircle2.setColor(R.color.grey)
                    tvCircle3.setColor(R.color.yellow)
                    tvCircle4.setColor(R.color.grey)
                    btnNext.text= getString(R.string.next)
                }
                3->{
                    ivIcon.backgroundDrawable(R.mipmap.ic_launcher)
                    tvTitle.text = getString(R.string.welcome_user)
                    tvDesc.text = getString(R.string.intro_desc_4)
                    tvCircle1.setColor(R.color.grey)
                    tvCircle2.setColor(R.color.grey)
                    tvCircle3.setColor(R.color.grey)
                    tvCircle4.setColor(R.color.yellow)
                    tvCircle4.setColor(R.color.yellow)
                    btnNext.text= getString(R.string.get_started)
                }
            }
        }
    }

}