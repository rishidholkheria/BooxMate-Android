package com.booxapp.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.*
import com.booxapp.data.Prefs
import com.booxapp.databinding.PurchaseRequestLayoutBinding
import com.booxapp.model.BookModel
import com.booxapp.model.UserModel
import com.booxapp.model.ViewRequestsModel
import com.google.firebase.database.*


class ViewRequestsAdapter(
    private val context: Context,
    val DataModel: ArrayList<UserModel>,
    val bookId: String
) :
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

        var userKey: String? = Prefs.getStringPrefs(context, "Id")
        lateinit var buyerId: String
        lateinit var phone: String


        var item: UserModel? = null
        fun bind(context: Context, reqModel: UserModel) {
            binding.reqBuyerName.text = reqModel.name
            binding.reqBuyerLoc.text = reqModel.loc
            binding.reqBuyerPno.text = reqModel.phone

            phone = reqModel.phone.toString()

            buyerId = reqModel.id.toString()

            binding.reqBuyerCall.setOnClickListener{
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:$phone")
                callIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(callIntent)
            }

            binding.reqAcceptReq.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Working on it...", Toast.LENGTH_SHORT).show()

                // set status to true
                acceptRequest(bookId!!, object : onCompleteFirebase {
                    override fun onCallback(value: Boolean) {
                        Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show()
                    }
                })

                //save in sold books
                saveInSoldBooks(object : onCompleteFirebase {
                    override fun onCallback(value: Boolean) {
                        Toast.makeText(context, "Saved in sold books", Toast.LENGTH_LONG).show()
                    }
                })

                //save in purchased books
                saveInPurchasedBooks(buyerId, object : onCompleteFirebase {
                    override fun onCallback(value: Boolean) {
                        Toast.makeText(context, "Saved in purchased books", Toast.LENGTH_LONG)
                            .show()
                    }
                })

            })

        }


        private fun acceptRequest(bId: String, onCompleteListener: onCompleteFirebase) {

            bDatabase?.child(bId)?.child("status")
                ?.addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            bDatabase!!.child(bId!!).child("status").setValue(
                                true,
                                DatabaseReference.CompletionListener { error, ref ->
                                    if (error == null) {
                                        onCompleteListener.onCallback(true)
                                    } else {
                                        Log.e(
                                            TAG,
                                            "Remove of " + ref + " failed: " + error.message
                                        )
                                        onCompleteListener.onCallback(false)
                                    }
                                })

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })

            bDatabase.child(bId).child("soldTo").setValue(buyerId)

        }

        private fun saveInSoldBooks(onCompleteListener: onCompleteFirebase) {
            uDatabase?.child(userKey!!)?.child("soldBooks")
                ?.addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            uDatabase!!.child(userKey!!).child("soldBooks")
                                .child("${dataSnapshot.childrenCount}").setValue(
                                    bookId,
                                    DatabaseReference.CompletionListener { error, ref ->
                                        if (error == null) {
                                            onCompleteListener.onCallback(true)
                                        } else {
                                            Log.e(
                                                TAG,
                                                "Remove of " + ref + " failed: " + error.message
                                            )
                                            onCompleteListener.onCallback(false)
                                        }
                                    })

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
        }

        private fun saveInPurchasedBooks(buyerKey: String, onCompleteListener: onCompleteFirebase) {
            uDatabase?.child(buyerKey!!)?.child("purchasedBooks")
                ?.addListenerForSingleValueEvent(
                    object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            uDatabase!!.child(buyerKey!!).child("purchasedBooks")
                                .child("${dataSnapshot.childrenCount}").setValue(
                                    bookId,
                                    DatabaseReference.CompletionListener { error, ref ->
                                        if (error == null) {
                                            onCompleteListener.onCallback(true)
                                        } else {
                                            Log.e(
                                                TAG,
                                                "Remove of " + ref + " failed: " + error.message
                                            )
                                            onCompleteListener.onCallback(false)
                                        }
                                    })

                            pageChange()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
        }

        private fun pageChange() {
            val i = Intent(context, OrderSuccesfull::class.java)
            context.startActivity(i)
        }

    }
}
