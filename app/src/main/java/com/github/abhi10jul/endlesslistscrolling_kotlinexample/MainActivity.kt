package com.github.abhi10jul.endlesslistscrolling_kotlinexample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.abhi10jul.endlesslistscrolling_kotlinexample.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityAdapter: MainActivityAdapter
    private var apiClient = ApiClient()
    private var currentPage = 1
    private var totalAvailablePages = 1
    private lateinit var tvShowList: ArrayList<TvShow>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViews()
    }


    private fun initViews() {
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tvShowList = ArrayList()
        mainActivityAdapter = MainActivityAdapter()
        binding.recyclerview.adapter = mainActivityAdapter
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.recyclerview.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1
                        loadPageList()
                    }
                }
            }
        })
        loadPageList()
    }

    private fun loadPageList() {
        toogleLoading()
        apiClient.callTvShowListRequest(currentPage, object : Callback<ShowModel> {
            override fun onResponse(call: Call<ShowModel>, response: Response<ShowModel>) {
                if (response.isSuccessful) {
                    val showModel = response.body()
                    if (showModel != null) {
                        val oldCount = tvShowList.size
                        totalAvailablePages = showModel.pages
                        tvShowList.addAll(showModel.tvShows)
                        mainActivityAdapter.updateList(tvShowList, oldCount, tvShowList.size)
                        Log.e(
                            TAG,
                            "oldCount $oldCount totalAvailablePages $totalAvailablePages tvShowList ${tvShowList.size}"
                        )
                    }
                }
                toogleLoading()
            }

            override fun onFailure(call: Call<ShowModel>, t: Throwable) {
                t.printStackTrace()
                Log.e(TAG, "exception", t)
                toogleLoading()
            }
        })
    }

    private fun toogleLoading() {
        if (currentPage == 1) {
            if (binding.defaultProgress.isShown) {
                binding.defaultProgress.visibility = View.GONE
            } else {
                binding.defaultProgress.visibility = View.VISIBLE
            }
        } else {
            if (binding.loadMoreProgress.isShown) {
                binding.loadMoreProgress.visibility = View.GONE
            } else {
                binding.loadMoreProgress.visibility = View.VISIBLE
            }
        }
    }
}