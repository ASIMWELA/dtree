package dhis2.d2.d_tree;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Locale;

import dhis2.d2.d_tree.user.FetchUsersState;
import dhis2.d2.d_tree.user.UserRecyclerViewAdapter;
import dhis2.d2.d_tree.user.UserViewModel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private UserViewModel userViewModel;
    private ProgressBar progressBar;
    private EditText edFilterByCity;
    private RecyclerView userRecyclerView;
    private UserRecyclerViewAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getUsers();

        userViewModel.checkLoadingState.observe(this, fetchUsersState -> {
            Log.e(TAG, fetchUsersState.name());
            if(fetchUsersState.name().equals(FetchUsersState.LOADING.name())){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }

        });

        userViewModel.users.observe(this, users -> {
            if(users != null){
                adapter = new UserRecyclerViewAdapter(users,this);
                LinearLayoutManager r = new LinearLayoutManager(this);
                userRecyclerView.setLayoutManager(r);
                userRecyclerView.setItemAnimator(new DefaultItemAnimator());
                userRecyclerView.setAdapter(adapter);

                edFilterByCity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(edFilterByCity.getText().toString().length()==0){
                            adapter.filterByCity("");

                        }else {
                            String filterCityText = edFilterByCity.getText()
                                    .toString().toLowerCase(Locale.getDefault());
                            adapter.filterByCity(filterCityText);
                        }

                    }
                });
            }

        });

    }

    private void initViews(){
        progressBar = findViewById(R.id.loadUsersProgressBar);
        edFilterByCity = findViewById(R.id.etFilterEntriesByCity);
        userRecyclerView = findViewById(R.id.rvUsersRecyclerView);
    }
}