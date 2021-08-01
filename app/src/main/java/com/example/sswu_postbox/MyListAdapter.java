package com.example.sswu_postbox;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
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

import java.util.ArrayList;


public class MyListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> post_title;
    ArrayList<String> post_date;



    public MyListAdapter(Context context, ArrayList<String> post_title, ArrayList<String> post_date) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
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
        ImageButton save_post  = (ImageButton) view.findViewById(R.id.save_btn);
        save_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());

                if (v.isSelected()){
                    Toast.makeText(context, "보관함 저장", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "보관함 저장 취소", Toast.LENGTH_SHORT).show();
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
}
