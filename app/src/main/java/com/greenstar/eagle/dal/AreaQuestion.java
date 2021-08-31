package com.greenstar.eagle.dal;

import android.view.View;
import android.widget.RadioButton;

import com.greenstar.eagle.model.Question;

import java.util.Map;

public class AreaQuestion {
    int areaId;
    String areaName;
    View areaView;
    Map<RadioButton, Question> questionRadioButtons;

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public View getAreaView() {
        return areaView;
    }

    public void setAreaView(View areaView) {
        this.areaView = areaView;
    }

    public Map<RadioButton, Question> getQuestionRadioButtons() {
        return questionRadioButtons;
    }

    public void setQuestionRadioButtons(Map<RadioButton, Question> questionRadioButtons) {
        this.questionRadioButtons = questionRadioButtons;
    }
}
