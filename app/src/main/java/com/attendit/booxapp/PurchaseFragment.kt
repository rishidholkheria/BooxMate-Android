package com.attendit.booxapp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.attendit.booxapp.adapter.MyAdapter
import com.attendit.booxapp.adapter.SellAdapter
import com.attendit.booxapp.databinding.FragmentPurchaseBinding
import com.attendit.booxapp.databinding.MainActivityBinding
import com.attendit.booxapp.model.BookModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PurchaseFragment : Fragment() {
    private var purchaseAdapter: MyAdapter? = null
    lateinit var mDatabase: DatabaseReference
    var adapter: MyAdapter? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var binding: FragmentPurchaseBinding

    private val TAG = "PurchaseFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        var myDataListModel: ArrayList<BookModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference("books")

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = MyAdapter(requireContext(), myDataListModel)
        binding.recyclerView!!.adapter = adapter

        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children){
                    child.key?.let { Log.i(TAG, it) }
                    var myDataListModelInternal = child.getValue(BookModel::class.java)
                    if (myDataListModelInternal != null) {
                        var title : String? = myDataListModelInternal.title
                        var offered_price: String? = myDataListModelInternal.offeredprice
                        myDataListModel.add(BookModel(title, offered_price, ""))
                    }
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })


        progressDialog = ProgressDialog(requireContext())
        progressDialog!!.setMessage("Please wait...")


        return binding.root



    }
}