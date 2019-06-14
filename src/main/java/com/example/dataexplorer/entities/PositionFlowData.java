package com.example.dataexplorer.entities;

import java.util.List;

public class PositionFlowData {
    private String _id;
    private List<PositionFlowDataPoints> data;

    public PositionFlowData() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<PositionFlowDataPoints> getData() {
        return data;
    }

    public void setData(List<PositionFlowDataPoints> data) {
        this.data = data;
    }
}
