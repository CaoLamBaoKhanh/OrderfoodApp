package com.caolambaokhanh.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.caolambaokhanh.DAO.GoiMonDAO;
import com.caolambaokhanh.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {
    int maban, mamonan;
    Button btnDOngYThemSoLuong;
    EditText edThemSoLuong;
    GoiMonDAO goiMonDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themsoluong);

        btnDOngYThemSoLuong = findViewById(R.id.btnDongYThemSoLuongMonAn);
        edThemSoLuong = findViewById(R.id.edThemSoLuongMonAn);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamonan = intent.getIntExtra("mamonan",0);
        Log.d("mabansoluong", maban + "");

        btnDOngYThemSoLuong.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        boolean kiemtra = goiMonDAO.KiemTraMonAnDaTonTai(magoimon, mamonan);
        if(kiemtra){
            //tien hanh cap nhat mon an da ton tai
            int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon, mamonan);
            int soluongmoi = Integer.parseInt(edThemSoLuong.getText().toString());

            int tongsoluong = soluongcu + soluongmoi;
            //cap nhat so luong
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(tongsoluong);
            boolean ktcapnhat = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            if(ktcapnhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }

        }else {
            //them mon an
            int soluonggoi = Integer.parseInt(edThemSoLuong.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(magoimon);
            chiTietGoiMonDTO.setMaMonAn(mamonan);
            chiTietGoiMonDTO.setSoLuong(soluonggoi);
            boolean ktcapnhat = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
            if(ktcapnhat){
                Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
