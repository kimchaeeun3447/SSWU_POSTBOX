package com.example.sswu_postbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    String TAG = SignUpActivity.class.getSimpleName();

    EditText id, password, major;
    Button sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sign_up = findViewById(R.id.signup_check_btn);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up();
            }
        });
    }

    void sign_up() {
        String url = "http://3.37.68.242:8000/users/";

        id = findViewById(R.id.signup_id_edit);
        password = findViewById(R.id.signup_pwd_edit);
        major = findViewById(R.id.signup_major_edit);

        HashMap<String, String> signup_json = new HashMap<>();
        signup_json.put("username", id.getText().toString());
        signup_json.put("password", password.getText().toString());
        signup_json.put("password2", password.getText().toString());
        signup_json.put("user_major", major.getText().toString());
        signup_json.put("user_major2", "");
        signup_json.put("user_major3", "");
        signup_json.put("user", null);

        JSONObject parameter = new JSONObject(signup_json);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                url,
                parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast toast = Toast.makeText(getApplicationContext(), "수룡이의 우편함에 오신 것을 환영합니다!", Toast.LENGTH_LONG);
                        toast.show();

                        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
                        toast.show();

                        error.printStackTrace();
                        Log.d(TAG, "sign up fail");
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}