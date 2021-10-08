package com.greenstar.eagle.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.greenstar.eagle.controller.IPCForms.NeighbourhoodForm;
import com.greenstar.eagle.dao.CRFormDAO;
import com.greenstar.eagle.dao.ChildRegistrationFormDAO;
import com.greenstar.eagle.dao.DashboardDAO;
import com.greenstar.eagle.dao.DropdownCRBDataDAO;
import com.greenstar.eagle.dao.NeighbourhoodAttendeesModelDAO;
import com.greenstar.eagle.dao.NeighbourhoodFormDAO;
import com.greenstar.eagle.dao.ProvidersDAO;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.ChildRegistrationForm;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;
import com.greenstar.eagle.model.NeighbourhoodFormModel;
import com.greenstar.eagle.model.Providers;

@Database(entities = {Providers.class, Dashboard.class,
        DropdownCRBData.class, CRForm.class, ChildRegistrationForm.class, NeighbourhoodFormModel.class, NeighbourhoodAttendeesModel.class},
        version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "eagledb";
    private static AppDatabase INSTANCE;

    public abstract ProvidersDAO getProvidersDAO();
    public abstract DashboardDAO getDashboardDAO();
    public abstract DropdownCRBDataDAO getDropdownCRBDataDAO();
    public abstract CRFormDAO getCrFormDAO();
    public abstract ChildRegistrationFormDAO getChildRegistrationFormDAO();
    public abstract NeighbourhoodFormDAO getNeighbourhoodFormDAO();
    public abstract NeighbourhoodAttendeesModelDAO getNeighbourhoodAttendeesModelDAO();

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
