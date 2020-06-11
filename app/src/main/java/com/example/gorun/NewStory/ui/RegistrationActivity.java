package com.example.gorun.NewStory.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText ETEmail;
    private EditText ETPassvord;
    private EditText ETName;
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
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        getSupportActionBar().setTitle("Back"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action ba

        mAuthListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();
            if(user1 != null){
                Intent intent = new Intent(RegistrationActivity.this, StravaActivity.class);
                startActivity(intent);
                if (!ETName.getText().toString().isEmpty()) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(ETName.getText().toString()).build();
                    user1.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        };
        Registration = (Button) findViewById(R.id.btn_registration_fr);
        ETEmail= (EditText) findViewById(R.id.et_email_fr);
        ETName = (EditText)findViewById(R.id.et_name_fr);
        ETPassvord = (EditText) findViewById(R.id.et_password_fr);
        Spinner = (Spinner) findViewById(R.id.spinner_fr);


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



        findViewById(R.id.btn_registration_fr).setOnClickListener(this);

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

        if(v.getId() == R.id.btn_registration_fr){
            registration(ETEmail.getText().toString(),ETPassvord.getText().toString());
        }
    }


    public void registration(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this,"Регистрация успешна",Toast.LENGTH_LONG).show();

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
                    Toast.makeText(RegistrationActivity.this,"Регистрация провалена",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@androidx.annotation.NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(RegistrationActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
