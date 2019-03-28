package moviesassignment.com.movies.repository.serverApi;


import moviesassignment.com.movies.repository.model.Movie;
import moviesassignment.com.movies.repository.model.MovieListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("/3/discover/movie?language=en-US&page=1")
    Call<MovieListResponse> getMovieSortByList(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);

    @GET("/3/movie/popular?language=en-US&page=1")
    Call<MovieListResponse> getMovieList(@Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}")
    Call<Movie> getMovieDetails(@Path("movie_id") String id, @Query("api_key") String apiKey);

}
