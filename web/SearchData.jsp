<%@ page import="com.cl.DTO.SearchData" %>
<%@ page import="com.cl.Service.SearchDataService" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>조회내역</title>
    <style>
        #customers {
            font-family: Arial, Helvetica, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        #customers td, #customers th {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #customers tr:nth-child(even){background-color: #f2f2f2;}

        #customers tr:hover {background-color: #ddd;}

        #customers th {
            padding-top: 12px;
            padding-bottom: 12px;
            text-align: left;
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>
<body>

<div id="result">
    <% SearchDataService searchDataService = new SearchDataService();
        List<SearchData> searchDatas= searchDataService.selectSearchData();
    %>
        <!--(제출이후 개선)-->
        <div>
            <h2> 공공 WIFI 정보 받아오기 </h2>
        </div>
        <a href="/JavaEnterprise/" style="color: blue; text-decoration: underline;">홈</a>

        <table id="customers">
        <tr>
            <th>ID</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>조회일시</th>
        </tr>
        <% if (searchDatas.size()>0) { %>
        <% for (SearchData searchData : searchDatas) { %>
        <tr>
            <td><%= searchData.getId() %></td>
            <td><%= searchData.getLAT() %></td>
            <td><%= searchData.getLNT() %></td>
            <td><%= searchData.getSearchTime() %></td>
        </tr>
        <% } %>
        <% } else { %>
        <tr>
            <td colspan="4">검색내역이 없습니다</td>
        </tr>
        <% } %>
    </table>
</body>
</html>
