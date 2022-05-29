package com.restaurant.exam.injection

import com.restaurant.exam.ui.add_food_table.AddFoodTableViewModel
import com.restaurant.exam.ui.confirm_bill.ConfirmViewModel
import com.restaurant.exam.ui.detail_food.DetailFoodViewModel
import com.restaurant.exam.ui.detail_statistic.DetailStatisticViewModel
import com.restaurant.exam.ui.login.LoginViewModel
import com.restaurant.exam.ui.home.HomeViewModel
import com.restaurant.exam.ui.main.MainViewModel
import com.restaurant.exam.ui.manage.ManageRestaurantViewModel
import com.restaurant.exam.ui.manage_staff.ManageStaffViewModel
import com.restaurant.exam.ui.table.DetailTableViewModel
import dagger.Component

/**
 * Component providing inject() methods for presenters.
 */
@Component(modules = [(NetworkModule::class)], dependencies = [ApplicationComponent::class])
@ViewModelScope
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(mainViewModel: MainViewModel)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(detailViewModel: DetailTableViewModel)
    fun inject(addFoodTableViewModel: AddFoodTableViewModel)
    fun inject(confirmViewModel: ConfirmViewModel)
    fun inject(detailFoodViewModel: DetailFoodViewModel)
    fun inject(manageRestaurantViewModel: ManageRestaurantViewModel)
    fun inject(manageStaffViewModel: ManageStaffViewModel)
    fun inject(detailStatisticViewModel: DetailStatisticViewModel)
    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder
        fun applicationComponent(applicationComponent: ApplicationComponent): Builder
    }
}