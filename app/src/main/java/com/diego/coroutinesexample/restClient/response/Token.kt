package com.diego.coroutinesexample.restClient.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token(@Expose @SerializedName("Access-Token") val token: String)