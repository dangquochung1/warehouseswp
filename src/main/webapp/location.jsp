<%@ page import="java.util.*, model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<%@include file="SideBar.jsp" %>

<style>

  /* Biến CSS để dễ tùy chỉnh */
  :root {
    --cell-width: 120px;  /* Chiều rộng mỗi cell (rack/aisle) */
    --cell-height: 60px;  /* Chiều cao mỗi cell */
    --grid-gap: 8px;      /* Khoảng cách giữa các cell */
    --header-bg: #f0f0f0; /* Màu nền header aisle */
    --border-color: #d1d5db; /* Màu viền chung */
  }

  /* Container chính (leftside + rightside) */
  .container {
    display: flex;
    flex-wrap: wrap; /* Để responsive nếu màn hình nhỏ */
    gap: 20px;
    margin: 20px 0;
    padding: 0 20px;
  }

  /* Phần bên trái: Filter và Lot info */
  .leftside {
    flex: 1 1 300px; /* Chiếm ít nhất 300px, linh hoạt */
    background: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .leftside form {
    display: flex;
    flex-direction: column;
    gap: 10px;
  }

  .leftside label {
    font-weight: bold;
    color: #4b5563;
  }

  .leftside select {
    padding: 8px;
    border: 1px solid var(--border-color);
    border-radius: 4px;
    background: #f9fafb;
    cursor: pointer;
  }

  .leftside select:focus {
    outline: none;
    border-color: #3b82f6; /* Màu xanh khi focus */
  }

  /* Phần Lot Section */
  .lot-section {
    margin-top: 20px;
  }

  .lot-section h3 {
    font-size: 18px;
    margin-bottom: 10px;
    color: #1f2937;
  }

  .lot-table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 10px;
  }

  .lot-table th, .lot-table td {
    border: 1px solid var(--border-color);
    padding: 8px 12px;
    text-align: left;
    font-size: 14px;
  }

  .lot-table th {
    background: var(--header-bg);
    font-weight: bold;
    color: #374151;
  }

  .lot-table td {
    color: #4b5563;
  }

  /* Status classes cho bảng lot */
  .status-inactive {
    color: #22c55e; /* Xanh cho "Done" */
    font-weight: bold;
  }

  .status-unknown {
    color: #ef4444; /* Đỏ cho "Pending" */
    font-weight: bold;
  }

  /* Phần bên phải: Grid sơ đồ kho */
  .rightside {
    flex: 2 1 600px; /* Chiếm nhiều không gian hơn */
    background: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  /* Grid: 7 columns (aisles), 7 rows (1 header + 6 racks) */
  .grid {
    display: grid;
    grid-template-columns: repeat(7, var(--cell-width)); /* 7 aisles */
    grid-template-rows: repeat(7, var(--cell-height)); /* 7 rows (header + 6 racks) */
    gap: var(--grid-gap);
    justify-content: center; /* Căn giữa nếu không full width */
  }

  /* Mỗi cell trong grid */
  .cell {
    border: 1px solid var(--border-color);
    border-radius: 4px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    cursor: pointer;
    transition: transform 0.2s, box-shadow 0.2s;
    background: #f9fafb; /* Nền mặc định */
    font-size: 12px;
    color: #4b5563;
    overflow: hidden; /* Ẩn nội dung thừa */
  }

  .cell:hover {
    transform: scale(1.05); /* Phóng to nhẹ khi hover */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
  }

  /* Header cho aisles */
  .aisle-header {
    background: var(--header-bg);
    font-weight: bold;
    color: #1f2937;
    font-size: 14px;
  }

  /* Tên rack */
  .rack-name {
    font-weight: bold;
    margin-bottom: 4px;
  }

  /* Dung lượng (sum / 50) */
  .capacity {
    font-size: 11px;
    color: #6b7280;
  }

  /* Màu sắc cho rack dựa trên dung lượng */
  .green {
    background: #dcfce7; /* Xanh nhạt cho rỗng (sum=0) */
    border-color: #22c55e;
  }

  .yellow {
    background: #fef9c3; /* Vàng cho ít hàng (sum < 50) */
    border-color: #eab308;
  }

  .orange {
    background: #ffedd5; /* Cam cho đầy (sum >=50) */
    border-color: #f97316;
  }

  /* Popup chi tiết lot */
  #popup {
    display: none;
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: #ffffff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.2);
    z-index: 1000;
    max-width: 400px;
    width: 90%;
    text-align: left;
  }

  #popup h3 {
    font-size: 16px;
    margin-bottom: 10px;
    color: #1f2937;
  }

  #popup pre {
    white-space: pre-wrap;
    word-wrap: break-word;
    background: #f3f4f6;
    padding: 10px;
    border-radius: 4px;
    font-family: monospace;
    font-size: 13px;
    color: #374151;
    max-height: 300px;
    overflow-y: auto;
  }

  #popup button {
    margin-top: 10px;
    background: #3b82f6;
    color: white;
    border: none;
    padding: 8px 16px;
    border-radius: 4px;
    cursor: pointer;
    font-weight: bold;
  }

  #popup button:hover {
    background: #2563eb;
  }

  /* Responsive: Nếu màn hình nhỏ, stack left/right vertically */
  @media (max-width: 768px) {
    .container {
      flex-direction: column;
    }

    .leftside, .rightside {
      width: 100%;
    }

    .grid {
      grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); /* Tự động điều chỉnh columns */
    }
  }
</style>
<body>
<div class="page-wrapper">
  <div class="content">

    <h1>Warehouse Layout</h1>

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
      <h3 style="margin-bottom:10px;">Lot in details</h3>
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

  </div>  <!-- Kết thúc .content -->
</div>  <!-- Kết thúc .page-wrapper -->
</div>  <!-- Kết thúc main-wrapper từ SideBar.jsp -->

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

<!-- Các script từ OutboundCompleteOrder.jsp (nếu cần thêm cho nhất quán, nhưng trang này không dùng table datatable nên có thể bỏ) -->
<script src="assets/js/jquery-3.6.0.min.js"></script>
<script src="assets/js/feather.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/script.js"></script>

</body>
</html>