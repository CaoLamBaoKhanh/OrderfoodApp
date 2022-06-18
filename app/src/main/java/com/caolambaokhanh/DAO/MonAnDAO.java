package com.caolambaokhanh.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.caolambaokhanh.DTO.MonAnDTO;
import com.caolambaokhanh.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    SQLiteDatabase database;

    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();

    }

    public boolean ThemMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN,monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN,monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI,monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH,monAnDTO.getHinhAnh());

        long kiemtra = database.insert(CreateDatabase.TB_MONAN,null,contentValues);
        if(kiemtra != 0 ){
            return true;
        }else {
            return false;
        }
    }
    @SuppressLint("Range")
    public List<MonAnDTO> LayDanhSachMonAnThemLoai(int maloai){

        List<MonAnDTO> monAnDTOList  = new ArrayList<MonAnDTO>();
        String truyvan = "select * from " +  CreateDatabase.TB_MONAN + " where " + CreateDatabase.TB_MONAN_MALOAI + " = '"
                + maloai + "' " ;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH))+"");
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));

            monAnDTOList.add(monAnDTO);
            cursor.moveToNext();

        }

        return monAnDTOList;
    }
}
