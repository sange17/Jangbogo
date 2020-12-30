package com.example.jangbogo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<ItemData> m_oData = null;
    private int nListCnt = 0;

    public ListAdapter(ArrayList<ItemData> _oData){
        m_oData = _oData;
        nListCnt = m_oData.size();
    }


    @Override
    public int getCount() {
        Log.i("TAG", "getCount");

        return nListCnt;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        final TextView oTextName = (TextView) convertView.findViewById(R.id.textName);
        final TextView oTextAmount = (TextView) convertView.findViewById(R.id.textAmount);
        final TextView oTextTransactionDate = (TextView) convertView.findViewById(R.id.textTransactionDate);
        ImageButton btn_delete = convertView.findViewById(R.id.btn_delete);



        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBOpenHelper db = new DBOpenHelper(MainActivity.mContext);
                db.open();
                db.deleteRow(oTextName.getText().toString(),oTextAmount.getText().toString(),Integer.parseInt(oTextTransactionDate.getText().toString()));


                Activity activity = (MainActivity)MainActivity.mContext;
                Intent intent = new Intent(MainActivity.mContext,MainActivity.class);
                activity.startActivity(intent);
                activity.finish();

            }
        });

        oTextName.setText(m_oData.get(position).strName);
        oTextAmount.setText(m_oData.get(position).strAmount);
        oTextTransactionDate.setText(m_oData.get(position).strTransactionDate);

        return convertView;
    }
}
