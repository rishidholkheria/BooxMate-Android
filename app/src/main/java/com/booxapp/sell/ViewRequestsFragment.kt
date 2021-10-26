//package com.booxapp
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.booxapp.Constants
//import com.booxapp.R
//import com.booxapp.adapter.SellAdapter
//import com.booxapp.adapter.ViewRequestsAdapter
//import com.booxapp.data.Prefs
//import com.booxapp.databinding.FragmentSellBinding
//import com.booxapp.databinding.FragmentViewRequestsBinding
//import com.booxapp.model.BookModel
//import com.booxapp.model.UserModel
//import com.booxapp.model.ViewRequestsModel
//import com.google.firebase.database.*
//import java.util.ArrayList
//
//class ViewRequestsFragment : Fragment() {
//
//    lateinit var binding: FragmentViewRequestsBinding
//    lateinit var bDatabase: DatabaseReference
//    var viewReqAdapter: ViewRequestsAdapter? = null
//    var buyersList: ArrayList<String> = ArrayList()
//
//    lateinit var uid: String
//
//    private val TAG = "View Requests"
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//
//        var myDataListModel: ArrayList<UserModel> = ArrayList()
//
//        bDatabase = FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)
//        addToBuyersList()
//
//
////        binding.viewReqRecycler.layoutManager =
////            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//////        viewReqAdapter = ViewRequestsAdapter(requireContext(), myDataListModel)
////        binding.viewReqRecycler!!.adapter = viewReqAdapter
//////        addToBuyersList()
////
////        uid = Prefs.getStringPrefs(
////            requireContext(),
////            "userId"
////        ).toString()
////
////        addToBuyersList()
////
////        bDatabase.addValueEventListener(object : ValueEventListener {
////            override fun onDataChange(snapshot: DataSnapshot) {
////                myDataListModel.clear()
////                for (child in snapshot.children) {
////                    child.key?.let { Log.i(TAG, it) }
////                    var myDataListModelInternal = child.getValue(BookModel::class.java)
////                    if (myDataListModelInternal != null) {
//////                        var name: String? = myDataListModelInternal.name
//////                        var loc: String? = myDataListModelInternal.loc
//////                        var contact: String? = myDataListModelInternal.phone
////
//////                        myDataListModel.add(
//////                            UserModel(
//////                                name!!,
//////                                loc!!,
//////                                contact!!,
//////                            )
//////                        )
////                    }
////                }
////                viewReqAdapter!!.notifyDataSetChanged()
////            }
////
////            override fun onCancelled(databaseError: DatabaseError) {
////                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
////            }
////        })
//
//        return binding.root
//
//    }
//
//    private fun addToBuyersList() {
//        bDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (child in dataSnapshot.children) {
//                    if (child.child("userId").value.toString() == uid) {
//                        var buyerIds = child.child("requests").children
//                        Log.i(TAG, buyerIds.toString())
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//    }
//
//}