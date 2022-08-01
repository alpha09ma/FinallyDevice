package com.example.finallydevice.ScreenStatics.home

import androidx.lifecycle.*

class Lifecycleobsever(val viewModel: HomeViewModel): LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event)
        {
            Lifecycle.Event.ON_START->{viewModel.starttime()}
            Lifecycle.Event.ON_PAUSE->{viewModel.endtime()}
        }
    }

}