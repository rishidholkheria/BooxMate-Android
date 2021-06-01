package com.attendit.booxapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.attendit.booxapp.adapter.SellAdapter
import com.attendit.booxapp.model.BookModel
import com.attendit.booxapp.model.SellModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class SellFragment : Fragment(), View.OnClickListener {

    lateinit var sellrecycle: RecyclerView
    lateinit var mDatabase: DatabaseReference

    var myLayoutManager: LinearLayoutManager? = null
    var mynewadapter: SellAdapter? = null

    private val TAG = "SellFragment"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sell, container, false)
        //udata = sell_data();

        sellrecycle = view.findViewById(R.id.sellrecycler)

        var myDataListModel: ArrayList<BookModel> = ArrayList()
        var cadapter = SellAdapter(requireContext(), myDataListModel)
        sellrecycle.adapter = cadapter

        mDatabase =
                FirebaseDatabase.getInstance().getReference("books")


        var sellfab: FloatingActionButton = view.findViewById(R.id.sellbtn)
        sellfab.setOnClickListener(View.OnClickListener { startActivity(Intent(context, SellDetails::class.java)) })

        sellrecycle.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        Log.d("debugMode", "The application stopped after this")
        mynewadapter = SellAdapter(requireContext(), myDataListModel)
        sellrecycle!!.adapter = mynewadapter


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
                mynewadapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        })

        return view

    }

    override fun onClick(v: View) {}
}