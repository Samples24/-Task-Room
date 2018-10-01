package projects.mostafagad.task.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import projects.mostafagad.task.R;
import projects.mostafagad.task.adapters.PostsAdapter;
import projects.mostafagad.task.adapters.PostsAdapter_Offline;
import projects.mostafagad.task.helpers.CheckInternetConnection;
import projects.mostafagad.task.helpers.Global;
import projects.mostafagad.task.helpers.MyServices;
import projects.mostafagad.task.helpers.SQL_DB;
import projects.mostafagad.task.models.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsActivity extends AppCompatActivity {

    RecyclerView postsReycler;
    LinearLayout loading, empty;
    List<PostModel> posts;
    LinearLayoutManager linearLayoutManager;
    public static SQL_DB sql_db;
    Boolean tableEmpty;
    Long table_rows;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        Declare_Controls();
        initRecycler();
        posts = new ArrayList<>();
        sql_db = new SQL_DB(getApplicationContext());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.action_bar);

        tableEmpty = sql_db.CheckTableEmpty();
        table_rows = sql_db.getTableCount();

        LoadData();

    }

    public void Declare_Controls() {
        postsReycler = findViewById(R.id.Posts_RecyclerView);
        loading = findViewById(R.id.Posts_LoadingLayout);
        empty = findViewById(R.id.history_empty_layout);
    }

    public void initRecycler() {
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        postsReycler.setLayoutManager(linearLayoutManager);
        postsReycler.setItemAnimator(new DefaultItemAnimator());
    }

    public void LoadData() {
        MyServices.ApiInterface apiInterface = MyServices.getClient().create(MyServices.ApiInterface.class);
        Call<List<PostModel>> call = apiInterface.GetPosts();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                if (response.isSuccessful()) {
                    loading.setVisibility(View.GONE);
                    posts.addAll(response.body());
                    sql_db.DeleteData();
                    for (int i = 0; i < response.body().size(); i++) {
                        sql_db.Add_record(response.body().get(i).getId(), response.body().get(i).getUserId(), response.body().get(i).getTitle(), response.body().get(i).getBody());
                    }
                    PostsAdapter postsAdapter = new PostsAdapter(getApplicationContext(), posts);
                    postsReycler.setAdapter(postsAdapter);
                } else if (!response.isSuccessful()) {
                    loading.setVisibility(View.GONE);
                    while (!tableEmpty) {
                        LoadDataOffline();
                    }
                } else {
                    empty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                loading.setVisibility(View.GONE);
                if (tableEmpty) {
                    LoadDataOffline();
                } else {
                    empty.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drawermenu, menu);

        MenuItem offline_item = menu.findItem(R.id.Menu_Offline);

        CheckInternetConnection checkInternetConnection = new CheckInternetConnection(getApplicationContext());
        boolean isConnected = checkInternetConnection.IsConnectingtoInternet();
        if (isConnected) {
            offline_item.setVisible(false);
        } else {
            offline_item.setVisible(true);
        }


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), splash.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), splash.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public void LoadDataOffline() {
        Cursor cursor = sql_db.getAlldata();
        if (cursor.getCount() == 0) {
            empty.setVisibility(View.VISIBLE);
            postsReycler.setVisibility(View.GONE);
            Global.makeLongToast(getApplicationContext(), getResources().getString(R.string.SQLITE_Alert_Empty), 7000);
        } else {
            posts = new ArrayList<>();
            while (cursor.moveToNext()) {
                Integer id = cursor.getInt(0);
                Integer userId = cursor.getInt(1);
                String title = cursor.getString(2);
                String body = cursor.getString(3);

                PostModel postModel = new PostModel(userId, id, title, body, getApplicationContext());
                posts.add(postModel);
                initRecycler();
                PostsAdapter_Offline myadapter = new PostsAdapter_Offline(getApplicationContext(), posts);
                postsReycler.setAdapter(myadapter);
            }
        }

    }
}
