package com.zuas.study.shopinglist.presentation

import android.app.Application
import com.zuas.study.shopinglist.di.DaggerApplicationComponent

class ShoppingApp: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}