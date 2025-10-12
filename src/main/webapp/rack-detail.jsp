<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, model.Warehouse, model.Area" %>
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
    h1 { font-size: 22px; margin-bottom: 20px; }

    form {
      background: #fff;
      padding: 16px;
      border-radius: 6px;
      box-shadow: 0 1px 2px rgba(0,0,0,0.1);
      margin-bottom: 20px;
      display: flex;
      gap: 12px;
      align-items: center;
    }

    label { font-weight: 600; }
    select {
      padding: 6px 10px;
      border-radius: 4px;
      border: 1px solid #cbd5e1;
      font-size: 14px;
    }

    :root {
      --cols: 7;
      --rows: 6;
      --cell-gap: 8px;
      --cell-width: 120px;
      --cell-height: 80px;
    }

    .grid {
      display: grid;
      grid-template-columns: repeat(var(--cols), var(--cell-width));
      grid-template-rows: repeat(var(--rows), var(--cell-height));
      gap: var(--cell-gap);
      margin-top: 10px;
    }

    .cell {
      background: white;
      border: 1px solid #d0d6dd;
      border-radius: 6px;
      box-shadow: 0 1px 2px rgba(16,24,40,0.05);
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .rack-name {
      font-size: 15px;
      font-weight: 600;
    }

    .capacity {
      font-size: 13px;
      color: #475569;
    }

    .stack {
      display: flex;
      flex-direction: column;
      align-items: center;
      line-height: 1.2;
    }
  </style>
</head>
<body>

<h1>Sơ đồ kho Laptop</h1>
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

<h2>Kết quả hiển thị</h2>
<p><b>Warehouse ID:</b> <%= selectedWarehouse %></p>
<p><b>Area ID:</b> <%= selectedArea %></p>


<%
  String warehouse = request.getParameter("warehouse");
  if (warehouse == null) warehouse = "W1";

  String area = request.getParameter("area");
  if (area == null) area = "A";

  int rows = 6;
  int cols = 7;
%>

<!--<h2>Warehouse <%= warehouse %> - Area <%= area %></h2> -->

<div class="grid">
  <%
    for (int r = 1; r <= rows; r++) {
      for (int c = 1; c <= cols; c++) {
        String rackId = warehouse + "_" + area + "_R" + r + "C" + c;
        String rackName = "R" + r + "C" + c;
  %>
  <div class="cell" id="<%= rackId %>">
    <div class="stack">
      <div class="rack-name"><%= rackName %></div>
      <div class="capacity">/ 50</div>
    </div>
  </div>
  <%
      }
    }
  %>
</div>

</body>
</html>
