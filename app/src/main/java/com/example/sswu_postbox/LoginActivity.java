package com.example.sswu_postbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    String TAG = LoginActivity.class.getSimpleName();

    Button go_sign_up, sign_in;
    EditText id, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        go_sign_up = findViewById(R.id.signup_btn);
        sign_in = findViewById(R.id.login_btn);

        go_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

                FirebaseMessaging.getInstance().subscribeToTopic("test")
                        .addOnCompleteListener( task -> {
                            if (task.isComplete()) Log.d(TAG, "구독 성공");
                            else Log.d(TAG, "구독 실패");
                        });
            }
        });
    }

    void login() {
        String url = "http://3.37.68.242:8000/login/";

        id = findViewById(R.id.login_id_edit);
        password = findViewById(R.id.login_pwd_edit);

        HashMap<String, String> login_json = new HashMap<>();
        login_json.put("username", id.getText().toString());
        login_json.put("password", password.getText().toString());

        JSONObject parameter = new JSONObject(login_json);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url,
                parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);

                        try {
                            String access_token = response.getString("access");

                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            Log.d(TAG, "access_token : " + access_token);

                            editor.putString("access_token", access_token).apply();
                            Log.d(TAG, "Shared test : " + sharedPreferences.getString("access_token", "null"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast toast = Toast.makeText(getApplicationContext(), "환영합니다.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                        toast.show();

                        error.printStackTrace();
                        Log.d(TAG, "login fail");
                    }
                });


        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}