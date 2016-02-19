package com.data.movie.movieapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.data.movie.movieapp.R;
import com.data.movie.movieapp.parser.MovieResponseParser;
import com.data.movie.movieapp.utils.Constants;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shailesh on 18/02/16.
 */
public class MovieDetailFragment extends Fragment {

    public static final String TAG = MovieDetailFragment.class.getSimpleName();

    @Bind(R.id.image_view_movie_cover)
    protected ImageView imageViewMovieCover;
    @Bind(R.id.text_view_movie_title)
    protected TextView textViewTitle;
    @Bind(R.id.text_view_plot)
    protected TextView textViewPlot;
    @Bind(R.id.text_view_rating)
    protected TextView textViewRating;
    @Bind(R.id.text_view_actors)
    protected TextView textViewActor;
    @Bind(R.id.text_view_released)
    protected TextView textViewReleased;
    @Bind(R.id.text_view_genre)
    protected TextView textViewGenre;
    @Bind(R.id.text_view_director)
    protected TextView textViewDirector;
    @Bind(R.id.text_view_writer)
    protected TextView textViewWriter;

    private MovieResponseParser movieResponseParser;
    private Context context;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        movieResponseParser = (MovieResponseParser) bundle.getSerializable(Constants
                .BUNDLE_MOVIE_DETAIL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        setValueInViews();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setValueInViews() {
        if (movieResponseParser != null && imageViewMovieCover != null) {

            if (!TextUtils.isEmpty(movieResponseParser.getTitle())) {
                textViewTitle.setText(movieResponseParser.getTitle());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getReleased())) {
                textViewReleased.setText(movieResponseParser.getReleased());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getGenre())) {
                textViewGenre.setText(movieResponseParser.getGenre());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getDirector())) {
                textViewDirector.setText(movieResponseParser.getDirector());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getWriter())) {
                textViewWriter.setText(movieResponseParser.getWriter());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getActors())) {
                textViewActor.setText(movieResponseParser.getActors());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getPlot())) {
                textViewPlot.setText(movieResponseParser.getPlot());
            }
            if (!TextUtils.isEmpty(movieResponseParser.getImdbRating())) {
                textViewRating.setText(movieResponseParser.getImdbRating());
            }

            Picasso.with(context).load(movieResponseParser.getPoster()).placeholder(android.R.drawable
                    .ic_menu_gallery).error(android.R.drawable.ic_menu_close_clear_cancel).fit()
                    .centerCrop().into(imageViewMovieCover);
        }
    }
}
