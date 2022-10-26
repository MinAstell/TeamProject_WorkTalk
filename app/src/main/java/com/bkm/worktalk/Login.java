package com.bkm.worktalk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText et_mail_login, et_pw_login;
    ImageButton ib_all_del1_login, ib_all_del2_login;
    Button btn_login, btn_transJoin, btn_selAccount, btn_selPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_mail_login = (EditText) findViewById(R.id.et_mail_login);
        et_pw_login = (EditText) findViewById(R.id.et_pw_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_transJoin = (Button) findViewById(R.id.btn_transJoin);
        btn_selAccount = (Button) findViewById(R.id.btn_selAccount);
        btn_selPw = (Button) findViewById(R.id.btn_selPw);

        ib_all_del1_login = (ImageButton) findViewById(R.id.ib_all_del1_login);
        ib_all_del2_login = (ImageButton) findViewById(R.id.ib_all_del2_login);

        btn_transJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Join_Find.class);
                intent.putExtra("0", "0"); // 액티비티가 넘어갈때 원하는 레이아웃이 뜨도록 하는 PutExtra를 보냄
                startActivity(intent);
            }
        });
        btn_selAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Join_Find.class);
                intent.putExtra("0", "1"); // 액티비티가 넘어갈때 원하는 레이아웃이 뜨도록 하는 PutExtra를 보냄
                startActivity(intent);
            }
        });
        btn_selPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Join_Find.class);
                intent.putExtra("0", "2"); // 액티비티가 넘어갈때 원하는 레이아웃이 뜨도록 하는 PutExtra를 보냄
                startActivity(intent);
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
    }
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