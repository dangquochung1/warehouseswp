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
                            <button type="button" class="add-product-btn" onclick="openAddProductPopup()">+ Add Product</button>
                        </div>

                        <table class="product-table" id="productTable">
                            <thead>
                            <tr>
                                <th style="width: 60px;">Stt</th>
                                <th style="width: 100px;">ID</th>
                                <th>Product name</th>
                                <th>Aisle</th>
                                <th>Lowest price</th>
                                <th>Avg price</th>
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
                        <button type="button" class="btn-cancel" onclick="window.location.href='outboundmanager'">Cancel</button>
                        <button type="submit" class="btn-submit">Create Outbound Order</button>
                    </div>
                </form>
            </div>
        </section>
    </div>
</div>
<!-- ============ Add Product Popup (tất cả sản phẩm, kèm list kho) ============ -->
<div id="addProductPopup" style="display:none; position:fixed; inset:0; background:rgba(0,0,0,.35); z-index:9999;">
    <div style="max-width:980px; margin:40px auto; background:#fff; border-radius:8px; padding:16px;">
        <div style="display:flex; justify-content:space-between; align-items:center; gap:12px; margin-bottom:12px;">
            <h3 style="margin:0;">Add products</h3>
            <input id="searchAll" placeholder="Search product..." style="flex:1; padding:8px; border:1px solid #ddd; border-radius:6px">
            <button type="button" onclick="closeAddProductPopup()">Close</button>
        </div>
        <div style="max-height:60vh; overflow:auto; border:1px solid #eee; border-radius:6px;">
            <table class="product-table" style="margin:0;">
                <thead>
                <tr>
                    <th style="width:32%">Product</th>
                    <th style="width:28%">Warehouse (stock)</th>
                    <th style="width:14%">Qty</th>
                    <th style="width:14%"></th>
                </tr>
                </thead>
                <tbody id="popupProductsBody"></tbody>
            </table>
        </div>
    </div>
</div>

<script>
    let rowCount = 0;

    // Khi đổi location: vẫn load staff như cũ, nhưng KHÔNG lọc sản phẩm nữa
    const locationSelect = document.getElementById("location");
    locationSelect.addEventListener("change", function () {
        const warehouseId = this.value;
        if (!warehouseId) return;
        const staffUrl = "getStaffByWarehouse?warehouseId=" + warehouseId;
        fetch(staffUrl)
            .then(r => r.json())
            .then(data => {
                const staffSelect = document.getElementById("responsibleStaff");
                staffSelect.innerHTML = '<option value="">-- Select Staff --</option>';
                data.forEach(staff => {
                    const opt = document.createElement("option");
                    opt.value = staff.uid;
                    opt.textContent = staff.fullname;
                    staffSelect.appendChild(opt);
                });
            })
            .catch(err => console.error("Error loading staff:", err));

        // Không reset bảng sản phẩm nữa (vì sản phẩm không còn phụ thuộc kho)
    });

    // context path an toàn trong JSP
    const CTX = '<c:out value="${pageContext.request.contextPath}" />';

    async function openAddProductPopup() {
        const kw = (document.querySelector('#searchAll') && document.querySelector('#searchAll').value) || '';
        const popup = document.querySelector('#addProductPopup');
        const body  = document.querySelector('#popupProductsBody');
        body.innerHTML = '<tr><td colspan="4">Loading...</td></tr>';
        popup.style.display = 'block';

        try {
            const res  = await fetch(CTX + '/getAllProducts?q=' + encodeURIComponent(kw));
            if (!res.ok) throw new Error('HTTP ' + res.status);
            const data = await res.json();

            if (!Array.isArray(data) || data.length === 0) {
                body.innerHTML = '<tr><td colspan="4">No products found.</td></tr>';
                return;
            }

            body.innerHTML = '';

            data.forEach(p => {
                const tr = document.createElement('tr');

                // Tạo options với warehouse + aisle
                const options = (p.warehouses && p.warehouses.length)
                    ? p.warehouses.map(w => {
                        const aisleText = w.aisleId && w.aisleName
                            ? ' - Aisle: ' + w.aisleName
                            : (w.aisleId ? ' - Aisle: ' + w.aisleId : '');
                        return '<option value="'+ w.warehouseId +'" data-qty="'+ w.quantity +'" data-aisleid="'+ (w.aisleId || '') +'" data-aislename="'+ (w.aisleName || '') +'">' +
                            w.warehouseName + aisleText + ' (tồn: ' + w.quantity + ')' +
                            '</option>';
                    }).join('')
                    : '<option value="">(No stock in any warehouse)</option>';

                tr.innerHTML =
                    '<td>' + p.productName + ' <small style="color:#888">(' + p.productId + ')</small></td>' +
                    '<td>' +
                    '<select class="sel-warehouse" style="width:100%;">' + options + '</select>' +
                    '</td>' +
                    '<td><input class="inp-qty" type="number" min="1" value="1" style="width:90px"></td>' +
                    '<td><button type="button" class="btn-add" data-pid="' + p.productId + '" data-pname="' + p.productName + '">Add</button></td>';

                body.appendChild(tr);
            });

            // Xử lý click Add
            body.onclick = (e) => {
                if (!e.target.classList.contains('btn-add')) return;
                const tr   = e.target.closest('tr');
                const pid  = e.target.dataset.pid;
                const pname= e.target.dataset.pname;
                const sel  = tr.querySelector('.sel-warehouse');
                const wid  = sel ? sel.value : null;
                const selectedOption = sel && sel.selectedOptions[0];
                const wtxt = selectedOption ? selectedOption.textContent : '';
                const qty  = parseInt(tr.querySelector('.inp-qty').value || '1', 10);

                // Lấy aisleId và aisleName từ data attribute
                const aisleId = selectedOption ? selectedOption.dataset.aisleid : null;
                const aisleName = selectedOption ? selectedOption.dataset.aislename : null;

                const productData = data.find(p => p.productId === pid);

                addToDraftTable({
                    productId: pid,
                    productName: pname,
                    preferredWarehouseId: wid,
                    preferredWarehouseName: wtxt,
                    aisleId: aisleId,
                    aisleName: aisleName,
                    lowestPrice: productData ? productData.lowestPrice : 0,
                    avgPrice: productData ? productData.avgPrice : 0,
                    quantity: qty
                });
            };

            const search = document.querySelector('#searchAll');
            if (search) {
                search.onkeydown = (e) => { if (e.key === 'Enter') openAddProductPopup(); };
            }

        } catch (err) {
            console.error('getAllProducts failed:', err);
            body.innerHTML = '<tr><td colspan="4" style="color:#c00;">Load products failed: ' + err.message + '</td></tr>';
        }
    }

    function closeAddProductPopup(){ document.querySelector('#addProductPopup').style.display='none'; }

    // ---------- Thêm dòng vào bảng + serialize items[] ----------
    function addToDraftTable(item) {
        const tbody = document.getElementById('productTableBody');
        const tr = tbody.insertRow();
        rowCount++;

        const aisleDisplay = item.aisleName || item.aisleId || '(No aisle)';

        tr.innerHTML =
            '<td>' + rowCount + '</td>' +
            '<td>' + item.productId + '</td>' +
            '<td>' + item.productName + '</td>' +
            '<td>' + aisleDisplay + '</td>' +
            '<td><input type="number" value="' + (item.lowestPrice || 0) + '" readonly style="width:90%;padding:5px;"></td>' +
            '<td><input type="number" value="' + (item.avgPrice || 0) + '" readonly style="width:95%;padding:5px;"></td>' +
            '<td>' + item.quantity + '</td>' +
            '<td><input type="text" class="row-note" style="width:95%;padding:5px;" placeholder="optional note"></td>' +
            '<td class="product-row-actions"><button type="button" onclick="removeProductRow(this)">Delete</button></td>';

        const hidden = document.createElement('input');
        hidden.type = 'hidden';
        hidden.name = 'items[]';
        hidden.value = encodeURIComponent(JSON.stringify(item));
        tr.appendChild(hidden);

        closeAddProductPopup();
    }


    window.removeProductRow = function(button) {
        button.closest("tr").remove();
        const rows = document.querySelectorAll('#productTableBody tr');
        rows.forEach((row, idx) => row.cells[0].textContent = idx + 1);
        rowCount = rows.length;
    };

    // Set default date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('orderDate').value = today;
    document.getElementById('expectedShipDate').value = today;
</script>


</body>
</html>