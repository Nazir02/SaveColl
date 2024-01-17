package com.example.savecoll

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.savecall.R

class MainActivity : AppCompatActivity() {
    companion object {
        public var number = "test"
    }
    private val permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.WRITE_CONTACTS
    )
    private val permissionRequestCode = 123 // You can use any value for the request code


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!arePermissionsGranted()) {
            requestPermissions()
        } else {
            findViewById<TextView>(R.id.tv_text).setOnClickListener {
                findViewById<TextView>(R.id.tv_text).text= number
            }
        }


    }
    private fun arePermissionsGranted(): Boolean {
        // Check each permission and return false if any permission is not granted
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestPermissions() {
        // Request permissions for devices running Android 6.0 (API level 23) or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, permissions, permissionRequestCode)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == permissionRequestCode) {
            // Check if all permissions are granted
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // All permissions granted, proceed with your logic
            } else {
                // Permissions were not granted, handle accordingly
                // You may inform the user about the importance of the permissions
            }
        }
    }


}