package com.jtarini.zupcryptowatcher.scene.select

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jtarini.zupcryptowatcher.R
import com.jtarini.zupcryptowatcher.databinding.ItemCoinBinding
import com.jtarini.zupcryptowatcher.model.Coin

class SelectAdapter: RecyclerView.Adapter<SelectAdapter.ViewHolder>() {
  
  var items: List<Coin>? = listOf()
    set(value) {
      field = value
      notifyDataSetChanged()
    }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_coin, parent, false))
  
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = items?.get(position) ?: return
    holder.bind(item)
  
    holder.itemView.setOnClickListener { view ->
      val action = SelectFragmentDirections.actionSelectFragmentToCoinFragment()
      action.coinSymbol = item.symbol
      
      view.findNavController().navigate(action)
    }
  }
  
  override fun getItemCount() = items?.size ?: 0
  
  inner class ViewHolder(private val binding: ItemCoinBinding): RecyclerView.ViewHolder(binding.root) {
    
    fun bind(item: Coin) {
      binding.coin = item
    }
    
  }
  
}