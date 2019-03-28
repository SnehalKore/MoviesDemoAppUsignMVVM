package moviesassignment.com.movies.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import moviesassignment.com.movies.application.MovieApplication;
import moviesassignment.com.movies.repository.MovieRepositary;

public class BaseViewModel extends AndroidViewModel {
    MovieRepositary movieRepositary;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        if (movieRepositary == null)
            movieRepositary = ((MovieApplication) application).getAppRepository();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
