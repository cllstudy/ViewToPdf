package com.rongsheng.viewtopdf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class JieTuActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecycleView;
    private Button mButton;
    List<Person> mList = new ArrayList<>();
    private HomeAdapter mHomeAdapter;
    String path = Environment.getExternalStorageDirectory() + "/1000ttt/";
    private Button mButton1;
    private Button mButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_tu);
        initView();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        mHomeAdapter = new HomeAdapter(R.layout.item_recycle_layout, mList);
        mHomeAdapter.openLoadAnimation();
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mHomeAdapter);
    }

    private void initView() {
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_view);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
    }

    private void initData() {
        for (int i = 1; i < 5; i++) {
            Person person = new Person();
            person.setName("张" + i);
            person.setId("20181114" + i);
            mList.add(person);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                Bitmap bitmap = Utils.shotRecyclerView(mRecycleView);
                Utils.saveBitmapToSdCard(JieTuActivity.this, bitmap, "测试截图");
                break;
            case R.id.button1:
                int i = Utils.pic2pdf(path + "测试截图.JPG", path + "ceshi.pdf");
                if (i == 0) {
                    Toast.makeText(JieTuActivity.this, "成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(JieTuActivity.this, "失败", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.button2:
                Intent intent=new Intent(JieTuActivity.this,Main2Activity.class);
                startActivity(intent);
                break;
        }
    }
}
