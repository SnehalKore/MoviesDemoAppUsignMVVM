package moviesassignment.com.movies.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import moviesassignment.com.movies.databinding.MovieListItemLayoutBinding;
import moviesassignment.com.movies.repository.model.Movie;
import moviesassignment.com.movies.views.listeners.MovieListAdapterListener;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    List<Movie> movieList;
    private MovieListAdapterListener listener;

    @NonNull
    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MovieListItemLayoutBinding itemBinding = MovieListItemLayoutBinding.inflate(layoutInflater, parent, false);
        itemBinding.setCallback(listener);
        return new ViewHolder(itemBinding);
    }

    public MovieListAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.ViewHolder holder, int position) {
        holder.binding.setMovieList(movieList.get(position));

        holder.binding.rating.setRating((Float.parseFloat(movieList.get(position).getVote_average())*5)/10);
        if (movieList.get(position).isAdult()) {
            holder.binding.adultStatus.setText("A");
        } else {
            holder.binding.adultStatus.setText("U");
        }
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MovieListItemLayoutBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = (MovieListItemLayoutBinding) binding;
        }
    }

    public void setListener(MovieListAdapterListener listener) {
        this.listener = listener;
    }
}
