package com.chullian.dchtest.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.chullian.dchtest.utils.LinePagerIndicatorDecoration
import com.chullian.dchtest.viewmodels.MainVm
import com.chullian.dchtest.adpters.CarouselAdapter
import com.chullian.dchtest.adpters.LabelsAdapter
import com.chullian.dchtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val carouselAdapter = CarouselAdapter()
    val labelAdapter = LabelsAdapter()
    lateinit var viewModel: MainVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainVm::class.java]
        viewModel.getCarousalData()
        viewModel.getLabelData()
        setupViews()
        observeChanges()
    }

    private fun observeChanges()  = with(viewModel){
        carouselData.observe(this@MainActivity){
            carouselAdapter.items = it
            carouselAdapter.notifyDataSetChanged()
        }
        labelData.observe(this@MainActivity){
            labelAdapter.items = it
        }
    }

    private fun setupViews()  = with(binding){

        //carouse setup
        carousel.apply {
            layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            adapter = carouselAdapter
            addItemDecoration(LinePagerIndicatorDecoration())
            PagerSnapHelper().attachToRecyclerView(this)
        }

        //searchbarSetup

        include.searchText.doAfterTextChanged {
            viewModel.searchLabel(it.toString())
        }

        //labelAdapterSetup
        labels.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = labelAdapter
        }

    }
}

