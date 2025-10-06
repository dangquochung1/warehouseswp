
<!DOCTYPE html>
<html lang="vi">
<%@include file="SideBar.jsp" %>

<body>

<div class="page-wrapper">
<div class="content">
 <div class="page-header">
            <h1 class="page-title" style="text-align:left; font-size:32px; font-weight:bold; margin-bottom:20px;">
                Outbound Order Detail
            </h1>
            </div>
<div class="row">
<div class="col-lg-6col-sm-12 col-12">
        <!-- Grid thông tin -->
        <section class="info-grid-form" style="display:grid; grid-template-columns:1fr 1fr; gap:15px; margin-bottom:20px;">
            <div><strong>Reference No:</strong> Xxx01</div>
            <div><strong>Create By:</strong> NHV</div>
            <div><strong>Location:</strong> Store A01</div>
            <div><strong>Order Date:</strong> 01/03/2025</div>
            <div><strong>Responsible staff:</strong> Ng Van A</div>
            <div><strong>Expected ship date:</strong> 05/03/2025</div>
            <div><strong>Status:</strong> <span style="color:orange;">In Progress</span></div>
        </section>

        <!-- Bảng danh sách sản phẩm -->
        <section class="product-section-form">
            <h3 style="margin-bottom:10px;">List Product</h3>
            <div class="product-list-table-form">
                <table style="width:100%; border-collapse:collapse;">
                    <thead>
                        <tr style="background:#2f3e47; color:#fff;">
                            <th style="padding:8px; border:1px solid #ccc;">Stt</th>
                            <th style="padding:8px; border:1px solid #ccc;">ID</th>
                            <th style="padding:8px; border:1px solid #ccc;">Product name</th>
                            <th style="padding:8px; border:1px solid #ccc;">Quantity</th>
                            <th style="padding:8px; border:1px solid #ccc;">Note</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td style="padding:8px; border:1px solid #ccc;">1</td>
                            <td style="padding:8px; border:1px solid #ccc;">X01</td>
                            <td style="padding:8px; border:1px solid #ccc;">AOG 244hz Monitor</td>
                            <td style="padding:8px; border:1px solid #ccc;">2</td>
                            <td style="padding:8px; border:1px solid #ccc;">Khách hàng gấp</td>
                        </tr>
                        <tr>
                            <td style="padding:8px; border:1px solid #ccc;">2</td>
                            <td style="padding:8px; border:1px solid #ccc;">Y15</td>
                            <td style="padding:8px; border:1px solid #ccc;">Laptop Gaming Pro P15</td>
                            <td style="padding:8px; border:1px solid #ccc;">1</td>
                            <td style="padding:8px; border:1px solid #ccc;">Kiểm tra kỹ seal</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </section>

    </div>


</div>
</div>
</div>
</div>
</div>
</div>


<script src="assets/js/jquery-3.6.0.min.js"></script>

<script src="assets/js/feather.min.js"></script>

<script src="assets/js/jquery.slimscroll.min.js"></script>

<script src="assets/js/jquery.dataTables.min.js"></script>
<script src="assets/js/dataTables.bootstrap4.min.js"></script>

<script src="assets/js/bootstrap.bundle.min.js"></script>

<script src="assets/plugins/apexchart/apexcharts.min.js"></script>
<script src="assets/plugins/apexchart/chart-data.js"></script>

<script src="assets/js/script.js"></script>
</body>
</html>