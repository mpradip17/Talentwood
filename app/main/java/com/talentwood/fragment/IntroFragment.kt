package com.talentwood.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieDrawable
import com.talentwood.R
import com.talentwood.databinding.FragmentIntroBinding
import com.talentwood.utils.backgroundDrawable
import com.talentwood.utils.disable
import com.talentwood.utils.gone
import com.talentwood.utils.setColor
import com.talentwood.utils.setOnClickWithDebounce
import com.talentwood.utils.visible


class IntroFragment : Fragment() ,Animation.AnimationListener{
    private var _binding: FragmentIntroBinding? = null
    private val binding: FragmentIntroBinding
        get() = _binding!!

    private var position=0

    private var moveToBottom: Animation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        moveToBottom = AnimationUtils.loadAnimation(requireContext(), R.anim.move)

        moveToBottom?.setAnimationListener(this)

        binding.btnNext.setOnClickWithDebounce {
            position=position.plus(1)
            initUi()
        }
    }

    private fun initUi() {
        with(binding){
            ivIcon.setImageDrawable(null)
            when (position) {
                0 -> {
                    ivIcon.setAnimation(getString(R.string.oscar_json))
                    ivIcon.repeatCount = LottieDrawable.INFINITE
                    ivIcon.playAnimation()
                    launcher.visible()
                   // ivIcon.backgroundDrawable(R.drawable.oscar)
                    tvTitle.text = getString(R.string.showcasing_talent)
                    tvDesc.text = getString(R.string.intro_desc_1)
                    tvCircle1.setColor(R.color.yellow)
                    tvCircle2.setColor(R.color.grey)
                    tvCircle3.setColor(R.color.grey)
                    tvCircle4.setColor(R.color.grey)
                    btnNext.text= getString(R.string.next)
                }

                1 -> {
                    launcher.visible()
                    ivIcon.setAnimation(getString(R.string.money_json))
                    ivIcon.repeatCount = LottieDrawable.INFINITE
                    ivIcon.playAnimation()
                    //  binding.ivIcon.backgroundDrawable(R.drawable.money)
                    tvTitle.text = getString(R.string.dual_rewards)
                    tvDesc.text = getString(R.string.intro_desc_2)
                    tvCircle1.setColor(R.color.grey)
                    tvCircle2.setColor(R.color.yellow)
                    tvCircle3.setColor(R.color.grey)
                    tvCircle4.setColor(R.color.grey)
                    btnNext.text= getString(R.string.next)
                }

                2 -> {
                    launcher.visible()
                    ivIcon.setAnimation(getString(R.string.handshake_json))
                    ivIcon.repeatCount = LottieDrawable.INFINITE
                    ivIcon.playAnimation()
                 //   ivIcon.backgroundDrawable(R.drawable.exp)
                    tvTitle.text = getString(R.string.engaging_experience)
                    tvDesc.text = getString(R.string.intro_desc_3)
                    tvCircle1.setColor(R.color.grey)
                    tvCircle2.setColor(R.color.grey)
                    tvCircle3.setColor(R.color.yellow)
                    tvCircle4.setColor(R.color.grey)
                    btnNext.text= getString(R.string.next)
                }
                3->{
                    binding.launcher.startAnimation(moveToBottom)

                   // launcher.gone()
                   // ivIcon.backgroundDrawable(R.mipmap.ic_launcher)
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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onAnimationStart(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        if (animation == moveToBottom) {
            binding.launcher.gone()
            binding.ivIcon.setAnimation(getString(R.string.oscar_json))
            binding.ivIcon.repeatCount = LottieDrawable.INFINITE
            binding.ivIcon.playAnimation()
            binding.btnNext.disable()
        }
    }

    override fun onAnimationRepeat(animation: Animation?) {
    }
}
