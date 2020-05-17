package com.example.trailgage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Next.setOnClickListener {
            val bIntent= Intent(this, Control::class.java)
            startActivity(bIntent)
            Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show()

        }


    }


}
