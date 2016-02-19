package com.data.movie.movieapp.model;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shailesh on 19/02/16.
 */
public class SearchHintModel extends Model {

    private static final String TAG = SearchHintModel.class.getSimpleName();

    @Column(name = "search_text")
    private String searchText;

    public SearchHintModel() {
    }

    public SearchHintModel(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public static void saveSearchHint(String searchString) {
        if (!isAlreadyExist(searchString)) {
            SearchHintModel searchHintModel = new SearchHintModel(searchString);
            searchHintModel.save();
        }
    }

    private static boolean isAlreadyExist(String searchString) {
        boolean isAlreadyExists;
        List<SearchHintModel> searchList =  new Select().from(SearchHintModel.class).where
                ("search_text = ?", searchString).execute();
        if (searchList != null && !searchList.isEmpty()) {
            isAlreadyExists = true;
        } else {
            isAlreadyExists = false;
        }
        Log.i(TAG, "isAlreadyExists " + isAlreadyExists);
        return isAlreadyExists;
    }

    public static ArrayList<String> getSearchString() {
        ArrayList<String> hintList = new ArrayList<>();
        List<SearchHintModel> searchList =  new Select().from(SearchHintModel.class).execute();
        if (searchList != null && !searchList.isEmpty()) {
            for (SearchHintModel searchHintModel : searchList) {
                hintList.add(searchHintModel.getSearchText());
            }
        }
        return hintList;
    }
}
