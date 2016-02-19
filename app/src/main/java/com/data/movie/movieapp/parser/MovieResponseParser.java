package com.data.movie.movieapp.parser;

import com.data.movie.movieapp.model.MovieDetailModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shailesh on 18/02/16.
 */
public class MovieResponseParser implements Serializable {

    @SerializedName("Title")
    private String Title;
    @SerializedName("Year")
    private String Year;
    @SerializedName("Rated")
    private String Rated;
    @SerializedName("Released")
    private String Released;
    @SerializedName("Runtime")
    private String Runtime;
    @SerializedName("Genre")
    private String Genre;
    @SerializedName("Director")
    private String Director;
    @SerializedName("Writer")
    private String Writer;
    @SerializedName("Actors")
    private String Actors;
    @SerializedName("Plot")
    private String Plot;
    @SerializedName("Language")
    private String Language;
    @SerializedName("Country")
    private String Country;
    @SerializedName("Awards")
    private String Awards;
    @SerializedName("Poster")
    private String Poster;
    @SerializedName("Metascore")
    private String Metascore;
    @SerializedName("imdbRating")
    private String imdbRating;
    @SerializedName("imdbVotes")
    private String imdbVotes;
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Type")
    private String Type;
    @SerializedName("Response")
    private String Response;

    public MovieResponseParser() {
    }

    public MovieResponseParser(MovieDetailModel movieDetailModel) {
        Title = movieDetailModel.getTitle();
        Year = movieDetailModel.getYear();
        Rated = movieDetailModel.getRated();
        Released = movieDetailModel.getReleased();
        Runtime = movieDetailModel.getRuntime();
        Genre = movieDetailModel.getGenre();
        Director = movieDetailModel.getDirector();
        Writer = movieDetailModel.getWriter();
        Actors = movieDetailModel.getActors();
        Plot = movieDetailModel.getPlot();
        Language = movieDetailModel.getLanguage();
        Country = movieDetailModel.getCountry();
        Awards = movieDetailModel.getAwards();
        Poster = movieDetailModel.getPoster();
        Metascore = movieDetailModel.getMetaScore();
        this.imdbRating = movieDetailModel.getImdbRating();
        this.imdbVotes = movieDetailModel.getImdbVotes();
        this.imdbID = movieDetailModel.getImdbId();
        Type = movieDetailModel.getType();
        Response = movieDetailModel.getResponse();
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getRated() {
        return Rated;
    }

    public String getReleased() {
        return Released;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public String getDirector() {
        return Director;
    }

    public String getWriter() {
        return Writer;
    }

    public String getActors() {
        return Actors;
    }

    public String getPlot() {
        return Plot;
    }

    public String getLanguage() {
        return Language;
    }

    public String getCountry() {
        return Country;
    }

    public String getAwards() {
        return Awards;
    }

    public String getPoster() {
        return Poster;
    }

    public String getMetascore() {
        return Metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return Type;
    }

    public String getResponse() {
        return Response;
    }
}
