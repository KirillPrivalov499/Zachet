package com.example.gorun.NewStory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gorun.NewStory.models.User;
import com.example.gorun.NewStory.rxJava.RxEditText;
import com.example.gorun.R;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETEmail;
    private EditText ETPassvord;
    private Button Login;

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener!=null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null) {
                    Intent intent = new Intent(LoginActivity.this, ListPeopleActivity.class);
                    startActivity(intent);

                }

            }
        };
        Login = (Button) findViewById(R.id.btn_sign_in_au);
        ETEmail= (EditText) findViewById(R.id.et_email_au);
        ETPassvord = (EditText) findViewById(R.id.et_password_au);
        findViewById(R.id.btn_sign_in_au).setOnClickListener(this);
        findViewById(R.id.btn_reg_au).setOnClickListener(this);
        Login.setEnabled(false);

        Observable<String> emailObservable = RxEditText.getTextWatherObservable(ETEmail);
        Observable<String> passwordObservable =RxEditText.getTextWatherObservable(ETPassvord);
        //TODO: сделать как-то spinner... и добавть наблюдателя на имя
        Observable.combineLatest(emailObservable, passwordObservable, (s, s2) -> {
            if(s.isEmpty() || s2.isEmpty())
                return false;
            else
                return true;
        }).subscribe(aBoolean -> {
            Login.setEnabled(aBoolean);
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_sign_in_au){
            signin(ETEmail.getText().toString(),ETPassvord.getText().toString());
        }
        if(v.getId()==R.id.btn_reg_au) {
            Intent intent =  new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        }



    }

    public void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, ListPeopleActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(LoginActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
