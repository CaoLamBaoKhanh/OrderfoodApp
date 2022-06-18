package com.caolambaokhanh.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.caolambaokhanh.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.caolambaokhanh.DAO.LoaiMonAnDAO;
import com.caolambaokhanh.DTO.LoaiMonAnDTO;
import com.caolambaokhanh.orderfood.R;
import com.caolambaokhanh.orderfood.ThemBanAnActivity;
import com.caolambaokhanh.orderfood.ThemThucDonActivity;
import com.caolambaokhanh.orderfood.TrangChuActivity;

import java.util.List;

public class HienThiThucDonFragment extends Fragment {

    GridView gridView;
    List<LoaiMonAnDTO> loaiMonAnDTOS;
    LoaiMonAnDAO loaiMonAnDAO;
    FragmentManager fragmentManager;
    int maban;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);
        setHasOptionsMenu(true);
        //dat ten toolbar
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);

        gridView = view.findViewById(R.id.gvHIenThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        loaiMonAnDTOS = loaiMonAnDAO.LayDanhSachLoaiMonAn();
        AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon = new AdapterHienThiLoaiMonAnThucDon(getActivity()
        ,R.layout.custom_layout_hienthiloaimonan,loaiMonAnDTOS);
        gridView.setAdapter(adapterHienThiLoaiMonAnThucDon);
        adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();

        Bundle bDuLieuThucDon = getArguments();
        if(bDuLieuThucDon != null){
             maban = bDuLieuThucDon.getInt("maban");

        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonAnDTOS.get(position).getMaLoai();

                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putInt("maban",maban);
                hienThiDanhSachMonAnFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content,hienThiDanhSachMonAnFragment).addToBackStack("hienthiloai");

                transaction.commit();

            }
        });



        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1,R.id.itThemThucDon,1,R.string.themthucdon);
        itThemBanAn.setIcon(R.drawable.logodangnhap);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                ;break;
        }
        return super.onOptionsItemSelected(item);
    }
}
