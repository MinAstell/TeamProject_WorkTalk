package com.bkm.worktalk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // merged test
    // git pull test 2
    
    private DatabaseReference mDatabase;    // 아래 디비 접근을 위한 초기화 부분에서 쓸 예정.
    private ValueEventListener valueEventListener;    // 아래 addValueEventListener에 쓰일 예정.

    private EditText etDeptno, etEmpno, etUserName;
    private Button btnPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteam);

        // 에디트 텍스트 입력 시 키보드가 입력하는 부분을 가리는 현상을 방지.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

         //아래 코드는 파이어베이스 접근을 위한 초기화 부분 입니다. (필수)
        mDatabase = FirebaseDatabase.getInstance().getReference("WorkTalk");


        /***
         아래 코드는 디비에 INSERT 하는 부분입니다. (경로는 지정해주셔야 합니다.)
            mDatabase.child("chatRoom").child(chatRoomPath).push().setValue(comment);
         **/


        /***
         아래 코드는 디비에 UPDATE 하는 부분입니다. (경로는 지정해주셔야 합니다.)
            mDatabase.child("chatRoom").child(chatRoomPath).updateChildren(map);
         **/


        /***
         아래 코드는 지정된 경로상에 존재하는 데이터를 불러오는 부분입니다. (경로는 지정해주셔야 합니다.)
            1.  addListenerForSingleValueEvent  :  지정된 경로상의 데이터를 한번만 불러오는 리스너
            2.  addValueEventListener  :  지정된 경로상에 있는 데이터의 변화를 계속 감지할 수 있는 리스너,
                                          변화가 감지되면 콜백함수가 실행됨. (콜백 함수 내에 로직구현)
                    -  주의 : 2번 리스너의 경우, 중복불가(한개만 있어야 됨.).
         **/


        /***
            리스너 사용형식 같은 경우, HowToUseListener.txt 파일을 참고해주시길 바랍니다.
         **/


        /***
            키값을 가져오고 싶은 경우
                -  snapshot.getKey();

            키에 속해있는 데이터를 가져오고 싶은 경우
                -  snapshot.getValue(DataList.class);
                   getValue 안쪽에는 DTO 클래스가 들어감. (DTO : Data Transfer Object)
         **/

        etDeptno = (EditText) findViewById(R.id.etDeptno);
        etEmpno = (EditText) findViewById(R.id.etEmpno);
        etUserName = (EditText) findViewById(R.id.etUserName);

        btnPush = (Button) findViewById(R.id.btnPush);

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pushToData();
            }
        });
    }

    private void pushToData() {

        String deptno = etDeptno.getText().toString();
        String empno = etEmpno.getText().toString();
        String userName = etUserName.getText().toString();

        if(deptno.equals("")) {
            showAlert("부서번호를 입력해주세요.");
            return;
        }
        if(empno.equals("")) {
            showAlert("사원번호를 입력해주세요.");
            return;
        }
        if(userName.equals("")) {
            showAlert("사원이름를 입력해주세요.");
            return;
        }

        Map<String, Object> map = new HashMap<>();
        map.clear();
        map.put("사원이름", userName);

        mDatabase.child("부서번호").child(deptno).child("사원번호").child(empno).updateChildren(map);

        showAlert("사원을 등록하였습니다.");
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