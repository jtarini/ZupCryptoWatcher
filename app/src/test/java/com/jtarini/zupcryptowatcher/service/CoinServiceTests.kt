package com.jtarini.zupcryptowatcher.service

import com.jtarini.zupcryptowatcher.util.RxImmediateSchedulerRule
import com.jtarini.zupcryptowatcher.model.Coin
import com.jtarini.zupcryptowatcher.model.CoinsResponse
import com.jtarini.zupcryptowatcher.networking.ApplicationApi
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CoinServiceTests {

  @Rule
  @JvmField
  var testSchedulerRule = RxImmediateSchedulerRule()
  
  @Mock
  lateinit var api: ApplicationApi
  
  @Mock
  lateinit var coinsResponse: CoinsResponse
  
  private lateinit var sut: CoinService
  
  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    
    sut = CoinService(api)
  }
  
  @Test
  fun testGetAllCoinsLatestNotEmpty() {
    // 1. given
    `when`(api.getAllCoinsLatest()).thenReturn(Single.just(coinsResponse))
    `when`(api.getAllCoinsLatest().blockingGet().coins).thenReturn(listOf(
      Coin(name = "Bitcoin"),
      Coin(name = "Ethereum")
    ))
  
    // 2. when
    val testSingle = sut.getAllCoinsLatest().test()
    
    // 3. then
    testSingle.assertValueCount(1)
    testSingle.assertNoErrors()
    testSingle.assertComplete()
    assertEquals(coinsResponse, testSingle.values()[0])
    assertEquals(2, testSingle.values()[0].coins.size)
    testSingle.dispose()
  }
  
}