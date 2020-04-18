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

public class SinginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETEmail;
    private EditText ETPassvord;
    private EditText ETName;
    private Button Login;
    private Button Registration;
    private Spinner Spinner;
    String spinnerChoose;

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
        setContentView(R.layout.activity_singin);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(SinginActivity.this, ListPeopleActivity.class);
                    startActivity(intent);
                    if (!ETName.getText().toString().isEmpty()) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(ETName.getText().toString()).build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("Display name: ", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                }
                            }
                        });
                    }
                }
                else{

                }
            }
        };
        Login = (Button) findViewById(R.id.btn_sign_in);
        Registration = (Button) findViewById(R.id.btn_registration);
        ETEmail= (EditText) findViewById(R.id.et_email);
        ETName = (EditText)findViewById(R.id.et_name);
        ETPassvord = (EditText) findViewById(R.id.et_password);
        Spinner = (Spinner) findViewById(R.id.spinner);


        createArrayAdapterForSpinner();

        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.catNames);
                spinnerChoose = choose[selectedItemPosition];
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        findViewById(R.id.btn_sign_in).setOnClickListener(this);
        findViewById(R.id.btn_registration).setOnClickListener(this);

        Login.setEnabled(false);
        Registration.setEnabled(false);

        Observable<String> emailObservable = RxEditText.getTextWatherObservable(ETEmail);
        Observable<String> passwordObservable =RxEditText.getTextWatherObservable(ETPassvord);
        //TODO: сделать как-то spinner... и добавть наблюдателя на имя
        Observable.combineLatest(emailObservable, passwordObservable, (s, s2) -> {
            if(s.isEmpty() || s2.isEmpty() || spinnerChoose == "Choose your activity")
                return false;
            else
                return true;
        }).subscribe(aBoolean -> {
            Login.setEnabled(aBoolean);
            Registration.setEnabled(aBoolean);
        });
    }

    private void createArrayAdapterForSpinner() {
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(this, R.array.catNames, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_sign_in ){
            signin(ETEmail.getText().toString(),ETPassvord.getText().toString());
        }else if(v.getId() == R.id.btn_registration){
            registration(ETEmail.getText().toString(),ETPassvord.getText().toString());
        }
    }

    public void signin(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SinginActivity.this, "Aвторизация успешна", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SinginActivity.this, ListPeopleActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(SinginActivity.this, "Aвторизация провалена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void registration(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SinginActivity.this,"Регистрация успешна",Toast.LENGTH_LONG).show();

                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child(spinnerChoose)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(new User(ETName.getText().toString(),
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail() ,
                                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    "",
                                    "",
                                    spinnerChoose));
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(new User(ETName.getText().toString(),
                                    FirebaseAuth.getInstance().getCurrentUser().getEmail() ,
                                    FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    "",
                                    "",
                                    spinnerChoose));
                }
                else{
                    Toast.makeText(SinginActivity.this,"Регистрация провалена",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
