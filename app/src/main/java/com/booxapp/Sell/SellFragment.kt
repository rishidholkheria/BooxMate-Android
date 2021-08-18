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
import com.booxapp.model.SellModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

class SellFragment : Fragment(), View.OnClickListener {

    lateinit var mDatabase: DatabaseReference
    var mynewadapter: SellAdapter? = null
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
        mynewadapter = SellAdapter(requireContext(), myDataListModel)
        binding.sellrecycler!!.adapter = mynewadapter

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    var myDataListModelInternal = child.getValue(BookModel::class.java)
                    if (myDataListModelInternal != null) {
                        var title: String? = myDataListModelInternal.title
                        var offered_price: String? = myDataListModelInternal.offeredprice
                        myDataListModel.add(BookModel(title, offered_price, myDataListModelInternal.imagelink ))
                    }
                }
                mynewadapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        return binding.root
    }

    override fun onClick(v: View) {}
}