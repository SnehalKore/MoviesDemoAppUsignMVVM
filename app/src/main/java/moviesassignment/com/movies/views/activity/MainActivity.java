package moviesassignment.com.movies.views.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import moviesassignment.com.movies.R;
import moviesassignment.com.movies.views.fragments.MovieListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, MovieListFragment.newInstance(), MovieListFragment.class.getName()).commit();

    }

    public void showProgress() {
        if (findViewById(R.id.progress_bar_main) != null) {
            findViewById(R.id.progress_bar_main).setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (findViewById(R.id.progress_bar_main) != null) {
            findViewById(R.id.progress_bar_main).setVisibility(View.GONE);
        }
    }
}
