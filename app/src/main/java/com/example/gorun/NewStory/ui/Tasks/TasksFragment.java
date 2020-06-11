package com.example.gorun.NewStory.ui.Tasks;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gorun.NewStory.adapters.RVActivitiesAdapter;
import com.example.gorun.NewStory.models.MyActivityModel;
import com.example.gorun.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static com.example.gorun.NewStory.ui.NavigationActivity.Authcode;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class TasksFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

            CalendarView calendarView = root.findViewById(R.id.calendarView);

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

                @Override
                public void onSelectedDayChange(CalendarView view, int year,
                                                int month, int dayOfMonth) {
                    int mYear = year;
                    int mMonth = month;
                    int mDay = dayOfMonth;
                    String selectedDate = new StringBuilder().append(mMonth + 1)
                            .append("-").append(mDay).append("-").append(mYear)
                            .append(" ").toString();
                    Toast.makeText(getContext(), selectedDate, Toast.LENGTH_LONG).show();
                }
            });

        return root;
    }





}
