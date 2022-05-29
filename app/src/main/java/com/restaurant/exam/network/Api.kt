package com.restaurant.exam.network

import com.restaurant.exam.data.model.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface Api {

   @POST("api/v1/staff/loginStaff")
   fun login(@Body request: Staff): Observable<Staff>

   @GET("api/v1/restaurant/getById/1")
   fun getId(): Observable<Restaurant>


   @GET("api/v1/floor/getByIdRes/{id}")
   fun getFloor(@Path("id") id: Int): Observable<List<Floor>>

   @GET("api/v1/food/getFoodByIdRes/{id}")
   fun getFood(@Path("id") id: Int): Observable<List<Food>>

   @GET("api/v1/table/getById/{id}")
   fun getTableByIdFloor(@Path("id") id: Int): Observable<List<TableRestaurant>>

   @GET("api/v1/staff/getByIdRes/{id}")
   fun getStaffByIdRes(@Path("id") id: Int): Observable<List<Staff>>

   @POST("api/v1/bill/saveItem")
   fun save(@Body data: FoodBill): Observable<Bill>

   @POST("api/v1/floor/save")
   fun saveFloor(@Body data: Floor): Observable<Floor>

   @POST("api/v1/table/save")
   fun saveTable(@Body data: TableRestaurant): Observable<TableRestaurant>

   @Multipart
   @POST("api/v1/food/saveFood")
   fun saveFood(
      @Part img: MultipartBody.Part,
      @Part("id_res") id_res: RequestBody,
      @Part("id_cate") id_cate: RequestBody,
      @Part("name_food") name_food: RequestBody,
      @Part("price_food") price_food: RequestBody,
      @Part("description_food") description_food: RequestBody,
   )
   : Observable<Food>

   @POST("api/v1/staff/saveStaff")
   fun saveStaff(@Body data: Staff): Observable<Staff>

   @POST("api/v1/staff/saveStaffAdmin")
   fun saveStaffAdmin(@Body data: Staff): Observable<Staff>

   @POST("api/v1/staff/signup")
   fun signup(@Body data: Staff): Observable<Staff>

   @GET("api/v1/bill/getBillIdResSort")
   fun getBillResSort(
      @Query("id") id: Int,
      @Query("from") from: String,
      @Query("to") to: String,
      @Query("sort") sort: Int,
   ): Observable<List<Bill>>

   @GET("api/v1/food/deleteFood/{id}")
   fun deleteFood(@Path("id") id: Int): Observable<Food>


   @GET("api/v1/staff/getUser/{id}")
   fun getStaff(@Path("id") id: Int): Observable<Staff>

   @GET("api/v1/bill/total")
   fun getStatistic(@Query("id") id: Int): Observable<Statistic>

   @GET("api/v1/staff/delete/{id}")
   fun deleteStaff(@Path("id") id: Int): Observable<Staff>



}