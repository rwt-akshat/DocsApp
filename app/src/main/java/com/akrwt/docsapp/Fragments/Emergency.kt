package com.akrwt.docsapp.Fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akrwt.docsapp.*
import com.akrwt.docsapp.R
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_emergency.*

class Emergency : Fragment() {

    var mAdapter: AccidentAdapter? = null
    var mDatabaseRef: DatabaseReference? = null
    var mUploads: ArrayList<AccidentModel>? = null
    val mActivity = MainActivity()

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_emergency, container, false)
        FirebaseApp.initializeApp(context!!)

        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                mActivity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                9
            )
        }

        activity!!.title = "Emergency"

        loadView(view)
        return view
    }


    private fun loadView(view: View) {


        var mRecyclerView: RecyclerView = view.findViewById(R.id.rv_emergency)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)


        mUploads = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("accident")
        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mUploads!!.clear()
                var testArray: ArrayList<AccidentModel>? = null

                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(AccidentModel::class.java)
                    mUploads!!.add(upload!!)

                    testArray = mUploads

                }

                if (context != null) {
                    if (emergencyProgress != null && testArray != null) {
                        emergencyProgress.visibility = View.GONE
                        mAdapter = AccidentAdapter(context!!, mUploads!!)
                        mRecyclerView.adapter = mAdapter
                        mAdapter!!.notifyDataSetChanged()

                    } else {
                        emergencyProgress.visibility = View.GONE
                        txtNothing.visibility = View.VISIBLE
                    }
                }

            }
        })
    }
}

