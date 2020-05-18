package com.example.trailgage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.example.trailgage.backend.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TextView.OnEditorActionListener {

    private var editTextTrailname: EditText? = null
    private var imm: InputMethodManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize views
        editTextTrailname = findViewById(R.id.editTextTrailname)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // setting the listner to the search button

        editTextTrailname?.setOnEditorActionListener(this)






        Next.setOnClickListener {
            val bIntent = Intent(this, Control::class.java)
            startActivity(bIntent)
            Toast.makeText(this, "clicked", Toast.LENGTH_LONG).show()

        }


    }

    override fun onEditorAction(p0: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        return if (p0 == editTextTrailname) {
            val trailName = editTextTrailname?.text?.trim().toString()
            if (trailName.isBlank() || trailName.isEmpty()) {
                editTextTrailname?.error = getString(R.string.name_cannot_be_empty)

            } else {

                imm?.hideSoftInputFromWindow(editTextTrailname?.windowToken,0)

                // connect the trail to server
                getConnectToServer(trailName)
            }
            true
        } else {
            false
        }

    }

    private fun getConnectToServer(trailName: String) {

        RetrofitClient
            .instance
            .getspeedorAngel(trailName)

    }


}
