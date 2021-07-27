package com.example.sswu_postbox;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class KeywordSettingActivity extends AppCompatActivity {
    String TAG = KeywordSettingActivity.class.getSimpleName();

    EditText keyword_add_text, keyword_del_text, keyword_search_text;
    Button keyword_add_btn, keyword_del_btn, keyword_search_btn;

    GridView my_keyword_list;
    MyGridAdapter gridAdapter;


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_setting);

        my_keyword_list = (GridView)findViewById(R.id.my_keyword_list);
        gridAdapter = new MyGridAdapter(this);
        my_keyword_list.setAdapter(gridAdapter);

        keyword_list();

        keyword_add_btn = findViewById(R.id.keyword_adding_btn);
        keyword_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword_add();
                notice_list_post();
            }
        });


        keyword_del_btn = findViewById(R.id.keyword_delete_btn);
        keyword_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword_del();
            }
        });

        keyword_search_btn = findViewById(R.id.keyword_searching_btn);
        keyword_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword_search();
            }
        });

        ImageButton back_btn = (ImageButton)findViewById(R.id.keyword_setting_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
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

    void keyword_add() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        keyword_add_text = findViewById(R.id.keyword_adding);

        HashMap<String, String> keywordAdd_json = new HashMap<>();
        keywordAdd_json.put("keyword", keyword_add_text.getText().toString());
        keywordAdd_json.put("user", null);

        JSONObject parameter = new JSONObject(keywordAdd_json);
        String url = "http://3.37.68.242:8000/keywords/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url,
                parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        gridAdapter.user_keyword_list.clear();
                        keyword_list();

                        Log.d(TAG, gridAdapter.user_keyword_list.toString());

                        try {
                            if (response.getString("keyword").equals("duplicate")) {
                                Toast toast = Toast.makeText(getApplicationContext(), "중복된 키워드 입니다.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else {
                                Toast toast = Toast.makeText(getApplicationContext(), "키워드 등록에 성공했습니다.", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "키워드가 정상적으로 추가되지 않았습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT);
                        toast.show();

                        error.printStackTrace();
                        Log.d(TAG, "keyword add fail " + token);
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

    void keyword_del() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        keyword_del_text = findViewById(R.id.keyword_delete);

        String url = "http://3.37.68.242:8000/detail/keywords/?keyword=" + keyword_del_text.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gridAdapter.user_keyword_list.clear();
                        keyword_list();

                        Toast toast = Toast.makeText(getApplicationContext(), "키워드를 삭제했습니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "키워드가 정상적으로 삭제되지 않았습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                        toast.show();

                        error.printStackTrace();
                        Log.d(TAG, "delete keyword fail");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return give_token(token);
            }
        };


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
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

    void keyword_search() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        keyword_search_text = findViewById(R.id.keyword_searching);

        String keyword = keyword_search_text.getText().toString();
        String url = "http://3.37.68.242:8000/detail/keywords/?search=" + keyword;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() == 0) {
                            Toast toast = Toast.makeText(getApplicationContext(), "찾으시는 검색 결과가 없습니다.", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        ArrayList<String> keyword_list = new ArrayList<>();

                        for(int i = 0; i < response.length(); i++) {
                            try {
                                keyword_list.add(response.getJSONObject(i).getString("keyword"));

                                Log.d(TAG, keyword_list.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        gridAdapter.user_keyword_list = keyword_list;
                        gridAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "키워드 검색에 실패했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                        toast.show();

                        error.printStackTrace();
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

    void notice_list_post() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/userNotice/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "notice list post success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, "notice list post fail");
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




