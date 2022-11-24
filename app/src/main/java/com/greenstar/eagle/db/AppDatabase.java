package com.greenstar.eagle.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.greenstar.eagle.controller.IPCForms.NeighbourhoodForm;
import com.greenstar.eagle.dao.CRFormDAO;
import com.greenstar.eagle.dao.ChildRegistrationFormDAO;
import com.greenstar.eagle.dao.DashboardDAO;
import com.greenstar.eagle.dao.DropdownCRBDataDAO;
import com.greenstar.eagle.dao.FollowupModelDAO;
import com.greenstar.eagle.dao.NeighbourhoodAttendeesModelDAO;
import com.greenstar.eagle.dao.NeighbourhoodFormDAO;
import com.greenstar.eagle.dao.ProvidersDAO;
import com.greenstar.eagle.dao.TokenModelDAO;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.ChildRegistrationForm;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.model.FollowupModel;
import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;
import com.greenstar.eagle.model.NeighbourhoodFormModel;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.TokenModel;

@Database(entities = {Providers.class, Dashboard.class,
        DropdownCRBData.class, CRForm.class, ChildRegistrationForm.class, NeighbourhoodFormModel.class, NeighbourhoodAttendeesModel.class,
        FollowupModel.class, TokenModel.class},
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
    public abstract FollowupModelDAO getFollowupModelDAO();
    public abstract TokenModelDAO getTokenModelDAO();

    public static AppDatabase getAppDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_2_3)
                            .build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE TokenModel "
                    + " ADD COLUMN remarks TEXT");
            database.execSQL("ALTER TABLE CRForm "
                    + " ADD COLUMN remarks TEXT");
            database.execSQL("ALTER TABLE FollowupModel "
                    + " ADD COLUMN remarks TEXT");
        }
    };

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
