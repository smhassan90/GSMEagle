package com.greenstar.eagle.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.content.SharedPreferences;

import com.greenstar.eagle.controller.Codes;
import com.greenstar.eagle.controller.IPCForms.NeighbourhoodForm;
import com.greenstar.eagle.dao.AreasDAO;
import com.greenstar.eagle.dao.CRFormDAO;
import com.greenstar.eagle.dao.ChildRegistrationFormDAO;
import com.greenstar.eagle.dao.DashboardDAO;
import com.greenstar.eagle.dao.DropdownCRBDataDAO;
import com.greenstar.eagle.dao.FollowupModelDAO;
import com.greenstar.eagle.dao.NeighbourhoodAttendeesModelDAO;
import com.greenstar.eagle.dao.NeighbourhoodFormDAO;
import com.greenstar.eagle.dao.ProvidersDAO;
import com.greenstar.eagle.dao.QuestionsDAO;
import com.greenstar.eagle.dao.ScreeningAreaDetailDAO;
import com.greenstar.eagle.dao.ScreeningFormHeaderDAO;
import com.greenstar.eagle.dao.ScreeningTestDAO;
import com.greenstar.eagle.dao.TokenModelDAO;
import com.greenstar.eagle.model.Areas;
import com.greenstar.eagle.model.CRForm;
import com.greenstar.eagle.model.ChildRegistrationForm;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.model.FollowupModel;
import com.greenstar.eagle.model.NeighbourhoodAttendeesModel;
import com.greenstar.eagle.model.NeighbourhoodFormModel;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.Questions;
import com.greenstar.eagle.model.ScreeningAreaDetail;
import com.greenstar.eagle.model.ScreeningFormHeader;
import com.greenstar.eagle.model.ScreeningTest;
import com.greenstar.eagle.model.TokenModel;

import static android.content.Context.MODE_PRIVATE;

@Database(entities = {Providers.class, Dashboard.class,
        DropdownCRBData.class, CRForm.class, ChildRegistrationForm.class, NeighbourhoodFormModel.class, NeighbourhoodAttendeesModel.class,
        FollowupModel.class, TokenModel.class, Questions.class, Areas.class, ScreeningFormHeader.class, ScreeningAreaDetail.class, ScreeningTest.class},
        version = 5)
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
    public abstract QuestionsDAO getQuestionsDAO();
    public abstract AreasDAO getAreasDAO();
    public abstract ScreeningFormHeaderDAO getScreeningFormHeaderDAO();
    public abstract ScreeningAreaDetailDAO getScreeningAreaDetailDAO();
    public abstract ScreeningTestDAO getScreeningTestDAO();

    public static AppDatabase getAppDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_2_3,MIGRATION_3_4, MIGRATION_4_5)
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
    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Areas` (`id` INTEGER, 'detail' TEXT, status INTEGER, type INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Questions` (`id` INTEGER,  'detail' TEXT, 'status' INTEGER, 'type' INTEGER, 'points' INTEGER, 'areaId' INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ScreeningAreaDetail` (`id` INTEGER, 'formId' INTEGER, 'areaId' INTEGER, 'totalIndicators' INTEGER, 'totalIndicatorsAchieved' INTEGER, 'totalCriticalIndicators' INTEGER, 'totalCriticalIndicatorsAchieved' INTEGER, 'totalPoints' INTEGER, 'comments' TEXT,  PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ScreeningFormHeader` (`id` INTEGER, 'clientId' INTEGER, 'type' INTEGER, 'approvalStatus' INTEGER, 'mobileDate' TEXT, 'remarks' TEXT,  PRIMARY KEY(`id`))");

        }
    };
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Areas` (`id` INTEGER, 'detail' TEXT, status INTEGER, type INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `Questions` (`id` INTEGER,  'detail' TEXT, 'status' INTEGER, 'type' INTEGER, 'points' INTEGER, 'areaId' INTEGER, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ScreeningAreaDetail` (`id` INTEGER, 'formId' INTEGER, 'areaId' INTEGER, 'totalIndicators' INTEGER, 'totalIndicatorsAchieved' INTEGER, 'totalCriticalIndicators' INTEGER, 'totalCriticalIndicatorsAchieved' INTEGER, 'totalPoints' INTEGER, 'comments' TEXT, 'finalOutcome' TEXT,'referred' TEXT, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ScreeningFormHeader` (`id` INTEGER, 'clientId' INTEGER, 'type' INTEGER, 'visitDate' TEXT, 'approvalStatus' INTEGER, 'mobileSystemDate' TEXT, 'remarks' TEXT,  PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE IF NOT EXISTS `ScreeningTest` (`id` INTEGER, 'areaId' INTEGER, 'testId' INTEGER,'formId' INTEGER, 'testOutcome' TEXT, 'referred' TEXT, 'testDetail' TEXT, PRIMARY KEY(`id`))");

        }
    };

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
