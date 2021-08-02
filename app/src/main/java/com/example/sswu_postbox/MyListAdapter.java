package com.example.sswu_postbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyListAdapter extends BaseAdapter {
    String TAG = MyListAdapter.class.getSimpleName();

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> post_title;
    ArrayList<String> post_date;
    ArrayList<Boolean> post_saved;


    public MyListAdapter(Context context, ArrayList<String> post_title, ArrayList<String> post_date, ArrayList<Boolean> post_saved) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.post_title = post_title;
        this.post_date = post_date;
        this.post_saved = post_saved;
    }

    @Override
    public int getCount() {
        return post_title.size();
    }

    @Override
    public Object getItem(int position) {
        return post_title.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = layoutInflater.inflate(R.layout.post_listview_layout, null);


        TextView contents_postTitle = view.findViewById(R.id.contents_postTitle);
        contents_postTitle.setText(post_title.get(position));

        // 제목 클릭 이벤트
        contents_postTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "제목 클릭", Toast.LENGTH_SHORT).show();
            }
        });


        TextView contents_date = view.findViewById(R.id.contents_date);
        contents_date.setText(post_date.get(position));


        View body = view.findViewById(R.id.body);


        ImageButton post_share_btn = view.findViewById(R.id.post_share_btn);

        // 보관함 저장 버튼
        ImageButton save_post = (ImageButton) view.findViewById(R.id.save_btn);
        save_post.setSelected(post_saved.get(position));
        save_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());

                if (v.isSelected()) {
                    store_modify(post_title.get(position), v.isSelected());
                }
                else{
                    store_modify(post_title.get(position), v.isSelected());
                }

            }
        });


        //공유 버튼
        post_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "share", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    void store_modify(String title, boolean state) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String token = sharedPreferences.getString("access_token", "null");

        String url = "http://3.37.68.242:8000/update/notice/";

        HashMap<String, String> store_json = new HashMap<>();
        store_json.put("title", title);
        store_json.put("store", Boolean.toString(state));

        JSONObject parameter = new JSONObject(store_json);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PATCH,
                url,
                parameter,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "보관 상태 수정 성공" + title + state);
                        if (state) Toast.makeText(context, "보관함 저장", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(context, "보관함 저장 취소", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(TAG, "보관 상태 수정 실패");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return give_token(token);
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(request);
    }

//    void store_state_get(String title) {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//        String token = sharedPreferences.getString("access_token", "null");
//
//        String url = "http://3.37.68.242:8000/userNotice/?search=" + title;
//
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            JSONObject user_notice = response.getJSONObject(0);
//                            boolean state = user_notice.getBoolean("store");
//                            save_post.setSelected(state);
//
//                            Log.d(TAG, "보관 상태 불러오기 성공" + title + " " + state);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                return give_token(token);
//            }
//        };
//
//        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
//        queue.add(request);
//    }

    Map<String, String> give_token(String token) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        return headers;
    }
}
