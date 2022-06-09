package com.idn.rumayshomobile.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.idn.rumayshomobile.R
import com.idn.rumayshomobile.databinding.FragmentHomeBinding
import com.idn.rumayshomobile.utils.adapter.SectionPagerAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpList.adapter = activity?.let { SectionPagerAdapter(it) }

        val tabList = arrayOf(
            R.string.tab1,
            R.string.tab2,
            R.string.tab3,
            R.string.tab4
        )
        TabLayoutMediator(binding.tabLayout, binding.vpList) { tab, position ->
            tab.text = getString(tabList[position])
        }.attach()
    }

}