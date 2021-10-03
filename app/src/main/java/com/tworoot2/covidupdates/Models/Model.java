package com.tworoot2.covidupdates.Models;

import android.widget.TextView;

import androidx.annotation.StringRes;

import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.List;

public class Model {

    private String stateName;
    private String confirmedC;
    private String recoveredC;
    private String activeC;
    private String deathC;
    private String dateD;
    private String dose1;
    private String dose2;
    private String totalDose;
    private JSONObject districtObject;

    public Model(String stateName, String confirmedC, String recoveredC, String activeC,
                 String deathC, String dateD, String dose1, String dose2, String totalDose, JSONObject districtObject) {
        this.stateName = stateName;
        this.confirmedC = confirmedC;
        this.recoveredC = recoveredC;
        this.activeC = activeC;
        this.deathC = deathC;
        this.dateD =dateD;
        this.dose1 =dose1;
        this.dose2 =dose2;
        this.totalDose =totalDose;
        this.districtObject =districtObject;
    }

    public JSONObject getDistrictObject() {
        return districtObject;
    }

    public void setDistrictObject(JSONObject districtObject) {
        this.districtObject = districtObject;
    }

    public String getDose1() {
        return dose1;
    }

    public void setDose1(String dose1) {
        this.dose1 = dose1;
    }

    public String getDose2() {
        return dose2;
    }

    public void setDose2(String dose2) {
        this.dose2 = dose2;
    }

    public String getTotalDose() {
        return totalDose;
    }

    public void setTotalDose(String totalDose) {
        this.totalDose = totalDose;
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

