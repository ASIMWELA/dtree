package dhis2.d2.d_tree.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dhis2.d2.d_tree.R;

public class UserRecyclerViewAdapter extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private final List<User> users;
    private final List<User> searchedUsers;
    private final Context context;

    public UserRecyclerViewAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
        searchedUsers = new ArrayList<>(users);
    }

    @NonNull
    @Override
    public UserRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerViewAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getName());
        holder.surname.setText(user.getSurname());
        holder.age.setText(user.getAge() +" years");
        holder.city.setText(user.getCity());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterByCity(String city) {
        city = city.toLowerCase(Locale.getDefault());
        users.clear();
        if (city.length() == 0) {
            users.addAll(searchedUsers);
        } else {
            for (User user : searchedUsers) {
                if (user.getCity().toLowerCase(Locale.getDefault())
                        .contains(city)) {
                    users.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, surname, city, age;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvUsername);
            surname = itemView.findViewById(R.id.tvUserSurname);
            city = itemView.findViewById(R.id.tvUserCity);
            age = itemView.findViewById(R.id.tvUserAge);
        }
    }
}
