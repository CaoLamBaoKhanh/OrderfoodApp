package com.caolambaokhanh.orderfood;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.caolambaokhanh.DAO.BanAnDAO;
import com.caolambaokhanh.Database.CreateDatabase;

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edThemTenBanAn;
    Button btnDongYThemTenBanAn;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thembanan);
        linkview();
        banAnDAO = new BanAnDAO(this);
        btnDongYThemTenBanAn.setOnClickListener(this);

    }

    private void linkview() {
        edThemTenBanAn = findViewById(R.id.edThemTenBanAn);
        btnDongYThemTenBanAn = findViewById(R.id.btnDongYThemBanAN);
    }


    @Override
    public void onClick(View v) {
        String sTenBanAn =  edThemTenBanAn.getText().toString();
        if(sTenBanAn != null || !sTenBanAn.equals("")){
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent  =new Intent();
            intent.putExtra("ketquathem",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
