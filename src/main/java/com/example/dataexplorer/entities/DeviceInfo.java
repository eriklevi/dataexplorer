package com.example.dataexplorer.entities;

public class DeviceInfo {
    private String mac;
    private String ouiName;
    private String ouiCompleteName;

    public DeviceInfo() {
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getOuiName() {
        return ouiName;
    }

    public void setOuiName(String ouiName) {
        this.ouiName = ouiName;
    }

    public String getOuiCompleteName() {
        return ouiCompleteName;
    }

    public void setOuiCompleteName(String ouiCompleteName) {
        this.ouiCompleteName = ouiCompleteName;
    }
}
