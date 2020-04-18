package com.example.gorun.NewStory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.gorun.NewStory.adapters.MessageAdapter;
import com.example.gorun.NewStory.models.Message;
import com.example.gorun.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ListMessages2Activity extends AppCompatActivity {

    private static int SIGN_IN_CODE = 1;
    private RelativeLayout activityMain;
    private FirebaseListAdapter<Message> adapter;
    private Button sendButton;
    private String loggedInUserName = "";
    private String chatUid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_messages2);
        activityMain = (RelativeLayout) findViewById(R.id.activity_list_messages2);
        sendButton = findViewById(R.id.sendButton);

        Bundle arguments = getIntent().getExtras();
        chatUid = arguments.get("chatUid").toString();

        getSupportActionBar().setTitle("Your Activity Title"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action ba

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = findViewById(R.id.messageField);
                if(textField.getText().toString().isEmpty()){
                    Toast.makeText(ListMessages2Activity.this, "Please enter some texts!", Toast.LENGTH_SHORT).show();
                }
                else{
                    FirebaseDatabase.getInstance()
                            .getReference()
                            .child("chats")
                            .child(chatUid)
                            .push()
                            .setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),FirebaseAuth.getInstance().getUid(),
                                    textField.getText().toString()));
                    textField.setText("");
                }
            }
        });


        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
        }
        else
            //Snackbar.make(activityMain, "Вы авторизованы", Snackbar.LENGTH_LONG).show();
            displayAllMessages();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SIGN_IN_CODE){
            if(resultCode==RESULT_OK){
                Snackbar.make(activityMain, "Вы авторизованы", Snackbar.LENGTH_LONG).show();
                displayAllMessages();
            }
            else {
                //Snackbar.make(activityMain, "Вы не авторизованы", Snackbar.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void displayAllMessages() {
        ListView listView = findViewById(R.id.list_of_messages);
        loggedInUserName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("Main", "user id: " + loggedInUserName);
        adapter = new MessageAdapter(this, Message.class, R.layout.list_item_left,
                FirebaseDatabase.getInstance().getReference().child("chats").child(chatUid));
        listView.setAdapter(adapter);
    }

    public String getLoggedInUserName(){
        return loggedInUserName;
    }
}
