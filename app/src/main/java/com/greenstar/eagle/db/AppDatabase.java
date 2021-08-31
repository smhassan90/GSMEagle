package com.greenstar.eagle.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.greenstar.eagle.dao.ApprovalQTVFormDAO;
import com.greenstar.eagle.dao.AreaDAO;
import com.greenstar.eagle.dao.AreaDetailDAO;
import com.greenstar.eagle.dao.DashboardDAO;
import com.greenstar.eagle.dao.DropdownCRBDataDAO;
import com.greenstar.eagle.dao.ProvidersDAO;
import com.greenstar.eagle.dao.QATFormHeaderDAO;
import com.greenstar.eagle.dao.QATFormQuestionDAO;
import com.greenstar.eagle.dao.QATTCFormDAO;
import com.greenstar.eagle.dao.QTVFormDAO;
import com.greenstar.eagle.dao.QuestionsDAO;
import com.greenstar.eagle.dao.approval.ApprovalQATAreaDAO;
import com.greenstar.eagle.dao.approval.ApprovalQATFormDAO;
import com.greenstar.eagle.dao.approval.ApprovalQATFormQuestionDAO;
import com.greenstar.eagle.model.DropdownCRBData;
import com.greenstar.eagle.model.QATTCForm;
import com.greenstar.eagle.model.approval.ApprovalQATArea;
import com.greenstar.eagle.model.approval.ApprovalQATForm;
import com.greenstar.eagle.model.approval.ApprovalQATFormQuestion;
import com.greenstar.eagle.model.approval.ApprovalQTVForm;
import com.greenstar.eagle.model.Area;
import com.greenstar.eagle.model.Dashboard;
import com.greenstar.eagle.model.Providers;
import com.greenstar.eagle.model.QATAreaDetail;
import com.greenstar.eagle.model.QATFormHeader;
import com.greenstar.eagle.model.QATFormQuestion;
import com.greenstar.eagle.model.QTVForm;
import com.greenstar.eagle.model.Question;

@Database(entities = {Providers.class, QTVForm.class, ApprovalQTVForm.class, Dashboard.class, Question.class, Area.class, QATFormHeader.class,
        QATFormQuestion.class, QATAreaDetail.class, ApprovalQATForm.class, ApprovalQATArea.class, ApprovalQATFormQuestion.class, QATTCForm.class,
        DropdownCRBData.class},
        version = 4)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "hsteamdb";
    private static AppDatabase INSTANCE;

    public abstract ProvidersDAO getProvidersDAO();
    public abstract DashboardDAO getDashboardDAO();
    public abstract QTVFormDAO getQTVFormDAO();
    public abstract ApprovalQTVFormDAO getApprovalQTVFormDAO();
    public abstract QuestionsDAO getQuestionsDAO();
    public abstract AreaDAO getAreaDAO();
    public abstract AreaDetailDAO getAreaDetailDAO();
    public abstract QATFormHeaderDAO getQatFormHeaderDAO();
    public abstract QATFormQuestionDAO getQatFormQuestionDAO();
    public abstract QATTCFormDAO getQattcFormDAO();

    public abstract ApprovalQATFormQuestionDAO getApprovalQATFormQuestionDAO();
    public abstract ApprovalQATAreaDAO getApprovalQATAreaDAO();
    public abstract ApprovalQATFormDAO getApprovalQATFormDAO();
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
