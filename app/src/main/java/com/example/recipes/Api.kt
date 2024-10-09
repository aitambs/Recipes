package com.example.recipes

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

data class ApiResponse(val error:String?=null, val data:String?=null)

class Api {
    companion object{
        private var instance: Api? = null
        private val syncObject:Any=Any()

        fun get(): Api {
            synchronized(syncObject){
                if (instance ==null){
                    instance = Api()
                }
            }
            return instance!!
        }
    }
    private val okHttpClient=OkHttpClient()
    fun getURL(url:String): ApiResponse{
        val request = Request.Builder().url(url).build()
        try {
            okHttpClient.newCall(request).execute().use { r ->
                return if (!r.isSuccessful) ApiResponse("Error code ${r.networkResponse?.code}", null)
                else ApiResponse(null,r.body.string())
            }
        } catch (e: IOException){
            e.printStackTrace()
            return ApiResponse("Internet not available?", null)
        }
    }
}