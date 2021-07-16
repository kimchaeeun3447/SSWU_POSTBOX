package com.example.sswu_postbox;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/* To. 채은...
     채은아.. 어뎁터가 다른 클래스 안에 들어가있으면 static으로 선언돼서 좀 코드가 꼬여서 따로 분리했어

    글고 어차피 재사용하기도 하고 찾아보니까 Adapter 파일은 분리하는게 좀 더 좋은듯!
    오류는 없게 다 고쳐놨어.. 그럼 20000
 */

// Gridview Adapter
public class MyGridAdapter extends BaseAdapter{
    Context context;

    //사용자가 등록한 모든 키워드들을 받아올 변수(임시로 텍스트 넣어둠)
    ArrayList<String> user_keyword_list = new ArrayList<>();

    public MyGridAdapter(Context c){
        context = c;
    }

    @Override
    //보여줄 키워드 개수
    public int getCount() {
        return user_keyword_list.size();
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

        textView.setText(user_keyword_list.get(position));

        return textView;
    }
}

