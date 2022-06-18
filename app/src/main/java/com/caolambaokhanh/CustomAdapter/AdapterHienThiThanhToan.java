package com.caolambaokhanh.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.caolambaokhanh.DTO.ThanhToanDTO;
import com.caolambaokhanh.orderfood.R;

import java.util.List;

public class AdapterHienThiThanhToan extends BaseAdapter {
    Context context;
    int layout;
    List<ThanhToanDTO> thanhToanDTOList;
    ViewHolderThanhToan viewHolderThanhToan;

    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOList){
       this.context = context;
       this.layout = layout;
       this.thanhToanDTOList = thanhToanDTOList;
    }
    @Override
    public int getCount() {
        return thanhToanDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view  = inflater.inflate(layout,parent,false);

            viewHolderThanhToan.txtTenMonAn = view.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolderThanhToan.txtSoLuong = view.findViewById(R.id.txtSoLuongThanhToan);
            viewHolderThanhToan.txtGiaTien = view.findViewById(R.id.txtGiaTienThanhToan);

            view.setTag(viewHolderThanhToan);
        }else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(position);

        viewHolderThanhToan.txtTenMonAn.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuong.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTien.setText(String.valueOf(thanhToanDTO.getGiaTien()));
        return view;
    }

    public class ViewHolderThanhToan{
        TextView txtTenMonAn, txtSoLuong, txtGiaTien;
    }
}
