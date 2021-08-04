package com.tworoot2.covidupdates;

import android.widget.TextView;

import androidx.annotation.StringRes;

import com.android.volley.toolbox.StringRequest;

import java.util.List;

public class Model {

    private String stateName;
    private String confirmedC;
    private String recoveredC;
    private String activeC;
    private String deathC;
    private String dateD;

    public Model(String stateName, String confirmedC, String recoveredC, String activeC, String deathC, String dateD) {
        this.stateName = stateName;
        this.confirmedC = confirmedC;
        this.recoveredC = recoveredC;
        this.activeC = activeC;
        this.deathC = deathC;
        this.dateD =dateD;
    }


    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getConfirmedC() {
        return confirmedC;
    }

    public void setConfirmedC(String confirmedC) {
        this.confirmedC = confirmedC;
    }

    public String getRecoveredC() {
        return recoveredC;
    }

    public void setRecoveredC(String recoveredC) {
        this.recoveredC = recoveredC;
    }

    public String getActiveC() {
        return activeC;
    }

    public void setActiveC(String activeC) {
        this.activeC = activeC;
    }

    public String getDeathC() {
        return deathC;
    }

    public void setDeathC(String deathC) {
        this.deathC = deathC;
    }

    public String getDateD() {
        return dateD;
    }

    public void setDateD(String dateD) {
        this.dateD = dateD;
    }
}

