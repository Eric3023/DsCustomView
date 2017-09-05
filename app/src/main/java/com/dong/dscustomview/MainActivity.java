package com.dong.dscustomview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dong.dscustomview.customradar.CusomRadarActivity;
import com.dong.dscustomview.customsuperman.CustomSuperManActivity;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
    }

    public void click(View v){
        int vId = v.getId();
        switch (vId){
            case R.id.custom_first:
                mContext.startActivity(new Intent(mContext, CustomSuperManActivity.class));
                break;
            case R.id.custom_second:
                mContext.startActivity(new Intent(mContext, CusomRadarActivity.class));
                break;
        }
    }


}
