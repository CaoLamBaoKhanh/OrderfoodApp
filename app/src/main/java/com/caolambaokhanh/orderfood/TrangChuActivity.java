package com.caolambaokhanh.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.caolambaokhanh.Fragment.HienThiBanAnFragment;
import com.caolambaokhanh.Fragment.HienThiNhanVienFragment;
import com.caolambaokhanh.Fragment.HienThiThucDonFragment;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView txtTenNhanVienNavigation;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);
        linkview();
        Intent intent= getIntent();
        String tendn = intent.getStringExtra("tendn");
        View view = navigationView.getHeaderView(0);
        txtTenNhanVienNavigation = view.findViewById(R.id.txtTenNhanVienNavigation);
        txtTenNhanVienNavigation.setText(tendn);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.mo, R.string.dong){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //dinh mau sac icon
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        //
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transHienThiBanAn = fragmentManager.beginTransaction();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        transHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
        transHienThiBanAn.commit();
    }


    private void linkview() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationview_trangchu);
        toolbar = findViewById(R.id.toolbarTrangchu);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangchu:
                FragmentTransaction transHienThiBanAn = fragmentManager.beginTransaction();
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                transHienThiBanAn.replace(R.id.content, hienThiBanAnFragment);
                transHienThiBanAn.commit();

//                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;
            case R.id.itThucDon:
                FragmentTransaction transHienThiThucDon = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                transHienThiThucDon.replace(R.id.content, hienThiThucDonFragment);
                transHienThiThucDon.commit();

//                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;

            case R.id.itNhanVien:
                FragmentTransaction transHienThiNhanVien = fragmentManager.beginTransaction();
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                transHienThiNhanVien.replace(R.id.content, hienThiNhanVienFragment);
                transHienThiNhanVien.commit();

//                item.setChecked(true);
                drawerLayout.closeDrawers();
                ;break;
        }
        return false;
    }
}
