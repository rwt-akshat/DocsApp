package com.akrwt.docsapp.Fragments

import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.akrwt.docsapp.MainActivity
import com.akrwt.docsapp.MySingleton
import com.akrwt.docsapp.R
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.fragment_response.*
import kotlinx.android.synthetic.main.fragment_response.view.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class Response : Fragment() {

    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey =
        "key=" + "AAAAZu9fBbE:APA91bHH1ejeiLczM49J4PmPI6eM5suCkgiJa3scqSsd0sXVV78zoIxBvmtAv_2qlzlgF425JbYZ4uRXxKf8XgZ2A7cTKNEWDnYxZ3409-PWUjofF5fhGhQsqOfHkP4XTQjPCcm_70dr"
    private val contentType = "application/json"

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_response, container, false)
        var smsManager = SmsManager.getDefault()

        activity!!.title = "Respond"


        view.available.setOnClickListener {
            val notification = JSONObject()
            val notificationBody = JSONObject()
            try {
                notificationBody.put("title", "Available")
                notificationBody.put("message", "Available")
                notification.put("to", "/topics/all")
                notification.put("data", notificationBody)

            } catch (e: JSONException) {
                Log.e("TAG", "onCreate: " + e.message)
            }
            sendNotification(notification)

            Toast.makeText(context, "Available", Toast.LENGTH_LONG)
                .show()

        }
        view.notAvailable.setOnClickListener {


            smsManager.sendTextMessage(
                "7088261815",
                null,
                "Resources not available,your data has been shared to the next hospital.",
                null,
                null
            )
            smsManager.sendTextMessage(
                "7088261815",
                null,
                "Please head towards this location.Location: https://maps.google.com/maps?q=loc:30.4006941,78.077988",
                null,
                null
            )

        }


        return view
    }

    private fun sendNotification(notification: JSONObject) {

        val tag = MainActivity::class.java.simpleName

        val jsonObjectRequest = object : JsonObjectRequest(
            Method.POST, FCM_API, notification,
            Response.Listener { response ->
                try {
                    Log.i("Response", "onResponse: $response")

                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }
            ,
            Response.ErrorListener { error ->
                error.printStackTrace()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = serverKey
                headers["Content-Type"] = contentType
                return headers
            }
        }
        MySingleton.instance?.addToRequestQueue(jsonObjectRequest, tag)


    }


}
