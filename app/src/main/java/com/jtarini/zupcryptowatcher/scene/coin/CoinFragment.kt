package com.jtarini.zupcryptowatcher.scene.coin

import android.os.Bundle
import android.view.View
import com.jtarini.zupcryptowatcher.R
import com.jtarini.zupcryptowatcher.databinding.FragmentCoinBinding
import com.jtarini.zupcryptowatcher.scene.base.BaseFragment
import org.jetbrains.anko.longToast
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinFragment: BaseFragment<FragmentCoinBinding>() {
  
  override val viewModel: CoinViewModel by viewModel()
  
  override fun layoutId(): Int = R.layout.fragment_coin
  
  override fun initializeView() {
    title = getString(R.string.title_fragment_coin)
    binding.viewModel = viewModel
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    val symbol = CoinFragmentArgs.fromBundle(arguments ?: return).coinSymbol
    context?.longToast(symbol)
  }
  
}