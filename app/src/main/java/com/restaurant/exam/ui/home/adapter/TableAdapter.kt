package com.restaurant.exam.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.base.BaseRecyclerAdapter
import com.restaurant.exam.data.model.Floor
import com.restaurant.exam.data.model.TableFirebase
import com.restaurant.exam.data.model.TableFirebaseInfor
import com.restaurant.exam.utils.DANG_AN
import restaurant.exam.R
import restaurant.exam.databinding.ItemTableBinding

class TableAdapter(val click: IClick) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {
    var listTable: List<TableFirebaseInfor> = listOf()

    fun setData(listTable: List<TableFirebaseInfor>) {
        this.listTable = listTable
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTableBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: TableFirebase, position: Int){
            binding.tvName.text = data.name
            binding.tvStatus.text = data.status
            Log.d("ptit", "fillData: " + data.status)
            if (data.status.toString() == DANG_AN) {
                binding.layout.setCardBackgroundColor( ContextCompat.getColor(
                    itemView.context,
                    R.color.color_checked_table
                ))
            } else {
                binding.layout.setCardBackgroundColor( ContextCompat.getColor(
                    itemView.context,
                    R.color.color_unchecked_table
                ))
            }

            itemView.setOnClickListener {
                Log.d("ptit", "fillData: " + data.name)
                click.onClick(data)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val table = listTable[position].infor
            fillData(table, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listTable!!.size
    }

    interface IClick {
        fun onClick(table: TableFirebase)
    }
}