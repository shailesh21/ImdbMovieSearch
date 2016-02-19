package com.data.movie.movieapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.data.movie.movieapp.R;
import com.data.movie.movieapp.activity.BaseActivity;
import com.data.movie.movieapp.activity.MovieListActivity;
import com.data.movie.movieapp.fragment.MovieDetailFragment;
import com.data.movie.movieapp.parser.MovieResponseParser;
import com.data.movie.movieapp.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shailesh on 19/02/16.
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MovieResponseParser> movieParserList = new ArrayList<>();

    public MovieListAdapter(Context context, ArrayList<MovieResponseParser> movieParser) {
        this.context = context;
        this.movieParserList = movieParser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_movie_list, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieResponseParser movieResponseParser = movieParserList.get(position);

        holder.movieTitle.setText(movieResponseParser.getTitle());
        Picasso.with(context).load(movieResponseParser.getPoster()).placeholder(android.R
                .drawable.ic_menu_gallery).error(android.R.drawable.ic_menu_close_clear_cancel)
                .fit().centerCrop().into(holder.movieCoverView);
        onRowClick(holder.movieCardView, position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (movieParserList != null && !movieParserList.isEmpty()) {
            count = movieParserList.size();
        }
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.movie_card_view)
        protected CardView movieCardView;
        @Bind(R.id.text_view_movie_title)
        protected TextView movieTitle;
        @Bind(R.id.image_view_movie_cover)
        protected ImageView movieCoverView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void onRowClick(final CardView cardView, final int position) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MovieResponseParser movieResponseParser = movieParserList.get(position);

                if (movieResponseParser != null) {
                    ((BaseActivity) context).hideKeyboard(cardView, context);
                    MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.BUNDLE_MOVIE_DETAIL, movieResponseParser);
                    movieDetailFragment.setArguments(bundle);

                    FragmentManager manager = ((MovieListActivity) context)
                            .getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.addToBackStack(MovieDetailFragment.TAG);
                    transaction.replace(R.id.content_view, movieDetailFragment,
                            MovieDetailFragment.TAG);
                    transaction.commit();
                }
            }
        });
    }
}
