package com.example.vama.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel<T>(initState: T) : ViewModel() {
    private val _state: StateView<T> = StateView(initState)

    fun subscribeOnState(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) {
        _state.observe(lifecycleOwner, observer)
    }

    fun getCurrentState(): T {
        return _state.value!!
    }

    protected suspend fun notifyViewState(newState: T) {
        withContext(Dispatchers.Main) {
            newState?.let {
                _state.postValue(it)
            }
        }
    }

    protected suspend fun notifyViewStates(vararg newState: T) {
        withContext(Dispatchers.Main) {
            newState.forEach {
                _state.postValue(it)
            }
        }
    }
}