package com.caolambaokhanh.orderfood;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.caolambaokhanh.CustomAdapter.AdapterHienThiThanhToan;
import com.caolambaokhanh.DAO.BanAnDAO;
import com.caolambaokhanh.DAO.GoiMonDAO;
import com.caolambaokhanh.DTO.ThanhToanDTO;
import com.caolambaokhanh.Fragment.HienThiBanAnFragment;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    Button btnThanhToan, btnThoat;
    GoiMonDAO goiMonDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    AdapterHienThiThanhToan adapterHienThiThanhToan;
    TextView txtTongTien;
    long tongtien = 0;
    BanAnDAO banAnDAO;
    int maban;
    int magoimon;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gridView = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoat = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        fragmentManager = getSupportFragmentManager();

        maban = getIntent().getIntExtra("maban",0);
        if(maban!=0){

            HienThiThanhToan();

            for(int i =0; i<thanhToanDTOList.size();i++){
                int soluong = thanhToanDTOList.get(i).getSoLuong();
                int giatien = thanhToanDTOList.get(i).getGiaTien();

                tongtien += (soluong*giatien);
            }
            txtTongTien.setText(getResources().getString(R.string.tongtien) + tongtien);
        }

        btnThanhToan.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
    }
    private void HienThiThanhToan(){
        magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");
        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);
        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this,R.layout.custom_layout_thanhtoan,thanhToanDTOList);
        gridView.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToan:
                //Loi không thanh toán dc
                boolean kiemtrabanan = banAnDAO.CapNhatLaiTinhTrangBan(maban,"false");
                //thanh toan roi khong thanh toan nua
                boolean kiemtragoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban,"true");
                if (kiemtrabanan && kiemtragoimon){
                    Toast.makeText(this, getResources().getString(R.string.thanhtoanthanhcon), Toast.LENGTH_SHORT).show();
                    HienThiThanhToan();
                }else {
                    Toast.makeText(this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
                ;break;

            case R.id.btnThoatThanhToan:
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                transaction.replace(R.id.content, hienThiBanAnFragment);
                transaction.commit();
                ;break;
        }
    }
}
