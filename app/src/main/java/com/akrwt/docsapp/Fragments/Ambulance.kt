package com.akrwt.docsapp.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.akrwt.docsapp.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.fragment_ambulance.*
import kotlinx.android.synthetic.main.fragment_ambulance.view.*

class Ambulance : Fragment() {

    private var storage: FirebaseStorage? = null
    private var database: FirebaseDatabase? = null

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ambulance, container, false)

        activity!!.title = "Ambulance"

        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
        view.btnUpload.setOnClickListener {

            if(editHospital.text.isEmpty() &&
            etnoOfAmbulance.text.isEmpty() &&
            editContact.text.isEmpty()){
                Toast.makeText(context,"Check:Maybe some fields are empty",Toast.LENGTH_LONG).show()
            }else {
                uploadFile()
            }
        }


        return view
    }

    private fun uploadFile() {

        var mUploadTask: StorageTask<UploadTask.TaskSnapshot>? = null

        val dialog = SpotsDialog.Builder()
            .setContext(context)
            .setTheme(R.style.Custom)
            .build()
            .apply {
                this.show()
            }

        val databaseReference = database!!.getReference("ambulance")


        val upload = AmbulanceModel(
            editHospital.text.toString(),
            etnoOfAmbulance.text.toString(),
            editContact.text.toString().trim()
        )

        val uploadId = databaseReference.push().key
        databaseReference.child(uploadId!!).setValue(upload)
        dialog.dismiss()

        Toast.makeText(
            context,
            "Sent Successfully",
            Toast.LENGTH_LONG
        )
            .show()
    }





}