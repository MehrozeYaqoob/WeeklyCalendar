package com.parim.weeklycalendar

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.parim.weeklycalendar.contracts.IHolidayAPI
import com.parim.weeklycalendar.model.RequestDTO
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
class IHolidayAPITest {
    @get:Rule
    val mockWebServer = MockWebServer()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val holidaysHttpClient = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val holidayApi by lazy {
        holidaysHttpClient.create(IHolidayAPI::class.java)
    }

    lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun getHolidays() {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(
                    response
                )
                .setResponseCode(200)
        )
        val call = holidayApi.getHolidays(
            RequestDTO(
                "apiKey",
                "2021-12-12",
                "2021-12-19"
            )
        )
        try {
            val response = call.execute()
            val holidays = response.body()!!
            Assert.assertTrue(response.isSuccessful)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        val response = "{\n" +
                "  \"error\": false,\n" +
                "  \"holidays\": {\n" +
                "    \"2019-02-02\": [\n" +
                "      {\n" +
                "        \"name\": \"Küünlapäev ehk pudrupäev\",\n" +
                "        \"type\": \"folk\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"2019-02-09\": [\n" +
                "      {\n" +
                "        \"name\": \"Luuvalupäev\",\n" +
                "        \"type\": \"folk\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"2019-02-22\": [\n" +
                "      {\n" +
                "        \"name\": \"Talvine peetripäev\",\n" +
                "        \"type\": \"folk\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"2019-02-24\": [\n" +
                "      {\n" +
                "        \"name\": \"Iseseisvuspäev\",\n" +
                "        \"type\": \"public\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"name\": \"Talvine madisepäev\",\n" +
                "        \"type\": \"folk\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}"
    }
}