package com.example.connectivity.view

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.connectivity.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BluetoothActivity : AppCompatActivity() {
    private lateinit var rvBluetoothList: RecyclerView
    private lateinit var fabRefresh: FloatingActionButton
    private lateinit var context: Context
    private val REQUEST_BLUETOOTH_SCAN_PERMISSION = 101

    override fun onDestroy() {
        super.onDestroy()

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth)
// Check if the BLUETOOTH_SCAN permission is granted
        val hasPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_SCAN
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasPermission) {
            // Request the BLUETOOTH_SCAN permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_SCAN),
                REQUEST_BLUETOOTH_SCAN_PERMISSION
            )
        }
        val hasPermissio1n = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasPermissio1n) {
            // Request the BLUETOOTH_SCAN permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                102
            )
        }
        rvBluetoothList = findViewById(R.id.rv_btList)
        fabRefresh = findViewById(R.id.fab_refresh)
        context = this
        val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Log.e("dutchman", "adapter returned as null")
        } else {
            Log.e("dutchman", "adapter returned not null" + bluetoothAdapter)

        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            startActivityForResult(enableBtIntent, 1)
        }

        val btManager =bluetoothManager
        val pairedDevices = btManager.adapter.bondedDevices
        if (pairedDevices.size > 0) {

            for (device in pairedDevices) {
                val deviceName = device.name
                val macAddress = device.address
                val aliasing = device.alias

//                Log.i(
//                    " dutchman ", "paired device: $deviceName at $macAddress + $aliasing "
//                )
            }


        }



        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        // Start discovering devices
        val status = btManager.adapter.startDiscovery()
        if (status) {
            Log.e("dutchman", "startdiscover true")

        } else Log.e("dutchman", "startdiscover false")




    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context, intent: Intent) {
            Log.e("dutchman", " not")

            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address

                    Log.e("dutchman", " not"+deviceName)

                }
            }
        }
    }


}

