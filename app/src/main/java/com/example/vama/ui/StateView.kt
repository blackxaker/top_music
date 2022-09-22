package com.example.vama.ui

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class StateView<E>(initState: E) : MutableLiveData<E>(initState) {
    private val mPending = AtomicBoolean(false)
    private val values: Queue<E> = LinkedList()

    init {
        postValue(initState)
    }

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in E>) {
        if (hasActiveObservers()) {
            Log.w(
                this::class.java.name,
                "Multiple observers registered but only one will be notified of changes."
            )
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t: E ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
                //call next value processing if have such
                if (values.isNotEmpty())
                    pollValue()
            }
        }
    }

    @MainThread
    override fun postValue(value: E) {
        values.add(value)
        pollValue()
    }

    private fun pollValue() {
        if (!mPending.get()) {
            value = values.poll()
        }
    }

    @MainThread
    override fun setValue(t: E?) {
        mPending.set(true)
        super.setValue(t)
    }
}