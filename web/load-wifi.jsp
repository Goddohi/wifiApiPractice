<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2024-09-06
  Time: 오전 1:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.cl.Service.WifiApi" %>
<%@ page import="com.cl.DTO.WifiInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% WifiApi wifiApi = new WifiApi();
    List<WifiInfo> wifiList = wifiApi.getWifiInfoFromApi();
    session = request.getSession();
    // 세션에 데이터 저장
    session.setAttribute("wifiList", wifiList);
    session.setAttribute("lastnum", wifiApi.lastnum);
%>
<div style="color: darkgreen">
    <h1><%=wifiApi.lastnum+"개의 Wifi정보를 불러왔습니다\n"%></h1>
</div>
<a href="/JavaEnterprise/" style="color: blue; text-decoration: underline;">메인으로 돌아가기</a>


</body>
</html>
