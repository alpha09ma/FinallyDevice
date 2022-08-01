package com.example.finallydevice.tomatocolock

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finallydevice.tomatocolock.function.ColockTimer
import kotlinx.coroutines.launch

class TomatoColockViewModel : ViewModel() {

    private  val _time= MutableLiveData<String>()
    val time=_time
    val timer=ColockTimer()
    // TODO: Implement the ViewModel
    fun setTime(timesum:String){
        timer.starttime(timesum,_time::postValue)
    }
    fun pausetime(){
        timer.cancel()
    }
}