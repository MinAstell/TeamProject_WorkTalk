package com.bkm.worktalk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class Fragment extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragProject fragProject;
    private FragTalk fragTalk;
    private FragSettings fragSettings;
    private FragUsers fragUsers;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        fragmentManager = getSupportFragmentManager();

        fragProject = new FragProject();
        fragTalk = new FragTalk();
        fragSettings = new FragSettings();
        fragUsers = new FragUsers();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, fragProject).commitAllowingStateLoss();

        findViewById(R.id.iv_project).setOnClickListener(mClick);
        findViewById(R.id.iv_talk).setOnClickListener(mClick);
        findViewById(R.id.iv_settings).setOnClickListener(mClick);
        findViewById(R.id.iv_users).setOnClickListener(mClick);
    }
    View.OnClickListener mClick = new View.OnClickListener() {
        public void onClick(View v) {
            transaction = fragmentManager.beginTransaction();
            switch(v.getId()) {
                case R.id.iv_project:
                    transaction.replace(R.id.framelayout, fragProject).commitAllowingStateLoss();
                    break;
                case R.id.iv_talk:
                    transaction.replace(R.id.framelayout, fragTalk).commitAllowingStateLoss();
                    break;
                case R.id.iv_settings:
                    transaction.replace(R.id.framelayout, fragSettings).commitAllowingStateLoss();
                    break;
                case R.id.iv_users:
                    transaction.replace(R.id.framelayout, fragUsers).commitAllowingStateLoss();
                    break;
            }
        }
    };
}