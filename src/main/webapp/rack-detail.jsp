<%--
  Created by IntelliJ IDEA.
  User: acer
  Date: 10/5/2025
  Time: 12:14 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Chi tiết Rack</title>
  <link rel="stylesheet" href="css/rack-detail.css">
</head>
<body>
<h1>Sơ đồ kho — 6 x 7 Rack</h1>

<div id="grid" class="grid">
  <%
    int rows = 6;
    int cols = 7;
    int idx = 1;
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        // tạo mã rack: R01..R42
        String rackId = String.format("R%02d", idx++);
  %>
  <div class="cell" data-rack-id="<%=rackId%>" onclick="openRackDetail('<%=rackId%>')">
    <div class="stack">
      <!-- dòng 1: tên rack -->
      <div class="rack-name" id="name-<%=rackId%>"><%=rackId%></div>
      <!-- dòng 2: số lượng hiện có / capacity (50) -->
      <div class="capacity" id="cap-<%=rackId%>">-- / 50</div>
    </div>
    <div class="badge" id="badge-<%=rackId%>" style="display:none">OK</div>
  </div>
  <%      }
  }
  %>
</div>

<script>
  // Khi click vào ô sẽ đến trang detail (bạn đã có servlet RackController)
  function openRackDetail(rackId) {
    // chuyển trang, server sẽ trả chi tiết rack
    window.location.href = 'rack-detail.jsp?rackId=' + encodeURIComponent(rackId);
  }

  // Hàm load dữ liệu occupancy từ server (endpoint trả JSON)
  // Kỳ vọng JSON dạng:
  // [
  //   { "rackId":"R01", "quantity": 30, "capacity": 50 },
  //   { "rackId":"R02", "quantity": 0, "capacity": 50 },
  //   ...
  // ]
  async function loadGridData() {
    try {
      const res = await fetch('api/rackGrid'); // bạn implement endpoint trả JSON
      if (!res.ok) throw new Error('HTTP ' + res.status);
      const data = await res.json();

      // chuyển mảng thành map để dễ lookup
      const map = {};
      data.forEach(item => { map[item.rackId] = item; });

      // cập nhật từng ô
      document.querySelectorAll('.cell').forEach(cell => {
        const rid = cell.getAttribute('data-rack-id');
        const elCap = document.getElementById('cap-' + rid);
        const badge = document.getElementById('badge-' + rid);
        const rec = map[rid];
        if (rec) {
          const q = rec.quantity || 0;
          const cap = rec.capacity || 50;
          elCap.textContent = q + ' / ' + cap;

          // badge màu tùy mức độ
          if (q === 0) {
            badge.style.display = 'block';
            badge.className = 'badge full';
            badge.textContent = 'Trống';
          } else if (q >= cap) {
            badge.style.display = 'block';
            badge.className = 'badge full';
            badge.textContent = 'Đầy';
          } else if (q >= cap * 0.8) {
            badge.style.display = 'block';
            badge.className = 'badge warn';
            badge.textContent = 'Gần đầy';
          } else {
            badge.style.display = 'block';
            badge.className = 'badge ok';
            badge.textContent = 'OK';
          }
        } else {
          // nếu server chưa trả dữ liệu cho ô này
          elCap.textContent = '-- / 50';
          badge.style.display = 'none';
        }
      });

    } catch (err) {
      console.warn('Không load được dữ liệu grid từ server. Dùng dữ liệu mock. Error:', err);
      // fallback dữ liệu mock (ví dụ để dev chạy ngay)
      fillMockData();
    }
  }

  // Dữ liệu mock để dev thử giao diện (nếu chưa có API)
  function fillMockData() {
    // random sample
    document.querySelectorAll('.cell').forEach((cell, idx) => {
      const rid = cell.getAttribute('data-rack-id');
      const elCap = document.getElementById('cap-' + rid);
      const badge = document.getElementById('badge-' + rid);
      // tạo số lượng mẫu: 0..50
      const q = Math.floor(Math.random() * 51);
      const cap = 50;
      elCap.textContent = q + ' / ' + cap;
      badge.style.display = 'block';
      if (q === 0) { badge.className = 'badge full'; badge.textContent = 'Trống'; }
      else if (q >= cap) { badge.className = 'badge full'; badge.textContent = 'Đầy'; }
      else if (q >= cap * 0.8) { badge.className = 'badge warn'; badge.textContent = 'Gần đầy'; }
      else { badge.className = 'badge ok'; badge.textContent = 'OK'; }
    });
  }

  // chạy khi trang load
  window.addEventListener('load', function() {
    // gọi API nếu có; nếu không, mock data sẽ tự dùng
    loadGridData();
  });
</script>
</body>
</html>
