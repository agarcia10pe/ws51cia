package com.hache.appentrepatas.ui.configuration;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.hache.appentrepatas.R;
import com.hache.appentrepatas.helper.DbContract;
import com.hache.appentrepatas.helper.EntrePatasDbHelper;
import com.hache.appentrepatas.helper.SharedPreferencesManager;
import com.hache.appentrepatas.util.Constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConfigurationFragment extends Fragment {
    Switch sSwitchConfig;
    EntrePatasDbHelper dbHelper;
    SQLiteDatabase db;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new EntrePatasDbHelper(getContext());
        db = dbHelper.getReadableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_configuration, container, false);
        sSwitchConfig = view.findViewById(R.id.switch_config);
        boolean data = getChecked();
        Toast.makeText(getContext(), String.valueOf(data), Toast.LENGTH_LONG);
        sSwitchConfig.setChecked(data);
        sSwitchConfig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                registerConfig(isChecked);
            }
        });
        return  view;
    }

    private boolean getChecked() {
        String[] projection = {
                BaseColumns._ID,
                DbContract.ConfigurationEntry.COLUMN_USUARIO,
                DbContract.ConfigurationEntry.COLUMN_ACTIVO
        };

        String sortOrder = DbContract.ConfigurationEntry.COLUMNS_FECHA + " DESC";
        Cursor cursor = db.query( DbContract.ConfigurationEntry.TABLE_NAME, projection, null, null, null, null, sortOrder);

        List items = new ArrayList();
        while(cursor.moveToNext()) {
            String activo  = cursor.getString(
                    cursor.getColumnIndexOrThrow(DbContract.ConfigurationEntry.COLUMN_ACTIVO));
            items.add(activo);
        }
        cursor.close();

        if (items.size() == 0)
            return  false;

        return  (items.get(0).equals("1"));
    }

    private void registerConfig(boolean isChecked) {
        int deletedRows = db.delete( DbContract.ConfigurationEntry.TABLE_NAME, null, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha = dateFormat.format(date);

        ContentValues values = new ContentValues();
        values.put(DbContract.ConfigurationEntry.COLUMN_USUARIO, SharedPreferencesManager.getSomeStringValue(Constants.PREF_USER));
        values.put(DbContract.ConfigurationEntry.COLUMNS_FECHA, fecha);
        values.put(DbContract.ConfigurationEntry.COLUMN_ACTIVO, isChecked ? "1" : "0");

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DbContract.ConfigurationEntry.TABLE_NAME, null, values);
    }
}