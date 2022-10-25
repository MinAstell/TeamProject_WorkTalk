package com.bkm.worktalk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login_Join extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private String myUid;

    EditText et_mail, et_pw, et_pw2, et_name, et_emp, et_phone, et_dept,
            et_job, et_mail_login, et_pw_login, et_inpEmp,
            et_inpEmail, et_newPw, et_newRePw;
    ImageButton ib_all_dell_1, ib_all_dell_2, ib_all_dell_3, ib_all_dell_4, ib_all_dell_5,
            ib_all_dell_6, ib_all_dell_7, ib_all_dell_8, ib_all_del1_login, ib_all_del2_login;
    Button btn_join, btn_login, btn_transJoin, btn_selAccount, btn_selPw, btn_sendToDbFindYourAct,
            btn_sendToDbFindYourPw, btn_modifyPw;

    LinearLayout loginPage, joinPage, selAccount, selPw, modifyPw;
    TextView tv_sucFindYourId, tv_viewId;

    ArrayList<JoinDTO> userInfo = new ArrayList<>();

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

        et_mail_login = (EditText) findViewById(R.id.et_mail_login);
        et_pw_login = (EditText) findViewById(R.id.et_pw_login);

        et_inpEmp = (EditText) findViewById(R.id.et_inpEmp);

        et_inpEmail = (EditText) findViewById(R.id.et_inpEmail);
        et_newPw = (EditText) findViewById(R.id.et_newPw);
        et_newRePw = (EditText) findViewById(R.id.et_newRePw);

        tv_sucFindYourId = (TextView) findViewById(R.id.tv_sucFindYourId);
        tv_viewId = (TextView) findViewById(R.id.tv_viewId);

        btn_join = (Button) findViewById(R.id.btn_join);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_transJoin = (Button) findViewById(R.id.btn_transJoin);

        btn_selAccount = (Button) findViewById(R.id.btn_selAccount);
        btn_selPw = (Button) findViewById(R.id.btn_selPw);

        btn_sendToDbFindYourAct = (Button) findViewById(R.id.btn_sendToDbFindYourAct);

        btn_sendToDbFindYourPw = (Button) findViewById(R.id.btn_sendToDbFindYourPw);
        btn_modifyPw = (Button) findViewById(R.id.btn_modifyPw);

        ib_all_dell_1 = (ImageButton) findViewById(R.id.ib_all_del1);
        ib_all_dell_2 = (ImageButton) findViewById(R.id.ib_all_del2);
        ib_all_dell_3 = (ImageButton) findViewById(R.id.ib_all_del3);
        ib_all_dell_4 = (ImageButton) findViewById(R.id.ib_all_del4);
        ib_all_dell_5 = (ImageButton) findViewById(R.id.ib_all_del5);
        ib_all_dell_6 = (ImageButton) findViewById(R.id.ib_all_del6);
        ib_all_dell_7 = (ImageButton) findViewById(R.id.ib_all_del7);
        ib_all_dell_8 = (ImageButton) findViewById(R.id.ib_all_del8);

        ib_all_del1_login = (ImageButton) findViewById(R.id.ib_all_del1_login);
        ib_all_del2_login = (ImageButton) findViewById(R.id.ib_all_del2_login);

        loginPage = (LinearLayout) findViewById(R.id.loginPage);
        joinPage = (LinearLayout) findViewById(R.id.joinPage);

        selAccount = (LinearLayout) findViewById(R.id.selAccount);
        selPw = (LinearLayout) findViewById(R.id.selPw);

        modifyPw = (LinearLayout) findViewById(R.id.modifyPw);

//        dellWatching thread = new dellWatching();
//        thread.start();

        btn_selAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selAccount.setVisibility(View.VISIBLE);
                selPw.setVisibility(View.INVISIBLE);
                loginPage.setVisibility(View.INVISIBLE);
                joinPage.setVisibility(View.INVISIBLE);
            }
        });
        btn_selPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selAccount.setVisibility(View.INVISIBLE);
                selPw.setVisibility(View.VISIBLE);
                loginPage.setVisibility(View.INVISIBLE);
                joinPage.setVisibility(View.INVISIBLE);
            }
        });

        btn_sendToDbFindYourPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_inpEmail.getText().toString().trim();

                if(email.equals("")) {
                    showAlert("이메일을 입력해주세요.");
                    return;
                }

                mDatabase.orderByChild("emailId").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                myUid = snapshot.getKey();
                            }

                            modifyPw.setVisibility(View.VISIBLE);
                        }
                        else {
                            showAlert("등록되지 않은 이메일 입니다. 다시 시도해주세요.");
                            et_inpEmail.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_modifyPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatePw = et_newPw.getText().toString().trim();
                String updateRePw = et_newRePw.getText().toString().trim();

                if(updatePw.equals("")) {
                    showAlert("새로운 비밀번호를 입력해주세요.");
                    return;
                }
                if(updateRePw.equals("")) {
                    showAlert("비밀번호를 확인해주세요.");
                    return;
                }

                if(!updatePw.equals(updateRePw)) {
                    showAlert("비밀번호가 서로 일치하지 않습니다.");
                    return;
                }

                Map<String, Object> map = new HashMap<>();
                map.put("pw", updateRePw);

                Log.d("myUid", myUid);

                mDatabase.child(myUid).updateChildren(map);

                showAlert("비밀번호 변경이 완료되었습니다.");

                modifyPw.setVisibility(View.INVISIBLE);
            }
        });

        btn_sendToDbFindYourAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empno = et_inpEmp.getText().toString().trim();

                if(empno.equals("")) {
                    showAlert("사원번호를 입력해주세요.");
                    return;
                }

                mDatabase.orderByChild("empno").equalTo(empno).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            userInfo.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                JoinDTO joinDTO = snapshot.getValue(JoinDTO.class);
                                userInfo.add(joinDTO);
                            }

                            Log.d("listSize", String.valueOf(userInfo.size()));

                            tv_sucFindYourId.setText("email : " + userInfo.get(0).emailId);
                            tv_viewId.setText(userInfo.get(0).name + "님의 계정은 다음과 같습니다.");
                        }
                        else {
                            showAlert("등록된 사원이 아닙니다. 다시 시도해주세요.");
                            et_inpEmp.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = et_mail_login.getText().toString().trim();
                String pw = et_pw_login.getText().toString().trim();

                if(id.equals("")) {
                    showAlert("아이디를 입력해주세요.");
                    return;
                }
                if(pw.equals("")) {
                    showAlert("비밀번호를 입력해주세요.");
                    return;
                }

                mAuth.signInWithEmailAndPassword(id, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(getApplication(), Fragment.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            showAlert("로그인에 실패했습니다. 다시 시도해주세요.");
                        }
                    }
                });
            }
        });

        btn_transJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selAccount.setVisibility(View.INVISIBLE);
                selPw.setVisibility(View.INVISIBLE);
                loginPage.setVisibility(View.INVISIBLE);
                joinPage.setVisibility(View.VISIBLE);
            }
        });

        ib_all_del1_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_mail_login.setText("");
            }
        });
        ib_all_del2_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_pw_login.setText("");
            }
        });

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

                                et_mail.setText("");
                                et_pw.setText("");
                                et_pw2.setText("");
                                et_name.setText("");
                                et_emp.setText("");
                                et_phone.setText("");
                                et_dept.setText("");
                                et_job.setText("");

                                selAccount.setVisibility(View.INVISIBLE);
                                selPw.setVisibility(View.INVISIBLE);
                                loginPage.setVisibility(View.VISIBLE);
                                joinPage.setVisibility(View.INVISIBLE);
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