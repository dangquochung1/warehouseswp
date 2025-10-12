<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Outbound order detail xxx for NHV (Staff)</title>
    <link rel="stylesheet" href="css/form_common_styles.css"> 
</head>
<body>
    <div class="container-form"> 
        <header class="form-header"> 
            <h1>Outbound order detail xxx for NHV (Staff)</h1>
            <p class="create-date">Create date: 24/9/2025</p>
        </header>

        <section class="info-grid-form"> 
            <div class="info-item-form">
                <span class="label">Reference No:</span>
                <span class="value">Xxx01</span>
            </div>
            <div class="info-item-form">
                <span class="label">Create By:</span>
                <span class="value">NHV</span>
            </div>

            <div class="info-item-form">
                <span class="label">Location:</span>
                <span class="value">Store A01</span>
            </div>
            <div class="info-item-form">
                <span class="label">Order Date:</span>
                <span class="value">x/x/x</span>
            </div>

            <div class="info-item-form">
                <span class="label">Responsible staff:</span>
                <span class="value">Ng Van A</span>
            </div>
            <div class="info-item-form">
                <span class="label">Expected ship date:</span>
                <span class="value">x/x/x</span>
            </div>

            <div class="info-item-form">
                <span class="label">Status:</span>
                <span class="value status-pending-staff">Pending</span>
            </div>
            <div class="info-item-form empty"></div>
        </section>

        <section class="product-section-form"> 
            <h2>List Product</h2> 

            <div class="product-list-table-form"> 
                <table>
                    <thead>
                        <tr>
                            <th class="col-stt">Stt</th>
                            <th class="col-id">ID</th>
                            <th>Product name</th>
                            <th class="col-qty">Quantity</th>
                            <th>Note</th>
                            <th class="col-action">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="col-stt">1</td>
                            <td class="col-id">x01</td>
                            <td>AOG 244hz</td>
                            <td class="col-qty">2</td>
                            <td></td>
                            <td class="col-action">
                                <div class="action-buttons-table">
                                    <button class="action-btn-mini view-btn">View</button>
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>

        <footer class="action-footer-staff">
            <button class="back-btn">Back</button> 
            
            <button class="start-processing-btn">Start Processing</button>
        </footer>
    </div>
</body>
</html>