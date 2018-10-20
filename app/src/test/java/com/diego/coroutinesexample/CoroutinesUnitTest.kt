package com.diego.coroutinesexample

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.io.File

class CoroutinesUnitTest {

    // Work around to user "any()" Matcher
    // for not nullable parameters
    // Casting null to the type required
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    @Mock lateinit var view: MainActivity
    lateinit var presenter: EventsPresenter

    lateinit var server: MockWebServer

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventsPresenter(view)

        server = MockWebServer()
        server.start()
    }

    @Test
    fun getEventListTestSuccess() {
        server.enqueue(MockResponse().setBody(getJson("json/token.json")))
        server.enqueue(MockResponse().setBody(getJson("json/eventos.json")))

        Constants.BASE_URL = server.url("/").toString()

        runBlocking {
            presenter.getEvents()
        }

        verify(view, times(1)).displayEvents(any())
        verify(view, times(0)).showError()
    }

    @Test
    fun getEventListTestError() {
        server.enqueue(MockResponse().setResponseCode(400))

        Constants.BASE_URL = server.url("/").toString()

        runBlocking {
            presenter.getEvents()
        }

        verify(view, times(0)).displayEvents(any())
        verify(view, times(1)).showError()
    }

    fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
