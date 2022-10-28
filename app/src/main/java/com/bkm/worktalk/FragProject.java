package com.bkm.worktalk;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FragProject extends Fragment {

    private FloatingActionButton fab_project; //플로팅 버튼(프로젝트 생성으로 이동)

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_frag_project, container, false);

        fab_project = (FloatingActionButton) view.findViewById(R.id.fab_project);

        // 플로팅 버튼을 누르면 프로젝트 생성 창으로======================================================
        fab_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateProject.class);
                startActivity(intent);
            }
        });
        return view;

    }
}