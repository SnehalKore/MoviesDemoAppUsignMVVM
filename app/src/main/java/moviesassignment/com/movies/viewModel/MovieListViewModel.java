package moviesassignment.com.movies.viewModel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import moviesassignment.com.movies.repository.NetworkState;
import moviesassignment.com.movies.repository.model.Movie;

public class MovieListViewModel extends BaseViewModel {

    private LiveData<List<Movie>> movieList;
    private LiveData<NetworkState> networkState;

    public MovieListViewModel(@NonNull Application application) {
        super(application);
    }

    public void getMovieList(String sortBy, boolean isConnected) {
        movieList = movieRepositary.getMovieList(sortBy,isConnected);
        networkState = movieRepositary.getNetworkState();
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }


}
