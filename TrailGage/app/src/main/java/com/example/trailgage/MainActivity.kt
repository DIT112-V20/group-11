package com.example.trailgage

import android.content.Context
import android.content.Intent
import android.net.nsd.NsdManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    private var TAG: EditText? = null
    private var imm: InputMethodManager? = null
    var mNsdManager: NsdManager? = null
    var mDeviceNameResolver: LocalNetworkDeviceNameResolver? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        TAG = findViewById(R.id.editTextTrailname)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        // setting the listner to the search button

        TAG?.setOnEditorActionListener(this)











        Next.setOnClickListener {
            val bIntent = Intent(this, Control::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show()

        }


    }

    override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (p0 == TAG) {
            val trailName = TAG?.text?.trim().toString()
            if (trailName.isBlank() || trailName.isEmpty()) {
                TAG?.error = getString(R.string.name_cannot_be_empty)

            } else {

                imm?.hideSoftInputFromWindow(TAG?.windowToken, 0)

                // connect the trail to server

            }
            true
        } else {
            false
        }

    }


    fun getTag(): String {

        return this.TAG.toString()

    }


}
