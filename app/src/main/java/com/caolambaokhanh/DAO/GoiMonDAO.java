package com.caolambaokhanh.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caolambaokhanh.DTO.ChiTietGoiMonDTO;
import com.caolambaokhanh.DTO.GoiMonDTO;
import com.caolambaokhanh.DTO.ThanhToanDTO;
import com.caolambaokhanh.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class GoiMonDAO {
    SQLiteDatabase database;
    public GoiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN,goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV,goiMonDTO.getMaNV());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI,goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG,goiMonDTO.getTinhTrang());

        long magoimon = database.insert(CreateDatabase.TB_GOIMON,null, contentValues);
        return magoimon;
    }

    @SuppressLint("Range")
    public long LayMaGoiMonTheoMaBan(int maban, String tinhtrang){
        String truyvan = "select * from " + CreateDatabase.TB_GOIMON + " where " + CreateDatabase.TB_GOIMON_MABAN + " = '"
                + maban + "' and " + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhtrang + "'";
        long magoimon = 0;
        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));
            cursor.moveToNext();
        }
        return magoimon;
    }

    public boolean KiemTraMonAnDaTonTai(int magoimon, int mamonan){
        String truyvan = "select * from " + CreateDatabase.TB_CHITIETGOIMON + " where " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " and " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " +magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    @SuppressLint("Range")
    public int LaySoLuongMonAnTheoMaGoiMon(int magoimon,int mamonan){
        int soluong = 0;
        String truyvan = "select * from " + CreateDatabase.TB_CHITIETGOIMON + " where " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " and " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " +magoimon;

        Cursor cursor = database.rawQuery(truyvan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));
            cursor.moveToNext();
        }
        return soluong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());

        long kiemtra  = database.update(CreateDatabase.TB_CHITIETGOIMON,contentValues,CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " +
                chiTietGoiMonDTO.getMaMonAn() + " and " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " +
                chiTietGoiMonDTO.getMaMonAn(),null);
        if (kiemtra != 0 ){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean ThemChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG,chiTietGoiMonDTO.getSoLuong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON,chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN,chiTietGoiMonDTO.getMaMonAn());

        long kiemtra  = database.insert(CreateDatabase.TB_CHITIETGOIMON,null, contentValues);
        if (kiemtra != 0 ){
            return true;
        }
        else {
            return false;
        }
    }
    @SuppressLint("Range")
    public List<ThanhToanDTO> LayDanhSachMonAnTheoMaGoiMon(int magoimon){
        String truyvan = "select * from " + CreateDatabase.TB_CHITIETGOIMON + " ct, " + CreateDatabase.TB_MONAN +
                " ma where " + "ct." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma." + CreateDatabase.TB_MONAN_MAMON +
                " and " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon + "'";

        List<ThanhToanDTO> thanhToanDTOList = new ArrayList<>();
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO =  new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            thanhToanDTOList.add(thanhToanDTO);
            cursor.moveToNext();
        }
        return thanhToanDTOList;
    }

    public boolean CapNhatTrangThaiGoiMonTheoMaBan(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG,maban);

        long kiemtra = database.update(CreateDatabase.TB_GOIMON,contentValues,CreateDatabase.TB_GOIMON_MABAN + " = '" +
                maban + "' ",null);
        if (kiemtra != 0){
            return true;
        }else {
            return false;
        }
    }
}
