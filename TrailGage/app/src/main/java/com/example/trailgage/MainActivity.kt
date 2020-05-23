package com.example.trailgage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        containedButton.setOnClickListener {
            val intent = Intent(this, Control::class.java)
            // start your next activity
            startActivity(intent)
        }
    }

}
