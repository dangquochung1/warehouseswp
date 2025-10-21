<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <c:forEach var="obdetail" items="${orderdetail}">
        <section class="info-grid-form" style="display:grid; grid-template-columns:1fr 1fr; gap:15px; margin-bottom:20px;">
            <div><strong>Outbound Order ID:</strong> ${obdetail.orderId}</div>
            <div><strong>Create By:</strong> ${obdetail.createdBy}</div>
            <div><strong>Location:</strong> ...</div>
            <div><strong>Order Date:</strong> ${obdetail.createdAt}</div>
            <div><strong>Responsible staff:</strong> ${obdetail.assignedTo}</div>
            <div><strong>Expected ship date:</strong> ${obdetail.scheduledDate}</div>
            <div><strong>Status:</strong> <span style="color:orange;">${obdetail.status}</span></div>
        </section>
        </c:forEach>
        <!-- Bảng danh sách sản phẩm -->
        <section class="product-section-form">
            <h3 style="margin-bottom:10px;">List Product</h3>
            <div class="product-list-table-form">
                <table style="width:100%; border-collapse:collapse;">
                    <thead>
                        <tr style="background:#2f3e47; color:#fff;">
                            <th style="padding:8px; border:1px solid #ccc;">Stt</th>
                            <th style="padding:8px; border:1px solid #ccc;">Product name</th>
                            <th style="padding:8px; border:1px solid #ccc;">Quantity</th>
                            <th style="padding:8px; border:1px solid #ccc;">Note</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="pod" items="${productOrderdetail}">
                        <tr>
                            <td style="padding:8px; border:1px solid #ccc;">...</td>
                            <td style="padding:8px; border:1px solid #ccc;">${pod.productNamebyId}</td>
                            <td style="padding:8px; border:1px solid #ccc;">${pod.quantity_actual}</td>
                            <td style="padding:8px; border:1px solid #ccc;">${pod.note}</td>
                        </tr>
                    </c:forEach>
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