<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.*" %>
<html>
<head>
    <title>Danh sách Lot (Từ Database)</title>
    <link rel="stylesheet" type="text/css" href="css/lot.css"/>

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
</head>

<body>
<h2>Danh sách Lot</h2>

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
    <tr><td colspan="6" style="text-align:center;">Không có dữ liệu Lot</td></tr>
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

    <tr id="detail-<%= l.getLotId() %>" class="detail-row">
        <td colspan="6">
            <div class="detail-container">
                <strong>Chi tiết Lot #<%= l.getLotId() %></strong>
                <table>
                    <tr>
                        <th>Lot Detail ID</th>
                        <th>Product</th>
                        <th>Purchase Price</th>
                        <th>Quantity Total</th>
                        <th>Quantity Remaining</th>
                        <th>Status</th>
                    </tr>
                    <%
                        List<LotDetail> details = lotDetailsMap != null ? lotDetailsMap.get(l.getLotId()) : null;
                        if (details != null && !details.isEmpty()) {
                            for (LotDetail d : details) {
                                int status = d.getQuantityRemaining(); // 0 = pending, 1 = done
                                String statusClass = status == 0 ? "done" : "pending";
                                String statusText = status == 0 ? "Done" : "Pending";
                    %>
                    <tr>
                        <td><%= d.getLotDetailId() %></td>
                        <td><%= productMap.getOrDefault(d.getProductId(), "(Không rõ)") %></td>
                        <td><%= d.getPurchasePrice() %></td>
                        <td><%= d.getQuantityTotal() %></td>
                        <td><%= d.getQuantityRemaining() %></td>
                        <td><span class="status <%= statusClass %>"><%= statusText %></span></td>
                    </tr>

                    <%
                        }
                    } else {
                    %>
                    <tr><td colspan="6" style="text-align:center;">Không có dữ liệu chi tiết</td></tr>
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
</body>
</html>
