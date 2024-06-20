package com.example.dpc

import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay


class MainActivity2 : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    companion object {
        private const val REQUEST_CODE_ENABLE_ADMIN = 1
        const val REQUEST_CODE_PROVISION_PROFILE = 1001
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация DevicePolicyManager и ComponentName
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(this, MyDeviceAdminReceiver::class.java)


        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)

        val r = findViewById<Button>(R.id.button)

        r.setOnClickListener{

//            lockDevice()
            deployWorkProfile()
        }


    }
    private fun lockDevice() {

        if (devicePolicyManager.isAdminActive(adminComponent)) {
            devicePolicyManager.lockNow()
        } else {
            // Request device admin permission
            // ...
        }
    }
    private fun deployWorkProfile() {
        if (devicePolicyManager.isProvisioningAllowed(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE)) {
            val intent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE)
            intent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME, adminComponent)


            startActivityForResult(intent, REQUEST_CODE_PROVISION_PROFILE)
        } else {
            // Provisioning not allowed
            // Handle the situation accordingly
        }
    }

    private fun unlockDevice() {
        if (devicePolicyManager.isAdminActive(adminComponent)) {
            devicePolicyManager.resetPassword("", 0)
            devicePolicyManager.lockNow()
        } else {
            // Request device admin permission
            // ...
        }
    }

}
