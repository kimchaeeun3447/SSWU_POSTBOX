package com.example.sswu_postbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LockerActivity extends AppCompatActivity {

    GridView my_keyword_list;
    MyGridAdapter gridAdapter;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

        my_keyword_list = (GridView)findViewById(R.id.my_keyword_list3);
        gridAdapter = new MyGridAdapter(this);
        my_keyword_list.setAdapter(gridAdapter);

        keyword_list();

        ImageButton locker_back_btn = findViewById(R.id.locker_back_btn);
        locker_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.notification_btn:
                        setFrag(0);
                        break;
                    case R.id.locker_btn:
                        setFrag(1);
                        break;
                    case R.id.home_btn:
                        setFrag(2);
                        break;
                    case R.id.setting_btn:
                        setFrag(3);
                        break;
                }

                return true;
            }
        });
    }

    void keyword_list() {
        String TAG = KeywordSettingActivity.class.getSimpleName();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/detail/keywords/";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                gridAdapter.user_keyword_list.add(response.getJSONObject(i).getString("keyword"));
                                gridAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "keyword list get fail");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return give_token(token);
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    Map<String, String> give_token(String token) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        return headers;
    }


    private void setFrag(int n) {

        switch (n){
            case 0:
                Intent notification = new Intent(this, NotificationListActivity.class);
                startActivity(notification);
                break;
            case 1:
                Intent locker = new Intent(this, LockerActivity.class);
                startActivity(locker);
                break;
            case 2:
                Intent home = new Intent(this, HomeActivity.class);
                startActivity(home);
                break;
            case 3:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                break;
        }
    }
}