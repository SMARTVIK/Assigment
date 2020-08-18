package com.vik.altimetricassignment.ui

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vik.altimetricassignment.R
import com.vik.altimetricassignment.listener.EndlessRecyclerViewScrollListener
import com.vik.altimetricassignment.listener.OnItemClick
import com.vik.altimetricassignment.model.Result
import com.vik.altimetricassignment.model.Status
import com.vik.altimetricassignment.viewmodel.MainActivityViewModel
import com.vik.altimetricassignment.viewmodelfactory.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var mp: MediaPlayer? = null
    private var isPLAYING: Boolean? = false
    private var adapter: SearchItemAdapter ? = null
    private var searchQuery: String ? = ""
    var viewModel : MainActivityViewModel ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = MainViewModelFactory(this)
        setUpSearchList()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)
        viewModel!!.getSearchData().observe(this, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    adapter!!.setData(it.data?.results)
                    Log.d(TAG, "getSearchData: Success")
                }
                Status.LOADING -> {
                    showProgressBar()
                    Log.d(TAG, "getSearchData: Loading")
                }
                Status.ERROR -> {
                    hideProgressBar()
                    Log.d(TAG, "getSearchData: Error")
                }
            }

        })

    }

    private fun setUpSearchList() {
        val layoutManager = LinearLayoutManager(this).apply { orientation = RecyclerView.VERTICAL }
        rvSearchList.layoutManager = layoutManager
        adapter = SearchItemAdapter(this, object : OnItemClick<Result>{
            override fun onItemClick(t: Result) {

            }
        })
        rvSearchList.adapter = adapter!!
        rvSearchList.setOnScrollListener(object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                viewModel?.getSearchData()
            }
        })
    }

    private fun showProgressBar() {
        progressBar.visibility = VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = GONE
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.action_search)
            .actionView as SearchView
        search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchQuery = query
                if(!searchQuery?.isEmpty()!!){
                viewModel!!.searchTerm(searchQuery!!)
                } else {
                    noCollectionsText.visibility = VISIBLE
                    rvSearchList.visibility = GONE
                }
                return true
            }
        })
        return true
    }
}