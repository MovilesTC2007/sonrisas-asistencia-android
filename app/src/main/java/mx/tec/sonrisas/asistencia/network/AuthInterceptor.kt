package mx.tec.sonrisas.asistencia.network

import mx.tec.sonrisas.asistencia.data.local.TokenStore
import okhttp3.Interceptor
import okhttp3.Response

// Adjunta el access_token a cada request. Si el backend responde 401 (token
// vencido -- hoy dura solo 30 min y no hay refresh token, ver TODO.md del
// backend), limpia la sesion local; la app detecta la sesion perdida la
// proxima vez que revise TokenStore.hasSession() (arranque o logout manual),
// no intenta redirigir a login desde aqui para mantener el interceptor simple.
class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenStore.getAccessToken()
        val request = if (token != null) {
            chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()
        } else {
            chain.request()
        }

        val response = chain.proceed(request)
        if (response.code == 401) {
            TokenStore.clear()
        }
        return response
    }
}
