package moviesassignment.com.movies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    /**
     * Function to check either mobile or wifi network is available or not.
     *
     * @param context
     * @return true if either mobile or wifi network is available else it
     * returns false.
     */
    //private static final String TAG = ConnectionDetector.class.getSimpleName();

    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public static boolean networkStatus(Context context) {

        return (ConnectionDetector.isWifiAvailable(context) || ConnectionDetector
                .isMobileNetworkAvailable(context));
    }

    public static boolean isMobileNetworkAvailable(Context ctx) {
        ConnectivityManager connecManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo = connecManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return (myNetworkInfo != null && myNetworkInfo.isConnected());

    }

    public static boolean isWifiAvailable(Context ctx) {
        ConnectivityManager myConnManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo = myConnManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return (myNetworkInfo != null && myNetworkInfo.isConnected());

    }

}
