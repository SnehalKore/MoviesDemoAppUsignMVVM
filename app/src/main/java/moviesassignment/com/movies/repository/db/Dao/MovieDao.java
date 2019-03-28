package moviesassignment.com.movies.repository.db.Dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import moviesassignment.com.movies.repository.model.Movie;

@Dao
public interface MovieDao {

    final String tableName = "Movie";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Movie> movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movies);

    @Query("SELECT * FROM " + tableName)
    List<Movie> fetchAllMovies();

    @Query("SELECT * FROM " + tableName + " WHERE ID = :movieID")
    Movie fetchMovieDetails(String movieID);
}
