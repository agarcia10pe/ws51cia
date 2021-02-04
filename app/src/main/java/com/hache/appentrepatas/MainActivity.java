package com.hache.appentrepatas;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.ui.home.HomeFragment;
import com.hache.appentrepatas.ui.request.ConfirmFragment;
import com.hache.appentrepatas.ui.request.DetailFragment;
import com.hache.appentrepatas.ui.request.EndFragment;
import com.hache.appentrepatas.ui.request.RequestFragment;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.General;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;

    FragmentTransaction transaction;
    FragmentManager  manager;
    NavigationView navigationView;
    TextView nameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_adopt, R.id.nav_request, R.id.nav_locate, R.id.nav_callup, R.id.nav_register, R.id.nav_list,
                R.id.nav_subtitle, R.id.nav_logoff)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Llamando nombre del perfil
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nameTxt = (TextView) headerView.findViewById(R.id.lbl_user_name);

        nameTxt.setText(Constants.user);

        if(!Constants.user.equals("ADMIN"))
        {
            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(6).setVisible(false);
            navigationView.getMenu().getItem(7).setVisible(false);
        }else{

            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
        }

        General.permisoCall(this);

    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    @Override
    public void onBackPressed() {
        /*List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for(Fragment f : fragments){
            if(f != null && f instanceof BaseFragment)
                ((BaseFragment)f).onBackPressed();
        }*/
        if(getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
/*
        boolean eventConsumed = false;
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment instanceof BaseFragment) {
                    eventConsumed = eventConsumed
                            || ((BaseFragment) fragment).onBackPressed();
                }
            }
        }
        if (!eventConsumed) {
            super.onBackPressed();
        } */
        /*Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (!(fragment instanceof BaseFragment) || !((BaseFragment) fragment).onBackPressed()) {
            super.onBackPressed();
        }*/
    }

    public void removeBackPressListener(Fragment frg){
        try{
            manager = this.getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.remove(frg).commit();
        }
        catch (Exception ex){
            System.out.println("Error remove fragment");
        }
    }
    public void addBackPressListener(Fragment frg){
        try{
            manager = this.getSupportFragmentManager();
            transaction = manager.beginTransaction();
            transaction.add(R.id.nav_host_fragment,frg).commit();
        }
        catch (Exception ex){
            System.out.println("Error add fragment");
        }
    }

    public void setFragment(int position) {
        switch (position) {
            case 1:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                DetailFragment detailFragment = new DetailFragment();
                transaction.replace(R.id.nav_host_fragment,detailFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case 2:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                ConfirmFragment confirmFragment = new ConfirmFragment();
                transaction.replace(R.id.nav_host_fragment,confirmFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case 3:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.nav_host_fragment,homeFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case 4:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                EndFragment endFragment = new EndFragment();
                transaction.replace(R.id.nav_host_fragment,endFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case 5:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                RequestFragment requestFragment = new RequestFragment();
                transaction.replace(R.id.nav_host_fragment,requestFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            case 6:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                AdoptFragment adoptFragment = new AdoptFragment();
                transaction.replace(R.id.nav_host_fragment,adoptFragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
        /*
        transaction.replace(R.id.nav_host_fragment, current);
        transaction.addToBackStack(null);
        transaction.commit();
*/
        //transaction.commitAllowingStateLoss();

        /*
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment)
                //.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }






}