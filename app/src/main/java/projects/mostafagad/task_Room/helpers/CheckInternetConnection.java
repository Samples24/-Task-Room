package projects.mostafagad.task_Room.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckInternetConnection {

    private Context context;

    public CheckInternetConnection(Context context) {
        this.context = context;
    }

    public boolean IsConnectingtoInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }
        return false;
    }


}
