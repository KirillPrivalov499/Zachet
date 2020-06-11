package com.example.gorun.NewStory.Logic;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.gorun.NewStory.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcceptRequestNotifications {


    private User runner;
    private User trainer;
    private String myUid;

    public AcceptRequestNotifications(User runner, String myUid) {
        this.runner = runner;
        this.myUid = myUid;
        DoAccept();
    }

    public void DoAccept() {
        Log.e("tratatataatat",myUid +"   "+ runner.getUid());
        FirebaseDatabase.getInstance().getReference().child("RunnersOfTrainer").child(myUid).child(runner.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.exists()){
                            FirebaseDatabase.getInstance().getReference().child("Users").child(myUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String email = dataSnapshot.getValue(User.class).getEmail();
                                        String activity = dataSnapshot.getValue(User.class).getActivty();
                                        String name = dataSnapshot.getValue(User.class).getName();
                                        String picture = dataSnapshot.getValue(User.class).getPicture();
                                        String uid = dataSnapshot.getValue(User.class).getUid();
                                        String yearsOld = dataSnapshot.getValue(User.class).getYearsOld();
                                        trainer = new User(name, email, uid, picture, yearsOld, activity);

                                    FirebaseDatabase.getInstance().getReference().child("RunnersOfTrainer").child(myUid).push().setValue(runner);

                                    FirebaseDatabase.getInstance().getReference().child("TrainersOfRunner").child(runner.getUid()).push().setValue(trainer);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });




    }

}
