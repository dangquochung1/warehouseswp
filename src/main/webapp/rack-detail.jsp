<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Sơ đồ kho</title>
  <style>
    :root {
      --cols: 7;      /* 7 cột - Aisle */
      --rows: 6;      /* 6 hàng - Rack */
      --cell-gap: 8px;
      --cell-width: 120px;
      --cell-height: 80px;
    }
    body {
      font-family: Arial, sans-serif;
      padding: 20px;
      background: #f5f7fa;
    }
    h1 { margin-bottom: 16px; font-size: 20px; }

    .grid {
      display: grid;
      grid-template-columns: repeat(var(--cols), var(--cell-width));
      grid-template-rows: repeat(var(--rows), var(--cell-height));
      gap: var(--cell-gap);
      justify-content: start;
    }

    .cell {
      background: white;
      border: 1px solid #d0d6dd;
      border-radius: 6px;
      box-shadow: 0 1px 2px rgba(16,24,40,0.04);
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: transform .12s, box-shadow .12s;
    }
    .cell:hover {
      transform: translateY(-4px);
      box-shadow: 0 6px 18px rgba(16,24,40,0.08);
    }

    .rack-name {
      font-size: 15px;
      color: #0f172a;
      font-weight: 600;
    }

    @media (max-width: 980px) {
      :root { --cell-width: 96px; --cell-height: 70px; }
    }
    @media (max-width: 640px) {
      :root { --cols: 4; --cell-width: 80px; --rows: auto; }
      .grid { grid-auto-rows: 70px; }
    }
    .stack {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      line-height: 1.2;
    }

    .capacity {
      font-size: 13px;
      color: #475569;
      font-weight: 500;
    }

  </style>
</head>
<body>
<h1>Sơ đồ kho — 6 hàng Rack × 7 Aisle</h1>

<div id="grid" class="grid">
  <%
    int rows = 6; // Rack (theo chiều dọc)
    int cols = 7; // Aisle (theo chiều ngang)
    for (int r = 1; r <= rows; r++) {
      for (int c = 1; c <= cols; c++) {
        // Mã rack duy nhất (vẫn có C để phân biệt nội bộ, nhưng không hiển thị)
        String rackId = String.format("R%dC%d", r, c);
        // Hiển thị chỉ "R1", "R2", ...
        String rackName = String.format("R%d", r);
  %>
  <div class="cell" data-rack-id="<%=rackId%>" onclick="openRackDetail('<%=rackId%>')">
    <div class="stack">
      <div class="rack-name"><%=rackName%></div>
      <div class="capacity">/ 50</div>
    </div>
  </div>
  <%    }
  }
  %>
</div>

</body>
</html>
