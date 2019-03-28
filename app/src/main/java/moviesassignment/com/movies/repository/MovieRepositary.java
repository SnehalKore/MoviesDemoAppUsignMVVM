package moviesassignment.com.movies.repository;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import moviesassignment.com.movies.R;
import moviesassignment.com.movies.application.AppExecutors;
import moviesassignment.com.movies.repository.model.Movie;
import moviesassignment.com.movies.repository.model.MovieListResponse;
import moviesassignment.com.movies.repository.serverApi.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepositary {

    private static MovieRepositary sInstance;

    private final AppDatabase mDatabase;
    private final ApiInterface serverApi;
    private MutableLiveData<NetworkState> networkState;
    private AppExecutors mAppExecutors;
    private MutableLiveData<List<Movie>> movieListResponse;
    private MutableLiveData<Movie> movieDetailResponse;
    private Context context;


    private MovieRepositary(final AppDatabase database, ApiInterface serverApi, AppExecutors mAppExecutors, Context context) {
        mDatabase = database;
        this.serverApi = serverApi;
        this.mAppExecutors = mAppExecutors;
        this.context = context;
    }

    public static MovieRepositary getInstance(final AppDatabase database, ApiInterface serverApi, AppExecutors mAppExecutors, Context context) {
        if (sInstance == null) {
            synchronized (MovieRepositary.class) {
                if (sInstance == null) {
                    sInstance = new MovieRepositary(database, serverApi, mAppExecutors, context);
                }
            }
        }
        return sInstance;
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<List<Movie>> getMovieList(String sortBy, boolean isConnected) {
        movieListResponse = new MediatorLiveData<>();
        networkState = new MediatorLiveData<>();
        try {
            networkState.postValue(NetworkState.LOADING);
            if (isConnected) {
                if (sortBy.equals("vote_average.desc") || sortBy.equals("release_date.desc")) {
                    serverApi.getMovieSortByList(context.getString(R.string.api_key), sortBy).enqueue(new Callback<MovieListResponse>() {
                        @Override
                        public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    movieListResponse.postValue(response.body().getResults());
                                }
                                mAppExecutors.diskIO().execute(() -> {
                                    if (response.body().getResults() != null && response.body().getResults().size() > 0) {
                                        mDatabase.movieDao().insertAll(response.body().getResults());
                                    }
                                });
                                networkState.postValue(NetworkState.LOADED);
                            } else {
                                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong"));
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieListResponse> call, Throwable t) {
                            String errorMessage = t == null ? "unknown error" : t.getMessage();
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        }
                    });
                } else {
                    serverApi.getMovieList(context.getString(R.string.api_key)).enqueue(new Callback<MovieListResponse>() {
                        @Override
                        public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    movieListResponse.postValue(response.body().getResults());
                                }
                                mAppExecutors.diskIO().execute(() -> {
                                    if (response.body().getResults() != null && response.body().getResults().size() > 0) {
                                        mDatabase.movieDao().insertAll(response.body().getResults());
                                    }
                                });
                                networkState.postValue(NetworkState.LOADED);
                            } else {
                                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong"));
                            }
                        }

                        @Override
                        public void onFailure(Call<MovieListResponse> call, Throwable t) {
                            String errorMessage = t == null ? "unknown error" : t.getMessage();
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                        }
                    });
                }
            } else {
                mAppExecutors.diskIO().execute(() -> {
                    List<Movie> movies = mDatabase.movieDao().fetchAllMovies();
                    movieListResponse.postValue(movies);
                });
            }
        } catch (Exception e) {
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }
        return movieListResponse;
    }


    public LiveData<Movie> getMovieDetail(String movieID, boolean isConnected) {
        movieDetailResponse = new MediatorLiveData<>();
        networkState = new MediatorLiveData<>();
        try {
            networkState.postValue(NetworkState.LOADING);
            if (isConnected) {
                serverApi.getMovieDetails(movieID, context.getString(R.string.api_key)).enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            movieDetailResponse.postValue(response.body());
                            mAppExecutors.diskIO().execute(() -> {
                                if (response.body() != null) {
                                    mDatabase.movieDao().insert(response.body());
                                }
                            });
                            networkState.postValue(NetworkState.LOADED);
                        } else {
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong"));
                        }
                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });

            } else {
                mAppExecutors.diskIO().execute(() -> {
                    //Fetch data from database
                    Movie movie = mDatabase.movieDao().fetchMovieDetails(movieID);
                    movieDetailResponse.postValue(movie);
                });
            }
        } catch (Exception e) {
            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, e.getMessage()));
        }
        return movieDetailResponse;

    }
}
