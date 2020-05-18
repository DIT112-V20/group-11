package com.example.trailgage.backend

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.*

interface Endpoint {

    @GET("drive?{speedorAngel}=100")
    fun getspeedorAngel(@Path("speedOrAngel")speedOrAngel:String)

    //@GET("drive?{speedorAngel}={speedOrAngelInt}")
    //fun getspeedorAngel(@Path("speedOrAngel")speedOrAngel:String,@Path("speedOrAngelInt")speedOrAngelInt:Int)


}