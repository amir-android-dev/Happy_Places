package com.amir.happyplaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_happy_places.*

class AddHappyPlacesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_happy_places)

        setSupportActionBar(toolbar_add_place)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_add_place?.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}