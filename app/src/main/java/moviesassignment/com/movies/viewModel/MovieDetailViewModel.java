package moviesassignment.com.movies.viewModel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import moviesassignment.com.movies.repository.NetworkState;
import moviesassignment.com.movies.repository.model.Movie;

public class MovieDetailViewModel extends BaseViewModel {

    private LiveData<Movie> movie;
    private LiveData<NetworkState> networkState;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMovieDetailResponse(String movieID, boolean isConnected) {
        movie = movieRepositary.getMovieDetail(movieID,isConnected);
        networkState = movieRepositary.getNetworkState();
    }

    public LiveData<Movie> getMovieDetail() {
        return movie;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }


}
