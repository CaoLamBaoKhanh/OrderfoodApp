package com.caolambaokhanh.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.caolambaokhanh.CustomAdapter.AdapterHienThiLoaiThucDon;
import com.caolambaokhanh.DAO.LoaiMonAnDAO;
import com.caolambaokhanh.DAO.MonAnDAO;
import com.caolambaokhanh.DTO.LoaiMonAnDTO;
import com.caolambaokhanh.DTO.MonAnDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    public static int REQUEST_CODE_IN = 1;
    ImageButton imThemLoaiThucDon;
    Spinner spLoaithucDOn;
    LoaiMonAnDAO loaiMonAnDAO;
    MonAnDAO monAnDAO;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    AdapterHienThiLoaiThucDon adapterHienThiLoaiThucDon;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn , btnThoatThemMonAn;
    String sDuongDanHinh;
    EditText edTenMonAn, edGiaTien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themthucdon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imThemLoaiThucDon = findViewById(R.id.imThemLoaiThucDon);
        spLoaithucDOn = findViewById(R.id.spLoaiThucDon);
        imHinhThucDon  =findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoatThemMonAn);
        edTenMonAn = findViewById(R.id.edThemTenMonAn);
        edGiaTien = findViewById(R.id.edThemGiaTien);

        HienThiSpinnerLoaiMonAn();
        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);

        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);

    }

    private void HienThiSpinnerLoaiMonAn(){
        loaiMonAnDTOS = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        adapterHienThiLoaiThucDon = new AdapterHienThiLoaiThucDon(ThemThucDonActivity.this,R.layout.custom_layout_spinloaithucdon,loaiMonAnDTOS);
        spLoaithucDOn.setAdapter(adapterHienThiLoaiThucDon);
        adapterHienThiLoaiThucDon.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiThucDon = new Intent(ThemThucDonActivity.this,ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiThucDon,REQUEST_CODE_THEMLOAITHUCDON);
                break;

            case R.id.imHinhThucDon:
//                Intent intent;
//                if (Build.VERSION.SDK_INT < 19) {
//                    intent = new Intent();
//                    intent.setAction(Intent.ACTION_GET_CONTENT);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, REQUEST_CODE_IN);
//                } else {
//                    intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                    intent.addCategory(Intent.CATEGORY_OPENABLE);
//                    intent.setType("*/*");
//                    startActivityForResult(intent, REQUEST_CODE_IN);
//                }

                Intent iMoHinh = new Intent();
                iMoHinh.setType("image/*");
//                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                iMoHinh.setAction(Intent.ACTION_PICK);;
                startActivityForResult(Intent.createChooser(iMoHinh,"Choose picture"),REQUEST_CODE_MOHINH);
                break;
            case R.id.btnDongYThemMonAn:
                //lay ma loai mon an trong spinner
                int vitri = spLoaithucDOn.getSelectedItemPosition();
                int maloai = loaiMonAnDTOS.get(vitri).getMaLoai();
                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();

                if(tenmonan != null && giatien != null && !tenmonan.equals("") && !giatien.equals("")){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(sDuongDanHinh);
                    monAnDTO.setMaLoai(maloai);
                    monAnDTO.setTenMonAn(tenmonan);

                    boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if(kiemtra){
                        Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, getResources().getString(R.string.loithemmonan), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if(resultCode == Activity.RESULT_OK){
                Intent dulieu = data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon", false);
                if(kiemtra){
                    HienThiSpinnerLoaiMonAn();
                    Toast.makeText(this, getResources().getString(R.string.themthanhcong), Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(this, getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            if(REQUEST_CODE_MOHINH == requestCode){
                if(resultCode == Activity.RESULT_OK && data != null){
//                    try {
//                        Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(),data.getData());
//                        imHinhThucDon.setImageBitmap(bm);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    sDuongDanHinh = data.getData().toString();
                    imHinhThucDon.setImageURI(data.getData());
                }
            }
        }
    }
}
