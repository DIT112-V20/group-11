package com.example.trailgage

import android.content.Context
import android.content.Intent
import android.net.nsd.NsdManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trailgage.LocalNetworkDeviceNameResolver.AddressResolutionListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    private var TAGS: String? = null
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
        TAGS = ""


        // setting the listner to the search button



        TAG?.setOnEditorActionListener(this)













        connetButton.setOnClickListener {
            val bIntent = Intent(this, Control::class.java)
            intent.putExtra("ipAdd",TAGS)
            startActivity(bIntent)
            Toast.makeText(this, "c", Toast.LENGTH_LONG).show()
           // openControll()

        }


    }

    private fun openControll() {

    }


    override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (p0 == TAG) {
            val trailName = TAG?.text?.trim().toString()
            if (trailName.isBlank() || trailName.isEmpty()) {
                TAG?.error = getString(R.string.name_cannot_be_empty)

            } else {

                imm?.hideSoftInputFromWindow(TAG?.windowToken, 0)
                println(TAG?.text?.trim().toString())
                TAGS = TAG?.text?.trim().toString()
                TAGS =getIpAdd(TAGS)
                println(TAGS+"  this is last")


            }
            true
        } else {
            false
        }

    }

    private fun getIpAdd(tags: String?): String {

        var ipAdd = ""

        // Don't use the same device name for multiple instances as they overwrite each other
        // Asynchronous device name resolution (suggested)
        mDeviceNameResolver = LocalNetworkDeviceNameResolver(this.applicationContext,
            TAGS, "_http._tcp.", 80,
            AddressResolutionListener { address ->

                Log.i(
                    TAGS,
                    "" + address.hostName
                )

            })
        ipAdd = mDeviceNameResolver!!.getAddress(10,TimeUnit.SECONDS).toString()
        println(mDeviceNameResolver!!.getAddress(10,TimeUnit.SECONDS).toString())

        return ipAdd

    }
}





