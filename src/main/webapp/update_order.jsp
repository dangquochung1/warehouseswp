<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product Modal (Improved)</title>
    <link rel="stylesheet" href="css\add_product_improved_styles.css"> 
</head>
<body>

    <div class="modal-backdrop">
        <div class="modal-content-improved">
            <h2 class="modal-title">Add product to list</h2>

            <div class="input-group">
                <label for="product-id">ID Sản phẩm</label>
                <div class="id-input-wrapper">
                    <input type="text" id="product-id" value="x01" class="id-input" placeholder="Nhập ID...">
                    <button class="dropdown-btn-improved">▼</button>
                </div>
            </div>

            <div class="product-name-display">
                <span class="label-name">Name:</span> 
                <span class="value-name">AOG 244hz</span>
            </div>

            <div class="input-group">
                <label for="quantity-input">Số lượng</label>
                <input type="number" id="quantity-input" value="2" class="quantity-input">
            </div>

            <div class="input-group">
                <label for="note-input">Ghi chú (Tùy chọn)</label>
                <textarea id="note-input" rows="3" class="note-input" placeholder="*blank"></textarea>
            </div>

            <div class="modal-actions-improved">
                <button class="action-btn-improved secondary-btn-improved">Cancel</button>
                <button class="action-btn-improved primary-btn-improved">Add product</button>
            </div>
        </div>
    </div>

</body>
</html>