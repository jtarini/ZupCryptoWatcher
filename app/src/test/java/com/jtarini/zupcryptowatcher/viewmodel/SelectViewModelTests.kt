package com.jtarini.zupcryptowatcher.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jtarini.zupcryptowatcher.model.Coin
import com.jtarini.zupcryptowatcher.model.CoinsResponse
import com.jtarini.zupcryptowatcher.networking.ApplicationApi
import com.jtarini.zupcryptowatcher.scene.select.SelectViewModel
import com.jtarini.zupcryptowatcher.service.CoinService
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class SelectViewModelTests {

  @get:Rule
  val rule = InstantTaskExecutorRule()
  
  @Mock
  lateinit var api: ApplicationApi

  @Mock
  lateinit var service: CoinService
  
  @Mock
  lateinit var coinsResponse: CoinsResponse

  @Mock
  lateinit var observerItems: Observer<List<Coin>>

  @Mock
  lateinit var observerErrorMessage: Observer<String>
  
  private lateinit var sut: SelectViewModel

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)

    sut = SelectViewModel(service)
    sut.items.observeForever(observerItems)
    sut.errorMessage.observeForever(observerErrorMessage)
  }
  
  @Test
  fun testFetchDataWithNotEmptyCoins() {
    // 1. given
    val expectedCoins = listOf(
      Coin(name = "Bitcoin"),
      Coin(name = "Ethereum")
    )
    `when`(api.getAllCoinsLatest()).thenReturn(Single.just(coinsResponse))
    `when`(api.getAllCoinsLatest().blockingGet().coins).thenReturn(expectedCoins)
    `when`(service.getAllCoinsLatest()).thenReturn(Single.just(coinsResponse))

    // 2. when
    sut.fetchData()
    
    // 3. then
    @Suppress("UNCHECKED_CAST")
    val captor = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<Coin>>

    captor.run {
      verify(observerItems, times(1)).onChanged(capture())
      assertEquals(expectedCoins, value)
    }
  }
  
  @Test
  fun testFetchDataWithEmptyCoins() {
    // 1. given
    val expectedCoins = ArrayList<Coin>()
    `when`(api.getAllCoinsLatest()).thenReturn(Single.just(coinsResponse))
    `when`(api.getAllCoinsLatest().blockingGet().coins).thenReturn(expectedCoins)
    `when`(service.getAllCoinsLatest()).thenReturn(Single.just(coinsResponse))
    
    // 2. when
    sut.fetchData()
  
    // 3. then
    @Suppress("UNCHECKED_CAST")
    val captor = ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<Coin>>

    captor.run {
      verify(observerItems, times(1)).onChanged(capture())
      assertEquals(expectedCoins, value)
    }
  }
  
  @Test
  fun testFetchDataWithError() {
    // 1. given
    val errorMessage = "error message"
    `when`(service.getAllCoinsLatest()).thenReturn(Single.error(Exception(errorMessage)))
    
    // 2. when
    sut.fetchData()
    
    // 3. then
    val captor = ArgumentCaptor.forClass(String::class.java)

    captor.run {
      verify(observerErrorMessage, times(1)).onChanged(capture())
      assertEquals(errorMessage, value)
    }
  }
  
}