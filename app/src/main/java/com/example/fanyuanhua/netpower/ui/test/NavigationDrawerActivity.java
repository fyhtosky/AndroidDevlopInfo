package com.example.fanyuanhua.netpower.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.fanyuanhua.netpower.R;
import com.example.fanyuanhua.netpower.base.BaseActivity;

import butterknife.BindView;

public class NavigationDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @Nullable
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @Nullable
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    ImageView ivIcon;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation_drawer;
    }

    @Override
    protected void onInitView(@Nullable Bundle bundle) {
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ivIcon=navigationView.getHeaderView(0).findViewById(R.id.imageView);
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NavigationDrawerActivity.this,LoginActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(NavigationDrawerActivity.this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.nav_camera:
                startActivity(new Intent(NavigationDrawerActivity.this,ItemListActivity.class));
                break;
            case R.id.nav_gallery:
                startActivity(new Intent(NavigationDrawerActivity.this,BottomMeunActivity.class));
                break;
            case R.id.nav_slideshow:
                startActivity(new Intent(NavigationDrawerActivity.this,FullscreenActivity.class));
                break;
            case R.id.nav_manage:
                startActivity(new Intent(NavigationDrawerActivity.this,ItemDetailActivity.class));
                break;
            case R.id.nav_share:
                startActivity(new Intent(NavigationDrawerActivity.this,ScrollingActivity.class));
                break;
            case R.id.nav_send:
                startActivity(new Intent(NavigationDrawerActivity.this,TableActivity.class));
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
