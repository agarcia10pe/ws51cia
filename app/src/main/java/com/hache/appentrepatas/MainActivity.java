package com.hache.appentrepatas;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.ui.home.HomeFragment;
import com.hache.appentrepatas.ui.request.ConfirmFragment;
import com.hache.appentrepatas.ui.request.DetailFragment;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;

    FragmentTransaction transaction;
    FragmentManager  manager;
    Menu menuItem;
    boolean isAdmin = false;
    NavigationView navigationView;
    Fragment fragment;

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
                R.id.nav_home, R.id.nav_adopt, R.id.nav_request, R.id.nav_locate, R.id.nav_callup, R.id.nav_register, R.id.nav_list, R.id.nav_statistics,
                R.id.nav_subtitle, R.id.nav_logoff)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

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
        manager = this.getSupportFragmentManager();
        General.permisoCall(this);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    public void setFragment(Fragment fragment){
        //FragmentManager manager = this.getSupportFragmentManager();
        /*transaction.replace(this, fragment).commit();
        transaction.addToBackStack(null);
        transaction.commit();*/
        /*getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment)
                .addToBackStack(BACK_STACK_ROOT_TAG)
                .commit(); */
        //getSupportFragmentManager().beginTransaction().remove(removeFrg).commit();


        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
              //  .addToBackStack(null)
                .commit();

        //getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();
        /*Intent intent = new Intent(getBaseContext(), fragment.getClass());
        startActivity(intent); */
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuItem = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.nav_logoff:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;
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
        //transaction = getSupportFragmentManager().beginTransaction();
        transaction = manager.beginTransaction();

        switch (v.getId()){
            default:
                break;
        }
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        fragment = fragment;
        //if(fragment != null)
        //    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }
}