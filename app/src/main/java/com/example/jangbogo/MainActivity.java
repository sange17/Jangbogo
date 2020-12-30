package com.example.jangbogo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    IntentIntegrator qrScan;
    Cursor cursor;
    DBOpenHelper db;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        db = new DBOpenHelper(MainActivity.this);
        db.open();
        final Cursor dbCursor = db.selectColumns();
        final SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Cursor dbCursor = db.selectColumns();
                ArrayList<ItemData> oData = new ArrayList<>();
                for (int i = 0; i < dbCursor.getCount(); i++) {
                    dbCursor.moveToPosition(i);
                    ItemData oItem = new ItemData();
                    oItem.strName = dbCursor.getString(1);
                    oItem.strAmount = dbCursor.getString(2);
                    oItem.strTransactionDate = dbCursor.getString(3);
                    oData.add(oItem);
                }
                ListView m_oListView = findViewById(R.id.listview);
                ListAdapter oAdapter = new ListAdapter(oData);
                m_oListView.setAdapter(oAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        qrScan = new IntentIntegrator(this);
        Button btn_QRScan = findViewById(R.id.btn_qrScan);
        btn_QRScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.setPrompt("Scanning...");
                qrScan.setOrientationLocked(false);
                qrScan.initiateScan();
            }
        });


        // ------------데이터 1000개 생성-----------

        ArrayList<ItemData> oData = new ArrayList<>();
        for (int i = 0; i < dbCursor.getCount(); i++) {
            dbCursor.moveToPosition(i);
            ItemData oItem = new ItemData();
            oItem.strName = dbCursor.getString(1);
            oItem.strAmount = dbCursor.getString(2);
            oItem.strTransactionDate = dbCursor.getString(3);
            oData.add(oItem);
        }
        ListView m_oListView = findViewById(R.id.listview);
        ListAdapter oAdapter = new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result == null) {
            Toast.makeText(MainActivity.this, "취소", Toast.LENGTH_SHORT).show();

        } else {
            try {
                JSONObject obj = new JSONObject(result.getContents());
                cursor = db.selectColumns();

                //db.deleteRow("초고추장 200g","2020/05/25",1);

                int count = 0;
                if (cursor.getCount() != 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);

                        if (cursor.getString(1).equals(obj.getString("name")) && cursor.getString(2).equals(obj.getString("orderdate"))) {

                            db.updateRow(obj.getString("name"), obj.getString("orderdate"), obj.getInt("numbers"));
                            count++;
                            break;
                        }


                    }

                    if(count == 0)
                    {
                        db.insertColumn(obj.getString("name"), obj.getString("orderdate"), obj.getInt("numbers"));
                    }
                } else {
                    db.insertColumn(obj.getString("name"), obj.getString("orderdate"), obj.getInt("numbers"));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ignored) {
            }
        }
    }
}