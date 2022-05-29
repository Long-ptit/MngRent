package com.restaurant.exam.ui.manage.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.restaurant.exam.data.model.Floor
import restaurant.exam.R

class SpinnerAdapterFloor(val context: Context) :  BaseAdapter(){
    var list: ArrayList<Floor> = arrayListOf()
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    fun setListData(listData: ArrayList<Floor>) {
        list = listData;
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list.size
    }

    fun getData(position: Int) : Floor {
        return list[position]
    }


    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
       return p0.toLong()
    }

    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_spinner_floor, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = list.get(p0).name
        return view
    }

    private class ItemHolder(row: View?) {
        val label: TextView

        init {
            label = row?.findViewById(R.id.tv) as TextView
        }
    }
}