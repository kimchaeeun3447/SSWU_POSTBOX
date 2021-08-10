package com.example.sswu_postbox;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    String TAG = SettingActivity.class.getSimpleName();
    TextView username, major;

    private BottomNavigationView bottomNavigationView;
    Switch notice;
    String shared = "file";

    public void goQuestion(View view) {
        Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
        startActivity(intent);
    }

    public void goDepartment(View view) {
        Intent intent = new Intent(getApplicationContext(), DepartModiActivity.class);
        startActivity(intent);
    }

    class noticeSwitchListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
            // 알림 설정 여부를 저장할 저장소
            SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (isChecked) {
                Toast.makeText(getApplicationContext(), "알림이 활성화되었습니다.", Toast.LENGTH_SHORT).show();
//                bool 키값을 가진 SharedPreferences에다가 true(알림 활성화)를 저장
                editor.putBoolean("bool", true);
                editor.commit();
            }
            else {
                Toast.makeText(getApplicationContext(), "알림이 비활성화되었습니다.", Toast.LENGTH_SHORT).show();
//                bool 키값을 가진 SharedPreferences에다가 false(알림 비활성화)를 저장
                editor.putBoolean("bool", false);
                editor.commit();
            }
        }
    }

//    로그아웃 클릭 시 팝업창
    public void btn_logout(View v) {
        new AlertDialog.Builder(this)
                .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent i = new Intent(SettingActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(i);
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Switch notice = findViewById(R.id.notice);

        SharedPreferences sharedPreferences = getSharedPreferences(shared, 0);
//        key값이 bool인 SharedPreferences 저장소에서 데이터를 가져온다
        boolean value = sharedPreferences.getBoolean("bool", true);
//        Switch의 checked에 value 값 대입
        notice.setChecked(value);

        get_user_info();


        ImageButton locker_back_btn = findViewById(R.id.locker_back_btn);
        locker_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button keyword = (Button) findViewById(R.id.keyword);
        keyword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), KeywordSettingActivity.class);
                startActivity(intent);
            }

        });

        Switch noticeSwitch = findViewById(R.id.notice);
        noticeSwitch.setOnCheckedChangeListener(new noticeSwitchListener());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.all_posts_btn:
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

    void get_user_info() {
        username = findViewById(R.id.userName);
        major = findViewById(R.id.department);

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

                            username.setText(userInfo.getString("user"));
                            major.setText(userInfo.getString("user_major"));
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


    private void setFrag(int n) {

        switch (n){
            case 0:
                Intent AllPosts = new Intent(this, CheckKeywordPostActivity.class);
                startActivity(AllPosts);
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