package com.restaurant.exam.ui.home

import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import restaurant.exam.R
import com.restaurant.exam.base.BaseFragment
import com.restaurant.exam.data.model.Floor
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.TableFirebase
import com.restaurant.exam.network.Api
import com.restaurant.exam.ui.home.adapter.FloorAdapter
import com.restaurant.exam.ui.home.adapter.TableAdapter
import com.restaurant.exam.ui.table.DetailTableActivity
import com.restaurant.exam.utils.recycleview_utils.GridSpacingItemDecoration
import com.restaurant.exam.view_model.ViewModelFactory
import restaurant.exam.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment :
    BaseFragment<HomeViewModel, FragmentHomeBinding>(),
    FloorAdapter.IClick,
    TableAdapter.IClick {

    private lateinit var mAdapter: TableAdapter
    private lateinit var mAdapterFloor: FloorAdapter
    private var idFloor: Int = 0


    override fun getContentLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelFactory(requireContext()))[HomeViewModel::class.java]
    }

    override fun initView() {
        mAdapter = TableAdapter(this)
        binding.rcv.adapter = mAdapter
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, 2)
        binding.rcv.layoutManager = mLayoutManager
        binding.rcv.itemAnimator = DefaultItemAnimator()
        binding.rcv.setHasFixedSize(true)
        binding.rcv.addItemDecoration(
            GridSpacingItemDecoration(
                2,
                25,
                true,
                0
            )
        )
        //viewModel.saveToFirebase()
        viewModel.getFloorByRes(viewModel.getIdRes())
        mAdapterFloor = FloorAdapter(this)
        binding.rvcFloor.adapter = mAdapterFloor
        binding.rvcFloor.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


    }

    override fun onResume() {
        super.onResume()
        Log.d("ptit", "onResume: ")
        viewModel.getFloorByRes(viewModel.getIdRes())
    }

    override fun initListener() {
    }

    override fun observerLiveData() {

        viewModel.apply {
            floorResponse.observe(this@HomeFragment) {
                if (it != null) {
                    mAdapterFloor.setList(it)
                    if (it.isNotEmpty()) {
                        Log.d("ptit", "data: " + it.toString())
                        idFloor = it.get(0).id!!
                        viewModel.getTable(it.get(0).id!!) {
                            mAdapter.setData(it)
                        }
                    } else {
                        Toast.makeText(context, "Bạn cần thêm 1 tầng", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onClick(floor: Floor) {
        idFloor = floor.id!!
        viewModel.getTable(idFloor) {
            Log.d("TAG", "observerLiveData: $it" + "id floor" + floor.id)
            mAdapter.setData(it)
        }
    }

    override fun onClick(table: TableFirebase) {
        var intent = Intent(
            context,
            DetailTableActivity::class.java
        )
        intent.putExtra("id_floor", idFloor)
        intent.putExtra("table", table)
        startActivity(intent)
    }
}