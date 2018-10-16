package com.diego.coroutinesexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import com.diego.coroutinesexample.restClient.response.Event
import com.diego.coroutinessample.restClient.Service
import com.diego.coroutinessample.restClient.awaitResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var eventsAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        launch(UI) {
            try {
                val tokenTask = Service.create()
                        .requestToken().awaitResponse()
                val token = tokenTask.body()?.token
                val events = Service.create()
                        .getEvents("Bearer $token").awaitResponse()

                displayEvents(events)
            } catch (trowable: Throwable) {
                errorMessage.visibility = VISIBLE
                progress.visibility = GONE
                eventsList.visibility = GONE
            }
        }
    }

    private fun displayEvents(events: Response<List<Event>>) {
        eventsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            eventsAdapter = CustomAdapter()
            adapter = eventsAdapter
        }

        progress.visibility = GONE
        eventsList.visibility = VISIBLE

        events.body()?.reversed()?.let { eventsAdapter.setList(it) }
    }
}
