package com.example.hasoo.studentinformation;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String DB_NAME = "student.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        final TextView textView = findViewById(R.id.textView);

        final SQLiteDatabase[] database = {null};

        final ArrayList<Student> arrayList = new ArrayList<>();

        arrayList.add(new Student("홍길동", "2018031234"));
        arrayList.add(new Student("복돼지", "2018035678"));
        arrayList.add(new Student("우동국", "2018039100"));
        arrayList.add(new Student("마진가", "2018031112"));
        arrayList.add(new Student("김만두", "2018031314"));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database[0] = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                if(database[0] != null){
                    Toast.makeText(getApplicationContext(), "데이터베이스가 생성되었습니다", Toast.LENGTH_SHORT). show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteDatabase(DB_NAME)){
                    try {
                        Toast.makeText(getApplicationContext(), "데이터베이스가 삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "데이터베이스 삭제 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "create table student ("+
                        "id integer primary key autoincrement,"+
                        "name text,"+
                        "stdno text);";

                if(database[0]!=null){
                    try {
                        database[0].execSQL(sql);
                        Toast.makeText(getApplicationContext(), "학생 테이블이 생성되었습니다", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "테이블 생성 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "DB에 먼저 연결하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql;
                if(database[0]!=null){
                    try{
                        for(Student item: arrayList){
                           sql = "insert into student (name, stdno) values ('" + item.getName() + "','" + item.getStdno()+"');";
                            database[0].execSQL(sql);
                        }
                        Toast.makeText(getApplicationContext(), "학생 데이터를 추가했습니다", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "데이터 추가 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "DB에 먼저 연결하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "select * from student";
                try{
                    StringBuilder dataBuilder = new StringBuilder();
                    Cursor cursor = database[0].rawQuery(sql, null);
                    while(cursor.moveToNext()){
                        // to avoid recycle problem
                        dataBuilder.append(cursor.getInt(0));
                        dataBuilder.append(" ");
                        dataBuilder.append(cursor.getString(1));
                        dataBuilder.append(" ");
                        dataBuilder.append(cursor.getString(2));
                        dataBuilder.append("\n");
                    }
                    cursor.close();
                    textView.setText(dataBuilder.toString());
                    Toast.makeText(getApplicationContext(), "학생 데이터 조회에 성공했습니다", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                   Toast.makeText(getApplicationContext(), "데이터 조회 실패" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
