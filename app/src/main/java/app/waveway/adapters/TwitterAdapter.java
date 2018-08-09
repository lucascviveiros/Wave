package app.waveway.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;
import app.waveway.Model.PostUser;
import app.waveway.R;

public class TwitterAdapter extends RecyclerView.Adapter<TwitterAdapter.TwitterViewHolder>{

    private Context context;
    private List<PostUser> listPost;

    public TwitterAdapter(Context context, List<PostUser> listPost) {
        this.context = context;
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public TwitterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, null);
        TwitterViewHolder holder = new TwitterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TwitterViewHolder holder, int position) {
        PostUser postUser = listPost.get(position);
        holder.textView.setText(postUser.getTexto());
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    class TwitterViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public TwitterViewHolder(View itemView){
            super(itemView);

            textView = itemView.findViewById(R.id.textViewID);
        }
    }
}
