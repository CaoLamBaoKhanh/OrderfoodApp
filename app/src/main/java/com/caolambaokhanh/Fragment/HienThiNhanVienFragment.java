package com.caolambaokhanh.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.caolambaokhanh.CustomAdapter.AdapterHienThiNhanVien;
import com.caolambaokhanh.DAO.NhanVienDAO;
import com.caolambaokhanh.DTO.NhanVienDTO;
import com.caolambaokhanh.orderfood.DangKyActivity;
import com.caolambaokhanh.orderfood.R;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {

    ListView listNhanVien;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOList;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:

                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(iDangKy);
                ;break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1,R.id.itThemNhanVien,1,R.string.themnhanvien);
        itThemBanAn.setIcon(R.drawable.nhanvien);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien,container,false);
        listNhanVien = view.findViewById(R.id.listNhanVien);
        nhanVienDAO = new NhanVienDAO(getActivity());
        nhanVienDTOList = nhanVienDAO.LayDanhSachNhanVien();
        setHasOptionsMenu(true);

        AdapterHienThiNhanVien adapterHienThiNhanVien = new AdapterHienThiNhanVien(getActivity(),
                R.layout.custom_layout_hienthinhanvien,nhanVienDTOList);
        listNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetChanged();
        return view;
    }
}
