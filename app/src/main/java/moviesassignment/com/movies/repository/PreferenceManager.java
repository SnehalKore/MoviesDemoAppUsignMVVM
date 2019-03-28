package moviesassignment.com.movies.repository;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferenceManager {

    Context context;

    private static final String SORT_PREF = "sort_pref";

    private static final String SORTING = "sorting";


    private static PreferenceManager instance;

    public static PreferenceManager getInstance() {
        synchronized (PreferenceManager.class) {
            if (instance == null) {
                instance = new PreferenceManager();
            }
            return instance;
        }
    }

    public void initPreferences(final Context context) {
        this.context = context;
    }

    public String getSort() {
        SharedPreferences preferences = context.getSharedPreferences(SORT_PREF, Context.MODE_PRIVATE);
        return preferences.getString(SORTING, "");
    }

    public void setSort(String temp) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SORT_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(SORTING, temp);
        sharedPreferencesEditor.apply();
    }

}