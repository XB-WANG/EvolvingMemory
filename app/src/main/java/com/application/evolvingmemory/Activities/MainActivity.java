package com.application.evolvingmemory.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.application.evolvingmemory.Adapters.MainVPAdapter;
import com.application.evolvingmemory.Fragments.CloudSpaceFragment;
import com.application.evolvingmemory.Fragments.CollectionFragment;
import com.application.evolvingmemory.Fragments.InventoryFragment;
import com.application.evolvingmemory.Fragments.ItemsFragment;
import com.application.evolvingmemory.Fragments.ListFragment;
import com.application.evolvingmemory.Fragments.NoteFragment;
import com.application.evolvingmemory.Fragments.QuickSkimFragment;
import com.application.evolvingmemory.Fragments.ScheduleFragment;
import com.application.evolvingmemory.Fragments.SettingFragment;
import com.application.evolvingmemory.Fragments.ShareFragment;
import com.application.evolvingmemory.R;
import com.application.evolvingmemory.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends FragmentActivity  {

    //Main
    LinearLayout mainLinear;
   /* @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.menu_btn)
    ImageView menuButton;
    @BindView(R.id.more_btn)
    ImageView moreButton;
    @BindView(R.id.main_drawer)
    DrawerLayout drawer;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.main_title)
    TextView mainTitle;*/
    ViewPager2 mainVP2;

    //Bottom
    TabLayout mainTab;
//    @BindView(R.id.img_list)
//    ImageView listImage;
//    @BindView(R.id.img_schedule)
//    ImageView scheduleImage;
//    @BindView(R.id.img_settings)
//    ImageView settingsImage;


    //Fragments
    private NoteFragment noteFragment;
    private InventoryFragment inventoryFragment;
    private ItemsFragment itemsFragment;
    private CollectionFragment collectionFragment;
    private ShareFragment shareFragment;
    private CloudSpaceFragment cloudSpaceFragment;
    private QuickSkimFragment quickSkimFragment;

    private ActivityMainBinding activityMainBinding;
    private List<Fragment> fragments = new ArrayList<>();
    private List<Integer> icon = new ArrayList<>();
    private List<Integer> icon_full = new ArrayList<>();
    Integer icon_flag = 0;
    ListFragment listFragment;
    ScheduleFragment scheduleFragment;
    SettingFragment settingFragment;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //???????????????
        immerseTransparent();
        setContentView(R.layout.activity_main);

        //????????????
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //Fragments

        //?????????????????????????????????????????????????????????
        //toolbarLayoutMargin();


        /*//??????toolbar?????????
        menuButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v){
                drawer.openDrawer(Gravity.START);
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //showMoreMenu();
            }
        });*/

/*        //?????????????????????
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);*/

        //??????????????????????????????
/*        listImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //show();
                //onBackPressed();
            }
        });
        scheduleImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //show();
                //onBackPressed();
            }
        });
        settingsImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //show();
                //onBackPressed();
            }
        });*/

    }



    //???????????????


/*    //??????????????????????????????toolbar???marginTop
    void toolbarLayoutMargin(){
        int height = 0;
        int resourceId = getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, height, 0, 0);
        toolbar.setLayoutParams(lp);
    }*/

    //???????????????
    void immerseTransparent(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

    }

    //Fragments?????????
    void init(){

/*        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        noteFragment = new NoteFragment();
        transaction.replace(R.id.content,noteFragment);
        transaction.commit();*/

        icon.add(R.mipmap.list);
        icon.add(R.mipmap.schedule);
        icon.add(R.mipmap.settings);

        icon_full.add(R.mipmap.list_full);
        icon_full.add(R.mipmap.schedule_full);
        icon_full.add(R.mipmap.settings_full);

        fragments.add(new ListFragment());
        fragments.add(new ScheduleFragment());
        fragments.add(new SettingFragment());

        //???????????????
        MainVPAdapter mainVPAdapter = new MainVPAdapter(getSupportFragmentManager(),getLifecycle(),fragments);
        activityMainBinding.mainVp2.setAdapter(mainVPAdapter);
//        activityMainBinding.mainVp2.setCurrentItem();
//        activityMainBinding.mainVp2.setOffscreenPageLimit(2);

        //TabLayout???ViewPage2??????????????????
        new TabLayoutMediator(activityMainBinding.bottomTab, activityMainBinding.mainVp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (icon_flag == 0)
                {
                    tab.setIcon(icon_full.get(position));
                    icon_flag = 1;
                }
                else
                    tab.setIcon(icon.get(position));
            }
        }).attach();

        //ViewPage2??????????????????
        activityMainBinding.mainVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

        //TabLayout?????????????????????
        activityMainBinding.bottomTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(icon_full.get(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(icon.get(tab.getPosition()));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.setIcon(icon_full.get(tab.getPosition()));
            }
        });


    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        init();

    }

/*    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
        Intent intent;

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        switch(menuItem.getItemId()){
            case R.id.note_item:
                noteFragment = new NoteFragment();
                transaction.replace(R.id.content,noteFragment);
                mainTitle.setText("??????");
                drawerIsVisible();
                break;
            case R.id.inventory_item:
                inventoryFragment = new InventoryFragment();
                transaction.replace(R.id.content,inventoryFragment);
                mainTitle.setText("??????");
                drawerIsVisible();
                break;
            case R.id.items_item:
                itemsFragment = new ItemsFragment();
                transaction.replace(R.id.content,itemsFragment);
                mainTitle.setText("??????");
                drawerIsVisible();
                break;
            case R.id.collection_item:
                collectionFragment = new CollectionFragment();
                transaction.replace(R.id.content,collectionFragment);
                mainTitle.setText("??????");
                drawerIsVisible();
                break;
            case R.id.share_item:
                shareFragment = new ShareFragment();
                transaction.replace(R.id.content,shareFragment);
                mainTitle.setText("??????");
                drawerIsVisible();
                break;
            case R.id.cloud_space_item:
                cloudSpaceFragment = new CloudSpaceFragment();
                transaction.replace(R.id.content,cloudSpaceFragment);
                mainTitle.setText("?????????");
                drawerIsVisible();
                break;
            case R.id.quick_skim_item:
                quickSkimFragment = new QuickSkimFragment();
                transaction.replace(R.id.content,quickSkimFragment);
                mainTitle.setText("??????");
                drawerIsVisible();
                break;
        }

        transaction.commit();

        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(navigationView.getVisibility()==View.VISIBLE){
                //?????????????????????????????????????????????
                drawer.closeDrawer(navigationView);
            }else if(event.getAction()==KeyEvent.ACTION_DOWN){
                System.exit(0);
            }
        }
        return true;
    }

    void drawerIsVisible(){
        if(navigationView.getVisibility()==View.VISIBLE)
            //?????????????????????????????????????????????
            drawer.closeDrawer(navigationView);
    }*/


}