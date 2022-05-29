package com.restaurant.exam.ui.manage.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.data.model.TableRestaurant
import restaurant.exam.R
import restaurant.exam.databinding.ItemTableAddBinding

class TableAddAdapter(val click: IClick) : RecyclerView.Adapter<TableAddAdapter.ViewHolder>() {
    var listTable: ArrayList<TableRestaurant> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TableRestaurant>) {
        listTable = arrayListOf()
        Log.d("ptit", "size: " + listTable.size)
        listTable = data as ArrayList<TableRestaurant>
        listTable.add(TableRestaurant(id = -1))
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTableAddBinding) : RecyclerView.ViewHolder(binding.root) {
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTableAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val data = listTable[position]

            if (position == listTable.size - 1) {
                Log.d("data", "vao day 1 lan: ")
            } else {
                Log.d("data", "vao day 5 lan: ")
            }

            if (position != listTable.size -1) {
                Log.d("ptit", "posi" + position)
                binding.tvName.text = data.name
                binding.layoutAdd.visibility = View.GONE
                binding.layoutTable.visibility = View.VISIBLE
                binding.layout.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.color_unchecked_table
                    )
                )
                itemView.setOnClickListener {
                    if (data.isSelected) {
                        binding.layout.setCardBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.color_unchecked_table
                            )
                        )
                        data.isSelected = false
                    } else {
                        binding.layout.setCardBackgroundColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.color_checked_table
                            )
                        )
                        data.isSelected = true
                    }
                    click.onClick(data)
                }
            } else {
                Log.d("ptit", "go go go" + position)
                binding.layout.setCardBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.color_add_table
                    )
                )
                binding.layoutAdd.visibility = View.VISIBLE
                binding.layoutTable.visibility = View.GONE
                binding.layout.setOnClickListener {
                    click.onClickAdd(data)
                }
            }

        }
    }


    // return the size of languageList
    override fun getItemCount(): Int {
        return listTable.size
    }

    interface IClick {
        fun onClick(table: TableRestaurant)
        fun onClickAdd(table: TableRestaurant)
    }

    fun getData() : ArrayList<TableRestaurant> {
        var listData  = arrayListOf<TableRestaurant>()
        listTable.forEach {
            if (it.isSelected) {
                listData.add(it)
            }
        }
        return listData
    }
}