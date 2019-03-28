package moviesassignment.com.movies.views.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import moviesassignment.com.movies.R;
import moviesassignment.com.movies.repository.PreferenceManager;
import moviesassignment.com.movies.utils.Constants;

public class SortByFragment extends Fragment {

    public static SortByFragment newInstance() {
        return new SortByFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort_by, container, false);
        RelativeLayout sortByDate = view.findViewById(R.id.date_sort);
        RelativeLayout sortByRating = view.findViewById(R.id.rate_sort);
        RelativeLayout clearSorting = view.findViewById(R.id.clear_all);
        sortByDate.setOnClickListener(v -> navigateBack(Constants.SORT_BY_DATE));
        sortByRating.setOnClickListener(v -> navigateBack(Constants.SORT_BY_RATING));
        clearSorting.setOnClickListener(v -> navigateBack(Constants.CLEAR));
        return view;
    }

    private void navigateBack(String sortBy) {
        PreferenceManager.getInstance().setSort(sortBy);
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStackImmediate();
        }
    }

}
