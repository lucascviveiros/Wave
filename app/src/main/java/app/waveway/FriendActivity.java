package app.waveway;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.koalap.geofirestore.GeoLocation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import app.waveway.Model.PostUser;
import app.waveway.Model.User;
import app.waveway.adapters.TwitterAdapter;
import app.waveway.adapters.UserSearchAdapter;

public class FriendActivity extends AppCompatActivity {
    private FirebaseFirestore firestoreDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText editText;

    private RecyclerView recyclerView;
    private UserSearchAdapter userSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        mAuth = FirebaseAuth.getInstance();
        editText = findViewById(R.id.id_textNome);
        firestoreDB = FirebaseFirestore.getInstance();
        mUser = mAuth.getCurrentUser();



        recyclerView = (RecyclerView) findViewById(R.id.drawerListUser);
        recyclerView.setHasFixedSize(true);
        //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(layoutManager);

        findViewById(R.id.id_button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFriend();
            }
        });
    }


    void SearchFriend() {
        String nome = editText.getText().toString();
        //User user = new User(editText.getText().toString());
        CollectionReference userref = firestoreDB.collection("User");
        Query query = userref.whereEqualTo("name", nome);

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.i("Fire", "erro: ", e);
                    return;
                }

                List<User> listUser = new ArrayList<>();

                for (DocumentSnapshot doc : queryDocumentSnapshots) {
                    User itemUser = doc.toObject(User.class);
                    itemUser.setName(doc.getString("name"));
                    //itemUser.setGeoLocal((GeoLocation) doc.get("geoLocal"));
                    listUser.add(itemUser);
                }

                Log.e("user",listUser.get(0).getName());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FriendActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                userSearchAdapter = new UserSearchAdapter(FriendActivity.this, listUser);
                recyclerView.setAdapter(userSearchAdapter);
            }
        });
    }
}