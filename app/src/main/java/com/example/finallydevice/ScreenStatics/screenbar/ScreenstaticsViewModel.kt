package com.example.finallydevice.ScreenStatics.screenbar

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finallydevice.ScreenStatics.home.dataclass.Screenusingdata
import com.example.finallydevice.repositry.ScreenusingRepository

class ScreenstaticsViewModel(context: Context,type: String) : ViewModel() {
    private val _screenstaticslist:LiveData<List<Screenusingdata>>?
    val screenstaticslist:LiveData<List<Screenusingdata>>? get() = _screenstaticslist
    private val repository:ScreenusingRepository
    init {
        repository= ScreenusingRepository(context)
        _screenstaticslist=repository.getinputtimedata()

    }
    fun getstatics_today(type: String):Long{
        Log.d("pppp",_screenstaticslist?.value.toString())
        return repository.getstatics(type)
    }
}