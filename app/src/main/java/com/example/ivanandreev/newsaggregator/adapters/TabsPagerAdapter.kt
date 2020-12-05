package com.example.ivanandreev.newsaggregator.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.ivanandreev.newsaggregator.fragments.AccountKeywordsFragment
import com.example.ivanandreev.newsaggregator.fragments.AccountSettingsFragment

class TabsPagerAdapter(fm: FragmentManager, private val tabTitles: Array<String>) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return AccountKeywordsFragment()
            1 -> return AccountSettingsFragment()
        }
        return AccountKeywordsFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

}
