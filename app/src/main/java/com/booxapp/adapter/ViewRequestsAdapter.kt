package com.booxapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.booxapp.databinding.PurchaseRequestLayoutBinding
import com.booxapp.model.UserModel
import com.booxapp.model.ViewRequestsModel


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

        var item: UserModel? = null
        fun bind(context: Context, reqModel: UserModel) {
            binding.reqBuyerName.text = reqModel.name
            binding.reqBuyerLoc.text = reqModel.loc
            binding.reqBuyerPno.text = reqModel.phone

            binding.reqAcceptReq.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "Accepting...", Toast.LENGTH_SHORT).show()
            })

        }
    }
}