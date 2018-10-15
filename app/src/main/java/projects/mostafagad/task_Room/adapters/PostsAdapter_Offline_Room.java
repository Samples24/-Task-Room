package projects.mostafagad.task_Room.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import projects.mostafagad.task_Room.models.PostModel;
import projects.mostafagad.task_Room.models.PostModel_Room;
import projects.mostafagad.taskv2.R;


public class PostsAdapter_Offline_Room extends RecyclerView.Adapter<PostsAdapter_Offline_Room.Holder> {


    private Context context;
    private List<PostModel_Room> posts;
    private int row_index = 0;


    public PostsAdapter_Offline_Room(Context context, List<PostModel_Room> posts) {
        this.context = context;
        this.posts = posts;
    }


    private static PostsAdapter_Offline_Room instance;

    public static PostsAdapter_Offline_Room getInstance(Context context, List<PostModel_Room> posts) {
        if (instance == null) {
            synchronized (PostModel.class) {
                if (instance == null) {
                    System.out.println("getInstance(): First time getInstance was invoked!");
                    instance = new PostsAdapter_Offline_Room(context, posts);
                }
            }
        }

        return instance;
    }

    @NonNull
    public PostsAdapter_Offline_Room.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row, null);
        item.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        PostsAdapter_Offline_Room.Holder Holder = new PostsAdapter_Offline_Room.Holder(item);
        return Holder;
    }

    @NonNull
    public void onBindViewHolder(final PostsAdapter_Offline_Room.Holder Holder, final int position) {
        PostModel_Room current_obj = posts.get(position);
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
