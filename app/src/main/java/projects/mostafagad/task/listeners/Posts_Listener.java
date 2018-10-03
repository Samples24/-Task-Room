package projects.mostafagad.task.listeners;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import projects.mostafagad.task.helpers.MyServices;
import projects.mostafagad.task.interfaces.Posts_Interface;
import projects.mostafagad.task.models.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static projects.mostafagad.task.activities.PostsActivity.sql_db;

public class Posts_Listener {

    private final Context context;
    private final Posts_Interface sinterface;
    MyServices services;


    public Posts_Listener(Context context, Posts_Interface sinterface) {
        this.context = context;
        this.sinterface = sinterface;
        this.services = new MyServices();

    }


    public void getPosts() {
        services.getAPI().GetPosts().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful()) {
                    try {
                        sinterface.onSuccess(response.body());
                        sql_db.DeleteData();
                        for (int i = 0; i < response.body().size(); i++) {
                            sql_db.Add_record(response.body().get(i).getId(), response.body().get(i).getUserId(), response.body().get(i).getTitle(), response.body().get(i).getBody());
                        }
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
