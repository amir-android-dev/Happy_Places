package com.amir.happyplaces.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amir.happyplaces.R
import kotlinx.android.synthetic.main.activity_add_happy_places.*
import kotlinx.android.synthetic.main.activity_map.*

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setSupportActionBar(toolbar_map)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_map?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}