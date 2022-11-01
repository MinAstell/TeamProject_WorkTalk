package com.bkm.worktalk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragInnerProject extends AppCompatActivity {

    private TextView tv_innerProjectName;
    private TextView tv_innerProjectExplain;
    private ArrayList<ProjectDTO> arrayList;
    public String projectName = "";
    public String projectExplain = "";

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_innerproject);

        tv_innerProjectName = (TextView) findViewById(R.id.tv_innerProjectName);
        tv_innerProjectExplain = (TextView) findViewById(R.id.tv_innerProjectExplain);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        projectName = bundle.getString("projectName");
        projectExplain = bundle.getString("projectExplain");

        tv_innerProjectName.setText(projectName);
        tv_innerProjectExplain.setText(projectExplain);

    }
}