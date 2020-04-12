package com.akrwt.docsapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class AccidentAdapter(
    var mContext: Context,
    private var mUploads: ArrayList<AccidentModel>
) :
    RecyclerView.Adapter<AccidentAdapter.MyViewHolder>() {


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
            this.textViewName = itemView.findViewById(R.id.nameAC)
            this.textViewInjury = itemView.findViewById(R.id.nameIN)
            this.textViewSex = itemView.findViewById(R.id.nameSX)
            this.textViewPhnNumber = itemView.findViewById(R.id.NamePH)
            this.textViewBloodGrp = itemView.findViewById(R.id.NameBG)
            this.resBtn = itemView.findViewById(R.id.resBtn)
            this.eventImage = itemView.findViewById(R.id.eventImage)
            this.progressbar = itemView.findViewById(R.id.imgLoadingProgress)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccidentAdapter.MyViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.accident_ticket, parent, false)


        return AccidentAdapter.MyViewHolder(v)

    }

    override fun getItemCount(): Int {
        return mUploads.size

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val uploadCurrent = mUploads.get(position)

        val UserName = uploadCurrent.getName()
        val UserInjury = uploadCurrent.getInj()
        val UserPhone = uploadCurrent.getphnNumber()
        val UserBG = uploadCurrent.getBG()
        val UserSex = uploadCurrent.getSex()

        holder.textViewName!!.text = "Patient's Name: " + UserName
        holder.textViewInjury!!.text = "Injury: " + UserInjury
        holder.textViewBloodGrp!!.text = "Blood Group: " + UserBG
        holder.textViewPhnNumber!!.text = "Phone Number: " + UserPhone
        holder.textViewSex!!.text = "Sex: " + UserSex


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


}
