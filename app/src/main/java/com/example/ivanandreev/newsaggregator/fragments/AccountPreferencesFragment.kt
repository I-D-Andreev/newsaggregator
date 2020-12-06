package com.example.ivanandreev.newsaggregator.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.ivanandreev.newsaggregator.LoginActivity
import com.example.ivanandreev.newsaggregator.R
import com.example.ivanandreev.newsaggregator.adapters.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth

class AccountPreferencesFragment : Fragment() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(
        R.layout.account_preferences_screen,
        container,
        false
    )!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadViewPager(view)
        view.findViewById<MaterialTextView>(R.id.username).text = firebaseAuth.currentUser?.email
        view.findViewById<AppCompatImageButton>(R.id.sign_out_button)
            .setOnClickListener(this::onSignOutClicked)
        view.findViewById<MaterialTextView>(R.id.sign_out_text_view)
            .setOnClickListener(this::onSignOutClicked)
    }

    private fun onSignOutClicked(view: View) {
        firebaseAuth.signOut()
        val intent = Intent(activity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }

    private fun loadViewPager(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager>(R.id.view_pager)
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val tabTitles = resources.getStringArray(R.array.account_preferences_tabs)
        val tabAdapter = TabsPagerAdapter(childFragmentManager, tabTitles)
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