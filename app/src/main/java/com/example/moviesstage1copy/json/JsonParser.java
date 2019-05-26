package com.example.moviesstage1copy.json;

import com.example.moviesstage1copy.model.MovieModel;
import com.example.moviesstage1copy.model.VideoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public MovieModel[] parseJson(String j)
    {
        final String RESULTS = "results";
        final String ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String TRAILER = "video";
        JSONObject moviesJson = null;
        try {
            moviesJson = new JSONObject(j);
            JSONArray resultsArray = moviesJson.getJSONArray(RESULTS);
            MovieModel[] movies = new MovieModel[resultsArray.length()];
            for (int i = 0; i < resultsArray.length(); i++) {
                movies[i] = new MovieModel();
                JSONObject movieInfo = resultsArray.getJSONObject(i);
                movies[i].setOriginalTitle(movieInfo.getString(ORIGINAL_TITLE));
                 movies[i].setPosterPath(movieInfo.getString(POSTER_PATH));
                 movies[i].setOverview(movieInfo.getString(OVERVIEW));
                Double test=movieInfo.getDouble(VOTE_AVERAGE);
                int id=movieInfo.getInt("id");
                String vt=test.toString();
                movies[i].setVoteAverage(vt);
                movies[i].setMovieId(id);
                movies[i].setReleaseDate(movieInfo.getString(RELEASE_DATE));
            }
            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    public VideoModel[] parseVideoJson(String j)
    {
        JSONObject root = null;
        try {
            root = new JSONObject(j);
            JSONArray results = root.getJSONArray("results");
            VideoModel[] videos = new VideoModel[results.length()];
            for(int i=0;i<results.length();i++)
            {
                videos[i]=new VideoModel();
                JSONObject result_item = results.getJSONObject(i);
                videos[i].setKey( result_item.getString("key"));
                videos[i].setName(result_item.getString("name"));
            }
            return  videos;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
