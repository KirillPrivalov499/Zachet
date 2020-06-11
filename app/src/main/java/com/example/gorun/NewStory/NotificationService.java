package com.example.gorun.NewStory;

import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.gorun.ChatTutorial.MainActivity;
import com.example.gorun.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;


public class NotificationService extends Service {

    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Служба создана", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseDatabase.getInstance().getReference().child("Notifications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("new_reuqest")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Uri ringURI =
                                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_account)
                                        .setContentTitle("Напоминание")
                                        .setContentText("Добавить нового друга?")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setSound(ringURI);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(NotificationService.this);
                        notificationManager.notify(NOTIFY_ID, builder.build());
                        Toast.makeText(NotificationService.this, "что то есть", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


        FirebaseDatabase.getInstance().getReference().child("Notifications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("new_messages")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Uri ringURI =
                                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(NotificationService.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_account)
                                        .setContentTitle("Новое сообщение")
                                        .setContentText("Вам написали новое сообщение!!!")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setSound(ringURI);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(NotificationService.this);
                        notificationManager.notify(NOTIFY_ID, builder.build());
                        Toast.makeText(NotificationService.this, "что то есть", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        Toast.makeText(this, "Служба запущена", Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Служба остановлена", Toast.LENGTH_SHORT).show();
    }
}
