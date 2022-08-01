package com.example.finallydevice.AppusingStatics.AppusingPieChart

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finallydevice.AppusingStatics.appusinglist.dataclass.Appusingdata_hour_database
import com.example.finallydevice.repositry.AppusingRepository
import kotlinx.coroutines.launch

class AppusingStaticsViewModel(val context: Context) : ViewModel() {
    val appusinglist: LiveData<List<Appusingdata_hour_database>>
    val repository:AppusingRepository
    init {
        repository= AppusingRepository(context)
        appusinglist=repository.appusinglist
    }
    fun setdata(){
        viewModelScope.launch {
            repository.setlist()
        }
    }
}