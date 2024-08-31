package com.cl.Service;

import com.cl.DTO.WifiInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.List;

public class WifiApi {

    public List<WifiInfo> getWifiInfo() {
        String jsonData = ""; // api에서 받아오기

        Gson gson = new Gson();
        try {
            WifiInfo wifiInfo = gson.fromJson(jsonData, WifiInfo.class);

            System.out.println("전체 Wi-Fi 개수: " + wifiInfo.getList_total_count());
            for (WifiInfo.Row row : wifiInfo.getRow()) {
            System.out.println("Wi-Fi 명칭: " + row.getX_SWIFI_MAIN_NM());
            System.out.println("위치: " + row.getX_SWIFI_ADRES1() + " " + row.getX_SWIFI_ADRES2());
            System.out.println("설치 연도: " + row.getX_SWIFI_CNSTC_YEAR());
            }
        } catch (JsonSyntaxException e){
        e.printStackTrace();
        }
        return null;//잠시 null
    }
}
