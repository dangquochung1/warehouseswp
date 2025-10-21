<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.*" %>
<html>
<head>
  <title>Sơ đồ kho Laptop</title>
  <link rel="stylesheet" href="css/location.css">
</head>
<body>
<h1>Sơ đồ kho Laptop</h1>
<a href="index.jsp">Home</a>
<div class="container">
  <div class="leftside">
    <%
      // ************************************************
      // Khối scriptlet LỌC VÀ CHUYỂN ĐỔI BỊ LOẠI BỎ
      // Chỉ giữ lại các biến cần thiết cho phần filter
      // ************************************************
      List<Warehouse> warehouses = (List<Warehouse>) request.getAttribute("warehouses");
      List<Area> areas = (List<Area>) request.getAttribute("areas");
      String selectedWarehouse = (String) request.getAttribute("selectedWarehouse");
      String selectedArea = (String) request.getAttribute("selectedArea");

      // Khai báo các biến cấu trúc từ Controller (Giảm scriptlet đầu tiên)
      List<Aisle> aisles = (List<Aisle>) request.getAttribute("aisles");
      Map<String, List<Rack>> mapAisleToRacks = (Map<String, List<Rack>>) request.getAttribute("mapAisleToRacks");
      Map<String, String> rackLotInfoFormatted = (Map<String, String>) request.getAttribute("rackLotInfoFormatted");

      // Dùng biến int đã được Controller tính
      int maxRows = (Integer) request.getAttribute("maxRows");
      int cols = (Integer) request.getAttribute("cols");

      // Khởi tạo an toàn (có thể dùng EL/JSTL nếu chuyển hết, nhưng tạm giữ để đảm bảo an toàn cho vòng lặp)
      if (aisles == null) aisles = Collections.emptyList();
      if (mapAisleToRacks == null) mapAisleToRacks = Collections.emptyMap();
      if (rackLotInfoFormatted == null) rackLotInfoFormatted = Collections.emptyMap();
    %>

    <form action="WarehouseAreaController" method="get" id="filterForm">
      <input type="hidden" name="lotId" value="<%= request.getParameter("lotId") != null ? request.getParameter("lotId") : "" %>">
      <label for="warehouse">Warehouse:</label>
      <select name="warehouse" id="warehouse" onchange="document.getElementById('filterForm').submit();">
        <%
          for (Warehouse w : warehouses) {
            String selected = w.getWarehouseId().equals(selectedWarehouse) ? "selected" : "";
        %>
        <option value="<%= w.getWarehouseId() %>" <%= selected %>><%= w.getName() %></option>
        <%
          }
        %>
      </select>

      <label for="area">Area:</label>
      <select name="area" id="area" onchange="document.getElementById('filterForm').submit();">
        <%
          for (Area a : areas) {
            String selected = a.getAreaId().equals(selectedArea) ? "selected" : "";
        %>
        <option value="<%= a.getAreaId() %>" <%= selected %>><%= a.getName() %></option>
        <%
          }
        %>
      </select>
    </form>
    <%
      Lot selectedLot = (Lot) request.getAttribute("selectedLot");
      List<LotDetail> selectedLotDetails = (List<LotDetail>) request.getAttribute("selectedLotDetails");
      Map<String, String> productMap = (Map<String, String>) request.getAttribute("productMap");
    %>

    <% if (selectedLot != null) { %>
    <div class="lot-section">
      <h3>Lot Code: <%= selectedLot.getLotCode() %></h3>
      <table class="lot-table">
        <tr>
          <th>LotDetail ID</th>
          <th>Product</th>
          <th>Quantity Remaining</th>
          <th>Status</th>
        </tr>
        <% if (selectedLotDetails != null && !selectedLotDetails.isEmpty()) {
          for (LotDetail d : selectedLotDetails) { %>
        <tr>
          <td><%= d.getLotDetailId() %></td>
          <td><%= productMap.getOrDefault(d.getProductId(), "(Không rõ)") %></td>
          <td><%= d.getRemaining() %></td>
          <%
            int st = d.getUnarrangedRemaining();
            String statusLabel, statusClass;
            switch (st) {
              case 0:  statusLabel = "Done"; statusClass = "status-inactive"; break;
              default: statusLabel = "Pending"; statusClass = "status-unknown"; break;
            }
          %>
          <td><span class="<%= statusClass %>"><%= statusLabel %></span></td>
        </tr>
        <%  } } else { %>
        <tr><td colspan="3" style="text-align:center;">Không có dữ liệu Lot Detail</td></tr>
        <% } %>
      </table>
    </div>
    <% } %>
  </div>
  <%
    // Kiểm tra điều kiện chính để render lưới
    if (!aisles.isEmpty()) {
  %>

  <div class="rightside">
    <div class="grid" style="grid-template-columns: repeat(<%= cols %>, var(--cell-width)); grid-template-rows: repeat(<%= maxRows+1 %>, var(--cell-height));">
      <%
        // Row 0: Aisle headers
        for (Aisle a : aisles) {
      %>
      <div class="cell aisle-header"><%= a.getName() %></div>
      <%
        }

        // Rows: racks
        for (int row = 0; row < maxRows; row++) {
          for (Aisle a : aisles) {
            List<Rack> listRacks = mapAisleToRacks.get(a.getAisleId());
            if (listRacks != null && row < listRacks.size()) {
              Rack r = listRacks.get(row);
              int sum = r.getSum();
              String colorClass;
              if (sum == 0) colorClass = "green";
              else if (sum >= 50) colorClass = "orange";
              else colorClass = "yellow";
      %>
      <%
        // ************************************************
        // Khối scriptlet định dạng LotInfo BỊ LOẠI BỎ
        // Thay bằng việc lấy chuỗi đã định dạng từ Controller
        // ************************************************
        String lotInfoStr = rackLotInfoFormatted.getOrDefault(r.getRackId(), "Lỗi dữ liệu.");
      %>
      <div class="cell <%= colorClass %>" data-lots="<%= lotInfoStr %>">
        <div class="rack-name"><%= r.getRackId() %></div>
        <div class="capacity"><%= sum %> / 50</div>
      </div>

      <%
      } else {
      %>
      <div class="cell"></div>
      <%
            }
          }
        }
      } else {
      %>
      <p>Không có dữ liệu để hiển thị.</p>
      <%
        }
      %>
    </div>
  </div>
</div>
<div id="popup" >
  <h3 style="margin-bottom:10px;">Chi tiết Lot trong Rack</h3>
  <pre id="popup-content" style="
    white-space:pre-wrap;
    font-size:14px;
    color:#334155;
  "></pre>
  <button onclick="closePopup()" style="
    background:#2563eb;
    color:white;
    border:none;
    border-radius:6px;
    padding:6px 12px;
    cursor:pointer;
  ">Đóng</button>
</div>
<script>
  // Khi click vào rack -> hiện popup
  document.querySelectorAll('.cell').forEach(cell => {
    cell.addEventListener('click', () => {
      const lots = cell.getAttribute('data-lots');
      if (lots) {
        document.getElementById('popup-content').innerText = lots;
        document.getElementById('popup').style.display = 'block';
      }
    });
  });

  // Đóng popup
  function closePopup() {
    document.getElementById('popup').style.display = 'none';
  }
</script>
</body>
</html>