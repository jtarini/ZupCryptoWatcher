package com.jtarini.zupcryptowatcher.scene.select

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.jtarini.zupcryptowatcher.R
import com.jtarini.zupcryptowatcher.databinding.FragmentSelectBinding
import com.jtarini.zupcryptowatcher.model.Coin
import com.jtarini.zupcryptowatcher.scene.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_select.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectFragment: BaseFragment<FragmentSelectBinding>() {
  
  override val viewModel: SelectViewModel by viewModel()
  
  override fun layoutId(): Int = R.layout.fragment_select

  override fun initializeView() {
    title = getString(R.string.title_fragment_select)

    binding.viewModel = viewModel
    
    setAdapter()
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recyclerView = rvCoinsList
    layoutRecyclerViewItemId = R.layout.item_coin
    refreshLayout = swipeRefreshLayout
  
    // super.onViewCreated need to be added after refreshLayout instantiation
    super.onViewCreated(view, savedInstanceState)
  }
  
  override fun onResume() {
    super.onResume()
  
    showLoading()
  }
  
  override fun fetchData() {
    viewModel.fetchData()
  }
  
  private fun setAdapter() {
    val adapter = SelectAdapter()
    binding.adapter = adapter
    
    viewModel.lazyItems.observe(this, Observer<List<Coin>>{ items ->
      adapter.items = items
      
      hideLoading()
    })
  }
  
}