package com.zuas.study.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zuas.study.shopinglist.R

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterShopList: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val buttonAddItem: FloatingActionButton = findViewById(R.id.button_add_shop_item)
        setupRecyclerView(buttonAddItem)
        buttonAddItem.setOnClickListener{
            val intent = ShopItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }

        viewModel.shopList.observe(this) {
            Log.d("MainActivity", it.toString())
            adapterShopList.submitList(it)
        }
        viewModel.item.observe(this) { singleItem ->
            singleItem?.let {
                Log.d("MainActivity", it.toString())
            }
        }


    }

    private fun setupRecyclerView(button: FloatingActionButton) {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapterShopList = ShopListAdapter()
        with(rvShopList) {
            adapter = adapterShopList
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupOnItemClickListeners()
        setupSwipeListener(rvShopList)
        rvShopList.setOnScrollChangeListener{ _, _, _, _, oldScrollY ->
            if (oldScrollY < 0) button.hide() else button.show()}
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapterShopList.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }

        })
        touchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupOnItemClickListeners() {
        with(adapterShopList) {
            onShopItemLongClickListener = { viewModel.changeEnableState(it) }
            onShopItemClickListener = {
                val intent = ShopItemActivity.newIntentEditItem(
                    this@MainActivity,it.id)
                startActivity(intent)
            }
        }
    }


}