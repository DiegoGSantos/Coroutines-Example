package com.diego.coroutinesexample.restClient.response

import com.google.gson.annotations.Expose

class Event(@Expose val descricao: String) {
    override fun toString(): String {
        return descricao
    }
}