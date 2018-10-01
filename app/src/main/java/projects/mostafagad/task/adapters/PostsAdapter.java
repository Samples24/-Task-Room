package projects.mostafagad.task.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.biubiubiu.justifytext.library.JustifyTextView;
import projects.mostafagad.task.R;
import projects.mostafagad.task.models.PostModel;

import static projects.mostafagad.task.activities.PostsActivity.sql_db;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder> {


    private Context context;
    private List<PostModel> posts;
    int row_index = 0;
    Boolean tableEmpty;
    Long table_rows;


    public PostsAdapter(Context context, List<PostModel> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public PostsAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, null);
        item.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        PostsAdapter.Holder Holder = new PostsAdapter.Holder(item);
        return Holder;
    }

    @Override
    public void onBindViewHolder(final PostsAdapter.Holder Holder, final int position) {
        PostModel current_obj = posts.get(position);
        Holder.id.setText(String.valueOf(current_obj.getId()));
        Holder.title.setText(current_obj.getTitle());
        Holder.body.setText(current_obj.getBody());

        tableEmpty = sql_db.CheckTableEmpty();
        table_rows = sql_db.getTableCount();


        if (tableEmpty || table_rows <= 100) {
            boolean inserted = sql_db.Add_record(current_obj.getId(), current_obj.getUserId(), current_obj.getTitle(), current_obj.getBody());
            if (inserted == true) {
                Log.i("Success", "insert successfully");
            } else {
                Log.i("Failed", "insert failed");
            }
        }

        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index = position;
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            Holder.id.setBackgroundColor(context.getResources().getColor(R.color.Red));
        } else {
            Holder.id.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }


    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView id, title;
        JustifyTextView body;

        public Holder(View view) {
            super(view);

            id = (TextView) itemView.findViewById(R.id.Post_row_TextView_Id);
            title = (TextView) itemView.findViewById(R.id.Post_row_TextView_Title);
            body = (JustifyTextView) itemView.findViewById(R.id.Post_row_TextView_Body);


        }
    }
}
