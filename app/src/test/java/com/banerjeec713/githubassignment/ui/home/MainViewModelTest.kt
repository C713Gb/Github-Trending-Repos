package com.banerjeec713.githubassignment.ui.home


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.banerjeec713.githubassignment.RxImmediateScheduler
import com.banerjeec713.githubassignment.Utils.generateTestOneItemModel
import com.banerjeec713.githubassignment.data.DataManager
import com.banerjeec713.githubassignment.data.models.TrendingItemModel
import io.reactivex.Observable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class MainViewModelTest {

    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Rule
    val rxImmediateScheduler = RxImmediateScheduler()
    @Mock
    lateinit var dataManager: DataManager
    private lateinit var viewModel: MainViewModel
    @Mock
    lateinit var dataObserver: Observer<List<TrendingItemModel>>
    @Mock
    lateinit var errorObserver: Observer<Boolean>
    @Captor
    lateinit var arg: ArgumentCaptor<List<TrendingItemModel>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(dataManager)
        viewModel.repos.observeForever(dataObserver)
        viewModel.error.observeForever(errorObserver)
    }

    @Test
    fun testNull() {
        `when`(dataManager.getTrendingRepos()).thenReturn(null)
        assertNotNull(viewModel.repos)
        assertTrue(viewModel.repos.hasActiveObservers())
        assertTrue(viewModel.error.hasObservers())
    }

    @Test
    fun testNewData() {
        val items: List<TrendingItemModel> = generateTestOneItemModel()

        //given
        given(dataManager.getTrendingRepos()).willReturn(Observable.just(items))
        //act
        viewModel.loadRepos()
        //verify
        then(dataObserver).should(times(1)).onChanged(items)
        then(errorObserver).should(times(1)).onChanged(false)
    }

    @Test
    fun testVerifyRightData() {
        val testOneItemModel: List<TrendingItemModel> = generateTestOneItemModel()

        //given
        given(dataManager.getTrendingRepos()).willReturn(Observable.just(testOneItemModel))
        //act
        viewModel.loadRepos()
        //verify
        then(dataObserver).should(times(1)).onChanged(arg.capture())
        val capturedItems: List<TrendingItemModel> = arg.allValues[0]
        assertEquals(capturedItems[0], testOneItemModel[0])
    }

    @Test
    fun testErrorLocation() {
        //given
        val error = Throwable("Error Response")
        given(dataManager.getTrendingRepos())
            .willReturn(Observable.error(error))
        // when
        viewModel.loadRepos()
        //then
        then(errorObserver).should(times(1)).onChanged(true)
        then(dataObserver).should(times(0)).onChanged(null)
    }
}