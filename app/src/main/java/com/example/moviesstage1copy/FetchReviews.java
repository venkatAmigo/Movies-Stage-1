package com.example.moviesstage1copy;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.example.moviesstage1copy.json.JsonParser;
import com.example.moviesstage1copy.model.ReviewModel;
import com.example.moviesstage1copy.model.VideoModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchReviews extends AsyncTaskLoader {
    String moviesid;
    private static final String BASE_REVIEWS= "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = "a2ae95b3c083c023197df998e1ef4475";
    public FetchReviews(@NonNull Context context, String movieid) {
        super(context);
        this.moviesid=movieid;
    }
    ReviewModel r[];
    @Override
    public void deliverResult(@Nullable Object data) {
        r= (ReviewModel[])data;
        super.deliverResult(data);
    }

    @Nullable
    @Override
    public Object loadInBackground() {
        Uri uri = Uri.parse(BASE_REVIEWS+moviesid+"/reviews?api_key="+API_KEY);
        try {
            URL url = new URL(uri.toString());
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                stringBuffer.append(line);
            }
            JsonParser j=new JsonParser();
            return  j.parseVideoJson(stringBuffer.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (r!=null) {
            deliverResult(r);
        }else{
            forceLoad();
        }
    }
}
