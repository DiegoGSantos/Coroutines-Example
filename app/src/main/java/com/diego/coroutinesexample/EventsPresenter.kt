package com.diego.coroutinesexample

import com.diego.coroutinessample.restClient.Service
import com.diego.coroutinessample.restClient.awaitResponse

class EventsPresenter(val view: MainActivity) {
    suspend fun getEvents() {
        try {
            val tokenTask = Service.create()
                    .requestToken().awaitResponse()
            val token = tokenTask.body()?.token
            val tokenType = tokenTask.body()?.tokenType

            val events = Service.create()
                    .getEvents("$tokenType $token").awaitResponse()

            events.body()?.let { view.displayEvents(it) }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            view.showError()
        }
    }
}