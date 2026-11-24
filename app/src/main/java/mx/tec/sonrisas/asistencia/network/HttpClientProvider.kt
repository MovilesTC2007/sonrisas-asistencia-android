package mx.tec.sonrisas.asistencia.network

import okhttp3.OkHttpClient

object HttpClientProvider {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }
}
