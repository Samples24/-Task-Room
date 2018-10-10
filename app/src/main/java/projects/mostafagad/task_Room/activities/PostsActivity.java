package projects.mostafagad.task_Room.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import projects.mostafagad.task_Room.adapters.PostsAdapter;
import projects.mostafagad.task_Room.adapters.PostsAdapter_Offline_Room;
import projects.mostafagad.task_Room.helpers.CheckInternetConnection;
import projects.mostafagad.task_Room.helpers.Global;
import projects.mostafagad.task_Room.helpers.RoomDataBase;
import projects.mostafagad.task_Room.interfaces.Posts_Interface;
import projects.mostafagad.task_Room.listeners.Posts_Listener;
import projects.mostafagad.task_Room.models.PostModel;
import projects.mostafagad.task_Room.models.PostModel_Room;
import projects.mostafagad.taskv2.R;

public class PostsActivity extends AppCompatActivity implements Posts_Interface {

    RecyclerView postsReycler;
    LinearLayout loading, empty;

    public static List<PostModel> posts;
    public static List<PostModel_Room> posts_;
    LinearLayoutManager linearLayoutManager;

    public static RoomDataBase roomDataBase;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);     //  Fixed Portrait orientation
        Declare_Controls();


        roomDataBase = Room.databaseBuilder(getApplicationContext(), RoomDataBase.class, "postsdb").allowMainThreadQueries().build();

        initRecycler();
        posts = new ArrayList<>();
        posts_ = new ArrayList<>();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.DarkBlue)));
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));
        }


        LoadPosts();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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


    public void LoadPosts() {
        Posts_Listener posts_listener = new Posts_Listener(getApplicationContext(), this);
        posts_listener.getPosts();
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
            case R.id.Menu_Arabic:
                setLocale("ar");
                break;
            case R.id.Menu_English:
                setLocale("en");
                break;
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), splash.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, splash.class);
        startActivity(refresh);
        finish();
    }


    public void LoadDataOfflineRoom() {
        posts_ = roomDataBase.oper().GetData();
        PostsAdapter_Offline_Room postsActivity_realm = new PostsAdapter_Offline_Room(getApplicationContext(), posts_);
        postsReycler.setAdapter(postsActivity_realm);

    }

    @Override
    public void onSuccess(List<PostModel> postModels) {
        loading.setVisibility(View.GONE);
        posts.clear();
        posts.addAll(postModels);
        PostsAdapter postsAdapter = new PostsAdapter(getApplicationContext(), posts);
        postsReycler.setAdapter(postsAdapter);

        roomDataBase.oper().Delete();
        for (int i = 0; i < postModels.size(); i++) {
            PostModel_Room postModel_room = new PostModel_Room();
            postModel_room.setId(postModels.get(i).getId());
            postModel_room.setUserId(postModels.get(i).getUserId());
            postModel_room.setTitle(postModels.get(i).getTitle());
            postModel_room.setBody(postModels.get(i).getBody());
            roomDataBase.oper().AddUser(postModel_room);
        }


    }

    @Override
    public void onError() {
        loading.setVisibility(View.GONE);
        Global.makeLongToast(getApplicationContext(), getResources().getString(R.string.error_connection), 3000);
        Global.makeLongToast(getApplicationContext(), getResources().getString(R.string.offline_mode), 3000);
        LoadDataOfflineRoom();
    }

}
