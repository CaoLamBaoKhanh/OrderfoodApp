package com.caolambaokhanh.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.caolambaokhanh.DAO.NhanVienDAO;
import com.caolambaokhanh.DTO.NhanVienDTO;
import com.caolambaokhanh.Fragment.DatePickerFragment;

public class DangKyActivity extends AppCompatActivity implements View.OnFocusChangeListener {
    EditText edtTenDK, edtMatkhauDK, edtNgaysinhDK, edtCMNDDK;
    Button btnDongYDK, btnThoatDK;
    RadioGroup radgGioiTinh;
    String sGioiTinh;

    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        linkViews();
        addEvents();

        nhanVienDAO = new NhanVienDAO(this);
    }

    private void linkViews() {
        edtTenDK = findViewById(R.id.edtTenDK);
        edtMatkhauDK = findViewById(R.id.edtMatkhauDK);
        edtNgaysinhDK = findViewById(R.id.edtNgaysinhDK);
        edtCMNDDK = findViewById(R.id.edtCMNDDK);
        btnDongYDK = findViewById(R.id.btnDongyDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        radgGioiTinh = findViewById(R.id.radgGioiTinh);
    }


    private void addEvents() {
        btnDongYDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id){
                    case R.id.btnDongyDK:
                        String sTenDN =  edtTenDK.getText().toString();
                        String sMatKhau = edtMatkhauDK.getText().toString();

                        switch (radgGioiTinh.getCheckedRadioButtonId()){
                            case R.id.radNam:
                                sGioiTinh = "Nam";
                                break;
                            case R.id.radNu:
                                sGioiTinh = "Nữ";
                                break;
                        }

                        String sNgaySinh = edtNgaysinhDK.getText().toString();
                        int sCMND = Integer.parseInt(edtCMNDDK.getText().toString());

                        if(sTenDN == null || sTenDN.equals("")){
                            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.nhaptendangnhap)+"", Toast.LENGTH_SHORT).show();
                        }else if(sMatKhau == null || sMatKhau.equals("")){
                            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.nhapmatkhau)+"", Toast.LENGTH_SHORT).show();
                        }else {
                            NhanVienDTO nhanVienDTO = new NhanVienDTO();
                            nhanVienDTO.setTENDN(sTenDN);
                            nhanVienDTO.setMAKHAU(sMatKhau);
                            nhanVienDTO.setGIOITINH(sGioiTinh);
                            nhanVienDTO.setNGAYSINH(sNgaySinh);
                            nhanVienDTO.setCMND(sCMND);

                            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
                            if(kiemtra != 0 ){
                                Toast.makeText(DangKyActivity.this, "Đăng ký tài khoản " + sTenDN + " thành công!", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(DangKyActivity.this, "Đăng ký tài khoản " + sTenDN + " thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
            }
        });
        btnThoatDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        edtNgaysinhDK.setOnFocusChangeListener(this);

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        int id = view.getId();
        switch (id){
            case R.id.edtNgaysinhDK:
                if(b){
                    DatePickerFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(), "Ngay sinh");
                    //xuat popup ngyasinh

                }
                break;
        }
    }
}