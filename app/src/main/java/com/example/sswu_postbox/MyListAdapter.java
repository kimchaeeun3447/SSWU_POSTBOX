package com.example.sswu_postbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> post_keyword;
    ArrayList<String> post_title;
    ArrayList<String> post_date;


    public MyListAdapter(Context context, ArrayList<String> post_keyword, ArrayList<String> post_title, ArrayList<String> post_date) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.post_keyword = post_keyword;
        this.post_title = post_title;
        this.post_date = post_date;
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

        TextView title_keyword = view.findViewById(R.id.title_keyword);
        title_keyword.setText(post_keyword.get(position));


        TextView contents_postTitle = view.findViewById(R.id.contents_postTitle);
        contents_postTitle.setText(post_title.get(position));


        TextView contents_date = view.findViewById(R.id.contents_date);
        contents_date.setText(post_date.get(position));


        View body = view.findViewById(R.id.body);

        ImageButton post_save_btn = view.findViewById(R.id.post_save_btn);
        ImageButton post_share_btn = view.findViewById(R.id.post_share_btn);

        //글 클릭 - url
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click list body", Toast.LENGTH_SHORT).show();
            }
        });


        //저장 버튼
        post_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click list btn1", Toast.LENGTH_SHORT).show();
            }
        });


        //공유 버튼
        post_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "click list btn2", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
