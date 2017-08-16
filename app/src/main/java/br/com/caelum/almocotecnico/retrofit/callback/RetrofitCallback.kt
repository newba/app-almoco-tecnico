package br.com.caelum.almocotecnico.retrofit.callback

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by alex on 16/08/17.
 */
class RetrofitCallback {
    fun <T> callback(execution: (Response<T>?, Throwable?) -> Unit): Callback<T> {
        return object : Callback<T> {
            override fun onFailure(call: Call<T>?, t: Throwable?) = execution(null, t)

            override fun onResponse(call: Call<T>?, response: Response<T>?) = execution(response, null)
        }
    }
}