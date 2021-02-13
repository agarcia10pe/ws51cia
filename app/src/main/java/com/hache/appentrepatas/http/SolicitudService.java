package com.hache.appentrepatas.http;

import com.hache.appentrepatas.dto.AprobacionSolicitudRequest;
import com.hache.appentrepatas.dto.BaseResponse;
import com.hache.appentrepatas.dto.ClienteRequest;
import com.hache.appentrepatas.dto.ConfirmacionSolicitudRequest;
import com.hache.appentrepatas.dto.LoginRequest;
import com.hache.appentrepatas.dto.PerroDTO;
import com.hache.appentrepatas.dto.PerroPartialDTO;
import com.hache.appentrepatas.dto.SolicitarAdopcionRequest;
import com.hache.appentrepatas.dto.SolicitudDTO;
import com.hache.appentrepatas.dto.SolicitudPartialDTO;
import com.hache.appentrepatas.dto.UsuarioDTO;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SolicitudService {
    @POST("api/seguridad/login")
    Call<BaseResponse<UsuarioDTO>> autenticarUsuario(@Body LoginRequest request);

    @POST("api/seguridad/registrar-cliente")
    Call<BaseResponse<String>> registrarCliente(@Body ClienteRequest request);

    @GET("api/solicitud/listar-perros")
    Call<BaseResponse<ArrayList<PerroPartialDTO>>> listarPerro();

    @GET("api/solicitud/listar-perros/detalle/{idPerro}")
    Call<BaseResponse<PerroDTO>> obtenerPerroPorId(@Path("idPerro") int idPerro);

    @Multipart
    @POST("api/solicitud/registrar-perro")
    Call<BaseResponse<String>> registrarPerro(@Part MultipartBody.Part file, @Part("perro") RequestBody perro);

    @POST("api/solicitud/solicitar-adopcion")
    Call<BaseResponse<String>> solicitarAdopcion(@Body SolicitarAdopcionRequest request);

    @POST("api/solicitud/confirmar-solicitud")
    Call<BaseResponse<String>> confirmarSolicitud(@Body ConfirmacionSolicitudRequest request);

    @POST("api/solicitud/rechazar-solicitud")
    Call<BaseResponse<String>> rechazarSolicitud(@Body AprobacionSolicitudRequest request);

    @POST("api/solicitud/aprobar-solicitud")
    Call<BaseResponse<String>> aprobarSolicitud(@Body AprobacionSolicitudRequest request);

    @GET("api/solicitud/listar-solicitudes")
    Call<BaseResponse<ArrayList<SolicitudDTO>>> listarSolicitudes();

    @GET("api/solicitud/mis-solicitudes/{correo}")
    Call<BaseResponse<ArrayList<SolicitudPartialDTO>>> listarSolicitudesPorCorreo(@Path("correo") String correo);

    @DELETE("api/solicitud/eliminar-solicitud/{correo}")
    Call<BaseResponse<String>> eliminarSolicitudInicializada(@Path("correo") String correo);
}
