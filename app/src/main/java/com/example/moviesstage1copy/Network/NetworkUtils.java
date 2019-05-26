package com.example.moviesstage1copy.Network;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {


    public static URL buildUrl(String sort_order) throws MalformedURLException {//String[] parameters
        final String TMDB_BASE_URL = "https://api.themoviedb.org/3/movie/"+sort_order+"?";
        final String API_KEY_PARAM = "api_key";
        final String API_KEY = "a2ae95b3c083c023197df998e1ef4475";

        Uri builtUri = Uri.parse(TMDB_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        return new URL(builtUri.toString());
    }
    public static URL buildTrailerUrl(String movieid) throws MalformedURLException {
        final String TMDB_VIDEOS_URL = "https://api.themoviedb.org/3/movie/"+movieid+"/videos?";
        final String API_KEY_PARAM = "api_key";
        final String API_KEY = "a2ae95b3c083c023197df998e1ef4475";

        Uri builtUri = Uri.parse(TMDB_VIDEOS_URL).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .build();
        return new URL(builtUri.toString());
    }




}
