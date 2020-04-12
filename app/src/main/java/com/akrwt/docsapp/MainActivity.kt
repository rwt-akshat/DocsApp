package com.akrwt.docsapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.telephony.SmsManager
import android.view.KeyEvent
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.akrwt.docsapp.Fragments.Ambulance
import com.akrwt.docsapp.Fragments.Emergency
import com.akrwt.docsapp.Fragments.Response
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var TEXT = "text"
    private var USER_IMAGE = "userImage"
    private var SHARED_PREFS = "sharedPrefs"
    var userName: String? = null
    var img: String? = null
    var phn: String? = null
    private var PHONE = "phone"


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    DashBoardFragment()
                ).commit()
            }
            R.id.nav_emergency ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    Emergency()
                ).commit()
            R.id.nav_respond ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    Response()
                ).commit()
            R.id.nav_ambulance ->
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    Ambulance()
                ).commit()


            R.id.nav_share ->
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Arogya")
                    var shareMessage = "\nLet me recommend you this application\n\n"
                    shareMessage =
                        shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (ex: Exception) {
                    ex.toString()
                }


            R.id.nav_sign_out -> {
                val pref = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
                val gSh = getSharedPreferences("resolved",Context.MODE_PRIVATE)
                gSh.edit().clear().apply()
                pref.edit().clear().apply()
                Toast.makeText(applicationContext, "Signed Out", Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                intent = Intent(applicationContext, PhoneVerActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    private var drawer: DrawerLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.SEND_SMS
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    123
                )
            }
        }


        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.overflowIcon!!.setColorFilter(
            ContextCompat.getColor(this, R.color.black),
            PorterDuff.Mode.SRC_ATOP
        )

        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue))

        setSupportActionBar(toolbar)


        val sharedRef = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

        userName = sharedRef!!.getString(TEXT, "DEFAULT")
        img = sharedRef.getString(USER_IMAGE, "DEFAULT")
        phn = sharedRef.getString(PHONE, "DEFAULT")


        drawer = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                DashBoardFragment()
            ).commit()

            navigationView.setCheckedItem(R.id.nav_dashboard)
        }

        var headerView = navigationView.getHeaderView(0)
        var usrName = headerView.findViewById<TextView>(R.id.NameIni)
        var phSub = headerView.findViewById<TextView>(R.id.PhoneNum)

        usrName.setText(userName)
        phSub.setText(phn)


    }

    override fun onBackPressed() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setCheckedItem(R.id.nav_dashboard)

        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)

        } else {

            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                DashBoardFragment()
            ).commit()

            val frags = supportFragmentManager.fragments
            if (frags != null) {
                for (fragment in frags) {
                    if (fragment != null && fragment.isVisible)
                        if (fragment.toString().substring(0, 17) == "DashBoardFragment") {
                            finish()
                        }
                }

            }
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {

        var action = event!!.action
        var keyCode = event.keyCode


        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (action == KeyEvent.ACTION_DOWN) {


                    var smsManager = SmsManager.getDefault()
                    smsManager.sendTextMessage(
                        "7275999997",
                        null,
                        "Emergency:Patient Details:\nName: Akshat Rawat\nSex: Male\nBG: ab+\nPhone: 7275999997",
                        null,
                        null
                    )

                    Toast.makeText(
                        applicationContext,
                        "Details sent..",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return true

            }
        }
        return super.dispatchKeyEvent(event)
    }
}



