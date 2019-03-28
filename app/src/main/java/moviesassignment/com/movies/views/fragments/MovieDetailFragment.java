package moviesassignment.com.movies.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import moviesassignment.com.movies.R;
import moviesassignment.com.movies.databinding.FragmentMovieDetailBinding;
import moviesassignment.com.movies.repository.NetworkState;
import moviesassignment.com.movies.repository.model.Movie;
import moviesassignment.com.movies.utils.ConnectionDetector;
import moviesassignment.com.movies.viewModel.MovieDetailViewModel;
import moviesassignment.com.movies.views.activity.MainActivity;


public class MovieDetailFragment extends Fragment {

    private final static String MOVIE_ID = "movie_id";
    private FragmentMovieDetailBinding fragmentMovieDetailBinding;
    private MovieDetailViewModel movieDetailViewModel;
    private String movieID;

    public static MovieDetailFragment newInstance(String movieID) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID, movieID);
        movieDetailFragment.setArguments(bundle);
        return movieDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieID = getArguments().getString(MOVIE_ID, "");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMovieDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        initialiseViewModel();
        makeGetMovieDetailCall();
        return fragmentMovieDetailBinding.getRoot();
    }

    private void makeGetMovieDetailCall() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showProgress();
        }
        movieDetailViewModel.getMovieDetailResponse(movieID, ConnectionDetector.networkStatus(getContext()));
        movieDetailViewModel.getMovieDetail().observe(this, movie -> {
            if (movie != null) {
                setData(movie);
            }
            ((MainActivity) getActivity()).hideProgress();
        });
        movieDetailViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState.getStatus() == NetworkState.Status.FAILED) {
                Toast.makeText(getContext(), "Network call failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData(Movie movie) {
        fragmentMovieDetailBinding.setMovie(movie);
        fragmentMovieDetailBinding.rating.setRating((Float.parseFloat(movie.getVote_average())) * 5 / 10);
    }

    private void initialiseViewModel() {
        movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
    }

}
