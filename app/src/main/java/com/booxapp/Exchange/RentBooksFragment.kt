package com.booxapp

import android.app.ProgressDialog
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
import com.booxapp.adapter.RentBooksAdapter
import com.booxapp.model.BookModel
import com.booxapp.model.RentBookModel
import com.google.firebase.database.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class RentBooksFragment : Fragment() {
    private val rentdata = ArrayList<RentBookModel?>()
    private var rpLayoutManager: LinearLayoutManager? = null
    private var rprecycler: RecyclerView? = null
    private var rentpurchaseadapter: RentBooksAdapter? = null
    private var rfilterall: Button? = null
    private var rfilternov: Button? = null
    private var rfilteredu: Button? = null
    private var rfilternotes: Button? = null
    private var rentdatabase: FirebaseDatabase? = null
    private var rentbooksRef: DatabaseReference? = null
    private var filterType = "All"
    private var progressDialog: ProgressDialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rent_books, container, false)
        rentdatabase = FirebaseDatabase.getInstance()
        rentbooksRef = FirebaseDatabase.getInstance().getReference("rentbooks")
        progressDialog = ProgressDialog(view.context)
        rfilterall = view.findViewById<View>(R.id.rentfilterall) as Button
        rfilternov = view.findViewById<View>(R.id.rentfilternovels) as Button
        rfilteredu = view.findViewById<View>(R.id.rentfiltereducation) as Button
        rfilternotes = view.findViewById<View>(R.id.rentfilternotes) as Button
        rprecycler = view.findViewById<View>(R.id.rent_purchase_recycler) as RecyclerView
        rpLayoutManager = LinearLayoutManager(this.activity)
        Log.d("debugMode", "The application stopped after this")
        rprecycler!!.layoutManager = rpLayoutManager
        applyFilter("All")
        rentpurchaseadapter = RentBooksAdapter(requireContext(), rentdata)
        rprecycler!!.adapter = rentpurchaseadapter
        rfilterall!!.setBackgroundResource(R.drawable.cat_selectedbtn)
        rfilterall!!.setTextColor(Color.parseColor("#ffffff"))
        rfilterall!!.setOnClickListener {
            rfilterall!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            rfilternotes!!.setBackgroundResource(R.drawable.category_btn)
            rfilteredu!!.setBackgroundResource(R.drawable.category_btn)
            rfilternov!!.setBackgroundResource(R.drawable.category_btn)
            rfilterall!!.setTextColor(Color.parseColor("#ffffff"))
            rfilternov!!.setTextColor(Color.parseColor("#000000"))
            rfilteredu!!.setTextColor(Color.parseColor("#000000"))
            rfilternotes!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "All") {
                filterType = "All"
                applyFilter(filterType)
            }
        }
        rfilternov!!.setOnClickListener {
            rfilternov!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            rfilternotes!!.setBackgroundResource(R.drawable.category_btn)
            rfilteredu!!.setBackgroundResource(R.drawable.category_btn)
            rfilterall!!.setBackgroundResource(R.drawable.category_btn)
            rfilternov!!.setTextColor(Color.parseColor("#ffffff"))
            rfilterall!!.setTextColor(Color.parseColor("#000000"))
            rfilteredu!!.setTextColor(Color.parseColor("#000000"))
            rfilternotes!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "Novel") {
                filterType = "Novel"
                applyFilter(filterType)
            }
        }
        rfilteredu!!.setOnClickListener {
            rfilteredu!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            rfilternotes!!.setBackgroundResource(R.drawable.category_btn)
            rfilterall!!.setBackgroundResource(R.drawable.category_btn)
            rfilternov!!.setBackgroundResource(R.drawable.category_btn)
            rfilteredu!!.setTextColor(Color.parseColor("#ffffff"))
            rfilternov!!.setTextColor(Color.parseColor("#000000"))
            rfilterall!!.setTextColor(Color.parseColor("#000000"))
            rfilternotes!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "Educational") {
                filterType = "Educational"
                applyFilter(filterType)
            }
        }
        rfilternotes!!.setOnClickListener {
            rfilternotes!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            rfilteredu!!.setBackgroundResource(R.drawable.category_btn)
            rfilterall!!.setBackgroundResource(R.drawable.category_btn)
            rfilternov!!.setBackgroundResource(R.drawable.category_btn)
            rfilternotes!!.setTextColor(Color.parseColor("#ffffff"))
            rfilternov!!.setTextColor(Color.parseColor("#000000"))
            rfilterall!!.setTextColor(Color.parseColor("#000000"))
            rfilterall!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "Notes") {
                filterType = "Notes"
                applyFilter(filterType)
            }
        }
        return view
    }

    private fun applyFilter(filterType: String) {
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        val upperdata = ArrayList<RentBookModel?>()
        if (filterType != "All") {
            rentbooksRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val books = snapshot.children
                    for (bookDetails in books) {
                        if (bookDetails.getValue(BookModel::class.java)!!.category == filterType) {
                            upperdata.add(bookDetails.getValue(RentBookModel::class.java))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Books", error.toString())
                }
            })
        } else {
            rentbooksRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val books = snapshot.children
                    for (bookDetails in books) {
                        upperdata.add(bookDetails.getValue(RentBookModel::class.java))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Books", error.toString())
                }
            })
        }
        progressDialog!!.dismiss()
        rentpurchaseadapter = RentBooksAdapter(requireContext(), upperdata)
        rprecycler!!.adapter = rentpurchaseadapter
    }
}