<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="com.cl.DTO.WifiInfo" %>
<%@ page import="com.cl.DTO.SearchData" %>
<%@ page import="com.cl.Service.SearchDataService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Wi-Fi Info</title>
  <script>
    function submitForm() {
      var lat = document.getElementById("lat").value;
      var lnt = document.getElementById("lnt").value;

      // URL에 쿼리 문자열을 붙이기
      var url = "?lat=" + encodeURIComponent(lat) + "&lnt=" + encodeURIComponent(lnt);
      window.location.href = url;
    }
    function getCurrentLocation() {
      if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
          document.getElementById("lat").value = position.coords.latitude;
          document.getElementById("lnt").value = position.coords.longitude;
        }, function(error) {
          alert("위치를 가져오는 데 실패했습니다: " + error.message);
        });
      } else {
        alert("이 브라우저는 Geolocation을 지원하지 않습니다.");
      }
    }
  </script>
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

<%
  List<WifiInfo> wifiList = new ArrayList<WifiInfo>();
  List<WifiInfo> findwifiList = new ArrayList<WifiInfo>();

  boolean found = false;

  double lat = 0;
  double lnt = 0;

  try {
    wifiList = (List<WifiInfo>) request.getSession().getAttribute("wifiList");

    // URL 쿼리 파라미터에서 LAT과 LNT 값을 가져옵니다.
    String latStr = request.getParameter("lat");
    String lntStr = request.getParameter("lnt");

    if (latStr != null && lntStr != null) {
      lat = Double.parseDouble(latStr);
      lnt = Double.parseDouble(lntStr);

      if (wifiList != null) {
        for (WifiInfo wifi : wifiList) {
          double wifiLat = wifi.getLAT(); // 위도 값 가져오기
          double wifiLnt = wifi.getLNT(); // 경도 값 가져오기
          double distance = wifi.haversine(lat, lnt);

          //2km미만의 값만 받기(자체로직추가)
          if(distance<=2) {
            wifi.setDistance(distance);
            findwifiList.add(wifi);
          }
          //문제규칙에는 없는데
          // 주간체크리스트에 20개라고 해놨길래
          // 20개를 넘어갈 경우 Stop
          if(findwifiList.size()>=20) {
            break;
          }
        }

        // 거리 기준으로 정렬
        findwifiList.sort(Comparator.comparingDouble(WifiInfo::getDistance));

        found = true;
        SearchDataService searchDataService = new SearchDataService();;
        searchDataService.insertdata(lat,lnt);
      }
    }
  } catch(Exception e) {
    e.printStackTrace();
  }
%>
<!-- 매인 홈 화면 (제출이후 개선)-->
<div>
  <h2> 공공 WIFI 정보 받아오기 </h2>
</div>

<div>
  <a href="/JavaEnterprise/" style="color: blue; text-decoration: underline;">홈</a>
  <a href="/JavaEnterprise/SearchData.jsp" style="color: blue; text-decoration: underline;">조회내역</a>
  <a href="/JavaEnterprise/load-wifi.jsp" style="color: blue; text-decoration: underline;">공공wifi데이터불러오기</a>
</div>

<!-- 입력란  -->

<form onsubmit="event.preventDefault(); submitForm();">
  <label for="lat">LAT:</label>
  <input type="text" id="lat" name="lat"  required>

  <label for="lnt">LNT:</label>
  <input type="text" id="lnt" name="lnt"  required>
  <input type="button" value="내 위치 가져오기" onclick="getCurrentLocation()">
  <input type="submit" value="근처 wpfi정보 보기">

</form>

<div id="result">
  <table id="customers">
    <tr>
      <th>거리(km)</th>
      <th>관리번호</th>
      <th>자치구</th>
      <th>와이파이명</th>
      <th>도로명주소</th>
      <th>상세주소</th>
      <th>설치위치(층)</th>
      <th>설치유형</th>
      <th>설치기관</th>
      <th>서비스구분</th>
      <th>망종류</th>
      <th>설치년도</th>
      <th>실내외구분</th>
      <th>WIFI접속환경</th>
      <th>X좌표</th>
      <th>Y좌표</th>
      <th>작업일자</th>
    </tr>
    <% if (found) { %>
    <% for (WifiInfo wifi : findwifiList) { %>
    <tr>
      <td><%= String.format("%.4f", wifi.getDistance()) %></td>
      <td><%= wifi.getX_SWIFI_MGR_NO() %></td>
      <td><%= wifi.getX_SWIFI_WRDOFC() %></td>
      <td><%= wifi.getX_SWIFI_MAIN_NM() %></td>
      <td><%= wifi.getX_SWIFI_ADRES1() %></td>
      <td><%= wifi.getX_SWIFI_ADRES2() %></td>
      <td><%= wifi.getX_SWIFI_INSTL_FLOOR() %></td>
      <td><%= wifi.getX_SWIFI_INSTL_TY() %></td>
      <td><%= wifi.getX_SWIFI_INSTL_MBY() %></td>
      <td><%= wifi.getX_SWIFI_SVC_SE() %></td>
      <td><%= wifi.getX_SWIFI_CMCWR() %></td>
      <td><%= wifi.getX_SWIFI_CNSTC_YEAR() %></td>
      <td><%= wifi.getX_SWIFI_INOUT_DOOR() %></td>
      <td><%= wifi.getX_SWIFI_REMARS3() %></td>
      <td><%= wifi.getLAT() %></td>
      <td><%= wifi.getLNT() %></td>
      <td><%= wifi.getWORK_DTTM() %></td>
    </tr>
    <% } %>
    <% } else { %>
    <tr>
      <td colspan="17">위치정보를 입력한 후에 조회해 주세요.</td>
    </tr>
    <% } %>
  </table>
</div>
</body>
</html>
