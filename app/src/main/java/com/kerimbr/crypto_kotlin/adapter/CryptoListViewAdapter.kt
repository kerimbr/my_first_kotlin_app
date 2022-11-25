package com.kerimbr.crypto_kotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kerimbr.crypto_kotlin.R
import com.kerimbr.crypto_kotlin.model.CryptoModel
import kotlinx.android.synthetic.main.row_layout.view.*

class CryptoListViewAdapter(private val cryptoList: ArrayList<CryptoModel>, val listener: Listener)
    : RecyclerView.Adapter<CryptoListViewAdapter.RowHolder>() {

    interface Listener {
        fun onClick(cryptoModel: CryptoModel)
    }

    class RowHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(cryptoModel: CryptoModel, listener: Listener) {
            itemView.setOnClickListener {
                listener.onClick(cryptoModel)
            }
            itemView.coinNameText.text = cryptoModel.currency
            itemView.coinPriceText.text = "$${cryptoModel.price}"
            itemView.circleAvatarText.text = cryptoModel.currency.substring(0,1).uppercase()
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return RowHolder(view)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position], listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }
}