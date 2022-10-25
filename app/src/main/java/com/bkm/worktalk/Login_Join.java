package com.bkm.worktalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Login_Join extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    EditText et_mail, et_pw, et_pw2, et_name, et_emp, et_phone, et_dept, et_job;
    ImageButton ib_all_dell_1, ib_all_dell_2, ib_all_dell_3, ib_all_dell_4, ib_all_dell_5, ib_all_dell_6, ib_all_dell_7, ib_all_dell_8;
    Button btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_join);

        mDatabase = FirebaseDatabase.getInstance().getReference("UserInfo");
        mAuth = FirebaseAuth.getInstance();

        et_mail = (EditText) findViewById(R.id.et_mail);
        et_pw = (EditText) findViewById(R.id.et_pw);
        et_pw2 = (EditText) findViewById(R.id.et_pw2);
        et_name = (EditText) findViewById(R.id.et_name);
        et_emp = (EditText) findViewById(R.id.et_emp);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_dept = (EditText) findViewById(R.id.et_dept);
        et_job = (EditText) findViewById(R.id.et_job);

        btn_join = (Button) findViewById(R.id.btn_join);

        ib_all_dell_1 = (ImageButton) findViewById(R.id.ib_all_del1);
        ib_all_dell_2 = (ImageButton) findViewById(R.id.ib_all_del2);
        ib_all_dell_3 = (ImageButton) findViewById(R.id.ib_all_del3);
        ib_all_dell_4 = (ImageButton) findViewById(R.id.ib_all_del4);
        ib_all_dell_5 = (ImageButton) findViewById(R.id.ib_all_del5);
        ib_all_dell_6 = (ImageButton) findViewById(R.id.ib_all_del6);
        ib_all_dell_7 = (ImageButton) findViewById(R.id.ib_all_del7);
        ib_all_dell_8 = (ImageButton) findViewById(R.id.ib_all_del8);

//        dellWatching thread = new dellWatching();
//        thread.start();

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = et_mail.getText().toString().trim();
                String pw = et_pw.getText().toString().trim();
                String rePw = et_pw2.getText().toString().trim();
                String name = et_name.getText().toString().trim();
                String emp = et_emp.getText().toString().trim();
                String hp = et_phone.getText().toString().trim();
                String dept = et_dept.getText().toString().trim();
                String job = et_job.getText().toString().trim();

                if(email.equals("")) {
                    showAlert("이메일 아이디를 입력해주세요.");
                    return;
                }
                if(pw.equals("")) {
                    showAlert("비밀번호를 입력해주세요.");
                    return;
                }
                if(rePw.equals("")) {
                    showAlert("비밀번호를 확인해주세요.");
                    return;
                }
                if(name.equals("")) {
                    showAlert("이름을 입력해주세요.");
                    return;
                }
                if(emp.equals("")) {
                    showAlert("사원번호를 입력해주세요.");
                    return;
                }
                if(hp.equals("")) {
                    showAlert("전화번호를 입력해주세요.");
                    return;
                }
                if(dept.equals("")) {
                    showAlert("부서를 입력해주세요.");
                    return;
                }
                if(job.equals("")) {
                    showAlert("직급을 입력해주세요.");
                    return;
                }

                if(!pw.equals(rePw)) {
                    showAlert("비밀번호가 일치하지 않습니다.");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, rePw)
                    .addOnCompleteListener(Login_Join.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                JoinDTO joinDTO = new JoinDTO(email, rePw, name, emp, hp, dept,job);

                                mDatabase.child(uid).setValue(joinDTO);
//                                    thread.interrupt();
                                showAlert("가입이 완료되었습니다.");
                            } else {
                                showAlert("가입에 실패했습니다. 다시 시도해주세요.");
                            }
                        }
                    });
            }
        });

        ib_all_dell_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_mail.setText("");
            }
        });
        ib_all_dell_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_pw.setText("");
            }
        });
        ib_all_dell_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_pw2.setText("");
            }
        });
        ib_all_dell_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_name.setText("");
            }
        });
        ib_all_dell_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_emp.setText("");
            }
        });
        ib_all_dell_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_phone.setText("");
            }
        });
        ib_all_dell_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_dept.setText("");
            }
        });
        ib_all_dell_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_job.setText("");
            }
        });
    }

//    private class dellWatching extends Thread {
//
//        public dellWatching() {
//            // 초기화 작업
//        }
//
//        public void run() {
//
//            while(true) {
//
//                // 스레드에게 수행시킬 동작들 구현
//                String email = et_mail.getText().toString().trim();
//                String pw = et_pw.getText().toString().trim();
//                String rePw = et_pw2.getText().toString().trim();
//                String name = et_name.getText().toString().trim();
//                String emp = et_emp.getText().toString().trim();
//                String hp = et_phone.getText().toString().trim();
//                String dept = et_dept.getText().toString().trim();
//                String job = et_job.getText().toString().trim();
//
//                if (!email.equals("")) {
//                    ib_all_dell_1.setVisibility(View.VISIBLE);
//                    ib_all_dell_1.setImageResource(R.drawable.btn_all_del);
//                }
//                if (email.equals("")) {
//                    ib_all_dell_1.setVisibility(View.GONE);
//                }
//
//                if (!pw.equals("")) {
//                    ib_all_dell_2.setVisibility(View.VISIBLE);
//                    ib_all_dell_2.setImageResource(R.drawable.btn_all_del);
//                }
//                if (pw.equals("")) {
//                    ib_all_dell_2.setVisibility(View.GONE);
//                }
//
//                if (!rePw.equals("")) {
//                    ib_all_dell_3.setVisibility(View.VISIBLE);
//                    ib_all_dell_3.setImageResource(R.drawable.btn_all_del);
//                }
//                if (rePw.equals("")) {
//                    ib_all_dell_3.setVisibility(View.GONE);
//                }
//
//                if (!name.equals("")) {
//                    ib_all_dell_4.setVisibility(View.VISIBLE);
//                    ib_all_dell_4.setImageResource(R.drawable.btn_all_del);
//                }
//                if (name.equals("")) {
//                    ib_all_dell_4.setVisibility(View.GONE);
//                }
//
//                if (!emp.equals("")) {
//                    ib_all_dell_5.setVisibility(View.VISIBLE);
//                    ib_all_dell_5.setImageResource(R.drawable.btn_all_del);
//                }
//                if (emp.equals("")) {
//                    ib_all_dell_5.setVisibility(View.GONE);
//                }
//
//                if (!hp.equals("")) {
//                    ib_all_dell_6.setVisibility(View.VISIBLE);
//                    ib_all_dell_6.setImageResource(R.drawable.btn_all_del);
//                }
//                if (hp.equals("")) {
//                    ib_all_dell_6.setVisibility(View.GONE);
//                }
//
//                if (!dept.equals("")) {
//                    ib_all_dell_7.setVisibility(View.VISIBLE);
//                    ib_all_dell_7.setImageResource(R.drawable.btn_all_del);
//                }
//                if (dept.equals("")) {
//                    ib_all_dell_7.setVisibility(View.GONE);
//                }
//
//                if (!job.equals("")) {
//                    ib_all_dell_8.setVisibility(View.VISIBLE);
//                    ib_all_dell_8.setImageResource(R.drawable.btn_all_del);
//                }
//                if (job.equals("")) {
//                    ib_all_dell_8.setVisibility(View.GONE);
//                }
//            }
//        }
//    }

    public void showAlert(String msg) {    // 다이얼로그 창 띄우는 메서드

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("").setMessage(msg);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}