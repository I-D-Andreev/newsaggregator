package com.example.ivanandreev.newsaggregator.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ivanandreev.newsaggregator.R
import com.google.android.material.button.MaterialButton
import android.provider.Settings
import com.example.ivanandreev.newsaggregator.ChangePasswordActivity
import com.example.ivanandreev.newsaggregator.helpers.NotificationSender


class AccountSettingsFragment : Fragment() {
    private lateinit var loadedView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.account_settings_fragment, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadedView = view
        view.findViewById<MaterialButton>(R.id.notification_settings)
            .setOnClickListener(this::onNotificationSettingsClicked)

        view.findViewById<MaterialButton>(R.id.change_password)
            .setOnClickListener(this::onChangePasswordClicked)
    }

    private fun onChangePasswordClicked(view: View) {
        val intent = Intent(view.context, ChangePasswordActivity::class.java)
        view.context.startActivity(intent)
    }

    private fun onNotificationSettingsClicked(view: View) {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context?.packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, NotificationSender.NOTIFICATION_CHANNEL_ID)
        startActivity(intent)
    }
}
