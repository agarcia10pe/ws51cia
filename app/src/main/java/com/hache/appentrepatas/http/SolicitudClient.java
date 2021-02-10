package com.hache.appentrepatas.http;

import com.hache.appentrepatas.util.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SolicitudClient {
    private static SolicitudClient instance = null;
    private SolicitudService solicitudService;
    private Retrofit retrofit;

    public SolicitudClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.API_SOLICITUD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        solicitudService = retrofit.create(SolicitudService.class);
    }

    // Patrón Singleton
    public static SolicitudClient getInstance() {
        if(instance == null) {
            instance = new SolicitudClient();
        }
        return instance;
    }

    public SolicitudService getSolicitudService() {
        return solicitudService;
    }
}
