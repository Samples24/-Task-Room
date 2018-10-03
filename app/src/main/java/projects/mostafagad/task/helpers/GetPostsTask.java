package projects.mostafagad.task.helpers;

import android.content.Context;
import android.os.AsyncTask;

import projects.mostafagad.task.interfaces.Posts_Interface;
import projects.mostafagad.task.listeners.Posts_Listener;

public class GetPostsTask extends AsyncTask {

    private final Posts_Interface sinterface;
    private Context context;

    public GetPostsTask(Context mContext, Posts_Interface sinterface) {
        this.context = mContext;
        this.sinterface = sinterface;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Posts_Listener posts_listener = new Posts_Listener(context, sinterface);
        posts_listener.getPosts();
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
