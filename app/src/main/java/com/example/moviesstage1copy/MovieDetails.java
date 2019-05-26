package com.example.moviesstage1copy;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviesstage1copy.Adapters.ReviewAdapter;
import com.example.moviesstage1copy.Adapters.VideoAdapter;
import com.example.moviesstage1copy.database_classes.FavViewModel;
import com.example.moviesstage1copy.database_classes.FavouriteMovies;
import com.example.moviesstage1copy.model.ReviewModel;
import com.example.moviesstage1copy.model.VideoModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MovieDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks <VideoModel[]>{
    ImageView poster;
    TextView release_date, vote, overview;
    String votes = "";
    private String path = "https://image.tmdb.org/t/p/w185";
    Button fav_button;
    String original_title,overviews,pp,release,url;
    int mid;
    boolean flag;
    private FavViewModel favViewModel;
    private final static int LOADER=2;
    RecyclerView recycleTrailers,recycleReviews;
    VideoAdapter videoAdapter;
    ReviewAdapter reviewAdapter;
    VideoModel[] vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        favViewModel= ViewModelProviders.of(this).get(FavViewModel.class);
        fav_button=findViewById(R.id.fav_button);
         mid=getIntent().getIntExtra("id",1);

        if(getSupportLoaderManager().getLoader(LOADER)!=null) {
            getSupportLoaderManager().initLoader(LOADER, null, this);
        }
        else
        {
            getSupportLoaderManager().restartLoader(LOADER,null,this);
        }

        recycleTrailers=findViewById(R.id.recycle_videos);
        videoAdapter=new VideoAdapter(this,vm);
        recycleTrailers.setLayoutManager(new LinearLayoutManager(this));
        recycleTrailers.setAdapter(videoAdapter);
        recycleReviews=findViewById(R.id.recycle_review);
        load(String.valueOf(mid));
        FavouriteMovies r =  favViewModel.checkMovieInDatabase(String.valueOf(mid));
        if(r!=null)
        {
            fav_button.setText("Unfavourite movies");
            flag = true;
        }
        else
        {
            fav_button.setText("Add to Favourites");
            flag = false;
        }
        poster = findViewById(R.id.imageView);
        release_date = findViewById(R.id.release_date);
        vote = findViewById(R.id.vote);
        overview = findViewById(R.id.overview);
        Intent intent = getIntent();
        if (intent.getStringExtra("poster").contains("http"))
            url = intent.getStringExtra("poster");
        else
            url = path + intent.getStringExtra("poster");
        Picasso
                .with(this)
                .load(url)
                .into(poster);
          release = intent.getStringExtra("release");
          original_title = intent.getStringExtra("original_title");
        if (intent.getStringExtra("vote") != null)
            votes = intent.getStringExtra("vote");
        overviews = intent.getStringExtra("overview");
        release_date.setText(release);
        vote.setText(votes);
        overview.setText(overviews);
    }

    public void addToFav(View view) {
        if (flag) {
            FavouriteMovies favouriteMovies=new FavouriteMovies(mid,original_title,overviews,url,release,votes);
            favViewModel.delete(favouriteMovies);
            Toast.makeText(this, "un favourited successfully", Toast.LENGTH_SHORT).show();
            fav_button.setText("Add to Favourites");
            flag = !flag;
        } else {
            FavouriteMovies favouriteMovies=new FavouriteMovies(mid,original_title,overviews,url,release,votes);
            Toast.makeText(this, "Added to successfully", Toast.LENGTH_SHORT).show();
            favViewModel.insert(favouriteMovies);
            fav_button.setText("Unfavourite movies");
            flag= !flag;
        }
    }

    @NonNull
    @Override
    public Loader<VideoModel[]> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new FetchVideos(this,String.valueOf(mid));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<VideoModel[]> loader, VideoModel[] s) {
        this.vm=s;
        VideoAdapter videoAdapter = new VideoAdapter(this, this.vm);
        recycleTrailers.setLayoutManager(new LinearLayoutManager(this));
        recycleTrailers.setAdapter(videoAdapter);
        videoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull Loader< VideoModel[] > loader) {

    }
    public void load(String id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/movie/" + id + "/reviews?api_key=" +"a2ae95b3c083c023197df998e1ef4475";
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject root = null;
                        ReviewModel[] reviews=null;
                        try {
                            root = new JSONObject(response);
                            JSONArray results = root.getJSONArray("results");
                            reviews = new ReviewModel[results.length()];
                            for(int i=0;i<results.length();i++)
                            {
                                reviews[i]=new ReviewModel();
                                JSONObject result_item = results.getJSONObject(i);
                                reviews[i].setAuthor( result_item.getString("author"));
                                reviews[i].setComment(result_item.getString("content"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(reviews!=null){
                        reviewAdapter=new ReviewAdapter(reviews,MovieDetails.this);
                        recycleReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recycleReviews.setAdapter(reviewAdapter);}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }
}
