package com.example.trailgage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        //accessing our textview from layout
     //   val about = findViewById<TextView>(R.id.about) as TextView
        val paragraph = findViewById<TextView>(R.id.paragraph) as TextView
        val logo = findViewById<ImageView>(R.id.logo) as ImageView

      //  about?.setOnClickListener{ Toast.makeText(this@InfoActivity,
      //      R.string.circlebutton, Toast.LENGTH_LONG).show() }
        paragraph?.setOnClickListener{ Toast.makeText(this@InfoActivity,
            R.string.circlebutton, Toast.LENGTH_LONG).show() }
        logo?.setOnClickListener{ Toast.makeText(this@InfoActivity,
            R.string.circlebutton, Toast.LENGTH_LONG).show() }
    }
}



