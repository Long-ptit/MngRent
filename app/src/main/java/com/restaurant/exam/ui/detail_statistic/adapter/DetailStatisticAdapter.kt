package com.restaurant.exam.ui.detail_statistic.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.data.model.Bill
import com.restaurant.exam.data.model.TableFirebase
import restaurant.exam.databinding.ItemStatisticBillBinding
import restaurant.exam.databinding.ItemTableBinding

class DetailStatisticAdapter() : RecyclerView.Adapter<DetailStatisticAdapter.ViewHolder>() {
    var listBill: ArrayList<Bill> = arrayListOf()

    fun setData(listBill: ArrayList<Bill>) {
        this.listBill = listBill
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemStatisticBillBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: Bill, position: Int){
            Log.d("ptit", "kaka: " + data.toString())
            binding.tvTime.text = data.dateNew.toString()
            binding.tvSum.text = "Doanh  thu: " + data.totalPrice.toString()
            binding.tvQuantity.text = data.quantity.toString()
            binding.tvTable.text = data.idBan.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStatisticBillBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val table = listBill[position]
            fillData(table, position)
        }
    }

    override fun getItemCount(): Int {
        Log.d("ptit", "getItemCount: " + listBill.size)
        return listBill!!.size
    }

    interface IClick {
        fun onClick(table: TableFirebase)
    }
}