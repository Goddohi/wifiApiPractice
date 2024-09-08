package com.cl.Service;

import com.cl.DTO.WifiInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WifiApi {
    public int lastnum = 1000;
    boolean count = false;

    public WifiApi() {}

    public List<WifiInfo> getWifiInfoFromApi() {
        // 리스트를 초기화
        List<WifiInfo> wifiDataList = new ArrayList<>();

        try {
            // lastnum 값에 따라 1000개씩 데이터를 가져옴
            for (int i = 1; i <= lastnum; i += 1000) {
                // API URL 설정
                //입력받은 키 넣으세용!
                String key = "";
                String apiUrl = String.format("http://openapi.seoul.go.kr:8088/%s/json/TbPublicWifiInfo/%d/%d/",key, i, i + 999);
                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");

                // API 응답 읽기
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();

                // 받은 JSON 문자열을 처리하고, 리스트에 추가
                String jsonString = content.toString();
                List<WifiInfo> wifiInfoBatch = getWifiInfo(jsonString);
                if (wifiInfoBatch != null) {
                    wifiDataList.addAll(wifiInfoBatch);  // 리스트에 데이터를 추가
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wifiDataList;
    }

    public List<WifiInfo> getWifiInfo(String jsonString) {
        // Gson 객체 생성
        Gson gson = new Gson();

        // JSON 문자열을 JsonObject로 변환
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);

        // "TbPublicWifiInfo" 객체 추출
        JsonObject tbPublicWifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");

        // 결과 코드 확인
        JsonObject result = tbPublicWifiInfo.getAsJsonObject("RESULT");
        String resultCode = result.get("CODE").getAsString();

        // 결과 코드가 "INFO-000"인지 확인
        if ("INFO-000".equals(resultCode)) {
            // 처음 한 번만 전체 데이터 개수를 가져옴
            if (!count) {
                lastnum = Integer.parseInt(tbPublicWifiInfo.get("list_total_count").getAsString());
                count = true;
            }

            // "row" 배열 추출
            JsonArray rows = tbPublicWifiInfo.getAsJsonArray("row");

            // "row" 데이터를 List<WifiData>로 변환
            Type listType = new TypeToken<List<WifiInfo>>() {
            }.getType();
            List<WifiInfo> wifiDataList = gson.fromJson(rows, listType);

            return wifiDataList;
        } else {
            System.out.println("Error: " + result.get("MESSAGE").getAsString());
        }
        return null;
    }
}
