package moviesassignment.com.movies.repository.model;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import moviesassignment.com.movies.R;

@Entity(tableName = "Movie")
public class Movie {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "ID")
    @SerializedName("id")
    protected String id;

    @ColumnInfo(name = "Vote_Count")
    @SerializedName("vote_count")
    private String voteCount;

    @ColumnInfo(name = "Vote_Average")
    @SerializedName("vote_average")
    private String vote_average;

    @ColumnInfo(name = "Title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "Poster_Path")
    @SerializedName("poster_path")
    private String poster_path;

    @ColumnInfo(name = "Adult")
    @SerializedName("adult")
    private boolean adult;

    @ColumnInfo(name = "Overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "Release_Date")
    @SerializedName("release_date")
    private String release_date;

    @BindingAdapter({"bind:poster_path"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load("http://image.tmdb.org/t/p/w185/" + imageUrl).fit().memoryPolicy(MemoryPolicy.NO_CACHE)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(view);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

}
