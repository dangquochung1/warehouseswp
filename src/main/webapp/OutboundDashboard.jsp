<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%@include file="SideBar.jsp" %>

<body>

<div class="page-wrapper">
<div class="content">
 <div class="page-header">
    <h1 class="page-title" style="text-align:left; font-size:32px; font-weight:bold; margin-bottom:20px;">
        Outbound Manager Dashboard
    </h1>
 </div>

<div class="row">
    <div class="col-lg-3 col-sm-6 col-12">
        <div class="dash-widget">
            <div class="dash-widgetcontent">
                <h5><span class="counters">${totalOutboundNumber}</span></h5>
                <h6>Total Outbound Orders</h6>
            </div>
        </div>
    </div>

    <div class="col-lg-3 col-sm-6 col-12">
        <div class="dash-widget dash1">
            <div class="dash-widgetcontent">
                <h5><span class="counters">${totalPendingNumber}</span></h5>
                <h6>Pending Orders</h6>
            </div>
        </div>
    </div>

    <div class="col-lg-3 col-sm-6 col-12">
        <div class="dash-widget dash2">
            <div class="dash-widgetcontent">
                <h5><span class="counters">${totalInProgressNumber}</span></h5>
                <h6>In Progress Orders</h6>
            </div>
        </div>
    </div>

    <div class="col-lg-3 col-sm-6 col-12">
        <div class="dash-widget dash3">
            <div class="dash-widgetcontent">
                <h5><span class="counters">${totalCompletedNumber}</span></h5>
                <h6>Complete Orders</h6>
            </div>
        </div>
    </div>
</div>

<div class="card mb-0">
<div class="card-body">
    <h4 class="card-title">List Of Outbound Orders</h4>
    <div class="table-responsive dataview">
        <table class="table datatable">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Create date</th>
                    <th>Create by</th>
                    <th>Assigned To</th>
                    <th>Status</th>
                    <th class="col-action">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="odlist" items="${Orderlist}">
                    <tr>
                        <td>${odlist.orderId}</td>
                        <td>${odlist.createdAt}</td>
                        <td>${odlist.createdBy}</td>
                        <td>${odlist.assignedTo}</td>
                        <td>${odlist.status}</td>
<td class="col-action">
    <div class="action-buttons-table" style="display:flex; gap:10px;">
        <c:choose>
            <c:when test="${odlist.status == 'done'}">
                <a href="${pageContext.request.contextPath}/outboundorderdetail?odid=${odlist.orderId}" class="view-btn">View</a>
            </c:when>

            <c:when test="${odlist.status == 'pending'}">
                <a href="${pageContext.request.contextPath}/outboundorderdetail?odid=${odlist.orderId}" class="view-btn">View</a>
                <a href="#" class="update-btn">Update</a>
                <a href="#" class="cancel-btn">Cancel</a>
            </c:when>

            <c:when test="${odlist.status == 'processing'}">
                <a href="${pageContext.request.contextPath}/outboundorderdetail?odid=${odlist.orderId}" class="view-btn">View</a>
                <a href="#" class="update-btn">Update</a>
            </c:when>

            <c:otherwise>
                <a href="${pageContext.request.contextPath}/outboundorderdetail?odid=${odlist.orderId}" class="view-btn">View</a>
            </c:otherwise>
        </c:choose>
    </div>
</td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
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
