package com.wark.todolist;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.wark.todolist.R.layout.setting_activity;

public class todolist extends AppCompatActivity{
    FloatingActionButton Fl_btn;
    Toolbar toolbar;
    TextView textview;
    ImageView imageview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        imageview = (ImageView) findViewById(R.id.imageview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textview = (TextView) findViewById(R.id.if_no_text);
        Fl_btn = (FloatingActionButton) findViewById(R.id.Fl_Btn);
        setSupportActionBar(toolbar);


        textview.setText("You don't have any todos");
        imageview.setBackgroundResource(R.drawable.empty_view_bg);

        Fl_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todolist.this,Set_todo.class);
                startActivity(intent);
            }
        });//플로팅 버튼을 눌르시 이동
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 메뉴버튼이 처음 눌러졌을 때 실행되는 콜백메서드
        // 메뉴버튼을 눌렀을 때 보여줄 menu 에 대해서 정의
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //옵션메뉴가 보여질때마다 호출
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴의 항목을 선택(클릭)했을 때 호출되는 콜백메서드
        int id = item.getItemId();
        switch(id) {
            case R.id.action_About:
                Intent intent = new Intent(todolist.this,about_activity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Intent intent_ = new Intent(todolist.this,setting_activity.class);
                startActivity(intent_);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
