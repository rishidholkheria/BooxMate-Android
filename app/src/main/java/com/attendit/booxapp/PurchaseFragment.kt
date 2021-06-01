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
import com.attendit.booxapp.model.BookModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PurchaseFragment : Fragment() {
    private var recycle: RecyclerView? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var newadapter: MyAdapter? = null
    private var database: FirebaseDatabase? = null
    private var booksRef: DatabaseReference? = null
    private var filterall: Button? = null
    private var filternov: Button? = null
    private var filteredu: Button? = null
    private var filternotes: Button? = null
    private val check = false
    private var filterType = "All"
    private var progressDialog: ProgressDialog? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_purchase, container, false)

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        database = FirebaseDatabase.getInstance()
        booksRef = FirebaseDatabase.getInstance().getReference("books")
        progressDialog = ProgressDialog(view.context)
        progressDialog!!.setMessage("Please wait...")
        filterall = view.findViewById<View>(R.id.filterall) as Button
        filternov = view.findViewById<View>(R.id.filternovels) as Button
        filteredu = view.findViewById<View>(R.id.filtereducation) as Button
        filternotes = view.findViewById<View>(R.id.filternotes) as Button
        recycle = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        mLayoutManager = LinearLayoutManager(this.activity)
        Log.d("debugMode", "The application stopped after this")
        recycle!!.layoutManager = mLayoutManager
        //fill_data();
        applyFilter("All")
        var cartfab: FloatingActionButton = view.findViewById(R.id.cartfab)
        cartfab.setOnClickListener(View.OnClickListener { startActivity(Intent(context, PurchaseCart::class.java)) })
        filterall!!.setBackgroundResource(R.drawable.cat_selectedbtn)
        filterall!!.setTextColor(Color.parseColor("#ffffff"))
        filterall!!.setOnClickListener {
            filterall!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            filternotes!!.setBackgroundResource(R.drawable.category_btn)
            filteredu!!.setBackgroundResource(R.drawable.category_btn)
            filternov!!.setBackgroundResource(R.drawable.category_btn)
            filterall!!.setTextColor(Color.parseColor("#ffffff"))
            filternov!!.setTextColor(Color.parseColor("#000000"))
            filteredu!!.setTextColor(Color.parseColor("#000000"))
            filternotes!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "All") {
                filterType = "All"
                applyFilter(filterType)
            }
        }
        filternov!!.setOnClickListener {
            filternov!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            filternotes!!.setBackgroundResource(R.drawable.category_btn)
            filteredu!!.setBackgroundResource(R.drawable.category_btn)
            filterall!!.setBackgroundResource(R.drawable.category_btn)
            filternov!!.setTextColor(Color.parseColor("#ffffff"))
            filterall!!.setTextColor(Color.parseColor("#000000"))
            filteredu!!.setTextColor(Color.parseColor("#000000"))
            filternotes!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "Novel") {
                filterType = "Novel"
                applyFilter(filterType)
            }
        }
        filteredu!!.setOnClickListener {
            filteredu!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            filternotes!!.setBackgroundResource(R.drawable.category_btn)
            filterall!!.setBackgroundResource(R.drawable.category_btn)
            filternov!!.setBackgroundResource(R.drawable.category_btn)
            filteredu!!.setTextColor(Color.parseColor("#ffffff"))
            filternov!!.setTextColor(Color.parseColor("#000000"))
            filterall!!.setTextColor(Color.parseColor("#000000"))
            filternotes!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "Educational") {
                filterType = "Educational"
                applyFilter(filterType)
            }
        }
        filternotes!!.setOnClickListener {
            filternotes!!.setBackgroundResource(R.drawable.cat_selectedbtn)
            filteredu!!.setBackgroundResource(R.drawable.category_btn)
            filterall!!.setBackgroundResource(R.drawable.category_btn)
            filternov!!.setBackgroundResource(R.drawable.category_btn)
            filternotes!!.setTextColor(Color.parseColor("#ffffff"))
            filternov!!.setTextColor(Color.parseColor("#000000"))
            filterall!!.setTextColor(Color.parseColor("#000000"))
            filterall!!.setTextColor(Color.parseColor("#000000"))
            if (filterType != "Notes") {
                filterType = "Notes"
                applyFilter(filterType)
            }
        }
        return view
    }

    private fun applyFilter(filterType: String) {
        progressDialog!!.show()
        val upperdata = ArrayList<BookModel?>()
        if (filterType != "All") {
            booksRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val books = snapshot.children
                    for (bookDetails in books) {
                        if (bookDetails.getValue(BookModel::class.java)!!.category == filterType) {
                            upperdata.add(bookDetails.getValue(BookModel::class.java))
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Books", error.toString())
                }
            })
        } else {
            booksRef!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val books = snapshot.children
                    for (bookDetails in books) {
                        upperdata.add(bookDetails.getValue(BookModel::class.java))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Books", error.toString())
                }
            })
        }
        progressDialog!!.dismiss()
        newadapter = MyAdapter(requireContext(), upperdata)
        recycle!!.adapter = newadapter
    }
}