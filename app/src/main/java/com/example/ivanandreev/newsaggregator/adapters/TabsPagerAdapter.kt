package com.example.ivanandreev.newsaggregator.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.ivanandreev.newsaggregator.fragments.AccountKeywordsFragment
import com.example.ivanandreev.newsaggregator.fragments.AccountTopicsFragment

class TabsPagerAdapter(fm: FragmentManager, private val tabTitles: Array<String>) :
    FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return AccountTopicsFragment()
            1 -> return AccountKeywordsFragment()
        }
        return AccountTopicsFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

}
