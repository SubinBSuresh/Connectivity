package com.example.connectivity.manager

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Bluetooths:BroadcastReceiver() {
    private val discoveredDevices = mutableListOf<BluetoothDevice>()
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action

        if (action == BluetoothDevice.ACTION_FOUND){
            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            if (device != null) {
                discoveredDevices.add(device)
            }
        }
    }

    fun getDiscoveredDevices(): List<BluetoothDevice> {
        return discoveredDevices
    }
}