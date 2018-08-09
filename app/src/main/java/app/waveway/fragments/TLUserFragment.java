package app.waveway.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthCredential;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import app.waveway.Model.PostUser;
import app.waveway.R;
import app.waveway.adapters.TwitterAdapter;


public class TLUserFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore firestoreDB;
    private RecyclerView recyclerView;
    private TwitterAdapter twitterAdapter;
    List<PostUser> listPost;

    public TLUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestoreDB = FirebaseFirestore.getInstance();
        ReturnFire();

        View layout = inflater.inflate(R.layout.fragment_tluser, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        return layout;
    }

    public void ReturnFire() {

        firestoreDB.collection("User")
                .document(mUser.getUid())
                .collection("postagens")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.i("Fire", "erro: ", e);
                            return;
                        }
                        List<PostUser> itens = new ArrayList<>();

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            PostUser itemPost = doc.toObject(PostUser.class);
                            itemPost.setDate(doc.getDate("date"));
                            itemPost.setTexto(doc.getString("texto"));

                            itens.add(itemPost);

                            Log.e("itens", itemPost.getTexto() + "\n");
                        }
                        Collections.reverse(itens);

                        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

                        recyclerView.setLayoutManager(layoutManager);
                        twitterAdapter = new TwitterAdapter(getContext(), itens);
                        recyclerView.setAdapter(twitterAdapter);

                    }
                });

    }


}
