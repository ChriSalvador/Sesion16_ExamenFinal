package com.example.sesion16_examenfinal.service;

import com.example.sesion16_examenfinal.model.Feriado;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PublicHolidayApiService {
    @GET("{year}/{countryCode}")
    Call<List<Feriado>> getFeriados(
            @Path("year") int year,
            @Path("countryCode") String countryCode
    );
}
