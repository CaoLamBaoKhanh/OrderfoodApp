package com.caolambaokhanh.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.caolambaokhanh.DAO.BanAnDAO;

public class SuaBanAnActivitty extends AppCompatActivity implements View.OnClickListener {
    Button btnDongYSua;
    EditText edSuaTenBan;
    BanAnDAO banAnDAO;
    int maban;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_suabanan);

        btnDongYSua = findViewById(R.id.btnDongYSuaBanAN);
        edSuaTenBan = findViewById(R.id.edSuaTenBanAn);

        banAnDAO = new BanAnDAO(this);

        maban = getIntent().getIntExtra("maban", 0);

        btnDongYSua.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String tenban = edSuaTenBan.getText().toString();
        if(tenban.trim().equals("")  || tenban.trim() != null){
            boolean kiemtra = banAnDAO.CapNhatLaiTenBan(maban,tenban);
            Intent intent = new Intent();
            intent.putExtra("kiemtra", kiemtra);
            setResult(Activity.RESULT_OK, intent);

            finish();
        }else {
            Toast.makeText(this, getResources().getString(R.string.vuilongnhapdulieu), Toast.LENGTH_SHORT).show();
        }
    }
}
