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
import com.booxapp.adapter.BuyExchangeAdapter
import com.booxapp.adapter.SellAdapter
import com.booxapp.data.Prefs
import com.booxapp.databinding.FragmentExBuyBinding
import com.booxapp.databinding.FragmentExchangeBinding
import com.booxapp.databinding.FragmentSellBinding
import com.booxapp.model.BookModel
import com.booxapp.model.ExchangeModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class ExBuyFragment : Fragment() {
    lateinit var mDatabase: DatabaseReference
    var exadapter: BuyExchangeAdapter? = null
    lateinit var binding: FragmentExBuyBinding

    private val TAG = "FragmentExBuyBinding"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExBuyBinding.inflate(inflater, container, false)

        var dataListModel: ArrayList<ExchangeModel> = ArrayList()
        mDatabase = FirebaseDatabase.getInstance().getReference("exchangeBooks")

        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        exadapter = BuyExchangeAdapter(requireContext(), dataListModel)
        binding.recyclerView!!.adapter = exadapter

        var uid = Prefs.getStringPrefs(
            requireContext(),
            "userId"
        )

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataListModel.clear()
                for (child in snapshot.children) {
                    child.key?.let { Log.i(TAG, it) }
                    var DataListModelInternal = child.getValue(ExchangeModel::class.java)
                    if (DataListModelInternal != null && DataListModelInternal.userId != uid) {
                        var title: String? = DataListModelInternal.title
                        var expectedBooks: String? = DataListModelInternal.expectedBooks
                        var location: String? = DataListModelInternal.location
                        var category: String? = DataListModelInternal.category
                        var description: String? = DataListModelInternal.description
                        var bookimage: String? = DataListModelInternal.imagelink
                        var exId : String? = DataListModelInternal.id

                        dataListModel.add(
                            ExchangeModel(
                                title,
                                location,
                                category,
                                expectedBooks,
                                description,
                                exId,
                                bookimage,
                                "",
                                false
                            )
                        )
                    }
                }
                exadapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", error.toException())
            }
        })

        return binding.root
    }

}