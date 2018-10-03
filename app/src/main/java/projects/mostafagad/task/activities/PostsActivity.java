package projects.mostafagad.task.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
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

import projects.mostafagad.task.R;
import projects.mostafagad.task.adapters.PostsAdapter;
import projects.mostafagad.task.adapters.PostsAdapter_Offline;
import projects.mostafagad.task.helpers.CheckInternetConnection;
import projects.mostafagad.task.helpers.Global;
import projects.mostafagad.task.helpers.SQL_DB;
import projects.mostafagad.task.interfaces.Posts_Interface;
import projects.mostafagad.task.listeners.Posts_Listener;
import projects.mostafagad.task.models.PostModel;

public class PostsActivity extends AppCompatActivity implements Posts_Interface {

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
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.DarkBlue)));
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.DarkBlue));
        }
        tableEmpty = sql_db.CheckTableEmpty();
        table_rows = sql_db.getTableCount();

        LoadPosts();

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

    @Override
    public void onSuccess(List<PostModel> postModels) {
        loading.setVisibility(View.GONE);
        posts.clear();
        posts.addAll(postModels);
        PostsAdapter postsAdapter = new PostsAdapter(getApplicationContext(), posts);
        postsReycler.setAdapter(postsAdapter);

    }

    @Override
    public void onError() {
        loading.setVisibility(View.GONE);
        Global.makeLongToast(getApplicationContext(), getResources().getString(R.string.error_connection), 3000);
        Global.makeLongToast(getApplicationContext(), getResources().getString(R.string.offline_mode), 3000);
        LoadDataOffline();

    }
}
