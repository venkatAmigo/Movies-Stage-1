package com.example.moviesstage1copy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.moviesstage1copy.Network.NetworkUtils;
import com.example.moviesstage1copy.json.JsonParser;
import com.example.moviesstage1copy.model.MovieModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovieAsync extends AsyncTaskLoader {

        private String SORT_PARAM="";
    public FetchMovieAsync(@NonNull Context context,String sort_order) {
        super(context);
        SORT_PARAM = sort_order;
    }
MovieModel m[];
    @Override
    public void deliverResult(@Nullable Object data) {
        m = (MovieModel[])data;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        JsonParser parser=new JsonParser();

        String moviesJsonStr = null;
        try {
            URL url = NetworkUtils.buildUrl(SORT_PARAM);//Build Url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();
            if (inputStream == null) {
                return null;
            }
            BufferedReader reader  = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            if (builder.length() == 0)
                return null;
            moviesJsonStr = builder.toString();
            return parser.parseJson(moviesJsonStr);
        }
        catch(Exception e)
        {
            Log.d("exception","Url not found");
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (m!=null) {
            deliverResult(m);
        }else{
            forceLoad();
        }
       // forceLoad();
    }
      //This work as a cache variable

}
