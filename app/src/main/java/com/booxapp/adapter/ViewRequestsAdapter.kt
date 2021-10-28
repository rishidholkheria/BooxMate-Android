package com.booxapp.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.Constants
import com.booxapp.data.Prefs
import com.booxapp.databinding.PurchaseRequestLayoutBinding
import com.booxapp.model.UserModel
import com.booxapp.model.ViewRequestsModel
import com.booxapp.onCompleteFirebase
import com.google.firebase.database.*


class ViewRequestsAdapter(private val context: Context, val DataModel: ArrayList<UserModel>) :
    RecyclerView.Adapter<ViewRequestsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            PurchaseRequestLayoutBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewRequestsAdapter.ViewHolder, position: Int) {
        holder.bind(context, DataModel[position])
    }

    override fun getItemCount(): Int {
        return DataModel.size
    }

    inner class ViewHolder(private val binding: PurchaseRequestLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var bDatabase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference(Constants.DB_NAME)

        var uDatabase: DatabaseReference =
            FirebaseDatabase.getInstance().getReference(Constants.USER_DB_NAME)

        var bId : String? = null
        var uId : String? = Prefs.getStringPrefs(context, "userId")


        var item: UserModel? = null
        fun bind(context: Context, reqModel: UserModel) {
            binding.reqBuyerName.text = reqModel.name
            binding.reqBuyerLoc.text = reqModel.loc
            binding.reqBuyerPno.text = reqModel.phone

            bId = reqModel.id.toString()

            //makes the button non functional
//            checkIfSold(bId!!)


            binding.reqAcceptReq.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Working on it...", Toast.LENGTH_SHORT).show()

//                // set status to true
//                acceptRequest(bId!!, object : onCompleteFirebase {
//                            override fun onCallback(value: Boolean) {
//                                Toast.makeText(context, "Status set to true", Toast.LENGTH_LONG).show()
//                            }
//                        })
//
//                //save in sold books
//                saveInSoldBooks(object : onCompleteFirebase {
//                    override fun onCallback(value: Boolean) {
//                        Toast.makeText(context, "Saved in sold books", Toast.LENGTH_LONG).show()
//                    }
//                })

            })

        }
//
//        private fun checkIfSold(bId: String) {
//            bDatabase.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (child in dataSnapshot.children) {
//                        if (child.child("id").value.toString() == bId && child.child("status").value == true) {
//                            binding.reqAcceptReq.isClickable = false
//                            break;
//                        }
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//            })
//        }
//
//        private fun acceptRequest(bId: String, onCompleteListener: onCompleteFirebase) {
//            bDatabase?.child(bId)?.child("status")
//                ?.addListenerForSingleValueEvent(
//                    object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            bDatabase!!.child(bId!!).child("status").setValue(
//                                    true,
//                                    DatabaseReference.CompletionListener { error, ref ->
//                                        if (error == null) {
//                                            onCompleteListener.onCallback(true)
//
//                                        } else {
//                                            Log.e(
//                                                TAG,
//                                                "Remove of " + ref + " failed: " + error.message
//                                            )
//                                            onCompleteListener.onCallback(false)
//                                        }
//                                    })
//
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//                    })
//        }
//
//        private fun saveInSoldBooks(onCompleteListener: onCompleteFirebase){
//            uDatabase?.child(uId!!)?.child("soldBooks")
//                ?.addListenerForSingleValueEvent(
//                    object : ValueEventListener {
//                        override fun onDataChange(dataSnapshot: DataSnapshot) {
//                            uDatabase!!.child(uId!!).child("soldBooks").setValue(
//                                bId,
//                                DatabaseReference.CompletionListener { error, ref ->
//                                    if (error == null) {
//                                        onCompleteListener.onCallback(true)
//
//                                    } else {
//                                        Log.e(
//                                            TAG,
//                                            "Remove of " + ref + " failed: " + error.message
//                                        )
//                                        onCompleteListener.onCallback(false)
//                                    }
//                                })
//
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//                    })
//        }
    }
}
