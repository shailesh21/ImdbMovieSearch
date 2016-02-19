package com.data.movie.movieapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.data.movie.movieapp.R;
import com.data.movie.movieapp.utils.SearchHintInterface;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shailesh on 19/02/16.
 */
public class MovieSearchHintAdapter extends RecyclerView.Adapter<MovieSearchHintAdapter.ViewHolder> {

    private static final String TAG = MovieSearchHintAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<String> searchHintList = new ArrayList<>();
    private SearchHintInterface searchHintInterface;

    public MovieSearchHintAdapter(Context context, ArrayList<String> searchHintList,
                                  SearchHintInterface searchHintInterface) {
        this.context = context;
        this.searchHintList = searchHintList;
        this.searchHintInterface = searchHintInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_search_hint, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.searchHintView.setText(searchHintList.get(position)+"");
        onRowClick(holder.searchHintView, position);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (searchHintList != null && !searchHintList.isEmpty()) {
            count = searchHintList.size();
        }
        return count;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.search_hint_view)
        protected TextView searchHintView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void onRowClick(TextView searchHintView, final int position) {
        searchHintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchString = searchHintList.get(position);
                searchHintInterface.onHintClick(searchString);
            }
        });
    }
}
