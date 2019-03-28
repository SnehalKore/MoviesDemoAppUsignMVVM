package moviesassignment.com.movies.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import moviesassignment.com.movies.R;
import moviesassignment.com.movies.databinding.FragmentMovieListBinding;
import moviesassignment.com.movies.repository.NetworkState;
import moviesassignment.com.movies.repository.PreferenceManager;
import moviesassignment.com.movies.repository.model.Movie;
import moviesassignment.com.movies.utils.ConnectionDetector;
import moviesassignment.com.movies.utils.Constants;
import moviesassignment.com.movies.viewModel.MovieListViewModel;
import moviesassignment.com.movies.views.activity.MainActivity;
import moviesassignment.com.movies.views.adapters.MovieListAdapter;


public class MovieListFragment extends BaseFragment {

    private MovieListViewModel movieListViewModel;
    private FragmentMovieListBinding fragmentMovieListBinding;
    private String sortBy;

    public static MovieListFragment newInstance() {
        return new MovieListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMovieListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false);
        sortBy = "";
        initialiseViewModel();
        makeGetMovieListCall();
        return fragmentMovieListBinding.getRoot();
    }

    private void makeGetMovieListCall() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).showProgress();
        }

        movieListViewModel.getMovieList(sortBy, ConnectionDetector.networkStatus(getContext()));
        movieListViewModel.getMovieList().observe(this, movie -> {
            if (movie != null) {
                setData(movie);
            }
            ((MainActivity) getActivity()).hideProgress();
        });
        movieListViewModel.getNetworkState().observe(this, networkState -> {
            if (networkState.getStatus() == NetworkState.Status.FAILED) {
                Toast.makeText(getContext(), "Network call failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.filter_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (ConnectionDetector.networkStatus(getContext())) {
            if (item.getItemId() == R.id.home) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStackImmediate();
                }
                return true;
            } else if (item.getItemId() == R.id.action_filter) {
                manageFragment(SortByFragment.newInstance(), SortByFragment.class.getName(), MovieListFragment.class.getName());
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setData(List<Movie> movieList) {
        MovieListAdapter movieListAdapter = new MovieListAdapter(movieList);
        fragmentMovieListBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        fragmentMovieListBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMovieListBinding.recyclerView.setAdapter(movieListAdapter);
        movieListAdapter.setListener(movie -> manageFragment(MovieDetailFragment.newInstance(movie.getId()), MovieDetailFragment.class.getName(), MovieListFragment.class.getName()));
    }

    private void initialiseViewModel() {
        movieListViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            switch (PreferenceManager.getInstance().getSort()) {
                case Constants.SORT_BY_DATE:
                    sortBy = getString(R.string.release_date_sort);
                    fragmentMovieListBinding.header.setText(R.string.sort_by_date_title);
                    break;
                case Constants.SORT_BY_RATING:
                    sortBy = getString(R.string.vote_avg_sort);
                    fragmentMovieListBinding.header.setText(R.string.vote_average);
                    break;
                case Constants.CLEAR:
                    sortBy = "";
                    fragmentMovieListBinding.header.setText(R.string.popular_movies);
                    break;
            }
            makeGetMovieListCall();

        }
    }
}
