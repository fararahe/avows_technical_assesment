package com.avows.auth.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.avows.auth.R
import javax.inject.Inject

class PermissionHandler @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationPermission(activity: Activity, requestPermissionLauncher: ActivityResultLauncher<String>) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

        when {
            ContextCompat.checkSelfPermission(activity, NOTIFICATION_PERMISSION) == PackageManager.PERMISSION_GRANTED -> {
                // Nothing to do, already granted
            }

            activity.shouldShowRequestPermissionRationale(NOTIFICATION_PERMISSION) -> {
                showPermissionRationale(activity, requestPermissionLauncher)
            }

            else -> { // Denied, so need to request the permission
                requestPermissionLauncher.launch(NOTIFICATION_PERMISSION)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showPermissionRationale(activity: Activity, requestPermissionLauncher: ActivityResultLauncher<String>) {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.title_notification_permission))
            .setMessage(activity.getString(R.string.label_notification_permission_detail))
            .setPositiveButton(activity.getString(R.string.label_request_permission_again)) { _, _ ->
                requestPermissionLauncher.launch(NOTIFICATION_PERMISSION)
            }
            .setNegativeButton(activity.getString(R.string.label_later)) { _, _ -> }
            .show()
    }

    private fun showPermissionSetting(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activity.packageName, null)
        intent.data = uri
        activity.startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun showPermissionDeniedPermanentlyRationale(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle(activity.getString(R.string.title_notification_permission))
            .setMessage(activity.getString(R.string.label_notif_permission_permanently_denied))
            .setPositiveButton(activity.getString(R.string.label_setting)) { _, _ ->
                showPermissionSetting(activity)
            }
            .setNegativeButton(activity.getString(R.string.label_later)) { _, _ -> }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun isShouldShowPermissionRationale(activity: Activity): Boolean {
        return activity.shouldShowRequestPermissionRationale(NOTIFICATION_PERMISSION)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        private const val NOTIFICATION_PERMISSION = Manifest.permission.POST_NOTIFICATIONS
    }
}