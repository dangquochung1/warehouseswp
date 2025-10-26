<%@ page import="java.util.*, model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<%@include file="SideBar.jsp" %>

    <script>
        function toggleDetails(lotId) {
            const all = document.querySelectorAll('.detail-row');
            all.forEach(row => row.style.display = 'none');
            const row = document.getElementById('detail-' + lotId);
            if (row) row.style.display = 'table-row';
        }
        function goToLocation() {
            window.location.href = "location.jsp";
        }

    </script>
<style>
    /* Biến CSS để dễ tùy chỉnh */
    :root {
        --table-border: #d1d5db; /* Màu viền bảng */
        --header-bg: #f0f0f0;    /* Màu nền header bảng */
        --row-hover-bg: #f3f4f6; /* Nền khi hover hàng */
        --text-primary: #1f2937; /* Màu chữ chính */
        --text-secondary: #4b5563; /* Màu chữ phụ */
        --status-done: #22c55e;  /* Xanh cho "Done" */
        --status-pending: #ef4444; /* Đỏ cho "Pending" */
        --button-bg: #3b82f6;    /* Nền nút Arrange All */
        --button-hover: #2563eb; /* Hover nút */
        --shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Bóng đổ */
    }

    /* Container chính cho nội dung */
    .page-wrapper .content {
        padding: 20px;
        background: #ffffff;
        border-radius: 8px;
        box-shadow: var(--shadow);
        max-width: 1200px;
        margin: 20px auto;
    }

    /* Tiêu đề trang */
    h2 {
        font-size: 24px;
        color: var(--text-primary);
        margin-bottom: 20px;
        text-align: center;
    }

    /* Bảng chính danh sách Lot */
    #lotTable {
        width: 100%;
        border-collapse: collapse;
        margin-bottom: 20px;
        box-shadow: var(--shadow);
        border: 1px solid var(--table-border);
        border-radius: 8px;
        overflow: hidden; /* Để bo góc không bị tràn */
    }

    /* Header bảng */
    #lotTable th {
        background: var(--header-bg);
        padding: 12px 16px;
        text-align: left;
        font-weight: bold;
        color: var(--text-primary);
        font-size: 14px;
        border-bottom: 2px solid var(--table-border);
    }

    /* Hàng bảng chính */
    #lotTable tr {
        cursor: pointer;
        transition: background 0.2s;
    }

    #lotTable tr:hover {
        background: var(--row-hover-bg);
    }

    /* Cell bảng chính */
    #lotTable td {
        padding: 12px 16px;
        border-bottom: 1px solid var(--table-border);
        color: var(--text-secondary);
        font-size: 14px;
    }

    /* Hàng chi tiết (ẩn mặc định) */
    .detail-row {
        display: none;
        background: #f9fafb; /* Nền nhạt cho chi tiết */
    }

    /* Container chi tiết */
    .detail-container {
        padding: 16px;
        border-top: 1px solid var(--table-border);
    }

    .detail-container strong {
        display: block;
        font-size: 16px;
        color: var(--text-primary);
        margin-bottom: 10px;
    }

    /* Bảng chi tiết Lot */
    .detail-container table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 10px;
        box-shadow: var(--shadow);
        border: 1px solid var(--table-border);
        border-radius: 4px;
        overflow: hidden;
    }

    .detail-container table th {
        background: var(--header-bg);
        padding: 10px 14px;
        text-align: left;
        font-weight: bold;
        color: var(--text-primary);
        font-size: 13px;
        border-bottom: 1px solid var(--table-border);
    }

    .detail-container table td {
        padding: 10px 14px;
        border-bottom: 1px solid var(--table-border);
        color: var(--text-secondary);
        font-size: 13px;
    }

    /* Status classes */
    .status {
        font-weight: bold;
        padding: 4px 8px;
        border-radius: 4px;
        display: inline-block;
    }

    .status.done {
        color: var(--status-done);
        background: #dcfce7; /* Nền xanh nhạt */
    }

    .status.pending {
        color: var(--status-pending);
        background: #fee2e2; /* Nền đỏ nhạt */
    }

    /* Nút Arrange All */
    .btn-link {
        background: var(--button-bg);
        color: white;
        border: none;
        padding: 8px 16px;
        border-radius: 6px;
        cursor: pointer;
        font-size: 14px;
        font-weight: bold;
        transition: background 0.2s;
    }

    .btn-link:hover {
        background: var(--button-hover);
    }

    /* Không có dữ liệu */
    #lotTable tr td[colspan="6"],
    .detail-container table tr td[colspan="7"] {
        text-align: center;
        color: #6b7280; /* Màu xám */
        font-style: italic;
        padding: 20px;
    }

    /* Responsive: Màn hình nhỏ */
    @media (max-width: 768px) {
        #lotTable th, #lotTable td {
            padding: 10px;
            font-size: 13px;
        }

        .detail-container table th, .detail-container table td {
            padding: 8px;
            font-size: 12px;
        }

        .btn-link {
            width: 100%; /* Nút full width trên mobile */
            margin-top: 10px;
        }
    }
</style>
<body>
<div class="page-wrapper">
    <div class="content">
<h2>Lot List</h2>

<table id="lotTable">
    <tr>
        <th>Lot ID</th>
        <th>Supplier</th>
        <th>Lot Code</th>
        <th>Received Date</th>
        <th>Description</th>
        <th>Action</th>
    </tr>

    <%
        List<Lot> lots = (List<Lot>) request.getAttribute("lotList");
        Map<String, String> supplierMap = (Map<String, String>) request.getAttribute("supplierMap");
        Map<String, List<LotDetail>> lotDetailsMap = (Map<String, List<LotDetail>>) request.getAttribute("lotDetailsMap");
        Map<String, String> productMap = (Map<String, String>) request.getAttribute("productMap");
        if (lots == null || lots.isEmpty()) {
    %>
    <tr><td colspan="6" style="text-align:center;">Lot have no information</td></tr>
    <%
    } else {
        for (Lot l : lots) {
            String supplierName = supplierMap != null ? supplierMap.getOrDefault(l.getSupplierId(), "(Không rõ)") : "(Không rõ)";
    %>

    <tr onclick="toggleDetails('<%= l.getLotId() %>')">
        <td><%= l.getLotId() %></td>
        <td><%= supplierName %></td>
        <td><%= l.getLotCode() %></td>
        <td><%= l.getReceivedDate() %></td>
        <td><%= l.getDescription() %></td>
        <td>
            <button class="btn-link"
                    onclick="event.stopPropagation();
                            window.location.href='RackLotController?warehouseId=W001&lotId=<%= l.getLotId() %>'">
                Arrange All
            </button>
        </td>
    </tr>

    <!-- Bảng chi tiết Lot -->
    <tr id="detail-<%= l.getLotId() %>" class="detail-row">
        <td colspan="6">
            <div class="detail-container">
                <strong> Lot In Details #<%= l.getLotId() %></strong>
                <table>
                    <tr>
                        <th>Lot Detail ID</th>
                        <th>Product</th>
                        <th>Purchase Price</th>
                        <th>Quantity Total</th>
                        <th>Unarranged Remaining</th>
                        <th>Arrange Status</th>
                        <th>Remaining</th>
                    </tr>
                    <%
                        List<LotDetail> details = lotDetailsMap != null ? lotDetailsMap.get(l.getLotId()) : null;
                        if (details != null && !details.isEmpty()) {
                            for (LotDetail d : details) {
                                int unarranged = d.getUnarrangedRemaining();
                                int remaining = d.getRemaining();

                                // UnarrangedRemaining = 0 → Done
                                String statusClass = unarranged == 0 ? "done" : "pending";
                                String statusText = unarranged == 0 ? "Done" : "Pending";
                    %>
                    <tr>
                        <td><%= d.getLotDetailId() %></td>
                        <td><%= productMap.getOrDefault(d.getProductId(), "(Không rõ)") %></td>
                        <td><%= String.format("%,.2f", d.getPurchasePrice()) %></td>
                        <td><%= d.getQuantityTotal() %></td>
                        <td><%= unarranged %></td>
                        <td><span class="status <%= statusClass %>"><%= statusText %></span></td>
                        <td><%= remaining %></td>
                    </tr>

                    <%
                        }
                    } else {
                    %>
                    <tr><td colspan="7" style="text-align:center;">No information in details</td></tr>
                    <% } %>
                </table>
            </div>
        </td>
    </tr>

    <%
            }
        }
    %>
</table>
    </div>
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
<!-- Các script từ OutboundCompleteOrder.jsp (nếu cần thêm cho nhất quán, nhưng trang này không dùng table datatable nên có thể bỏ) -->
<script src="assets/js/jquery-3.6.0.min.js"></script>
<script src="assets/js/feather.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/script.js"></script>

</body>
</html>
