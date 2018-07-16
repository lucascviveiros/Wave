package app.waveway;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Model.User;

public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String phoneNumber;
    private EditText editText_Name;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();


        editText_Name = findViewById(R.id.id_textNome);

        Bundle extra = getIntent().getExtras();
        if(extra != null) {
            phoneNumber = extra.getString("idPhoneNumber");
        }

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();


        findViewById(R.id.id_button_salvar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameuser = editText_Name.getText().toString();
                CreateUser(nameuser);
            }
        });
    }

    public void checkEditText(){
        String name = editText_Name.getText().toString();
        if(name.isEmpty()){
            editText_Name.setError("Digite o nome!");
            editText_Name.requestFocus();
            return;
        }
    }

    public void CreateUser(String name){

        User user = new User(phoneNumber, name);

        FirebaseDatabase.getInstance().getReference("User")
                .child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "SHOW!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
