package com.example.moviesstage1copy.model;

public class MovieModel  {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private String mOriginalTitle;
    private String mPosterPath;
    private String mDetailPosterPath;
    private String mOverview;
    private String mVoteAverage;
    private String mReleaseDate;

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    private int movieId;

    public MovieModel() {
    }
    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }
    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }
    public void setDetailPosterPath(String posterPath) {
        mDetailPosterPath = posterPath;
    }
    public void setOverview(String overview) {
        if(!overview.equals("null")) {
            mOverview = overview;
        }
    }
    public void setVoteAverage(String voteAverage) {
        mVoteAverage = voteAverage;
    }
    public void setReleaseDate(String releaseDate) {
        if(!releaseDate.equals("null")) {
            mReleaseDate = releaseDate;
        }
    }
    public String getOriginalTitle() {
        return mOriginalTitle;
    }
    public String getPosterPath() {
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";

        return  TMDB_POSTER_BASE_URL +mPosterPath;
    }
    public String getDetailPosterPath() {
        return  mPosterPath;
    }
    public String getOverview() {
        return mOverview;
    }
    public String getVoteAverage() {
        return mVoteAverage;
    }
    public String getReleaseDate() {
        return mReleaseDate;
    }
    public String getDetailedVoteAverage() {
        return String.valueOf(getVoteAverage());
    }
    public String getDateFormat() {
        return DATE_FORMAT;
    }

}
