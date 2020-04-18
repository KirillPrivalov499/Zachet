package com.example.gorun.NewStory;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateOrJoinNewChat {

    private Context context;
    public CreateOrJoinNewChat(Context context, String Uid1,String Uid2){
        this.context = context;
        CompareUid(Uid1, Uid2);
    }

    private void CompareUid(String Uid1, String Uid2){
        int result = Uid1.compareTo(Uid2);
        if(result > 0){
           joinToChat(Uid1+Uid2);
        }
        else{
            joinToChat(Uid2+Uid1);
        }
    }

//    private void searchChat(String chatUid){
//        FirebaseDatabase.getInstance().getReference().child("chats")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.child(chatUid).exists()){
//                            joinToChat(chatUid);
//                        }else{
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                    }
//                });
//    }
    private void joinToChat(String chatUid){
        Intent intent = new Intent(context,ListMessages2Activity.class);
        intent.putExtra("chatUid",chatUid);
        context.startActivity(intent);

    }



}
