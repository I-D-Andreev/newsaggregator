package com.example.ivanandreev.newsaggregator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.ivanandreev.newsaggregator.adapters.TabsPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class AccountPreferences : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_preferences_screen)

        setUpNavBar()
        loadViewPager()
    }

    private fun setUpNavBar() {
        val navBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navBar.menu.getItem(2).isChecked = true
        NavBar.setUpBar(navBar, this)
    }

    private fun loadViewPager() {
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val tabTitles = resources.getStringArray(R.array.account_preferences_tabs)
        val tabAdapter = TabsPagerAdapter(supportFragmentManager, tabTitles)

        viewPager.adapter = tabAdapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })


    }


}