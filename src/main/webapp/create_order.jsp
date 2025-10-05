<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Outbound order</title>
    <link rel="stylesheet" href="css\form_common_styles.css"> 
</head>
<body>
    <div class="container-form">
        <header class="form-header">
            <h1>Create Outbound order</h1>
            <p class="create-date">Create date: 24/9/2025</p>
        </header>

        <section class="info-grid-form">
            <div class="info-item-form">
                <span class="label">Reference No:</span>
                <span class="value">xxx (Chưa có)</span>
            </div>
            <div class="info-item-form">
                <span class="label">Create By:</span>
                <span class="value">NHV</span>
            </div>

            <div class="info-item-form editable-field">
                <span class="label" for="location">Location:</span>
                <input type="text" id="location" value="...">
            </div>
            <div class="info-item-form editable-field">
                <span class="label" for="order-date">Order Date:</span>
                <input type="text" id="order-date" value="...">
            </div>

            <div class="info-item-form editable-field">
                <span class="label" for="responsible-staff">Responsible staff:</span>
                <select id="responsible-staff">
                    <option selected>Ng van A</option>
                    <option>User B</option>
                </select>
            </div>
            <div class="info-item-form editable-field">
                <span class="label" for="ship-date">Expected ship date:</span>
                <input type="text" id="ship-date" value="...">
            </div>

            <div class="info-item-form">
                <span class="label">Status:</span>
                <span class="value status-pending">Pending</span>
            </div>
            <div class="info-item-form empty"></div>
        </section>

        <section class="product-section-form">
            <div class="product-header-form">
                <h2>List Add Product</h2>
                <button class="add-more-btn">Add Product</button>
            </div>

            <div class="product-list-table-form">
                <table>
                    <thead>
                        <tr>
                            <th class="col-stt">Stt</th>
                            <th class="col-id">ID</th>
                            <th>Product name</th>
                            <th class="col-qty">Quantity</th>
                            <th>Note</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="col-stt">1</td>
                            <td class="col-id">X01</td>
                            <td>AOG 244hz</td>
                            <td class="col-qty">2</td>
                            <td></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>
        
        <footer class="action-footer-form">
            <button class="back-btn">Cancel</button>
            <button class="update-btn">Create Outbound Order</button>
        </footer>
    </div>
</body>
</html>