package com.jtarini.zupcryptowatcher.scene.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton
import com.google.android.material.snackbar.Snackbar
import com.jtarini.zupcryptowatcher.R

abstract class BaseFragment<T: ViewDataBinding>: Fragment() {
  
  protected lateinit var binding: T
  abstract val viewModel: BaseViewModel
  private lateinit var skeleton: Skeleton
  lateinit var refreshLayout: SwipeRefreshLayout
  lateinit var recyclerView: RecyclerView
  var layoutRecyclerViewItemId: Int = 0
  var title = ""
  
  private var isSkeletonLoading = false
  private var skeletonItemCount = 10
  private var isSkeletonBeenShown = false
  
  private val errorRetryClickListener = View.OnClickListener { retryFetchData() }
  
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
    binding.lifecycleOwner = this
  
    viewModel.errorMessage.observe(this.viewLifecycleOwner, Observer<String>{ message ->
      showError(message)
    })
  
    initializeView()

    if (title.isNotBlank()) {
      (activity as AppCompatActivity).supportActionBar?.title = title
    }
    
    return binding.root
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    if (::refreshLayout.isInitialized) {
      refreshLayout.setOnRefreshListener {
        retryFetchData()
      }
    }
  }
  
  override fun onDestroy() {
    super.onDestroy()
    
    // clear Disposables from the view model subscription
    viewModel.clear()
  }
  
  /**
   * Show swipe refresh layout and skeleton layout for a specific RecyclerView.
   */
  protected fun showLoading(isRecyclerView: Boolean = true) {
    startSkeleton(isRecyclerView)
  }
  
  /**
   * Hide the actual swipe refresh layout been shown.
   */
  protected fun hideLoading() {
    if (::refreshLayout.isInitialized) {
      refreshLayout.isRefreshing = false
    }
    
    stopSkeleton()
  }
  
  /**
   * Change the skeleton layout item count been used.
   */
  protected fun setSkeletonItemCount(skeletonItemCount: Int) {
    this.skeletonItemCount = skeletonItemCount
  }

  open fun fetchData() {

  }
  
  /**
   * Snackbar retry button action.
   */
  private fun retryFetchData() {
    isSkeletonBeenShown = false
    showLoading()
    fetchData()
  }
  
  /**
   * Apply and show skeleton layout for a specific RecyclerView.
   */
  private fun startSkeleton(isRecyclerView: Boolean) {
    if (isSkeletonBeenShown) {
      return
    }
    
    if (isRecyclerView) {
      skeleton = recyclerView.applySkeleton(layoutRecyclerViewItemId, skeletonItemCount)
    }
    
    skeleton.showSkeleton()
    isSkeletonLoading = true
  }
  
  /**
   * Hide the actual skeleton layout been shown.
   */
  private fun stopSkeleton() {
    if (isSkeletonBeenShown) {
      return
    }
    
    skeleton.showOriginal()

    isSkeletonLoading = false
    isSkeletonBeenShown = true
  }
  
  /**
   * Show the error message and stops skeleton layout if is has been shown.
   */
  private fun showError(message: String) {
    Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).apply {
      setAction(R.string.retry, errorRetryClickListener)
      show()
    }
    
    hideLoading()
  }
  
  abstract fun layoutId(): Int
  
  abstract fun initializeView()
  
}