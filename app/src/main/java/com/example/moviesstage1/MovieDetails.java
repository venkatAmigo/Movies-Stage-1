package com.example.moviesstage1;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetails extends AppCompatActivity {
    ImageView poster;
    TextView release_date, vote, overview;
    Button add_to_favor;
    String votes = "";
    private String url;
    private String path = "https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
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
        final String release = intent.getStringExtra("release");
        final String original_title = intent.getStringExtra("original_title");
        //Trailers
        if (intent.getStringExtra("vote") != null)
            votes = intent.getStringExtra("vote");
        final String overviews = intent.getStringExtra("overview");
        release_date.setText(release);
        vote.setText(votes);
        overview.setText(overviews);
    }
}
