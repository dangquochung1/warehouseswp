<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.*" %>
<html>
<head>
  <title>Sơ đồ kho Laptop</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f5f7fa;
      padding: 20px;
      color: #0f172a;
    }
    .container {
      display: flex;
      justify-content: center;
    }
    h1 {
      font-size: 22px;
      margin-bottom: 20px;
      padding:20px;
      text-align: center;
    }

    form {
      background: #fff;
      border-radius: 6px;
      box-shadow: 0 1px 2px rgba(0,0,0,0.1);
      margin-bottom: 20px;
      display: flex;
      gap: 12px;
      align-items: center;
      flex-direction: column;
      padding: 40px;
      margin-right:80px;
      margin-left:30px;
    }

    label { font-weight: 600; }
    select {
      padding: 6px 10px;
      border-radius: 4px;
      border: 1px solid #cbd5e1;
      font-size: 14px;
    }

    :root {
      --cell-gap: 8px;
      --cell-width: 120px;
      --cell-height: 80px;
    }

    .grid {
      display: grid;
      gap: var(--cell-gap);
    }

    .cell {
      border: 1px solid #d0d6dd;
      border-radius: 6px;
      box-shadow: 0 1px 2px rgba(16,24,40,0.05);
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      transition: background 0.3s;
    }

    /* ✅ màu theo trạng thái */
    .cell.green {
      background: #bbf7d0; /* xanh lá nhạt */
    }
    .cell.yellow {
      background: #fef9c3; /* vàng nhạt */
    }
    .cell.orange {
      background: #fdba74; /* cam nhạt */
    }

    .rack-name {
      font-size: 15px;
      font-weight: 600;
    }

    .capacity {
      font-size: 13px;
      color: #475569;
    }

    .aisle-header {
      font-weight: bold;
      color: #2563eb;
      margin-bottom: 6px;
    }
  </style>
</head>
<body>
<h1>Sơ đồ kho Laptop</h1>
<div class="container">

  <%
    List<Warehouse> warehouses = (List<Warehouse>) request.getAttribute("warehouses");
    List<Area> areas = (List<Area>) request.getAttribute("areas");
    String selectedWarehouse = (String) request.getAttribute("selectedWarehouse");
    String selectedArea = (String) request.getAttribute("selectedArea");
  %>

  <form action="WarehouseAreaController" method="get" id="filterForm">
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
    List<Aisle> aisles = (List<Aisle>) request.getAttribute("aisles");
    List<Rack> racks = (List<Rack>) request.getAttribute("racks");

    if (aisles != null && racks != null) {

      // Tạo map: aisleId -> list<Rack>
      Map<String, List<Rack>> map = new LinkedHashMap<>();
      int maxRows = 0;
      for (Aisle a : aisles) {
        List<Rack> listRacks = new ArrayList<>();
        for (Rack r : racks) {
          if (r.getAisleId() != null && r.getAisleId().equalsIgnoreCase(a.getAisleId())) {
            listRacks.add(r);
          }
        }
        map.put(a.getAisleId(), listRacks);
        if (listRacks.size() > maxRows) maxRows = listRacks.size();
      }

      int cols = aisles.size();
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
            List<Rack> listRacks = map.get(a.getAisleId());
            if (listRacks != null && row < listRacks.size()) {
              Rack r = listRacks.get(row);
              int sum = r.getSum();
              String colorClass;
              if (sum == 0) colorClass = "green";
              else if (sum >= 50) colorClass = "orange";
              else colorClass = "yellow";
      %>
      <div class="cell <%= colorClass %>">
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
</body>
</html>
