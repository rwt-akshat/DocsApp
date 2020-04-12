package com.akrwt.docsapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import kotlin.collections.ArrayList


class DoctorAdapter(
    var mContext: Context,
    private var mUploads: MutableList<DoctorModel>
) :
    RecyclerView.Adapter<DoctorAdapter.MyViewHolder>() {

    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey =
        "key=" + "AAAAZu9fBbE:APA91bHH1ejeiLczM49J4PmPI6eM5suCkgiJa3scqSsd0sXVV78zoIxBvmtAv_2qlzlgF425JbYZ4uRXxKf8XgZ2A7cTKNEWDnYxZ3409-PWUjofF5fhGhQsqOfHkP4XTQjPCcm_70dr"
    private val contentType = "application/json"


    var url: String? = null
    var UserName: String? = null
    var UserInjury: String? = null
    var UserSex: String? = null
    var UserPhone: String? = null
    var UserBG: String? = null


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var textViewName: TextView? = null
        internal var textViewInjury: TextView? = null
        internal var textViewSex: TextView? = null
        internal var textViewPhnNumber: TextView? = null
        internal var textViewBloodGrp: TextView? = null
        internal var resBtn: Button? = null
        internal var eventImage: ImageView? = null
        internal var progressbar: ProgressBar? = null


        init {
            this.textViewName = itemView.findViewById(R.id.tvName)
            this.textViewInjury = itemView.findViewById(R.id.tvInjury)
            this.textViewSex = itemView.findViewById(R.id.tvSex)
            this.textViewPhnNumber = itemView.findViewById(R.id.tvPhone)
            this.textViewBloodGrp = itemView.findViewById(R.id.tvBG)
            this.resBtn = itemView.findViewById(R.id.resBtn)
            this.eventImage = itemView.findViewById(R.id.eventImage)
            this.progressbar = itemView.findViewById(R.id.imgLoadingProgress)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.items, parent, false)


        return MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mUploads.size

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val uploadCurrent = mUploads.get(position)

        url = uploadCurrent.getUrl()
        UserName = uploadCurrent.getName()
        UserInjury = uploadCurrent.getInjury()
        UserPhone = uploadCurrent.getphnNumber()
        UserBG = uploadCurrent.getBG()
        UserSex = uploadCurrent.getSex()

        holder.textViewName!!.text = "Patient's Name: $UserName"
        holder.textViewInjury!!.text = "Injury: $UserInjury"
        holder.textViewBloodGrp!!.text = "Blood Group: $UserBG"
        holder.textViewPhnNumber!!.text = "Phone Number: $UserPhone"
        holder.textViewSex!!.text = "Sex: $UserSex"

        val gSh = mContext.getSharedPreferences("resolved",Context.MODE_PRIVATE)
        if(gSh.getString("status$position","def") == "Resolved"){
            holder.resBtn!!.text = "Resolved"
            holder.resBtn!!.isClickable = false
            holder.resBtn!!.isEnabled=false
        }

        holder.resBtn!!.setOnClickListener {
            val edittext=EditText(mContext)
            val alert = AlertDialog.Builder(mContext)
                .setTitle("Docs App")
                .setMessage("Please enter prescription: ")
                .setView(edittext)
                .setPositiveButton("SEND") { _: DialogInterface?, _: Int ->

                    var text=edittext.text.toString()

                    val notification = JSONObject()
                    val notificationBody = JSONObject()
                    try {
                        notificationBody.put("title", "Prescription Received")
                        notificationBody.put("message",text)
                        notification.put("to", "/topics/all")
                        notification.put("data", notificationBody)


                    } catch (e: JSONException) {
                        Log.e("TAG", "onCreate: " + e.message)
                    }
                    sendNotification(notification)

                    Toast.makeText(mContext, "Prescription sent successfully", Toast.LENGTH_LONG)
                        .show()
                    holder.resBtn!!.text = "Resolved"
                    val sharedpref = mContext.getSharedPreferences("resolved", Context.MODE_PRIVATE)
                    val editor = sharedpref.edit()
                    editor.putString("status$position","Resolved")
                    editor.apply()
                    holder.resBtn!!.isClickable = false

                }
                .setNegativeButton("CANCEL") { dialogInterface: DialogInterface?, _: Int ->
                    dialogInterface!!.cancel()
                }

            alert.create().show()

        }

        Picasso.get()
            .load(uploadCurrent.getUrl())
            .fit()
            .centerCrop()
            .into(holder.eventImage, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    holder.progressbar!!.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    Toast.makeText(mContext, e!!.message, Toast.LENGTH_LONG).show()
                }

            })
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