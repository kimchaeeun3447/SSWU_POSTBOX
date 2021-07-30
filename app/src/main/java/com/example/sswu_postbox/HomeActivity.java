package com.example.sswu_postbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    String TAG = HomeActivity.class.getSimpleName();

    ArrayList<String> major = new ArrayList<String>() {};
    String user_major, user_major2, user_major3;

    //Recyclerview
    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;


    //listView
    ListView postList;
    ArrayList<String> post_title = new ArrayList<>();
    ArrayList<String> post_date = new ArrayList<>();
    MyListAdapter myListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // 사용자 전공 가져오기(챈아 밑에서 이 함수 찾아가서 내가 표시해놓은 곳에 url 연결하면 돼)
        user_major();

        // recyclerView
        home_keyword_list();


        // listView
        postList = findViewById(R.id.home_post_list);
        myListAdapter = new MyListAdapter(this, post_title, post_date);
        postList.setAdapter(myListAdapter);

        notice_list();


        Button plus_keyword_btn = findViewById(R.id.my_keyword_list_plus_btn);
        plus_keyword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KeywordSettingActivity.class);
                startActivity(intent);
            }
        });


        Button check_keyword_post_btn = findViewById(R.id.check_keyword_post_btn);
        check_keyword_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CheckKeywordPostActivity.class);
                startActivity(intent);
            }
        });

/*
        ImageButton notification_btn = (ImageButton)findViewById(R.id.notification_btn);
        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationListActivity.class);
                startActivity(intent);
            }
        });


        ImageButton locker_btn = (ImageButton)findViewById(R.id.locker_btn);
        locker_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LockerActivity.class);
                startActivity(intent);
            }
        });

*/
    }


    //Recyclerview
    private void home_keyword_list(){

        recyclerView = findViewById(R.id.home_keyword_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> itemList = new ArrayList<>();
        itemList.add("키워드1");
        itemList.add("키워드2");
        itemList.add("키워드3");
        itemList.add("키워드4");
        itemList.add("키워드5");
        itemList.add("키워드6");
        itemList.add("키워드7");
        itemList.add("키워드8");
        itemList.add("키워드9");

        adapter = new MyRecyclerAdapter(this, itemList, onClickItem);
        recyclerView.setAdapter(adapter);

    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(HomeActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };


    void notice_list() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/userNotice/";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject notice = response.getJSONObject(i).getJSONObject("notice");
                                post_title.add(notice.getString("title"));
                                post_date.add(notice.getString("date"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        myListAdapter.notifyDataSetChanged();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(TAG, "notice user list error");
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


    public void user_major() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/detail/user/";

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                JSONObject userInfo = response.getJSONObject(0);

                                user_major = userInfo.getString("user_major");
                                user_major2 = userInfo.getString("user_major2");
                                user_major3 = userInfo.getString("user_major3");

                                // user_major -> 주전공 , user_major2 -> 복수전공, user_major3 -> 부전공
                                // 여기부터 학과별 학과 홈페이지 url 연결하기
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Log.d(TAG, "user major call fail");
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
}