package com.cl.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class WifiInfo {
        private String X_SWIFI_MGR_NO;
        private String X_SWIFI_WRDOFC;
        private String X_SWIFI_MAIN_NM;
        private String X_SWIFI_ADRES1;
        private String X_SWIFI_ADRES2;
        private String X_SWIFI_INSTL_FLOOR;
        private String X_SWIFI_INSTL_TY;
        private String X_SWIFI_INSTL_MBY;
        private String X_SWIFI_SVC_SE;
        private String X_SWIFI_CMCWR;
        private String X_SWIFI_CNSTC_YEAR;
        private String X_SWIFI_INOUT_DOOR;
        private String X_SWIFI_REMARS3;
        private double LAT;
        private double LNT;
        private String WORK_DTTM;
        private double distance;

        public double haversine(double lat1, double lon1) {
                final double R = 6371; // 지구의 반경 (km)
                double dLat = Math.toRadians(LAT - lat1);
                double dLon = Math.toRadians(LNT - lon1);
                double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(LAT)) *
                                Math.sin(dLon / 2) * Math.sin(dLon / 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                return R * c;
        }
    }
