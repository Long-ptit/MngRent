package com.restaurant.exam.ui.add_food_table.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.utils.BASE_URL
import restaurant.exam.R
import restaurant.exam.databinding.ItemFoodAddBinding

class AddFoodAdapter(val click: IClick) : RecyclerView.Adapter<AddFoodAdapter.ViewHolder>() {
    var listFood = arrayListOf<Food>()
    fun setList(listFood: ArrayList<Food>) {
        this.listFood = arrayListOf()
        this.listFood.addAll(listFood)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemFoodAddBinding) : RecyclerView.ViewHolder(binding.root) {
        fun fillData(data: Food, position: Int){
            Log.d("ptit", "fillData: " + position)
            binding.tvTitle.text = data.name
            binding.tvPrice.text = data.price.toString()
            Glide.with(itemView.context)
                .load(BASE_URL +"api/v1/food/getImage/${data.id}.jpg")
                .transform(CenterCrop(), RoundedCorners(14))
                .into(binding.imgLogo)

            binding.layout.setOnClickListener {
                if (data.isSelected) {
                    binding.layout.setCardBackgroundColor(Color.RED)
                    listFood.get(position).isSelected = false
                    listFood.get(position).quantity = 0
                    binding.layoutAddSub.visibility = View.INVISIBLE
                    binding.layout.setCardBackgroundColor( ContextCompat.getColor(
                        itemView.context,
                        R.color.color_unchecked_table
                    ))
                } else {
                    listFood.get(position).isSelected = true
                    listFood.get(position).quantity = 1
                    binding.layoutAddSub.visibility = View.VISIBLE
                    binding.tvQuantity.text = listFood.get(position).quantity.toString()
                    binding.layout.setCardBackgroundColor( ContextCompat.getColor(
                        itemView.context,
                        R.color.color_checked_table
                    ))
                }
            }
            binding.btnAdd.setOnClickListener {
                listFood.get(position).quantity = listFood.get(position).quantity.plus(1)
                binding.tvQuantity.text = listFood.get(position).quantity.toString()
            }

            binding.btnSub.setOnClickListener {
                if (listFood.get(position).quantity != 1) {
                    listFood.get(position).quantity = listFood.get(position).quantity.minus(1)
                }
                binding.tvQuantity.text = listFood.get(position).quantity.toString()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            val food = listFood[position]
            Log.d("ptit", "onBindViewHolder: " + position)
            fillData(food, position)
        }
    }

    fun checkFab(): Boolean{
        listFood.forEach {
            if (it.isSelected) return true
        }
        return false;
    }

    fun getList(): ArrayList<Food> {
        var listResult = ArrayList<Food>()
        listResult.addAll(listFood)
       listResult.removeAll { !it.isSelected }
        return listResult
    }

    override fun getItemCount(): Int {
        return listFood.size;
    }

    interface IClick {
        fun onClick(food: Food)
    }
}