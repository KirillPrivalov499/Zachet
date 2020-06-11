package com.example.gorun.NewStory.Logic;

import android.content.Context;
import android.content.Intent;

import com.example.gorun.NewStory.ui.ListMessages2Activity;

public class CreateOrJoinNewChat {

    String uid2;
    private Context context;
    public CreateOrJoinNewChat(Context context, String Uid1,String Uid2){
        this.context = context;
        uid2 = Uid2;
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
        Intent intent = new Intent(context, ListMessages2Activity.class);
        intent.putExtra("chatUid",chatUid);
        intent.putExtra("recipientUid",uid2);
        context.startActivity(intent);

    }



}
