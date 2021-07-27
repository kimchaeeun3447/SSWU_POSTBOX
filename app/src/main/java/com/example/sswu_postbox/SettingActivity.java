package com.example.sswu_postbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class SettingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


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