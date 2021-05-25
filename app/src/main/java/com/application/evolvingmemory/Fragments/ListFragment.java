package com.application.evolvingmemory.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.application.evolvingmemory.Adapters.ListVPAdapter;
import com.application.evolvingmemory.R;
import com.application.evolvingmemory.databinding.ActivityMainBinding;
import com.application.evolvingmemory.databinding.FragmentListBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    //Binding
    private FragmentListBinding fragmentListBinding;

    //Fragments
    private NoteFragment noteFragment;
    private InventoryFragment inventoryFragment;
    private ItemsFragment itemsFragment;
    private CollectionFragment collectionFragment;
    private ShareFragment shareFragment;
    private CloudSpaceFragment cloudSpaceFragment;
    private QuickSkimFragment quickSkimFragment;

    //Adapter
    private List<Fragment> fragments=new ArrayList<>();
    private List<String> titles=new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    void init(){

        noteFragment = new NoteFragment();
        inventoryFragment = new InventoryFragment();
        itemsFragment = new ItemsFragment();
        collectionFragment = new CollectionFragment();
        shareFragment = new ShareFragment();
        cloudSpaceFragment = new CloudSpaceFragment();
        quickSkimFragment = new QuickSkimFragment();



        fragments.add(noteFragment);
        fragments.add(inventoryFragment);
        fragments.add(itemsFragment);
        fragments.add(collectionFragment);
        fragments.add(shareFragment);
        fragments.add(cloudSpaceFragment);
        fragments.add(quickSkimFragment);

//        ListVPAdapter listVPAdapter = new ListVPAdapter(getFragmentManager(),getLifecycle(),fragments);
//        fragmentListBinding.listVp2.setAdapter(listVPAdapter);
//
//        fragmentListBinding.listVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//            }
//        });

        fragmentListBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();

                switch(item.getItemId()){
                    case R.id.note_item:
                        transaction.replace(R.id.fg_content,noteFragment);
                        fragmentListBinding.listTitle.setText("笔记");
                        drawerIsVisible();
                        break;
                    case R.id.inventory_item:
                        transaction.replace(R.id.fg_content,inventoryFragment);
                        fragmentListBinding.listTitle.setText("清单");
                        drawerIsVisible();
                        break;
                    case R.id.items_item:
                        transaction.replace(R.id.fg_content,itemsFragment);
                        fragmentListBinding.listTitle.setText("事项");
                        drawerIsVisible();
                        break;
                    case R.id.collection_item:
                        transaction.replace(R.id.fg_content,collectionFragment);
                        fragmentListBinding.listTitle.setText("收藏");
                        drawerIsVisible();
                        break;
                    case R.id.share_item:
                        transaction.replace(R.id.fg_content,shareFragment);
                        fragmentListBinding.listTitle.setText("共享");
                        drawerIsVisible();
                        break;
                    case R.id.cloud_space_item:
                        transaction.replace(R.id.fg_content,cloudSpaceFragment);
                        fragmentListBinding.listTitle.setText("云空间");
                        drawerIsVisible();
                        break;
                    case R.id.quick_skim_item:
                        transaction.replace(R.id.fg_content,quickSkimFragment);
                        fragmentListBinding.listTitle.setText("速记");
                        drawerIsVisible();
                        break;
                }

                transaction.commit();

                return false;
            }
        });


        //设置toolbar监听器
        fragmentListBinding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListBinding.listDrawer.openDrawer(GravityCompat.START);
            }
        });

        fragmentListBinding.moreBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //showMoreMenu();
            }
        });

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //绑定视图
        fragmentListBinding = FragmentListBinding.inflate(inflater,container,false);
        return fragmentListBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        init();

    }

    @Override
    public void onResume() {
        super.onResume();



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentListBinding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(fragmentListBinding.navigationView.getVisibility()==View.VISIBLE){
                //当左边的侧滑栏是可见的，则关闭
                fragmentListBinding.listDrawer.closeDrawer(fragmentListBinding.navigationView);
            }else if(event.getAction()==KeyEvent.ACTION_DOWN){
                System.exit(0);
            }
        }
        return true;
    }

    void drawerIsVisible(){
        if(fragmentListBinding.navigationView.getVisibility()==View.VISIBLE)
            //当左边的侧滑栏是可见的，则关闭
            fragmentListBinding.listDrawer.closeDrawer(fragmentListBinding.navigationView);
    }


}