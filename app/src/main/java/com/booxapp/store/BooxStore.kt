package com.booxapp.store

import android.R
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.BooxstoreAdapter
import com.booxapp.databinding.FragmentPurchaseBinding
import com.booxapp.model.BooxstoreModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class BooxStore : Fragment() {
    lateinit var mDatabase: DatabaseReference
    var adapter: BooxstoreAdapter? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var binding: FragmentPurchaseBinding
    lateinit var category: String

    private val TAG = "BooxStore"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false)
        binding.toolbarLayout.toolbar.visibility = View.VISIBLE


        var myDataListModel: ArrayList<BooxstoreModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference("booxstore")

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = BooxstoreAdapter(requireContext(), myDataListModel)
        binding.recyclerView!!.adapter = adapter

        var cartfab: FloatingActionButton = binding.cartfab
        cartfab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    context,
                    BooxCart::class.java
                )
            )
        })

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    var myDataListModelInternal = child.getValue(BooxstoreModel::class.java)
                    if (myDataListModelInternal != null) {
                        var id: String? = myDataListModelInternal.id
                        var title: String? = myDataListModelInternal.title
                        var mrp: String? = myDataListModelInternal.mrp
                        var offered_price: String? = myDataListModelInternal.offeredprice
                        var category: String? = myDataListModelInternal.category
                        var description: String? = myDataListModelInternal.description
                        var bookimage: String? = myDataListModelInternal.imagelink
                        var bAddress: String? = myDataListModelInternal.bAddress
                        var bContact: String? = myDataListModelInternal.bContact
                        var buyerId: String? = myDataListModelInternal.buyerId
                        var stockCount: String? = myDataListModelInternal.stockCount
                        var status: Boolean? = myDataListModelInternal.status

                        myDataListModel.add(
                            BooxstoreModel(
                                id,
                                title,
                                mrp,
                                offered_price,
                                category,
                                description,
                                bookimage,
                                bAddress,
                                bContact,
                                buyerId,
                                stockCount,
                                status
                            )
                        )
                    }
                }
                adapter!!.notifyDataSetChanged()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })
        return binding.root
    }

    //for back btn toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            // do something
        }
        return super.onOptionsItemSelected(item)
    }
}