package com.greenstar.eagle.dal;

import android.view.View;
import android.widget.RadioButton;

import com.greenstar.eagle.model.Questions;

import java.util.Map;

public class AreaQuestion {
    int areaId;
    String areaName;
    View areaView;
    View areaTestView;
    Map<RadioButton, Questions> questionRadioButtons;

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

    public Map<RadioButton, Questions> getQuestionRadioButtons() {
        return questionRadioButtons;
    }

    public void setQuestionRadioButtons(Map<RadioButton, Questions> questionRadioButtons) {
        this.questionRadioButtons = questionRadioButtons;
    }

    public View getAreaTestView() {
        return areaTestView;
    }

    public void setAreaTestView(View areaTestView) {
        this.areaTestView = areaTestView;
    }
}