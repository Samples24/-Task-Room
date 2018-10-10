package projects.mostafagad.task_Room.adapters;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import projects.mostafagad.task_Room.models.PostModel;
import projects.mostafagad.taskv2.R;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder> {


    int row_index = 0;
    Boolean tableEmpty;
    Long table_rows;
    private Context context;
    private List<PostModel> posts;


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
    public void onBindViewHolder(final Holder Holder, final int position) {
        PostModel current_obj = posts.get(position);
        Holder.id.setText(String.valueOf(current_obj.getId()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Holder.title.setText(Html.fromHtml(current_obj.getTitle() , Html.FROM_HTML_MODE_COMPACT));
            Holder.title.setText(Html.fromHtml(current_obj.getTitle() , Html.FROM_HTML_MODE_COMPACT));
        }
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
