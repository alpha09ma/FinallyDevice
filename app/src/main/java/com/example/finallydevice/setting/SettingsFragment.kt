package com.example.finallydevice.setting

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.finallydevice.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val appOps: AppOpsManager =  requireActivity().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), requireActivity().getPackageName())
        if(mode != AppOpsManager.MODE_ALLOWED)
            findPreference<Preference>("setting")?.summary="未授予权限"
        else
            findPreference<Preference>("setting")?.summary="已授予权限"
        findPreference<Preference>("setting")?.setOnPreferenceClickListener(
            object :Preference.OnPreferenceClickListener{
                override fun onPreferenceClick(preference: Preference?): Boolean {
                        val intent1: Intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
                        startActivity(intent1)
                    return true
                }

            }
        )
    }
    override fun onStart() {
        super.onStart()
        val appOps: AppOpsManager =  requireActivity().getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow("android:get_usage_stats", android.os.Process.myUid(), requireActivity().getPackageName())
        if(mode != AppOpsManager.MODE_ALLOWED)
            findPreference<Preference>("setting")?.summary="未授予权限"
        else
            findPreference<Preference>("setting")?.summary="已授予权限"
    }
}