package com.data.movie.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.data.movie.movieapp.R;
import com.data.movie.movieapp.activity.BaseActivity;
import com.data.movie.movieapp.adapter.MovieListAdapter;
import com.data.movie.movieapp.adapter.MovieSearchHintAdapter;
import com.data.movie.movieapp.model.MovieDetailModel;
import com.data.movie.movieapp.model.SearchHintModel;
import com.data.movie.movieapp.parser.MovieResponseParser;
import com.data.movie.movieapp.utils.SearchHintInterface;
import com.data.movie.movieapp.utils.WebServiceInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Shailesh on 18/02/16.
 */
public class MovieListFragment extends Fragment implements SearchHintInterface {

    public static final String TAG = MovieListFragment.class.getSimpleName();
    private static final String FALSE_RESPONSE = "False";

    @Bind(R.id.edit_text_movie_name)
    protected EditText searchMovieView;
    @Bind(R.id.movie_list_view)
    protected RecyclerView movieListView;
    @Bind(R.id.recent_search_list_view)
    protected RecyclerView recentSearchListView;
    @Bind(R.id.progress_view)
    protected ProgressBar progressBar;
    @Bind(R.id.snack_bar_view)
    protected TextView snackBarView;

    private Context context;
    private MovieSearchHintAdapter searchHintAdapter;
    private MovieListAdapter movieListAdapter;
    private ArrayList<String> searchHintList = new ArrayList<>();
    private ArrayList<String> tempHintList = new ArrayList<>();
    private ArrayList<MovieResponseParser> movieParserList = new ArrayList<>();

    public MovieListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchMovieView.requestFocus();
        searchMovieView.setText("");
        setMovieAdapter(true);
        fetchAllHintFromDb();
        setRecentSearchAdapter();
        addMovieTextWatcher();
        onEditTextSearchDoneClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void addMovieTextWatcher() {
        searchMovieView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int
                    after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String searchHint = searchMovieView.getText().toString().trim();
                if (!TextUtils.isEmpty(searchHint)) {
                    if (searchHintList != null && !searchHintList.isEmpty()) {
                        tempHintList.clear();
                        progressBar.setVisibility(View.VISIBLE);
                        for (String hintString : searchHintList) {
                            if (hintString.toLowerCase().contains(searchHint.toLowerCase())) {
                                tempHintList.add(hintString);
                            }
                        }
                        setRecentSearchAdapter();
                    }
                } else {
                    tempHintList.clear();
                    setRecentSearchAdapter();
                    ArrayList<MovieResponseParser> movieParser = MovieDetailModel
                            .fetchAllSearchItem();
                    if (movieParser != null && !movieParser.isEmpty()) {
                        movieParserList.clear();
                        movieParserList.addAll(movieParser);
                        setMovieAdapter(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void onEditTextSearchDoneClick() {
        searchMovieView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    ((BaseActivity) context).hideKeyboard(searchMovieView, context);
                    String searchString = searchMovieView.getText().toString().trim();
                    if (!TextUtils.isEmpty(searchString)) {
                        progressBar.setVisibility(View.VISIBLE);
                        fetchMovieFromApi(searchString);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        ((BaseActivity) context).showSnackBar(snackBarView, getString(R.string
                                .enter_title));
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void fetchAllHintFromDb() {
        Observable.create(new Observable.OnSubscribe<ArrayList<String>>() {
            @Override
            public void call(Subscriber<? super ArrayList<String>> subscriber) {
                ArrayList<String> searchList = SearchHintModel.getSearchString();
                if (searchList != null && !searchList.isEmpty()) {
                    searchHintList.clear();
                    searchHintList.addAll(searchList);
                    subscriber.onNext(searchHintList);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Subscriber<ArrayList<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ArrayList<String> searchHintList) {
                    }
                });
    }

    private void fetchMovieFromApi(String movieTitle) {
        if (((BaseActivity) getActivity()).isNetworkAvailable()) {

            progressBar.setVisibility(View.VISIBLE);
            Map<String, String> mapParameters = new HashMap<>();
            mapParameters.put("t", movieTitle);
            mapParameters.put("y", "");
            mapParameters.put("plot", "");
            mapParameters.put("r", "");
            mapParameters.put("type", "movie");

            WebServiceInterface webServiceInterface = ((BaseActivity) getActivity()).getService();
            Call<MovieResponseParser> movieCall = webServiceInterface.getMovieDetails(mapParameters);
            movieCall.enqueue(new Callback<MovieResponseParser>() {
                @Override
                public void onResponse(Response<MovieResponseParser> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Log.i(TAG, "success");
                        MovieResponseParser movieResponseParser = response.body();
                        processMovieData(movieResponseParser);
                    } else if (!TextUtils.isEmpty(response.message())) {
                        ((BaseActivity) context).showSnackBar(snackBarView, response.message());
                    } else if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                        ((BaseActivity) context).showSnackBar(snackBarView, getString(R.string
                                .common_error));
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE);
                        ((BaseActivity) context).showSnackBar(snackBarView, getString(R.string
                                .common_error));
                    }
                }
            });
        } else if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
            ((BaseActivity) getActivity()).showSnackBar(snackBarView, getString(R.string
                    .network_error));
        }
    }

    private void processMovieData(final MovieResponseParser movieResponseParser) {
        if (movieResponseParser.getResponse().equals(FALSE_RESPONSE)) {
            progressBar.setVisibility(View.GONE);
            ((BaseActivity) context).hideKeyboard(snackBarView, context);
            ((BaseActivity) context).showSnackBar(snackBarView, context.getString(R.string
                    .search_not_found));
        } else {
            Observable.create(new Observable.OnSubscribe<MovieResponseParser>() {
                @Override
                public void call(Subscriber<? super MovieResponseParser> subscriber) {
                    SearchHintModel.saveSearchHint(movieResponseParser.getTitle());
                    MovieDetailModel.saveMovieDetails(movieResponseParser);
                    if (!searchHintList.contains(movieResponseParser.getTitle()))
                        searchHintList.add(movieResponseParser.getTitle());
                    subscriber.onNext(movieResponseParser);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                    new Subscriber<MovieResponseParser>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(MovieResponseParser movieResponseParser) {
                            if (movieResponseParser != null) {
                                movieParserList.clear();
                                movieParserList.add(movieResponseParser);
                                Log.i(TAG, "movieParser size " + movieParserList.size());
                                setMovieAdapter(false);
                            }
                        }
                    });
        }
    }

    private void setRecentSearchAdapter() {
        if (recentSearchListView != null) {
            recentSearchListView.setVisibility(View.VISIBLE);
            movieListView.setVisibility(View.GONE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recentSearchListView.setLayoutManager(layoutManager);

            searchHintAdapter = new MovieSearchHintAdapter(context, tempHintList, this);
            recentSearchListView.setAdapter(searchHintAdapter);
            progressBar.setVisibility(View.GONE);
        }
    }

    private void setMovieAdapter(boolean isNewFragment) {
        if (movieListView != null) {
            movieListView.setVisibility(View.VISIBLE);
            recentSearchListView.setVisibility(View.GONE);
            if (movieListAdapter != null && !isNewFragment) {
                movieListAdapter.notifyDataSetChanged();
            } else {
                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                movieListView.setLayoutManager(layoutManager);

                movieListAdapter = new MovieListAdapter(context, movieParserList);
                movieListView.setAdapter(movieListAdapter);
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onHintClick(String hintString) {
        movieListView.setVisibility(View.VISIBLE);
        recentSearchListView.setVisibility(View.GONE);
        fetchMoviesFromDatabase(hintString);
    }

    private void fetchMoviesFromDatabase(String searchString) {
        if (progressBar != null) {
            ArrayList<MovieResponseParser> movieResponseParsers = MovieDetailModel
                    .fetchRecentSearch(searchString);

            if (movieResponseParsers != null && !movieResponseParsers.isEmpty()) {
                movieParserList.clear();
                movieParserList.addAll(movieResponseParsers);
                setMovieAdapter(false);
            } else if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }
        }
    }
}
