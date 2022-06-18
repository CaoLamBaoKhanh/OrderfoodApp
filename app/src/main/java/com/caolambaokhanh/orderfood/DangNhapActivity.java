package com.caolambaokhanh.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.caolambaokhanh.DAO.NhanVienDAO;

public class DangNhapActivity extends AppCompatActivity {

    Button btnDongYDN, btnDangKyDN;
    EditText edtTenDangNhapDN, edtMatKhauDN;
    NhanVienDAO nhanVienDAO ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        nhanVienDAO = new NhanVienDAO(this);
        linkViews();
//        HienThiButtonDangKyVSDongY();
        HienThiButton();

        addEvents();

    }

    private void linkViews() {
        btnDangKyDN=findViewById(R.id.btnDangKyDN);
        btnDongYDN=findViewById(R.id.btnDongYDN);
        edtTenDangNhapDN=findViewById(R.id.edtTenDangNhapDn);
        edtMatKhauDN=findViewById(R.id.edtMatKhauDN);

    }

    private void HienThiButton(){

        btnDangKyDN.setVisibility(View.VISIBLE);
        btnDongYDN.setVisibility(View.VISIBLE);

    }

    private void HienThiButtonDangKyVSDongY() {
        boolean kiemtra = nhanVienDAO.KiemTraNhanVien();
        if(kiemtra){
            btnDangKyDN.setVisibility(View.GONE);
            btnDongYDN.setVisibility(View.VISIBLE);
        }else{
            btnDongYDN.setVisibility(View.GONE);
            btnDangKyDN.setVisibility(View.VISIBLE);
        }
    }

    private void btnDangNhap(){
        Intent intentDangKy = new Intent(DangNhapActivity.this,DangKyActivity.class );
        startActivity(intentDangKy);
    }
    private void btnDongY(){
        String sTenDangNhap = edtTenDangNhapDN.getText().toString();
        String sMatKhau = edtMatKhauDN.getText().toString();
        int kiemtra = nhanVienDAO.KiemTraDangNhap(sTenDangNhap, sMatKhau);
        if(kiemtra != 0 ){
            Intent iTrangChu = new Intent(DangNhapActivity.this,TrangChuActivity.class);
            iTrangChu.putExtra("tendn",edtTenDangNhapDN.getText().toString());
            iTrangChu.putExtra("manhanvien", kiemtra);
            startActivity(iTrangChu);
        }else {
            Toast.makeText(this, getResources().getString(R.string.dangnhapthatbai), Toast.LENGTH_SHORT).show();
        }
    }
    private void addEvents() {
        btnDangKyDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDangNhap();
            }
        });
        btnDongYDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDongY();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButton();
    }
}
