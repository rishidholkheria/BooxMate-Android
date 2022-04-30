package com.booxapp.sell

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.Constants
import com.booxapp.adapter.ViewRequestsAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityViewRequestsBinding
import com.booxapp.model.UserModel
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class ViewRequests : AppCompatActivity() {
    private lateinit var binding: ActivityViewRequestsBinding
    lateinit var bDatabase: DatabaseReference
    lateinit var uDatabase: DatabaseReference

    var viewReqAdapter: ViewRequestsAdapter? = null

    private val TAG = "ViewRequests"

    lateinit var uid: String
    lateinit var bId: String

    var buyerIds: ArrayList<String> = ArrayList()
    var myDataListModel: ArrayList<UserModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toolbar)

        binding.toolbarLayout.toolbar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        bDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)


        val bundle = intent.extras
        bId = bundle!!.getString("bookid").toString()

        Log.e(TAG, bId)

        uid = Prefs.getStringPrefs(
            applicationContext,
            "userId"
        ).toString()

        addBuyersId()

        binding.viewRequestRecycler.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        viewReqAdapter = ViewRequestsAdapter(applicationContext, myDataListModel, bId)
        binding.viewRequestRecycler!!.adapter = viewReqAdapter

        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    for(subChild in buyerIds){
                        var myDataListModelInternal = child.getValue(UserModel::class.java)
                        if (myDataListModelInternal != null && myDataListModelInternal.userId == subChild) {
                            var name: String? = myDataListModelInternal.name
                            var loc: String? = myDataListModelInternal.loc
                            var contact: String? = myDataListModelInternal.phone
                            var buyerId: String? = myDataListModelInternal.id

                            Log.i(TAG, name!!)

                            myDataListModel.add(
                                UserModel(
                                    name,
                                    loc,
                                    contact,
                                    buyerId
                                )
                            )

                        }
                        else{
                            Log.i(TAG, "Testing it is!")
                        }
                    }
                }
                viewReqAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

    }

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addBuyersId() {
        bDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                lateinit var ids: String
                for (child in dataSnapshot.children) {
                    if (child.child("id").value.toString() == bId ) {
                            buyerIds = child.child("requests").value as ArrayList<String>
                    }
                }
                Log.e(TAG, buyerIds.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}