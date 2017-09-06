package com.cx.retrofitdemo;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cx.retrofitdemo.adapter.HomePagerAdapter;
import com.cx.retrofitdemo.base.BaseActivity;
import com.cx.retrofitdemo.mvp.mvp_cardtop.view.CardTopFragment;
import com.cx.retrofitdemo.mvp.mvp_news.view.NewsFragment;
import com.cx.retrofitdemo.mvp.mvp_video.view.VideoFragment;

public class MainActivity extends BaseActivity {
    public static final int DEFAULT_PAGES = 1;
//    @InjectView(R.id.toolbar)
//    Toolbar toolbar;
//    @InjectView(R.id.tabs)
//    TabLayout tabs;
//    @InjectView(R.id.appbar)
//    AppBarLayout appbar;
//    @InjectView(R.id.viewpager)
//    ViewPager viewpager;
//    @InjectView(R.id.main_content)
//    CoordinatorLayout mainContent;
//    @InjectView(R.id.nav_view)
//    NavigationView navView;
//    @InjectView(R.id.drawlayout)
//    DrawerLayout drawlayout;


    private DrawerLayout drawlayout;
    private CoordinatorLayout mainContent;
    private AppBarLayout appbar;
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager viewpager;
    private NavigationView navView;



    @Override
    protected void initVars() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        drawlayout = (DrawerLayout) findViewById(R.id.drawlayout);
        mainContent = (CoordinatorLayout) findViewById(R.id.main_content);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabs = (TabLayout) findViewById(R.id.tabs);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        navView = (NavigationView) findViewById(R.id.nav_view);
//      ButterKnife.inject(this); //采用ButterKnief的注解
    }

    @Override
    protected void loadData() {
        //toolbar的设置
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar(); //设置ActionBar
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setTitle("cx羽");
            ab.setDisplayHomeAsUpEnabled(true);
        }
        //侧滑MUNU
        if (navView != null) {
            setupDrawerContent(navView); //设置侧滑菜单
        }
        //viewpager
        if (viewpager != null) {
            setupViewPager(viewpager);   //设置ViewPager
            tabs.setupWithViewPager(viewpager);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//适合很多tab
            tabs.setTabMode(TabLayout.MODE_FIXED);//tab均分,适合少的tab
        }
    }

    private void setupViewPager(ViewPager viewpager) {
        HomePagerAdapter adapter = new HomePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(CardTopFragment.newInstance("头条"), "头条");
        adapter.addFragment(VideoFragment.newInstance("视频"), "视频");
        adapter.addFragment(NewsFragment.newInstance("体育"), "体育");
        adapter.addFragment(NewsFragment.newInstance("娱乐"), "娱乐");
        adapter.addFragment(NewsFragment.newInstance("科技"), "科技");
        adapter.addFragment(NewsFragment.newInstance("NBA"), "NBA");
        adapter.addFragment(NewsFragment.newInstance("股票"), "股票");
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(DEFAULT_PAGES);
    }

    //侧滑的item点击
    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_photo:
//                                startActivity(new Intent(HomeAcitivty.this, PhotoActivity.class));
                                Snackbar.make(navigationView, "图片持续更新中....", Snackbar.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_video:
                                Snackbar.make(navigationView, "视频持续更新中....", Snackbar.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_music:
                                Snackbar.make(navigationView, "音乐持续更新中....", Snackbar.LENGTH_SHORT).show();
                                break;
                            case R.id.nav_about:
                                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                                break;
                        }
                        drawlayout.closeDrawers(); //关闭抽屉
                        drawlayout.setSelected(true);
                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
