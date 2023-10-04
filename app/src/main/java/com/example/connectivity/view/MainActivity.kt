package com.example.connectivity.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.connectivity.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnBluetooth: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBluetooth =  findViewById(R.id.btn_goBluetooth)

        btnBluetooth.setOnClickListener {
            val intent = Intent(this, BluetoothActivity::class.java)
            startActivity(intent)
        }
    }
}