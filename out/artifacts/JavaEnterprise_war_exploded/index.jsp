<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 2024-08-31
  Time: 오후 1:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="com.cl.Service.WifiApi" %>
<%@ page import="com.cl.DTO.WifiInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body><% WifiApi wifiApi = new WifiApi();
          List<WifiInfo> wifiList = wifiApi.getWifiInfoFromApi();

  %>

  <%=wifiApi.lastnum%>

  <%=wifiList.get(wifiApi.lastnum-1).getX_SWIFI_CMCWR()%>
  <%=wifiList.get(1).getX_SWIFI_CMCWR()%>
  <table><tr><td>
  </td>
  </tr>
  </table>
  </body>
</html>
