package com.booxapp.exchange.sellExchange

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.Constants
import com.booxapp.adapter.ExViewRequestsAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.ActivityViewRequestsBinding
import com.booxapp.model.UserModel
import com.google.firebase.database.*


class ExchangeViewRequests : AppCompatActivity() {
    private lateinit var binding: ActivityViewRequestsBinding
    lateinit var eDatabase: DatabaseReference
    lateinit var uDatabase: DatabaseReference

    var exViewReqAdapter: ExViewRequestsAdapter? = null

    private val TAG = "ExViewRequests"

    lateinit var uid: String
    lateinit var bId: String

    var buyerIds: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLayout.toobar)

        binding.toolbarLayout.toobar.overflowIcon?.setTint(Color.WHITE)

        supportActionBar?.setDisplayHomeAsUpEnabled(true) //For back btn
        supportActionBar?.setDisplayShowHomeEnabled(true) //Both lines for back btn

        eDatabase = FirebaseDatabase.getInstance().getReference(Constants.EX_DB_NAME)
        uDatabase = FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        var myDataListModel: ArrayList<UserModel> = ArrayList()

        val bundle = intent.extras
        bId = bundle!!.getString("exBookId").toString()

//        Log.e(TAG+"Bookid", bId)

        uid = Prefs.getStringPrefs(
                applicationContext,
                "userId"
        ).toString()

        addBuyersId()

        binding.viewRequestRecycler.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        exViewReqAdapter = ExViewRequestsAdapter(applicationContext, myDataListModel, bId)
        binding.viewRequestRecycler!!.adapter = exViewReqAdapter

        uDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
//                    child.key?.let { Log.i(TAG, it) }
                    for(subChild in buyerIds){
                        var myDataListModelInternal = child.getValue(UserModel::class.java)
                        if (myDataListModelInternal != null && myDataListModelInternal.userId!! == subChild) {
                            var name: String? = myDataListModelInternal.name!!
                            var loc: String? = myDataListModelInternal.loc!!
                            var contact: String? = myDataListModelInternal.phone!!
                            var buyerId: String? = myDataListModelInternal.id!!

//                            Log.i(TAG, name!!)


                            myDataListModel.add(
                                UserModel(
                                    name!!,
                                    loc!!,
                                    contact!!,
                                    buyerId
                                )
                            )
                            break
                        }
//                        else{
//                           Log.i(TAG, "Testing it is!")
//                        }
                    }
                }
                exViewReqAdapter!!.notifyDataSetChanged()
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
        eDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (child in dataSnapshot.children) {
                    if (child.child("id").value.toString() == bId ) {
                            buyerIds = child.child("requests").value as ArrayList<String>
                    }
                }
//                Log.e(TAG, buyerIds.toString())

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}