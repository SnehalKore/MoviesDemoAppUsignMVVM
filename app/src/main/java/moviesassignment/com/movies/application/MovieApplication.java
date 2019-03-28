package moviesassignment.com.movies.application;

import android.app.Application;

import moviesassignment.com.movies.repository.AppDatabase;
import moviesassignment.com.movies.repository.MovieRepositary;
import moviesassignment.com.movies.repository.PreferenceManager;
import moviesassignment.com.movies.repository.serverApi.ApiClient;
import moviesassignment.com.movies.repository.serverApi.ApiInterface;

public class MovieApplication extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
        PreferenceManager.getInstance().initPreferences(this);
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }


    public ApiInterface getApiInterface() {
        return ApiClient.getClient().create(ApiInterface.class);
    }

    public MovieRepositary getAppRepository() {
        return MovieRepositary.getInstance(getDatabase(), getApiInterface(), mAppExecutors,this);
    }
}
