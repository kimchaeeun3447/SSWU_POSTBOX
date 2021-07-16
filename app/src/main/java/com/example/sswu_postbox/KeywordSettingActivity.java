package com.example.sswu_postbox;

import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeywordSettingActivity extends AppCompatActivity {
    String TAG = KeywordSettingActivity.class.getSimpleName();

    EditText keyword_add_text, keyword_del_text;
    Button keyword_add_btn, keyword_del_btn;

    static String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_setting);

        final GridView my_keyword_list = (GridView)findViewById(R.id.my_keyword_list);
        MyGridAdapter gridAdapter = new MyGridAdapter(this);
        my_keyword_list.setAdapter(gridAdapter);

        keyword_list();

        keyword_add_btn = findViewById(R.id.keyword_adding_btn);
        keyword_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword_add();
            }
        });


        keyword_del_btn = findViewById(R.id.keyword_delete_btn);
        keyword_del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword_del();
            }
        });
    }

    // Gridview Adapter
    public static class MyGridAdapter extends BaseAdapter{
        Context context;

        //사용자가 등록한 모든 키워드들을 받아올 변수(임시로 텍스트 넣어둠)
        static String[] user_keyword_list = { "A" };

        public MyGridAdapter(Context c){
            context = c;
        }

        @Override
        //보여줄 키워드 개수
        public int getCount() {
            return user_keyword_list.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        // 띄우는 부분
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView = new TextView(context);
            textView.setLayoutParams(new GridView.LayoutParams(300,100));
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(Dimension.DP, 30);
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.keyword_search));

            textView.setText(user_keyword_list[position]);

            return textView;
        }
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
                        Toast toast = Toast.makeText(getApplicationContext(), "키워드 등록에 성공했습니다.", Toast.LENGTH_LONG);
                        toast.show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(), "키워드가 정상적으로 추가되지 않았습니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_LONG);
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
                        Toast toast = Toast.makeText(getApplicationContext(), "키워드를 삭제했습니다.", Toast.LENGTH_LONG);
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
                        ArrayList<String> keyword_list = new ArrayList<>();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                keyword_list.add(response.getJSONObject(i).getString("keyword"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        MyGridAdapter.user_keyword_list = keyword_list.toArray(new String[keyword_list.size()]);
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
}




