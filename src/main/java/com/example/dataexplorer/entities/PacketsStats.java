package com.example.dataexplorer.entities;

public class PacketsStats {
    long global;
    long local;

    public PacketsStats() {
    }

    public long getGlobal() {
        return global;
    }

    public void setGlobal(long global) {
        this.global = global;
    }

    public long getLocal() {
        return local;
    }

    public void setLocal(long local) {
        this.local = local;
    }
}
