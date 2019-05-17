package com.example.dataexplorer.services;

import java.util.List;

public interface GeneralDataService {

    long getTotalParsedPackets();
    long getTotalRawPackets();
    long getTotalParsedPacketsBySnifferMac(String snifferMac);
    long getTotalRawPacketsBySnifferMac(String snifferMac);
}
