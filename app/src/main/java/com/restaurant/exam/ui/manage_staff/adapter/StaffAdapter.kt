package com.restaurant.exam.ui.manage_staff.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.Staff
import restaurant.exam.databinding.ItemStaffBinding

class StaffAdapter(val click: IClick) : RecyclerView.Adapter<StaffAdapter.ViewHolder>() {
    var listStaff = arrayListOf<Staff>()
    fun setList(listStaff: ArrayList<Staff>) {
        this.listStaff = arrayListOf()
        this.listStaff.addAll(listStaff)
        notifyDataSetChanged()
    }
    inner class ViewHolder(val binding: ItemStaffBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("CheckResult")
        fun fillData(data: Food, position: Int){

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStaffBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
//            val food = listFood[position]
            val data = listStaff[position]
            binding.tvName.text = data.name
            binding.tvEmail.text = data.username
            itemView.setOnClickListener {
                click.onClick(data)
            }
//            Log.d("ptit", "onBindViewHolder: " + position)
//            fillData(food, position)
        }
    }


    override fun getItemCount(): Int {
//        return listFood.size;
        return listStaff.size;
    }

    interface IClick {
        fun onClick(data: Staff)
    }
}