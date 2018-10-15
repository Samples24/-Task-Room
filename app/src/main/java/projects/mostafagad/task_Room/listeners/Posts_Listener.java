package projects.mostafagad.task_Room.listeners;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import projects.mostafagad.task_Room.helpers.MyServices;
import projects.mostafagad.task_Room.interfaces.Posts_Interface;
import projects.mostafagad.task_Room.models.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Posts_Listener {

    private final Context context;
    private final Posts_Interface sinterface;
    MyServices services;


    private static Posts_Listener instance;


    public Posts_Listener(Context context, Posts_Interface sinterface) {
        this.context = context;
        this.sinterface = sinterface;
        this.services = new MyServices();

    }

    public static Posts_Listener getInstance(Context context, Posts_Interface sinterface) {
        if (instance == null) {
            synchronized (PostModel.class) {
                if (instance == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new Posts_Listener(context, sinterface);
                }
            }
        }

        return instance;
    }


    public void getPosts() {
        services.getAPI().GetPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful()) {
                    try {
                        sinterface.onSuccess(response.body());
                    } catch (IOException e) {
                        e.printStackTrace();
                        sinterface.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.i("Response Failure", t.getMessage());
                sinterface.onError();
            }
        });
    }

}
