package com.example.gorun.NewStory.ui.Profile.ChildFragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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


public class ManageActivitiesFragment extends Fragment {

    List<MyActivityModel> activityModelList;
    private RVActivitiesAdapter adapter;
    private RecyclerView recyclerView;
    Spinner Spinner;
    String spinnerChoose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new LoadActivities().execute();
        View view = inflater.inflate(R.layout.fragment_manage_activities, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_activities);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        Spinner = (Spinner) view.findViewById(R.id.spinner);
        createArrayAdapterForSpinner();
        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {

                String[] choose = getResources().getStringArray(R.array.typesActivities);
                spinnerChoose = choose[selectedItemPosition];
                Toast toast = Toast.makeText(getContext(),
                        "Ваш выбор: " + choose[selectedItemPosition], Toast.LENGTH_SHORT);
                toast.show();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

       return view;
    }

    private void createArrayAdapterForSpinner() {
        ArrayAdapter<?> adapter =
                ArrayAdapter.createFromResource(getContext(), R.array.typesActivities, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner.setAdapter(adapter);
    }

    class LoadActivities extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            final MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            String jsonStr = "access_token=" + Authcode;
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://www.strava.com/api/v3/athlete/activities?" + jsonStr)
                    .get()
                    .build();

            try {
                okhttp3.Response response = okHttpClient.newCall(request).execute();
                assert response.body() != null;
                //Data Received
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("fefeffef",s);
            activityModelList = new ArrayList<>();
            Gson gson = new Gson();
            try{
                MyActivityModel[] activityArray = gson.fromJson(s, MyActivityModel[].class);
                activityModelList = new ArrayList<>(Arrays.asList(activityArray));
                adapter = new RVActivitiesAdapter(getActivity(),activityModelList, spinnerChoose);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapter);
            }catch (Exception e){
            }
            // Toast.makeText(MainActivity.this, "The new Expiry time is: " + ExpiryTime + " and System time is " + System.currentTimeMillis(), Toast.LENGTH_LONG).show();

        }
    }
}
