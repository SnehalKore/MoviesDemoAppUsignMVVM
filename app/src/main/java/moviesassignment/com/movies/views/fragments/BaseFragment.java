package moviesassignment.com.movies.views.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import moviesassignment.com.movies.R;


public class BaseFragment extends Fragment {
    protected Context context;

    public static BaseFragment newInstance() {
        return new BaseFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    public void replaceFragment(Fragment newFragment, String tag) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, newFragment, tag).commit();

    }

    @SuppressLint("ResourceType")
    public void manageFragment(Fragment fragment, String tag, String currentFragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (getFragmentManager().findFragmentByTag(currentFragment) != null) {
            ft.hide(getFragmentManager().findFragmentByTag(currentFragment));
        }
        ft.add(R.id.fragment_container, fragment, tag);
        ft.addToBackStack(currentFragment);
        ft.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setTransitionStyle(android.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commitAllowingStateLoss();
        manager.executePendingTransactions();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
