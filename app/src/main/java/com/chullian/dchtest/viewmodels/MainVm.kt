package com.chullian.dchtest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chullian.dchtest.data.models.LabelItem
import com.chullian.dchtest.data.models.SliderItem
import com.chullian.dchtest.data.repository.Repository

class MainVm:ViewModel() {
    var carouselData = MutableLiveData<List<SliderItem>>()
    var labelData = MutableLiveData<List<LabelItem>>()

    fun getCarousalData() {
        carouselData.postValue( Repository.carouselItems )
    }

    fun getLabelData() {
        labelData.postValue( Repository.labelItems )
    }

    fun searchLabel(searchTerm: String) {
        labelData.postValue(Repository.labelItems.filter { it.name.contains(searchTerm,true) })
    }
}