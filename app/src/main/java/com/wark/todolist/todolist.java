package com.wark.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class todolist extends AppCompatActivity{
    FloatingActionButton Fl_btn;
    Toolbar toolbar;
    TextView textview;
    TextView textview_2;
    ImageView imageview;
    Recycler_Adapter list_Adapter;
    RecyclerView daily;
    ArrayList<Data> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageview = (ImageView) findViewById(R.id.imageview);
        textview = (TextView) findViewById(R.id.if_no_text);
        textview_2 = (TextView) findViewById(R.id.textView_2);
        Fl_btn = (FloatingActionButton) findViewById(R.id.Fl_Btn);
        daily = (RecyclerView) findViewById(R.id.listview);
        list_Adapter = new Recycler_Adapter(items, R.layout.lest_item, this);
        setSupportActionBar(toolbar);

        list_Adapter. setItemClick(new Recycler_Adapter.ItemClick() {
            public void onClick(View view, int position) {
                Toast.makeText(todolist.this, view + "    " + position, Toast.LENGTH_SHORT).show();
                Intent pointent = new Intent(todolist.this, Set_todo.class);
                pointent.putExtra("position",position);
                startActivityForResult(pointent, 1234);
            }
        });

        daily.setAdapter(list_Adapter);
        daily.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        daily.setItemAnimator(new DefaultItemAnimator());

        loadNowData();
        saveNowData();
        list_Adapter.notifyDataSetChanged();

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(list_Adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(daily);

        if(items.size()==0){                                               //일정이 없을때 이미지 텍스트
            imageview.setImageResource(R.drawable.empty_view_bg);
            textview.setText("You don't have any todos");
        }
        daily.addOnScrollListener(new RecyclerView.OnScrollListener(){              //스크롤 시 플로팅버튼 숨기기
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0){
                    Fl_btn.hide();
                }
                else if (dy < 0)
                    Fl_btn.show();
            }
        });

        Fl_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todolist.this,Set_todo.class);
                startActivityForResult(intent, 333);
            }
        });//플로팅 버튼을 눌르시 이동
        Log.d("fab", String.valueOf(items));

    }//--

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {         //result코드가 333일때 gson,json 형식으로 데이터를 받아옴
        Gson gson = new Gson();
        if(resultCode == RESULT_OK){
            if (requestCode == 333) {//플로팅 버튼 이동
                textview.setText("");
                imageview.setImageResource(0);
                items.clear();
                String _data = data.getStringExtra("result");
                ArrayList<Data> items_ = new ArrayList<>();
                items_ = gson.fromJson(_data, new TypeToken<ArrayList<Data>>(){}.getType());
                items.addAll(items_);
                saveNowData();
                list_Adapter.notifyDataSetChanged();
            }else if(requestCode == 1234){//아이템을 눌렀을때
                items.clear();
                String data_ = data.getStringExtra("result");
                ArrayList<Data> items__ = new ArrayList<>();
                items__ = gson.fromJson(data_, new TypeToken<ArrayList<Data>>(){}.getType());
                items.addAll(items__);
                saveNowData();
                list_Adapter.notifyDataSetChanged();
            }
        }

    }

    void loadNowData() {
        Gson gson = new Gson();
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        String json = pref.getString("save", "");
        ArrayList<Data> items_ = new ArrayList<>();
        items_ = gson.fromJson(json, new TypeToken<ArrayList<Data>>(){}.getType());
        if(items_ != null)items.addAll(items_);
    }


    void saveNowData() { //items 안의 내용이 저장됨
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String json = new Gson().toJson(items);
        editor.putString("save", json);
        Log.d("asdf",json);
        editor.commit();
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
