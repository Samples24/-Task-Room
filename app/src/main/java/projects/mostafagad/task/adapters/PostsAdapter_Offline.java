package projects.mostafagad.task.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import projects.mostafagad.task.R;
import projects.mostafagad.task.models.PostModel;


public class PostsAdapter_Offline extends RecyclerView.Adapter<PostsAdapter_Offline.Holder> {


    private Context context;
    private List<PostModel> posts;
    private int row_index = 0;


    public PostsAdapter_Offline(Context context, List<PostModel> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    public PostsAdapter_Offline.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, null);
        item.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        PostsAdapter_Offline.Holder Holder = new PostsAdapter_Offline.Holder(item);
        return Holder;
    }

    @NonNull
    public void onBindViewHolder(final PostsAdapter_Offline.Holder Holder, final int position) {
        PostModel current_obj = posts.get(position);
        Holder.id.setText(String.valueOf(current_obj.getId()));
        Holder.title.setText(current_obj.getTitle());
        Holder.body.setText(current_obj.getBody());


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
        TextView id, title, body;

        public Holder(View view) {
            super(view);

            id = itemView.findViewById(R.id.Post_row_TextView_Id);
            title = itemView.findViewById(R.id.Post_row_TextView_Title);
            body = itemView.findViewById(R.id.Post_row_TextView_Body);


        }
    }
}
