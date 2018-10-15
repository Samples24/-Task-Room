package projects.mostafagad.task_Room.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import projects.mostafagad.task_Room.models.PostModel;


public class CheckInternetConnection {

    private Context context;
    private static CheckInternetConnection instance;

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


    public static CheckInternetConnection getInstance(Context context) {
        if (instance == null) {
            synchronized (PostModel.class) {
                if (instance == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new CheckInternetConnection(context);
                }
            }
        }

        return instance;
    }


}
