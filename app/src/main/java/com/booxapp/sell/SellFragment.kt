package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.SellAdapter
import com.booxapp.databinding.FragmentSellBinding
import com.booxapp.model.BookModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

class SellFragment : Fragment() {

    lateinit var mDatabase: DatabaseReference
    var selladapter: SellAdapter? = null
    lateinit var binding: FragmentSellBinding

    private val TAG = "SellFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSellBinding.inflate(inflater, container, false)

        var myDataListModel: ArrayList<BookModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference("books")


        var sellfab: FloatingActionButton = binding.sellbtn
        sellfab.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    context,
                    SellDetails::class.java
                )
            )
        })

        binding.sellrecycler.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        selladapter = SellAdapter(requireContext(), myDataListModel)
        binding.sellrecycler!!.adapter = selladapter

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    var myDataListModelInternal = child.getValue(BookModel::class.java)
                    if (myDataListModelInternal != null) {
                        var title: String? = myDataListModelInternal.title
                        var offered_price: String? = myDataListModelInternal.offeredprice
                        var mrp: String? = myDataListModelInternal.mrp
                        var location: String? = myDataListModelInternal.location
                        var category: String? = myDataListModelInternal.mrp
                        var description: String? = myDataListModelInternal.description
                        var sellerName: String? = myDataListModelInternal.seller_name
                        var sellerEmail: String? = myDataListModelInternal.seller_email
                        var bookimage: String? = myDataListModelInternal.imagelink

                        myDataListModel.add(
                            BookModel(
                                title,
                                location,
                                mrp,
                                "₹ " + offered_price,
                                "id",
                                category,
                                true,
                                description,
                                sellerName,
                                sellerEmail,
                                bookimage
                            )
                        )
                    }
                }
                selladapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        return binding.root
    }

}