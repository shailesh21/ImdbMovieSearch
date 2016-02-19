package com.data.movie.movieapp.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;
import com.data.movie.movieapp.parser.MovieResponseParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shailesh on 19/02/16.
 */
public class MovieDetailModel extends Model {

    @Column(name = "title")
    private String title;
    @Column(name = "year")
    private String year;
    @Column(name = "rated")
    private String rated;
    @Column(name = "released")
    private String released;
    @Column(name = "runtime")
    private String runtime;
    @Column(name = "genre")
    private String genre;
    @Column(name = "director")
    private String director;
    @Column(name = "writer")
    private String writer;
    @Column(name = "actors")
    private String actors;
    @Column(name = "plot")
    private String plot;
    @Column(name = "language")
    private String language;
    @Column(name = "country")
    private String country;
    @Column(name = "awards")
    private String awards;
    @Column(name = "poster")
    private String poster;
    @Column(name = "meta_score")
    private String metaScore;
    @Column(name = "imdb_rating")
    private String imdbRating;
    @Column(name = "imdb_votes")
    private String imdbVotes;
    @Column(name = "imdb_id")
    private String imdbId;
    @Column(name = "type")
    private String type;
    @Column(name = "response")
    private String response;

    public MovieDetailModel() {
    }

    public MovieDetailModel(MovieResponseParser movieResponseParser) {
        this.title = movieResponseParser.getTitle();
        this.year = movieResponseParser.getYear();
        this.rated = movieResponseParser.getRated();
        this.released = movieResponseParser.getReleased();
        this.runtime = movieResponseParser.getRuntime();
        this.genre = movieResponseParser.getGenre();
        this.director = movieResponseParser.getDirector();
        this.writer = movieResponseParser.getWriter();
        this.actors = movieResponseParser.getActors();
        this.plot = movieResponseParser.getPlot();
        this.language = movieResponseParser.getLanguage();
        this.country = movieResponseParser.getCountry();
        this.awards = movieResponseParser.getAwards();
        this.poster = movieResponseParser.getPoster();
        this.metaScore = movieResponseParser.getMetascore();
        this.imdbRating = movieResponseParser.getImdbRating();
        this.imdbVotes = movieResponseParser.getImdbVotes();
        this.imdbId = movieResponseParser.getImdbID();
        this.type = movieResponseParser.getType();
        this.response = movieResponseParser.getResponse();
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getMetaScore() {
        return metaScore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

    public static void saveMovieDetails(MovieResponseParser movieResponseParser) {
        if (movieResponseParser != null) {
            ActiveAndroid.beginTransaction();
            try {
                new MovieDetailModel(movieResponseParser).save();
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
    }

    public static ArrayList<MovieResponseParser> fetchRecentSearch(String searchString) {
        ArrayList<MovieResponseParser> movieResponseParsers = new ArrayList<>();
        List<MovieDetailModel> moviesList = new Select().from(MovieDetailModel.class).where
                ("title = ?", searchString).execute();

        if (moviesList != null && !moviesList.isEmpty()) {
            int size = moviesList.size();
            for (int loop = size - 1; loop >= 0; loop--) {
                movieResponseParsers.add(new MovieResponseParser(moviesList.get(loop)));
            }
        }
        return movieResponseParsers;
    }

    public static ArrayList<MovieResponseParser> fetchAllSearchItem() {
        ArrayList<MovieResponseParser> movieResponseParsers = new ArrayList<>();
        List<MovieDetailModel> moviesList = new Select().from(MovieDetailModel.class).execute();

        if (moviesList != null && !moviesList.isEmpty()) {
            int size = moviesList.size();
            for (int loop = size - 1; loop >= 0; loop--) {
                movieResponseParsers.add(new MovieResponseParser(moviesList.get(loop)));
            }
        }
        return movieResponseParsers;
    }
}
