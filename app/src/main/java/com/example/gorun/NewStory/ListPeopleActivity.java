package com.example.gorun.NewStory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gorun.NewStory.adapters.UsersAdapter;
import com.example.gorun.NewStory.models.User;
import com.example.gorun.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;


public class ListPeopleActivity extends AppCompatActivity {

    FirebaseListAdapter<User> adapter;
    ListView listView;
    String myActivity;
    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_people);
        listView = (ListView)findViewById(R.id.list_of_people);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myActivity = dataSnapshot.getValue(User.class).getActivty();
                setAdapter();
                Toast.makeText(getApplicationContext(),dataSnapshot.getValue(User.class).getActivty() + " eeeeeeee",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setAdapter() {
        userArrayList = new ArrayList<>();
        if(myActivity.equals("Trainer")){
            adapter = new UsersAdapter(this, User.class,R.layout.item_person,
                    FirebaseDatabase.getInstance().getReference().child("Runner"));
            FirebaseDatabase.getInstance().getReference().child("Runner").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                      String email = postSnapshot.getValue(User.class).getEmail();
                      String activity =  postSnapshot.getValue(User.class).getActivty();
                      String name = postSnapshot.getValue(User.class).getName();
                      String picture = postSnapshot.getValue(User.class).getPicture();
                      String uid =  postSnapshot.getValue(User.class).getUid();
                      String yearsOld = postSnapshot.getValue(User.class).getYearsOld();
                      userArrayList.add(new User(name,email,uid,picture,yearsOld,activity));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }else {
            adapter = new UsersAdapter(this, User.class,R.layout.item_person,
                    FirebaseDatabase.getInstance().getReference().child("Trainer"));
            FirebaseDatabase.getInstance().getReference().child("Trainer").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        String email = postSnapshot.getValue(User.class).getEmail();
                        String activity =  postSnapshot.getValue(User.class).getActivty();
                        String name = postSnapshot.getValue(User.class).getName();
                        String picture = postSnapshot.getValue(User.class).getPicture();
                        String uid =  postSnapshot.getValue(User.class).getUid();
                        String yearsOld = postSnapshot.getValue(User.class).getYearsOld();
                        userArrayList.add(new User(name,email,uid,picture,yearsOld,activity));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        listView.setAdapter(adapter);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ListPeopleActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
