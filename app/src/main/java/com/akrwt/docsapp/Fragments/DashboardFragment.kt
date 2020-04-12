package com.akrwt.docsapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashBoardFragment : Fragment() {

    var mAdapter: DoctorAdapter? = null
    var mDatabaseRef: DatabaseReference? = null
    var mUploads: MutableList<DoctorModel>? = null

    @Nullable
    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        FirebaseApp.initializeApp(context!!)

        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                MainActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                9
            )
        }

        activity!!.title = "Dashboard"

        loadView(view)
        return view
    }


    private fun loadView(view: View) {


        var mRecyclerView: RecyclerView = view.findViewById(R.id.rv_assignments)
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)


        mUploads = ArrayList()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("patients")
        mDatabaseRef!!.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, p0.message, Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                mUploads!!.clear()
                var testArray: MutableList<DoctorModel>? = null
                val sharedPref = context!!.getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

                val doc = sharedPref.getString("doc", "default")

                for (postSnapshot in dataSnapshot.children) {
                    for (pS in postSnapshot.children) {
                        if (doc == postSnapshot.key.toString()) {
                            val upload = pS.getValue(DoctorModel::class.java)
                            mUploads!!.add(upload!!)
                            testArray = mUploads!!
                        }
                    }

                }

                if (context != null) {
                    if (progressB != null && testArray != null) {
                        progressB.visibility = View.GONE
                        mUploads!!.reverse()
                        mAdapter = DoctorAdapter(context!!, mUploads!!)
                        mRecyclerView.adapter = mAdapter
                        mAdapter!!.notifyDataSetChanged()

                    } else {
                        progressB.visibility = View.GONE
                        textViewNothing.visibility = View.VISIBLE
                    }
                }
            }
        })
    }
}

