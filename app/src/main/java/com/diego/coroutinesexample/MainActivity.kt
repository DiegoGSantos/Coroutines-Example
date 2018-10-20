package com.diego.coroutinesexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View.GONE
import android.view.View.VISIBLE
import com.diego.coroutinesexample.restClient.response.Event
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var eventsAdapter: CustomAdapter
    private val presenter = EventsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
           presenter.getEvents()
        }
    }

    fun showError() {
        errorMessage.visibility = VISIBLE
        progress.visibility = GONE
        eventsList.visibility = GONE
    }

    fun displayEvents(events: List<Event>) {
        eventsList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            eventsAdapter = CustomAdapter()
            adapter = eventsAdapter
        }

        progress.visibility = GONE
        eventsList.visibility = VISIBLE

        events.reversed().let { eventsAdapter.setList(it) }
    }
}
