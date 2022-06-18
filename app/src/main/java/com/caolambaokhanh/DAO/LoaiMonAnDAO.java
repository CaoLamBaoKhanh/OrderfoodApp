package com.caolambaokhanh.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.caolambaokhanh.DTO.LoaiMonAnDTO;
import com.caolambaokhanh.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiMonAnDAO {
    SQLiteDatabase database;

    public LoaiMonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();

    }
    public boolean ThemLoaiMonAn(String tenloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenloai);

        long kiemtra = database.insert(CreateDatabase.TB_LOAIMONAN,null,contentValues);
        if(kiemtra != 0 ){
            return true;
        }else {
            return false;
        }
    }
    @SuppressLint("Range")
    public List<LoaiMonAnDTO> LayDanhSachLoaiMonAn(){
        List<LoaiMonAnDTO> loaiMonAnDTOS = new ArrayList<>();

        String truyvan = "select * from " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            loaiMonAnDTOS.add(loaiMonAnDTO);
            cursor.moveToNext();
        }
        return loaiMonAnDTOS;
    }


    //lay hinh anh
//    String hinhanh;
    @SuppressLint("Range")
    public String LayHinhLoaiMonAn(int maloai){
        String hinhanh = "";
        String truyvan = "select * from " +  CreateDatabase.TB_MONAN + " where " + CreateDatabase.TB_MONAN_MALOAI + " = '"
                + maloai + "' " + " and " + CreateDatabase.TB_MONAN_HINHANH + " != '' order by "
                + CreateDatabase.TB_MONAN_MAMON + " limit 1";
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hinhanh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();

        }

//        if (hinhanh == null || hinhanh.equals("")){
//            cursor.moveToPrevious();
//        }
        return hinhanh;

    }
}
