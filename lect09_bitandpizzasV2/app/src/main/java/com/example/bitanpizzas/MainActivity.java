package com.example.bitanpizzas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

/**
 * MainActivity是整个Tab滑动页面的核心。
 *  里面包含了
 *  用于（）的AppBarLayout
 *      AppBarLayout里面包含了
 *          ToolBar以及
 *          TabLayout
 *  用于（将Fragment显示出来）的ViewPager
 */
public class MainActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;

    /**
     * 实例化我们写好的FragmentPagerAdapter，传入getSupportFragmentManager()给他的构造器。
     *  访问 layout file里面的 ViewPager，
     *  使用将FragmentPagerAdapter的实例传入ViewPager的setAdapter，将FragmentPagerAdapter与ViewPager连接在一起。
     *  ：FragmentPagerAdapter与ViewPager链接起来，我们能够将FragmentPagerAdapter管理的Fragment，软编码地显示到ViewPager里面。
     *
     * 访问layout file里面的TabLayout，
     * 将TabLayout与ViewPager连接在一起，
     * ：TabLayout与ViewPager连接在一起使得ViewPager能够对应上TabLayout上的Tab。
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Attach the SectionPagerAdapter to the ViewPager
        SectionsPagerAdapter pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Want to join me for pizza?");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                // Code to run when the Create Order item is clicked
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    // ####################################
    // ###        Helper Class          ###
    // ####################################

    /**
     * 内部类 SectionsPagerAdapter 继承了 FragmentPagerAdapter，
     *  用于（将Fragment放到当前的 view pager里面）
     *
     * 我们需要给定2个信息：
     *  要放进来页面（fragment）的数量
     *  当页面滑倒某一个为止（tab）的时候，对应的Item（页面）是什么
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * 告诉FragmentPagerAdapter当滑动到某个页面（tab）的时候，
         * 页面应该放的Fragment（Item）是谁
         * @param position Android的内调函数，告诉当前用户滑动到哪一个页面（tab）
         * @return 我们判断，当用户滑动到页面的某一个（tab）的时候，会给Android什么的Fragment
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TopFragment();
                case 1:
                    return new PizzaFragment();
                case 2:
                    return new PastaFragment();
                case 3:
                    return new StoriesFragment();
            }
            return null;
        }

        /**
         * 告诉FragmentPagerAdapter页面的数量
         * @return 页面的数量给FragmentPagerAdapter
         */
        @Override
        public int getCount() {
            return 4;
        }

        /**
         * 设置TabLayout tab的标题。
         * @param position Android的内调函数，传入当前用户滑动到的tab的位置
         * @return 我们告诉安卓，页面对应某个位置的标题是什么。
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getText(R.string.home_tab);
                case 1:
                    return getResources().getText(R.string.pizza_tab);
                case 2:
                    return getResources().getText(R.string.pasta_tab);
                case 3:
                    return getResources().getText(R.string.store_tab);
            }
            return null;
        }
    }

}