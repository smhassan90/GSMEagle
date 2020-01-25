package com.greenstar.mecwheel.crb.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.greenstar.mecwheel.crb.dao.CRBFormDAO;
import com.greenstar.mecwheel.crb.dao.DropdownCRBDataDAO;
import com.greenstar.mecwheel.crb.dao.ProvidersDAO;
import com.greenstar.mecwheel.crb.model.CRBForm;
import com.greenstar.mecwheel.crb.model.DropdownCRBData;
import com.greenstar.mecwheel.crb.model.Providers;


@Database(entities = {Providers.class, CRBForm.class, DropdownCRBData.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "dbgreenstar";
    private static AppDatabase INSTANCE;

    public abstract ProvidersDAO getProvidersDAO();
    public abstract CRBFormDAO getCRBFormDAO();
    public abstract DropdownCRBDataDAO getDropdownCRBDataDAO();

    public static AppDatabase getAppDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
