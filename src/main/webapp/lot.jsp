<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Danh sách Lot & Lot Detail (Demo)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f7fa;
            padding: 20px;
            color: #0f172a;
        }
        h2 {
            color: #1e293b;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            background: white;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-bottom: 10px;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background: #1e293b;
            color: white;
        }
        tr:hover {
            background: #f1f5f9;
            cursor: pointer;
        }
        .detail-row {
            display: none;
        }
        .detail-container {
            background: #f8fafc;
            padding: 10px 20px;
        }
        .btn-link {
            background: #2563eb;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 6px;
            cursor: pointer;
        }
        .btn-link:hover {
            background: #1e40af;
        }

        /* ===== STATUS STYLE ===== */
        .status {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 6px;
            color: white;
            font-weight: bold;
            text-align: center;
            min-width: 80px;
        }
        .status.done {
            background-color: #22c55e; /* xanh lá */
        }
        .status.pending {
            background-color: #facc15; /* vàng */
            color: #1e293b;
        }
    </style>

    <script>
        // ===== DỮ LIỆU MẪU =====
        var productMap = {
            'P001': 'Laptop Dell XPS 13',
            'P002': 'Laptop Dell Inspiron 15',
            'P003': 'Laptop HP Pavilion 14',
            'P004': 'Laptop HP Envy 13'
        };

        var lotData = [
            {
                lot_id: 1,
                supplier_id: 'S001',
                lot_code: 'LOT-001',
                received_date: '2025-10-10',
                description: 'Lô hàng Laptop Dell',
                arrange_status: 'done',
                details: [
                    {lotdetail_id: 101, product_id: 'P001', purchase_price: 1200, quantity_total: 10, quantity_remaining: 8},
                    {lotdetail_id: 102, product_id: 'P002', purchase_price: 950, quantity_total: 5, quantity_remaining: 5}
                ]
            },
            {
                lot_id: 2,
                supplier_id: 'S002',
                lot_code: 'LOT-002',
                received_date: '2025-10-11',
                description: 'Lô hàng Laptop HP',
                arrange_status: 'pending',
                details: [
                    {lotdetail_id: 103, product_id: 'P003', purchase_price: 1000, quantity_total: 7, quantity_remaining: 6},
                    {lotdetail_id: 104, product_id: 'P004', purchase_price: 1100, quantity_total: 4, quantity_remaining: 4}
                ]
            }
        ];

        // ===== HÀM HIỂN THỊ =====
        function toggleDetails(lotId) {
            var all = document.querySelectorAll('.detail-row');
            for (var i = 0; i < all.length; i++) {
                all[i].style.display = 'none';
            }
            var row = document.getElementById('detail-' + lotId);
            if (row) row.style.display = 'table-row';
        }

        function goToLocation() {
            window.location.href = "location.jsp";
        }

        function renderLots() {
            var table = document.getElementById('lotTable');
            var html = "";
            html += "<tr>" +
                "<th>Lot ID</th>" +
                "<th>Supplier ID</th>" +
                "<th>Lot Code</th>" +
                "<th>Received Date</th>" +
                "<th>Description</th>" +
                "<th>Arrange</th>" +  // ✅ cột Arrange
                "<th>Action</th>" +   // cột nút A
                "</tr>";

            for (var i = 0; i < lotData.length; i++) {
                var lot = lotData[i];
                var statusClass = lot.arrange_status === 'done' ? 'done' : 'pending';
                var statusText = lot.arrange_status === 'done' ? 'Done' : 'Pending';

                html += "<tr onclick='toggleDetails(" + lot.lot_id + ")'>" +
                    "<td>" + lot.lot_id + "</td>" +
                    "<td>" + lot.supplier_id + "</td>" +
                    "<td>" + lot.lot_code + "</td>" +
                    "<td>" + lot.received_date + "</td>" +
                    "<td>" + lot.description + "</td>" +
                    "<td><span class='status " + statusClass + "'>" + statusText + "</span></td>" + // ✅ Arrange status
                    "<td><button class='btn-link' onclick='event.stopPropagation(); goToLocation()'>A</button></td>" + // Nút A
                    "</tr>";

                html += "<tr id='detail-" + lot.lot_id + "' class='detail-row'>" +
                    "<td colspan='7'>" +
                    "<div class='detail-container'>" +
                    "<strong>Chi tiết Lot #" + lot.lot_id + "</strong>" +
                    "<table>" +
                    "<tr>" +
                    "<th>Lot Detail ID</th>" +
                    "<th>Product</th>" +
                    "<th>Purchase Price</th>" +
                    "<th>Quantity Total</th>" +
                    "<th>Quantity Remaining</th>" +
                    "</tr>";

                if (lot.details.length > 0) {
                    for (var j = 0; j < lot.details.length; j++) {
                        var d = lot.details[j];
                        var productName = productMap[d.product_id] || "(Không rõ)";
                        html += "<tr>" +
                            "<td>" + d.lotdetail_id + "</td>" +
                            "<td>" + d.product_id + " - " + productName + "</td>" +
                            "<td>" + d.purchase_price + "</td>" +
                            "<td>" + d.quantity_total + "</td>" +
                            "<td>" + d.quantity_remaining + "</td>" +
                            "</tr>";
                    }
                } else {
                    html += "<tr><td colspan='5' style='text-align:center;'>Không có dữ liệu chi tiết</td></tr>";
                }

                html += "</table></div></td></tr>";
            }

            table.innerHTML = html;
        }

        window.onload = renderLots;
    </script>
</head>

<body>
<h2>Danh sách Lot (Demo tĩnh)</h2>
<table id="lotTable"></table>
</body>
</html>
