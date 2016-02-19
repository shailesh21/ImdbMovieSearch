package com.data.movie.movieapp.utils;

import com.data.movie.movieapp.parser.MovieResponseParser;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Shailesh on 18/02/16.
 */
public interface WebServiceInterface {

    @GET("/")
    Call<MovieResponseParser> getMovieDetails(@QueryMap Map<String, String> mapParameters);

}
