package com.cl.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WifiInfoSaver {

    // MySQL 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            // API 호출
            String apiUrl = "http://openapi.seoul.go.kr:8088/sample/json/TbPublicWifiInfo/1/5/";  // 실제 API URL로 대체
            HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
            conn.setRequestMethod("GET");

            // API 응답 데이터 읽기
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // JSON 파싱
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(content.toString(), JsonObject.class);

            // "RESULT" 객체에서 "CODE" 값을 확인
            JsonObject result = jsonObject.getAsJsonObject("TbPublicWifiInfo").getAsJsonObject("RESULT");
            String code = result.get("CODE").getAsString();

            // "INFO-000" 코드인 경우에만 처리
            if ("INFO-000".equals(code)) {
                // "row" 데이터 추출
                JsonArray rows = jsonObject.getAsJsonObject("TbPublicWifiInfo").getAsJsonArray("row");

                // MySQL에 데이터 삽입
                try (Connection connDb = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    String sql = "INSERT INTO WifiInfo (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = connDb.prepareStatement(sql);

                    // 각 row 데이터를 DB에 삽입
                    for (JsonElement element : rows) {
                        JsonObject row = element.getAsJsonObject();

                        pstmt.setString(1, row.get("X_SWIFI_MGR_NO").getAsString());
                        pstmt.setString(2, row.get("X_SWIFI_WRDOFC").getAsString());
                        pstmt.setString(3, row.get("X_SWIFI_MAIN_NM").getAsString());
                        pstmt.setString(4, row.get("X_SWIFI_ADRES1").getAsString());
                        pstmt.setString(5, row.get("X_SWIFI_ADRES2").getAsString());
                        pstmt.setString(6, row.get("X_SWIFI_INSTL_FLOOR").getAsString());
                        pstmt.setString(7, row.get("X_SWIFI_INSTL_TY").getAsString());
                        pstmt.setString(8, row.get("X_SWIFI_INSTL_MBY").getAsString());
                        pstmt.setString(9, row.has("X_SWIFI_SVC_SE") ? row.get("X_SWIFI_SVC_SE").getAsString() : null);
                        pstmt.setString(10, row.get("X_SWIFI_CMCWR").getAsString());
                        pstmt.setString(11, row.get("X_SWIFI_CNSTC_YEAR").getAsString());
                        pstmt.setString(12, row.get("X_SWIFI_INOUT_DOOR").getAsString());
                        pstmt.setString(13, row.has("X_SWIFI_REMARS3") ? row.get("X_SWIFI_REMARS3").getAsString() : null);
                        pstmt.setString(14, row.get("LAT").getAsString());
                        pstmt.setString(15, row.get("LNT").getAsString());
                        pstmt.setString(16, row.get("WORK_DTTM").getAsString());

                        // SQL 실행
                        pstmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("API 응답이 정상적이지 않습니다. 코드: " + code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
