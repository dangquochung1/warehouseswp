<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Outbound Order</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .create-order-container {
            max-width: 900px;
            margin: 20px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .create-order-header {
            margin-bottom: 30px;
        }

        .create-order-header h1 {
            font-size: 28px;
            margin-bottom: 10px;
            color: #333;
        }

        .create-date {
            color: #666;
            font-size: 14px;
        }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 30px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            font-weight: 600;
            margin-bottom: 8px;
            color: #333;
            font-size: 14px;
        }

        .form-group input,
        .form-group select {
            padding: 10px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #5b9bd5;
        }

        .status-badge {
            display: inline-block;
            padding: 4px 12px;
            background: #fff3cd;
            color: #856404;
            border-radius: 4px;
            font-size: 14px;
            font-weight: 500;
        }

        .product-section {
            margin-top: 30px;
        }

        .product-section-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .product-section-header h2 {
            font-size: 20px;
            color: #333;
        }

        .add-product-btn {
            background: #5b9bd5;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
        }

        .add-product-btn:hover {
            background: #4a8bc2;
        }

        .product-table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 30px;
        }

        .product-table thead {
            background: #f8f9fa;
        }

        .product-table th,
        .product-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .product-table th {
            font-weight: 600;
            color: #333;
            font-size: 14px;
        }

        .product-table td {
            font-size: 14px;
        }

        .product-table tbody tr:hover {
            background: #f8f9fa;
        }

        .form-actions {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

        .btn-cancel {
            padding: 12px 30px;
            background: #fff;
            color: #333;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
        }

        .btn-cancel:hover {
            background: #f8f9fa;
        }

        .btn-submit {
            padding: 12px 30px;
            background: #5cb85c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
        }

        .btn-submit:hover {
            background: #4cae4c;
        }

        .product-row-actions button {
            background: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 3px;
            cursor: pointer;
            font-size: 12px;
        }

        .product-row-actions button:hover {
            background: #c82333;
        }
    </style>
</head>
<body>
<div class="container">
    <header class="header">
        Outbound management
    </header>

    <div class="main-content">
        <aside class="sidebar">
            <div class="sidebar-item">Dashboard</div>
            <div class="sidebar-item active">Create outbound order</div>
        </aside>

        <section class="content-area">
            <div class="create-order-container">
                <div class="create-order-header">
                    <h1>Create Outbound order</h1>
                    <p class="create-date">Create date: ${currentDate}</p>
                </div>

                <form action="outboundcreateorder" method="post" id="createOrderForm">
                    <div class="form-grid">
                        <div class="form-group">
                            <label for="referenceNo">Reference No:</label>
                            <input type="text" id="referenceNo" name="referenceNo" value="xxx" readonly>
                        </div>

                        <div class="form-group">
                            <label for="createBy">Create By: </label>
                            <input type="text" id="createBy" name="createBy" value="Nguyễn văn A" readonly>
                        </div>

                        <div class="form-group">
                            <label for="location">Location:</label>
                            <select id="location" name="location" required>
                                <option value="xxx">xxx</option>
                                <c:forEach items="${warehouseList}" var="warehouse">
                                    <option value="${warehouse.warehouseId}">${warehouse.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="orderDate">Order Date:</label>
                            <input type="date" id="orderDate" name="orderDate" required>
                        </div>

                        <div class="form-group">
                            <label for="responsibleStaff">Responsible staff:</label>
                            <select id="responsibleStaff" name="responsibleStaff" required>
                                <option value="">Nguyễn văn A</option>
                                <c:forEach items="${staffList}" var="staff">
                                    <option value="${staff.uid}">${staff.fullname}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="expectedShipDate">Expected ship date:</label>
                            <input type="date" id="expectedShipDate" name="expectedShipDate" required>
                        </div>
                    </div>

                    <div class="form-group" style="grid-column: 1 / -1;">
                        <label>Status:</label>
                        <span class="status-badge">Pending</span>
                    </div>

                    <div class="product-section">
                        <div class="product-section-header">
                            <h2>List Add Product</h2>
                            <button type="button" class="add-product-btn" onclick="addProductRow()">+ Add Product</button>
                        </div>

                        <table class="product-table" id="productTable">
                            <thead>
                            <tr>
                                <th style="width: 60px;">Stt</th>
                                <th style="width: 100px;">ID</th>
                                <th>Product name</th>
                                <th style="width: 120px;">Quantity</th>
                                <th style="width: 200px;">Note</th>
                                <th style="width: 80px;">Action</th>
                            </tr>
                            </thead>
                            <tbody id="productTableBody">
                            </tbody>
                        </table>
                    </div>

                    <div class="form-actions">
                        <button type="button" class="btn-cancel" onclick="window.location.href='outbounddashboard'">Cancel</button>
                        <button type="submit" class="btn-submit">Create Outbound Order</button>
                    </div>
                </form>
            </div>
        </section>
    </div>
</div>

<script>

    let rowCount = 0;

    // Khi chọn location, lọc nhân viên và reset bảng sản phẩm
    const locationSelect = document.getElementById("location");

    locationSelect.addEventListener("change", function () {
        const warehouseId = this.value;
        if (!warehouseId) return;
        const staffUrl = "getStaffByWarehouse?warehouseId=" + warehouseId;
        // Gọi AJAX lấy staff
        fetch(staffUrl)
            .then(response => response.json())
            .then(data => {
                const staffSelect = document.getElementById("responsibleStaff");
                staffSelect.innerHTML = '<option value="">-- Select Staff --</option>';
                data.forEach(staff => {
                    const option = document.createElement("option");
                    option.value = staff.uid;
                    option.textContent = staff.fullname;
                    staffSelect.appendChild(option);
                });
            })
            .catch(err => console.error("Error loading staff:", err));

        // Reset bảng sản phẩm
        document.getElementById("productTableBody").innerHTML = "";
        rowCount = 0;
    });

    // Hiển thị danh sách sản phẩm theo location
    window.addProductRow = function() {
        const warehouseId = document.getElementById("location").value;
        if (!warehouseId) {
            alert("Please select a location first!");
            return;
        }
        const productUrl = `getProductsByWarehouse?warehouseId=` + warehouseId;
        fetch(productUrl)
            .then(response => {
                console.log("Response status:", response.status);
                return response.text();
            })
            .then(text => {
                const products = text ? JSON.parse(text) : [];
                console.log("Products loaded:", products);
                if (products.length === 0) {
                    alert("No products found in this warehouse!");
                    return;
                }

                const tbody = document.getElementById("productTableBody");
                const newRow = tbody.insertRow();
                rowCount++;

                let productOptions = "";
                products.forEach(p => {
                    productOptions += `<option value="` + p.productId + `">` + p.productId + ` - ` + p.productName + `</option>`;
                });

                console.log("===== PRODUCT OPTIONS HTML =====");
                console.log(productOptions);
                console.log("Options length:", productOptions.length);
                console.log("================================");

                const rowHTML =
                    '<td>' + rowCount + '</td>' +
                    '<td>' +
                    '<select name="productId" required style="width:90%;padding:5px;">' +
                    '<option value="">--Select--</option>' +
                    productOptions +
                    '</select>' +
                    '<input type="hidden" name="rackId">' +
                    '</td>' +
                    '<td><input type="text" name="productName" readonly style="width:95%;padding:5px;"></td>' +
                    '<td><input type="number" name="quantity" min="1" required style="width:90%;padding:5px;"></td>' +
                    '<td><input type="text" name="note" style="width:95%;padding:5px;"></td>' +
                    '<td class="product-row-actions">' +
                    '<button type="button" onclick="removeProductRow(this)">Delete</button>' +
                    '</td>';

                console.log("===== ROW HTML =====");
                console.log(rowHTML);
                console.log("====================");

                newRow.innerHTML = rowHTML

                // Khi chọn product, tự set productName và rackId
                const productSelect = newRow.querySelector("select[name='productId']");
                const productNameInput = newRow.querySelector("input[name='productName']");
                const rackInput = newRow.querySelector("input[name='rackId']");

                productSelect.addEventListener("change", function() {
                    const selected = products.find(p => p.productId == this.value);
                    if (selected) {
                        productNameInput.value = selected.productName;
                        rackInput.value = selected.rackId; // tự động set rackId
                    } else {
                        productNameInput.value = "";
                        rackInput.value = "";
                    }
                });
            })
            .catch(err => console.error("Error loading products:", err));
    };

    window.removeProductRow = function(button) {
        button.closest("tr").remove();
        updateRowNumbers();
    };

    function updateRowNumbers() {
        const rows = document.querySelectorAll('#productTableBody tr');
        rows.forEach((row, index) => {
            row.cells[0].textContent = index + 1;
        });
        rowCount = rows.length;
    }

    // Set default date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('orderDate').value = today;
    document.getElementById('expectedShipDate').value = today;


</script>

</body>
</html>