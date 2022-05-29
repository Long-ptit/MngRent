package com.restaurant.exam.ui.manage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Floor
import com.restaurant.exam.data.model.Restaurant
import com.restaurant.exam.data.model.TableRestaurant
import com.restaurant.exam.ui.manage.adapter.SpinnerAdapterFloor
import com.restaurant.exam.ui.manage.adapter.TableAddAdapter
import com.restaurant.exam.view_model.ViewModelFactory
import kotlinx.android.synthetic.main.layout_manage_restaurant.*
import restaurant.exam.R
import restaurant.exam.databinding.LayoutManageRestaurantBinding

class ManageRestaurantActivity :
    BaseActivity<ManageRestaurantViewModel, LayoutManageRestaurantBinding>(),
    TableAddAdapter.IClick {

    lateinit var mAdapterSpinner: SpinnerAdapterFloor
    lateinit var mTableAddAdapter: TableAddAdapter

    companion object {
        fun getIntent(
            context: Context
        ): Intent {
            return Intent(context, ManageRestaurantActivity::class.java)
        }
    }


    override fun getContentLayout(): Int {
        return R.layout.layout_manage_restaurant
    }

    override fun observerLiveData() {
        viewModel.apply {
            floorResponse.observe(this@ManageRestaurantActivity) {
                if (it != null) {
                    mAdapterSpinner.setListData(it as ArrayList<Floor>)
                    if (it.isNotEmpty()) {
                        viewModel.getTable(mAdapterSpinner.getData(0).id!!)
                    }
                }
            }
        }

        viewModel.apply {
            tableSaveResponse.observe(this@ManageRestaurantActivity) {
                if (it != null) {
//                    mAdapterSpinner.setListData(it as ArrayList<Floor>)
                    viewModel.getTable(mAdapterSpinner.getData(binding.sp.selectedItemPosition).id!!)
                    showError("Save table with name ${it.name}")
                    val idFloor = mAdapterSpinner.getData(binding.sp.selectedItemPosition).id
                    viewModel.saveTableToFirebase(idFloor!!, it.id!!, it.name!!)
                }
            }
        }

        viewModel.apply {
            floorSaveResponse.observe(this@ManageRestaurantActivity) {
                if (it != null) {
//                    mAdapterSpinner.setListData(it as ArrayList<Floor>)
                    viewModel.getFloorByRes()
                }
            }
        }

        viewModel.apply {
            getTableResponse.observe(this@ManageRestaurantActivity) {
                if (it != null) {
                    mTableAddAdapter.setData(it)
                }
            }
        }
    }

    override fun initView() {
        showError = true
        mAdapterSpinner = SpinnerAdapterFloor(this)
        binding.sp.adapter = mAdapterSpinner
        binding.sp.setSelection(0)
        binding.sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.getTable(mAdapterSpinner.getData(binding.sp.selectedItemPosition).id!!)
                binding.btnDelete.visibility = View.GONE
            }

        }

        mTableAddAdapter = TableAddAdapter(this)
        binding.rcv.adapter = mTableAddAdapter
        binding.rcv.layoutManager = GridLayoutManager(this, 2)
    }

    override fun initListener() {
        viewModel.getFloorByRes()
        binding.btnAdd.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.DialogCustomTheme)
            val view = layoutInflater.inflate(R.layout.layout_bottom_add_floor, null)
            dialog.setContentView(view)
            val btnSave = view.findViewById<TextView>(R.id.btn_add)
            val editText = view.findViewById<EditText>(R.id.edt_name)
            btnSave.setOnClickListener {
                val floor =
                    Floor(name = editText.text.toString(), restaurant = Restaurant(id = viewModel.getResId()))
                viewModel.saveFloor(floor)
                dialog.dismiss()
            }
            dialog.show()
        }

        binding.btnEdit.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.DialogCustomTheme)
            val view = layoutInflater.inflate(R.layout.layout_bottom_add_floor, null)
            dialog.setContentView(view)
            val btnSave = view.findViewById<TextView>(R.id.btn_add)
            val editText = view.findViewById<EditText>(R.id.edt_name)
            editText.setText(mAdapterSpinner.getData(binding.sp.selectedItemPosition).name!!)
            btnSave.setOnClickListener {
                val floor =
                    Floor(id = mAdapterSpinner.getData(binding.sp.selectedItemPosition).id!! ,
                        name = editText.text.toString(),
                        restaurant = Restaurant(id = viewModel.getResId()))
                viewModel.saveFloor(floor)
                dialog.dismiss()
            }
            dialog.show()
        }
        binding.btnDelete.setOnClickListener {
            var data = mTableAddAdapter.getData()
            data.forEach {
                val idFloor = mAdapterSpinner.getData(binding.sp.selectedItemPosition).id
                viewModel.deleteTable(idFloor!!, it.id!!)
            }
        }

    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(this))[ManageRestaurantViewModel::class.java]
    }

    override fun onClick(table: TableRestaurant) {
        var data = mTableAddAdapter.getData()
        if (data.size == 0) {
            binding.btnDelete.visibility = View.GONE
        } else {
            binding.btnDelete.text = "Xóa (${data.size})"
            binding.btnDelete.visibility = View.VISIBLE
        }
    }

    override fun onClickAdd(table: TableRestaurant) {
        val dialog = BottomSheetDialog(this, R.style.DialogCustomTheme)
        val view = layoutInflater.inflate(R.layout.layout_bottom_add_table, null)
        dialog.setContentView(view)
        val btnSave = view.findViewById<TextView>(R.id.btn_add)
        val editText = view.findViewById<EditText>(R.id.edt_name)
        btnSave.setOnClickListener {
            val tableRes = TableRestaurant(
                name = editText.text.toString(),
                floor = Floor(id = mAdapterSpinner.getData(binding.sp.selectedItemPosition).id)
            )
          viewModel.saveTable(tableRes)
            dialog.dismiss()
        }

        dialog.show()
    }

}