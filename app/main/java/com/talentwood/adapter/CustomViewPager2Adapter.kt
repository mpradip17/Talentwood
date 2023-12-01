package com.talentwood.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.talentwood.model.CustomViewPager2

/**
 * Created by pradeep
 */

class CustomViewPager2Adapter(fragmentManager: FragmentManager,val fragments:MutableList<Fragment>, viewlifecycler: Lifecycle)
    : FragmentStateAdapter(fragmentManager, viewlifecycler)
{

    override fun getItemCount(): Int
    {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        val args = Bundle()

        when (position) {
            1 -> {
                args.putString("KEY_ID", "inProgress")
                fragments[position].arguments = args
                return fragments[position]
            }
            2 -> {
                args.putString("KEY_ID", "cancel")
                fragments[position].arguments = args
                return fragments[position]
            }
            3 ->
            {
                args.putString("KEY_ID","deliver")
                fragments[position].arguments=args
                return fragments[position]
            }
            else -> {
                args.putString("KEY_ID", "all")
                fragments[position].arguments = args
                return fragments[position]
            }
        }

    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find {it.id.hashCode().toLong() == itemId } != null
    }


}
/*
class CustomViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: MutableList<CustomViewPager2> = mutableListOf()
    fun addFragment(customVpFragment: CustomViewPager2) {
        fragmentList.add(customVpFragment)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentList[position].fragment
        val bundle = fragmentList[position].bundle
        bundle?.let { bundle_ ->
            fragment.arguments = bundle_
        }
        return fragment
    }
}*/
