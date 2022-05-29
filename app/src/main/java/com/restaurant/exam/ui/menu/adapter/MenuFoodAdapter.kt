package com.restaurant.exam.ui.menu.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.utils.BASE_URL
import restaurant.exam.databinding.ItemFoodAddMenuBinding

class MenuFoodAdapter(val click: IClick) : RecyclerView.Adapter<MenuFoodAdapter.ViewHolder>() {
    var listFood = arrayListOf<Food>()
    fun setList(listFood: ArrayList<Food>) {
        this.listFood = arrayListOf<Food>()
        this.listFood.addAll(listFood)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemFoodAddMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun fillData(data: Food, position: Int){
            Log.d("ptit", "fillData: " + position)
            binding.tvTitle.text = data.name
            binding.tvPrice.text = data.price.toString()
            Glide.with(itemView.context)
                .load(BASE_URL +"api/v1/food/getImage/${data.id}.jpg")
                .transform(CenterCrop(), RoundedCorners(14))
                .into(binding.imgLogo)
            binding.layout.setOnClickListener {
                click.onClick(data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodAddMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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