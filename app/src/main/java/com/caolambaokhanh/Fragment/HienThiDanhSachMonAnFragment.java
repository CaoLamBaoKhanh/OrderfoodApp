package com.caolambaokhanh.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.caolambaokhanh.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.caolambaokhanh.DAO.MonAnDAO;
import com.caolambaokhanh.DTO.MonAnDTO;
import com.caolambaokhanh.orderfood.R;
import com.caolambaokhanh.orderfood.SoLuongActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {

    GridView gridView;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOList;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maban;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon,container,false);

        gridView = view.findViewById(R.id.gvHIenThiThucDon);

        monAnDAO = new MonAnDAO(getActivity());


        Bundle bundle = getArguments();
        if(bundle != null){
            int maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");

            monAnDTOList = monAnDAO.LayDanhSachMonAnThemLoai(maloai);
            adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),R.layout.custom_layout_hienthidanhsachmonan,
                    monAnDTOList);
            gridView.setAdapter(adapterHienThiDanhSachMonAn);
            adapterHienThiDanhSachMonAn.notifyDataSetChanged();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(maban!=0){
                        Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan",monAnDTOList.get(position).getMaMonAn());
                        startActivity(iSoLuong);
                    }
                }
            });
        }
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }
}
