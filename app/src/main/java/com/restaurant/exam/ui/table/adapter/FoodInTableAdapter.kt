package com.restaurant.exam.ui.table.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.data.model.Food
import restaurant.exam.databinding.ItemFoodTableBinding

class FoodInTableAdapter(val click: IClick) : RecyclerView.Adapter<FoodInTableAdapter.ViewHolder>() {
    var listFood = arrayListOf<Food>()
    fun setList(listFood: ArrayList<Food>) {
        this.listFood = arrayListOf<Food>()
        this.listFood.addAll(listFood)
        this.listFood.add(Food())
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemFoodTableBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun fillData(data: Food, position: Int){

            if (position == listFood.size - 1) {
                binding.layoutAddFood.visibility = View.VISIBLE
                binding.layoutFood.visibility = View.GONE
                binding.layoutAddFood.setOnClickListener {
                    click.onClick(data)
                }
            } else {
                binding.tvPrice.text = data.price.toString()
                binding.tvQuantity.text = "x" + data.quantity.toString()
                binding.tvName.text = data.name
                binding.layoutFood.visibility = View.VISIBLE
                binding.layoutAddFood.visibility = View.GONE
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val food = listFood[position]
            fillData(food, position)
        }
    }

    override fun getItemCount(): Int {
        Log.d("ptit", "size reutrn: " + listFood.size)
        return listFood.size
    }

    interface IClick {
        fun onClick(food: Food)
    }
}