package com.rongsheng.viewtopdf;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lei
 * @desc 手机上生成的pdf
 * @date 2018/11/13 0013 -- 下午 3:07.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLlTotalView;
    private Button mBtCreatPdf;
    private ScrollView mSc;
    private Button mBtJietu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mLlTotalView = (LinearLayout) findViewById(R.id.ll_total_view);
        mBtCreatPdf = (Button) findViewById(R.id.bt_creat_pdf);

        mBtCreatPdf.setOnClickListener(this);
        mSc = (ScrollView) findViewById(R.id.sc);

        mBtJietu = (Button) findViewById(R.id.bt_jietu);
        mBtJietu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_creat_pdf:
                creatPdf();
                break;
            case R.id.bt_jietu:
                  Intent intent=new Intent(MainActivity.this,JieTuActivity.class);
                  startActivity(intent);
                break;
        }
    }

    private void creatPdf() {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument
                .PageInfo
                .Builder(mSc.getMeasuredWidth(), mSc.getMeasuredHeight(), 1)
                .create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        mSc.draw(page.getCanvas());
        pdfDocument.finishPage(page);

        try {
            String path = Environment.getExternalStorageDirectory() + File.separator + "table.pdf";
            File e = new File(path);
            if (e.exists()) {
                e.delete();
            }
            pdfDocument.writeTo(new FileOutputStream(e));
            Toast.makeText(MainActivity.this, "pdf生成成功!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfDocument.close();
    }
}
