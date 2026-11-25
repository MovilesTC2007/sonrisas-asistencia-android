package mx.tec.sonrisas.asistencia.data.remote

import mx.tec.sonrisas.asistencia.BuildConfig
import mx.tec.sonrisas.asistencia.network.HttpClientProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val api: SonrisasApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(HttpClientProvider.client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SonrisasApiService::class.java)
    }
}
