package com.dong.dscustomview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dong.dscustomview.customoclock.CustomOclockActivity;
import com.dong.dscustomview.customradar.CusomRadarActivity;
import com.dong.dscustomview.customsuperman.CustomSuperManActivity;
import com.dong.dscustomview.customsuspend.CustomSuspendActivity;

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
            case R.id.custom_third:
                mContext.startActivity(new Intent(mContext, CustomOclockActivity.class));
                break;
            case R.id.custom_fourth:
                mContext.startActivity(new Intent(mContext, CustomSuspendActivity.class));
        }
    }


}
