package com.caolambaokhanh.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caolambaokhanh.DTO.LoaiMonAnDTO;
import com.caolambaokhanh.orderfood.R;

import java.util.List;

public class AdapterHienThiLoaiThucDon extends BaseAdapter {
    Context context;
    int layout;
    List<LoaiMonAnDTO> loaiMonAnDTOList;
    ViewHolderLoaiThucDon viewHolderLoaiThucDon;
    public AdapterHienThiLoaiThucDon(Context context, int layout, List<LoaiMonAnDTO> loaiMonAnDTOList){
        this.context = context;
        this.layout = layout;
        this.loaiMonAnDTOList = loaiMonAnDTOList;
    }
    @Override
    public int getCount() {
        return loaiMonAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiMonAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return loaiMonAnDTOList.get(position).getMaLoai();
    }

    //so xuong ds spnner

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            viewHolderLoaiThucDon = new ViewHolderLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent,false);

            viewHolderLoaiThucDon.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiThucDon);
        }else {
            viewHolderLoaiThucDon = (ViewHolderLoaiThucDon) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiThucDon.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiThucDon.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            viewHolderLoaiThucDon = new ViewHolderLoaiThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_layout_spinloaithucdon, parent,false);

            viewHolderLoaiThucDon.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiThucDon);
        }else {
            viewHolderLoaiThucDon = (ViewHolderLoaiThucDon) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = loaiMonAnDTOList.get(position);
        viewHolderLoaiThucDon.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiThucDon.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());
        return view;
    }
    public  class ViewHolderLoaiThucDon{
        TextView txtTenLoai;
    }
}
