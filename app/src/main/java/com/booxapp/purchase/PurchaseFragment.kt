package com.booxapp

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.adapter.MyAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.FragmentPurchaseBinding
import com.booxapp.model.BookModel
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class PurchaseFragment : Fragment() {
    lateinit var mDatabase: DatabaseReference
    var adapter: MyAdapter? = null
    private var progressDialog: ProgressDialog? = null
    lateinit var binding: FragmentPurchaseBinding

    private val TAG = "PurchaseFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPurchaseBinding.inflate(inflater, container, false)

        var myDataListModel: ArrayList<BookModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference("books")

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter = MyAdapter(requireContext(), myDataListModel)
        binding.recyclerView!!.adapter = adapter

        var uid = Prefs.getStringPrefs(
            requireContext(),
            "userId"
        )

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myDataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    var myDataListModelInternal = child.getValue(BookModel::class.java)
                    if (myDataListModelInternal != null && myDataListModelInternal.userId != uid) {
                        var title: String? = myDataListModelInternal.title
                        var offered_price: String? = myDataListModelInternal.offeredprice
                        var mrp: String? = myDataListModelInternal.mrp
                        var id: String? = myDataListModelInternal.id
                        var location: String? = myDataListModelInternal.location
                        var category: String? = myDataListModelInternal.category
                        var description: String? = myDataListModelInternal.description
                        var bookimage: String? = myDataListModelInternal.imagelink
                        var userid: String? = myDataListModelInternal.userId

                        myDataListModel.add(
                            BookModel(
                                title,
                                location,
                                mrp,
                                id,
                                offered_price,
                                category,
                                true,
                                description,
                                bookimage,
                                userid
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


        progressDialog = ProgressDialog(requireContext())
        progressDialog!!.setMessage("Please wait...")


        return binding.root

    }
}