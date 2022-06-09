package com.idn.rumayshomobile.utils.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.idn.rumayshomobile.ui.fragment.AqidahFragment
import com.idn.rumayshomobile.ui.fragment.AmalanFragment
import com.idn.rumayshomobile.ui.fragment.AkhlaqFragment
import com.idn.rumayshomobile.ui.fragment.LatestFragment

class SectionPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LatestFragment()
            1 -> AqidahFragment()
            2 -> AkhlaqFragment()
            3 -> AmalanFragment()
            else -> LatestFragment()
        }
    }

}