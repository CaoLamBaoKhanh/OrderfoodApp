package com.caolambaokhanh.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.caolambaokhanh.DAO.BanAnDAO;
import com.caolambaokhanh.DAO.GoiMonDAO;
import com.caolambaokhanh.DAO.LoaiMonAnDAO;
import com.caolambaokhanh.DTO.BanAnDTO;
import com.caolambaokhanh.DTO.GoiMonDTO;
import com.caolambaokhanh.DTO.LoaiMonAnDTO;
import com.caolambaokhanh.DTO.ThanhToanDTO;
import com.caolambaokhanh.Fragment.HienThiThucDonFragment;
import com.caolambaokhanh.orderfood.R;
import com.caolambaokhanh.orderfood.ThanhToanActivity;
import com.caolambaokhanh.orderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {
    Context context;
    int layout;
    List<BanAnDTO> banAnDTOList;
    ViewHolderBanAn viewHolderBanAn;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;
    LoaiMonAnDAO loaiMonAnDAO;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> banAnDTOList){
        this.context = context;
        this.layout = layout;
        this.banAnDTOList  = banAnDTOList;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        loaiMonAnDAO = new LoaiMonAnDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }
    @Override
    public int getCount() {
        return banAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return banAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return banAnDTOList.get(position).getMaBan();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderBanAn  = new ViewHolderBanAn();
            view = inflater.inflate(R.layout.custom_layout_hienthibanan,parent,false);
            viewHolderBanAn.imvBanAn = view.findViewById(R.id.imvBanAn);
            viewHolderBanAn.imvGoiMon  = view.findViewById(R.id.imvGoiMon);
            viewHolderBanAn.imvThanhToan = view.findViewById(R.id.imvThanhToan);
            viewHolderBanAn.imvAnButton = view.findViewById(R.id.imvAnButton);
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);

            view.setTag(viewHolderBanAn);
        }else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }
        //hien thi 3 icon
        if(banAnDTOList.get(position).isDuocChon()){
            HienThiButtton();
        }else {
            AnButtton();
        }

        BanAnDTO banAnDTO = banAnDTOList.get(position);

        //cap nhat tinh trang ban
        String kttinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        if(kttinhtrang.equals("true")){
            viewHolderBanAn.imvBanAn.setImageResource(R.drawable.banantrue);
        }else {
            viewHolderBanAn.imvBanAn.setImageResource(R.drawable.banan);
        }
        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        //set vi tri
        viewHolderBanAn.imvBanAn.setTag(position);

        //event click
        viewHolderBanAn.imvBanAn.setOnClickListener(this);
        viewHolderBanAn.imvGoiMon.setOnClickListener(this);
        viewHolderBanAn.imvThanhToan.setOnClickListener(this);
        return view;
    }

    private void HienThiButtton(){
        viewHolderBanAn.imvGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imvThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imvAnButton.setVisibility(View.VISIBLE);
    }
    private void AnButtton(){
        viewHolderBanAn.imvGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imvThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imvAnButton.setVisibility(View.INVISIBLE);
    }

    public class ViewHolderBanAn{

        ImageView imvBanAn, imvThanhToan, imvAnButton,imvGoiMon;
        TextView txtTenBanAn;
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        int vitri1 = (int) viewHolderBanAn.imvBanAn.getTag();
        int maban = banAnDTOList.get(vitri1).getMaBan();
        switch (id){
            case R.id.imvBanAn:
                String tenban = viewHolderBanAn.txtTenBanAn.getText().toString();
                int vitri = (int) v.getTag();
                banAnDTOList.get(vitri).setDuocChon(true);
                HienThiButtton();
                ;break;

            case R.id.imvGoiMon:
                List<LoaiMonAnDTO> loaiMonAnDTOS = loaiMonAnDAO.LayDanhSachLoaiMonAn();
                if(loaiMonAnDTOS.size() > 0 ){
                    Intent layITrangchu = ((TrangChuActivity)context).getIntent();
                    int manhanvien  = layITrangchu.getIntExtra("manhanvien", 0);
                    String tinhtrang;
                    tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);

                    if(tinhtrang.equals("false")){
                        //code them bang goi mon va cap nhat lai tinh trang ban
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yy");
                        String ngaygoimon = dateFormat.format(calendar.getTime());

                        GoiMonDTO goiMonDTO = new GoiMonDTO();
                        goiMonDTO.setMaBan(maban);
                        goiMonDTO.setMaNV(manhanvien);
                        goiMonDTO.setNgayGoi(ngaygoimon);
                        goiMonDTO.setTinhTrang("false");

                        long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                        banAnDAO.CapNhatLaiTinhTrangBan(maban,"true");

                        if(kiemtra == 0){
                            Toast.makeText(context, context.getResources().getString(R.string.themthatbai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    FragmentTransaction tranThucDonTransaction = fragmentManager.beginTransaction();
                    HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                    Bundle bDuLieuThucDon = new Bundle();
                    bDuLieuThucDon.putInt("maban", maban);

                    hienThiThucDonFragment.setArguments(bDuLieuThucDon);
                    tranThucDonTransaction.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                    tranThucDonTransaction.commit();
                    ;break;
                }else {
                    Toast.makeText(context,"Chưa có danh sách món ăn",Toast.LENGTH_SHORT).show();
                }

            case R.id.imvThanhToan:
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban",maban);
                context.startActivity(iThanhToan);
                int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
                List<ThanhToanDTO> thanhToanDTOS = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);

                if (magoimon != 0){
                    if (thanhToanDTOS.size() > 0){
                        iThanhToan = new Intent(context, ThanhToanActivity.class);
                        iThanhToan.putExtra("maban", maban);
                        context.startActivity(iThanhToan);
                    }else
                        Toast.makeText(context, "Bàn chưa gọi món!!!", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(context, "Bàn chưa gọi món!!!", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
