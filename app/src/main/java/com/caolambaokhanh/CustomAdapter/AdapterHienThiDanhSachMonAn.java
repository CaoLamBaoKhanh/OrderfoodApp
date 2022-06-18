package com.caolambaokhanh.CustomAdapter;

import static android.os.Build.VERSION_CODES.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.caolambaokhanh.DTO.MonAnDTO;
import com.caolambaokhanh.orderfood.R;

import java.util.List;

public class AdapterHienThiDanhSachMonAn extends BaseAdapter {
    Context context;
    int layout;
    List<MonAnDTO> monAnDTOList;
    ViewHOlderHienThiDanhSachMonAn viewHOlderHienThiDanhSachMonAn;

    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> monAnDTOList){
        this.context = context;
        this.layout  = layout;
        this.monAnDTOList = monAnDTOList;

    }
    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnDTOList.get(position).getMaMonAn();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view  =convertView;
        if(view == null ){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHOlderHienThiDanhSachMonAn = new ViewHOlderHienThiDanhSachMonAn();
            view  = inflater.inflate(layout,parent,false);

            viewHOlderHienThiDanhSachMonAn.iHinhMonAn = view.findViewById(com.caolambaokhanh.orderfood.R.id.imHienThiDSMonAn);
            viewHOlderHienThiDanhSachMonAn.txtTenMonAn = view.findViewById(com.caolambaokhanh.orderfood.R.id.txtTenDSMonAn);
            viewHOlderHienThiDanhSachMonAn.txtGiaTien = view.findViewById(com.caolambaokhanh.orderfood.R.id.txtGiaTenDSMonAn);

            view.setTag(viewHOlderHienThiDanhSachMonAn);
        }else {
            viewHOlderHienThiDanhSachMonAn = (ViewHOlderHienThiDanhSachMonAn) view.getTag();
        }
        MonAnDTO monAnDTO = monAnDTOList.get(position);
        String hinhanh = monAnDTO.getHinhAnh();
        if(hinhanh == null || hinhanh.equals("")){
            viewHOlderHienThiDanhSachMonAn.iHinhMonAn.setImageResource(com.caolambaokhanh.orderfood.R.drawable.backgroundheader);
        }else {
            //Load hinh anh danh sach mon an theo loai

//            Uri bm = MediaStore.Images.Media.getContentUri(hinhanh);
//            viewHOlderHienThiDanhSachMonAn.iHinhMonAn.setImageURI(bm);

//            Uri uri = Uri.parse(hinhanh);
//            viewHOlderHienThiDanhSachMonAn.iHinhMonAn.setImageURI(uri);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                viewHOlderHienThiDanhSachMonAn.iHinhMonAn.setImageURI(uri);
////                viewHOlderHienThiDanhSachMonAn.iHinhMonAn.setImageIcon(Icon.createWithContentUri(uri));
//            }
        }


        viewHOlderHienThiDanhSachMonAn.txtTenMonAn.setText(monAnDTO.getTenMonAn());
        viewHOlderHienThiDanhSachMonAn.txtGiaTien.setText(context.getResources().getString(com.caolambaokhanh.orderfood.R.string.gia) + monAnDTO.getGiaTien());

        return view;
    }

    public class ViewHOlderHienThiDanhSachMonAn{
        ImageView iHinhMonAn;
        TextView txtTenMonAn, txtGiaTien;
    }

}
