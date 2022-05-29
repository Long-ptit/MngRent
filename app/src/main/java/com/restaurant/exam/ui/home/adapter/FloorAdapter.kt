package com.restaurant.exam.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.data.model.Floor
import restaurant.exam.databinding.ItemFloorBinding

class FloorAdapter(val click: IClick) : RecyclerView.Adapter<FloorAdapter.ViewHolder>() {
    var listFloor: List<Floor> = listOf()
    fun setList(listFloor: List<Floor>) {
        this.listFloor = listFloor
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemFloorBinding) : RecyclerView.ViewHolder(binding.root) {
            fun fillData(data: Floor, position: Int){
                binding.tvName.text = data.name
                itemView.setOnClickListener {
                    Log.d("ptit", "fillData: " + data.name)
                    click.onClick(data)
                }
            }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFloorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    // bind the items with each item
    // of the list languageList
    // which than will be
    // shown in recycler view
    // to keep it simple we are
    // not setting any image data to view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
           val floor = listFloor[position]
            fillData(floor, position)
        }
    }

    // return the size of languageList
    override fun getItemCount(): Int {
        return listFloor.size
    }

    interface IClick {
        fun onClick(floor: Floor)
    }
}