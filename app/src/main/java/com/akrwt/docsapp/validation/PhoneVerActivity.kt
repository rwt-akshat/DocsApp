package com.akrwt.docsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_phone_ver.*

class PhoneVerActivity : AppCompatActivity() {

    private var editTextMobile: EditText? = null
    private lateinit var auth: FirebaseAuth


    public override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI()
        }

    }

    private fun updateUI() {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_ver)


        editTextMobile = findViewById(R.id.editTextMobile)

        buttonContinue.setOnClickListener {

            val mobile = editTextMobile!!.text.toString()

            if (mobile.isEmpty() || mobile.length < 10) {
                editTextMobile!!.error = "Enter a valid mobile number"
            } else {

                val intent = Intent(applicationContext, OtpVerActivity::class.java)
                intent.putExtra("mobile", mobile)
                startActivity(intent)
                finish()
            }
        }
    }
}





