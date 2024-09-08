package com.cl.Service;

import com.cl.DTO.SearchData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchDataService {

    // MySQL 연결 정보
    private static final String DB_URL = "jdbc:mysql://localhost:3306/publicwifi";

    //사용시 아이디 입력요망
    private static final String USER = "";
    //사용시 비밀번호 입력요망
    private static final String PASSWORD = "";

    static {
        try {
            // JDBC 드라이버 로드 (필요시)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertdata(double LAT, double LNT) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO searchdata (LAT, LNT) VALUES (?, ?)")) {

            // SQL 쿼리
            preparedStatement.setDouble(1, LAT);
            preparedStatement.setDouble(2, LNT);

            // 쿼리 실행
            int rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<SearchData> selectSearchData() {
        List<SearchData> list = new ArrayList<>();
        String sql = "SELECT id, LAT, LNT, search_time FROM searchdata order by id DESC ";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // 결과 처리
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                double LAT = resultSet.getDouble("LAT");
                double LNT = resultSet.getDouble("LNT");
                Timestamp searchTime = resultSet.getTimestamp("search_time");

                //불러온 데이터를 리스트에 추가 (빌더방식으로 객체생성)
                list.add(new SearchData().builder()
                        .id(id)
                        .LAT(LAT)
                        .LNT(LNT)
                        .searchTime(searchTime)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
