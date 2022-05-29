package com.restaurant.exam.ui.detail_food

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.restaurant.exam.base.BaseActivity
import com.restaurant.exam.data.model.Food
import com.restaurant.exam.data.model.Staff
import com.restaurant.exam.ui.login.LoginActivity
import com.restaurant.exam.ui.login.LoginViewModel
import com.restaurant.exam.ui.main.MainActivity
import com.restaurant.exam.utils.BASE_URL
import com.restaurant.exam.utils.RealPathUtils
import com.restaurant.exam.view_model.ViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import restaurant.exam.R
import restaurant.exam.databinding.LayoutDetailFoodBinding
import restaurant.exam.databinding.LayoutLoginBinding
import java.io.File

class DetailFoodActivity : BaseActivity<DetailFoodViewModel, LayoutDetailFoodBinding>() {

    var food: Food = Food()

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun getContentLayout(): Int {
        return R.layout.layout_detail_food
    }

    override fun observerLiveData() {

        viewModel.apply {
            deleteResponse.observe(this@DetailFoodActivity, androidx.lifecycle.Observer {
                if (it != null) {
                    showError("Xóa món ăn thành công với id là ${it.id}")
                    finish()
                }
            })
        }


        viewModel.apply {
            testResponse.observe(this@DetailFoodActivity, androidx.lifecycle.Observer {
                if (it != null) {
                    finish()
                }
            })
        }
    }

    override fun initView() {
        showError = true
        food = intent.getSerializableExtra("food") as Food

        binding.edtNameFood.setText(food.name)
        binding.edtPrice.setText(food.price.toString())
        Glide.with(this).load(BASE_URL +"api/v1/food/getImage/${food.id}.jpg").into(binding.img)

        binding.img.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.btnSave.setOnClickListener {
            val reNameFood = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edtNameFood.text.toString()
            )
            val rePriceFood = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edtPrice.text.toString()
            )
            val reNoteFood = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                binding.edtDescription.text.toString()
            )
            val reIdRes = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                viewModel.getIdRestaurant().toString()
            )
            val reIdCate = RequestBody.create(
                "multipart/form-data".toMediaTypeOrNull(),
                (binding.sp.selectedItemPosition + 1).toString()
            )
            val strRealPath = RealPathUtils.getRealPath(this, imageUri)
            val file = File(strRealPath)
            val requestFile = file
                .asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData(
                "img",
                file.name,
                requestFile
            )
            viewModel.save(body, reIdRes, reIdCate, reNameFood, rePriceFood, reNoteFood)
        }

        binding.btnDelete.setOnClickListener {
            viewModel.delete(food.id!!)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            Glide.with(this).load(imageUri).into(binding.img)
        }
    }

    override fun initListener() {
    }


    override fun initViewModel() {
        viewModel = ViewModelProvider(this, ViewModelFactory(this))[DetailFoodViewModel::class.java]
    }
}