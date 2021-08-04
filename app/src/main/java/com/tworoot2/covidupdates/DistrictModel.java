package com.tworoot2.covidupdates;

public class DistrictModel {
    private String districtName;
    private String confirmedC;
    private String recoveredC;
    private String activeC;
    private String deathC;
    private String dateD;

    public DistrictModel(String districtName, String confirmedC, String recoveredC, String activeC, String deathC, String dateD) {
        this.districtName = districtName;
        this.confirmedC = confirmedC;
        this.recoveredC = recoveredC;
        this.activeC = activeC;
        this.deathC = deathC;
        this.dateD = dateD;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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
