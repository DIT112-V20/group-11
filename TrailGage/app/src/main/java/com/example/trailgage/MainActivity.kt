package com.example.trailgage

import android.content.Context
import android.content.Intent
import android.net.nsd.NsdManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trailgage.LocalNetworkDeviceNameResolver.AddressResolutionListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    private var trailName: String? = null
    private var trailIpAddress: String? = null
    private var trailNameEditText: EditText? = null
    private var imm: InputMethodManager? = null
    var mNsdManager: NsdManager? = null
    var mDeviceNameResolver: LocalNetworkDeviceNameResolver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        trailNameEditText = findViewById(R.id.editTextTrailname)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        trailNameEditText?.setOnEditorActionListener(this)



        connetButton.setOnClickListener {
            //when you press the connect button it will move from main activity to control activity
            val bIntent = Intent(this, Control::class.java)
            //passes the resolved ipAddress to the control activity
            intent.putExtra("ipAddress",trailIpAddress)
            startActivity(bIntent)

        }

        val circlebutton = findViewById<Button>(R.id.circlebutton)
        circlebutton.setOnClickListener{
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openControll() {

    }


    override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (p0 == trailNameEditText) {
            var trailName = trailNameEditText?.text?.trim().toString()
            if (trailName.isBlank() || trailName.isEmpty()) {
                trailNameEditText?.error = getString(R.string.name_cannot_be_empty)

            } else {

                imm?.hideSoftInputFromWindow(trailNameEditText?.windowToken, 0)
                trailName = trailNameEditText?.text?.trim().toString()
                trailIpAddress =getIpAdd(trailName)


            }
            true
        } else {
            false
        }

    }

    private fun getIpAdd(tags: String?): String {

        var tempIpAddress = ""

        // Don't use the same device name for multiple instances as they overwrite each other
        // Asynchronous device name resolution (suggested)
        mDeviceNameResolver = LocalNetworkDeviceNameResolver(this.applicationContext,
            trailName, "_http._tcp.", 80,
            AddressResolutionListener { address ->

                Log.i(
                    trailName,
                    "" + address.hostName
                )

            })

        tempIpAddress = mDeviceNameResolver!!.getAddress(10,TimeUnit.SECONDS).toString()

        return tempIpAddress

    }


}





