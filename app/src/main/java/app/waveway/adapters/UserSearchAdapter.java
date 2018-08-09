package app.waveway.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import app.waveway.Model.User;
import app.waveway.R;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchAdapter.UserViewHolder>{

    private Context context;
    private List<User> listUser;

    public UserSearchAdapter(Context context, List<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @NonNull
    @Override
    public UserSearchAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_layout, null);
        UserSearchAdapter.UserViewHolder holder = new UserSearchAdapter.UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchAdapter.UserViewHolder holder, int position) {
        User user = listUser.get(position);
        holder.textView.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public UserViewHolder(View itemView){
            super(itemView);

            textView = itemView.findViewById(R.id.textViewID);
        }
    }
}

