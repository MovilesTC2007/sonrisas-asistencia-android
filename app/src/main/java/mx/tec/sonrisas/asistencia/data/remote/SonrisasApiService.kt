package mx.tec.sonrisas.asistencia.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

// Endpoints del backend FastAPI. Agregar aqui los que se vayan necesitando.
interface SonrisasApiService {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequestDto): LoginResponseDto

    // Se llama justo despues de login(), antes de guardar la sesion, por eso
    // el token va explicito en vez de dejarselo al AuthInterceptor.
    @GET("roles/{roleId}")
    suspend fun getRole(
        @Path("roleId") roleId: String,
        @Header("Authorization") bearerToken: String,
    ): RoleResponseDto
}
