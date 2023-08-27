package com.android.testapp.core.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<STATE> : ViewModel() {

    private val _viewStates: MutableStateFlow<STATE?> = MutableStateFlow(null)
    val viewStates = _viewStates.asStateFlow()

    private val _viewEvents: MutableSharedFlow<STATE?> = MutableSharedFlow(
    )
    val viewEvents = _viewEvents.asSharedFlow()

    protected fun setValue(event: STATE) {
        _viewStates.value = event
    }

    protected fun emitState(
        event: STATE,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        isSingleLife: Boolean = false
    ) {
        viewModelScope.launch(dispatcher) {
            if (isSingleLife) {
                _viewEvents.emit(event)
            } else {
                _viewStates.emit(event)
            }
        }
    }

    protected fun emitEvent(
        event: STATE,
        dispatcher: CoroutineDispatcher = Dispatchers.Main
    ) {
        viewModelScope.launch(dispatcher) {
            _viewEvents.emit(event)
        }
    }

    protected inline fun <JOB> backgroundJob(crossinline block: suspend () -> JOB) {
        viewModelScope.launch(Dispatchers.IO) {
            block.invoke()
        }
    }

    protected inline fun <JOB> uiJob(crossinline block: suspend () -> JOB) {
        viewModelScope.launch(Dispatchers.Main) {
            block.invoke()
        }
    }

    protected inline fun <JOB> backgroundJobBlocking(crossinline block: suspend () -> JOB) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                block.invoke()
            }
        }
    }

}