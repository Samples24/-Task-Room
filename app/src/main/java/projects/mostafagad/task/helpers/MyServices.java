package projects.mostafagad.task.helpers;

import java.util.List;

import projects.mostafagad.task.models.PostModel;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MyServices {


    public interface ApiInterface
    {

        @GET("posts")
        Call<List<PostModel>> GetPosts();
    }


    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
