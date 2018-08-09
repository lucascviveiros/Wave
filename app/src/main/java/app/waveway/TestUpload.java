package app.waveway;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

import app.waveway.Model.PostUser;
import app.waveway.Model.User;

public class TestUpload extends AppCompatActivity {

    private EditText editText;
    private FirebaseFirestore firestoreDB;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        firestoreDB = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        mUser = mAuth.getCurrentUser();

        editText = findViewById(R.id.id_textNome);

        findViewById(R.id.id_button_salvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestString();
            }
        });
    }

    public void TestString(){

        PostUser post = new PostUser(editText.getText().toString());
        //User user = new User(post);

        firestoreDB.collection("User").document(mUser.getUid()).collection("postagens").document(Calendar.getInstance().getTime().toString()).set(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(TestUpload.this, "SHOW!", Toast.LENGTH_SHORT).show();
                        }
                    }});

        startActivity(new Intent(getApplicationContext(), NavigationActivity.class));

    }

}
