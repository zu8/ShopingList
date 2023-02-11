package com.zuas.study.shopinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zuas.study.shopinglist.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var adapterShopList: ShopListAdapter
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ShoppingApp).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)
        val buttonAddItem: FloatingActionButton = findViewById(R.id.button_add_shop_item)
        setupRecyclerView(buttonAddItem)
        buttonAddItem.setOnClickListener{
            if(isOnePaneMode()){
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }else{
                val fragment = ShopItemFragment.newInstanceAddItem()
                launchFragment(fragment)
            }

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

    override fun onEditingFinished(){
        Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
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

    private fun launchFragment(fragment: Fragment){
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.shop_item_container,fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupOnItemClickListeners() {
        with(adapterShopList) {
            onShopItemLongClickListener = { viewModel.changeEnableState(it) }
            onShopItemClickListener = {
                if (isOnePaneMode()){
                    val intent = ShopItemActivity.newIntentEditItem(
                        this@MainActivity,it.id)
                    startActivity(intent)
                }else{
                    val fragment = ShopItemFragment.newInstanceEditItem(it.id)
                    launchFragment(fragment)
                }

            }
        }
    }
}