package com.zuas.study.shopinglist.di

import android.app.Application
import com.zuas.study.shopinglist.presentation.MainActivity
import com.zuas.study.shopinglist.presentation.ShopItemFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, CashModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: ShopItemFragment)


    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}