package com.hache.appentrepatas;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.hache.appentrepatas.helper.EntrePatasDbHelper;
import com.hache.appentrepatas.ui.adopt.AdoptFragment;
import com.hache.appentrepatas.ui.home.HomeFragment;
import com.hache.appentrepatas.ui.request.ConfirmFragment;
import com.hache.appentrepatas.ui.request.DetailFragment;
import com.hache.appentrepatas.ui.request.EndFragment;
import com.hache.appentrepatas.ui.request.RequestFragment;
import com.hache.appentrepatas.util.Constants;
import com.hache.appentrepatas.util.EnumSolicitud;
import com.hache.appentrepatas.util.General;
import com.hache.appentrepatas.util.SeguridadUtil;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
    ProgressDialog progressDialog;
    FragmentTransaction transaction;
    FragmentManager  manager;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView nameTxt;
    AlertDialog dialog;
    EntrePatasDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SeguridadUtil.esAutenticado()) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            return;
        }
        dbHelper = new EntrePatasDbHelper(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_adopt, R.id.nav_request, R.id.nav_locate, R.id.nav_callup, R.id.nav_register, R.id.nav_list,
                R.id.nav_subtitle, R.id.nav_setting ,R.id.nav_logoff)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        nameTxt  = (TextView) navigationView.getHeaderView(0).findViewById(R.id.lbl_user_name);

        navigationView.getMenu().findItem(R.id.nav_logoff).setOnMenuItemClickListener(menuItem -> {
            dialog.show();
            return true;
        });
        nameTxt.setText(Constants.user);

        if(SeguridadUtil.getUsuario().getIdTipoUsu() == EnumSolicitud.TipoUsuario.ADMINISTRADOR.getCode())
        {
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
            navigationView.getMenu().getItem(4).setVisible(false);
        } else {

            navigationView.getMenu().getItem(5).setVisible(false);
            navigationView.getMenu().getItem(6).setVisible(false);
            navigationView.getMenu().getItem(7).setVisible(false);
        }
        initProgressDialog();
        createDialogLogout();
        General.permisoCall(this);
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    @Override
    public void onBackPressed() {
        closeLoading();
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            if((fragment instanceof BaseFragment))
            {
                ((BaseFragment) fragment).onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        } else {
            //super.onBackPressed();
        }

//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
//        if (!(fragment instanceof BaseFragment) || !((BaseFragment) fragment).onBackPressed()) {
//            super.onBackPressed();
//        }
    }
        /*
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        boolean handled = false;
        for(Fragment f : fragmentList) {
            if(f instanceof BaseFragment) {
                handled = ((BaseFragment)f).onBackPressed();

                if(handled) {
                    break;
                }
            }
        }

        if(!handled) {
            super.onBackPressed();
        }
    }*/

    public void setFragment(int position, Bundle bundle, boolean addToBackStack) {
    //public void setFragment(Fragment current) {
        switch (position) {
            case 1:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                DetailFragment detailFragment = new DetailFragment();
                if (bundle != null)
                    detailFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment,detailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 2:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                ConfirmFragment confirmFragment = new ConfirmFragment();
                if (bundle != null)
                    confirmFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment,confirmFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 3:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                transaction.replace(R.id.nav_host_fragment,homeFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 4:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                EndFragment endFragment = new EndFragment();
                if (bundle != null)
                    endFragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment,endFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 5:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                RequestFragment requestFragment = new RequestFragment();
                transaction.replace(R.id.nav_host_fragment,requestFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case 6:
                manager = this.getSupportFragmentManager();
                transaction = manager.beginTransaction();
                AdoptFragment adoptFragment = new AdoptFragment();
                transaction.replace(R.id.nav_host_fragment,adoptFragment);
                if (addToBackStack)
                    transaction.addToBackStack(null);
                transaction.commit();
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
            /*case R.id.nav_logoff:
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                return true;*/
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

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void showLoading(String mensaje) {
        progressDialog.setMessage( mensaje != null && !mensaje.isEmpty() ? mensaje : getString(R.string.mensaje_loading));
        progressDialog.show();
    }

    public void closeLoading() {
        progressDialog.dismiss();
    }

    private void createDialogLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo_confirmacion)
                .setMessage(R.string.mensaje_cerrar_sesion)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_Aceptar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logout();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.btn_Cancelar, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.create();
    }

    private void logout() {
        SeguridadUtil.logout();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}