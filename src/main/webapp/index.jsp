<%--<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>JSP + Controller</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<h1>Hello from JSP!</h1>--%>
<%--<a href="WarehouseAreaController">Go to location</a><br>--%>
<%--<a href="LotController">Lot List</a><br>--%>

<%--</body>--%>
<%--</html>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
    <title>Dashboard | Dreams POS</title>

    <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.jpg">
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet" href="assets/css/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/fontawesome.min.css">
    <link rel="stylesheet" href="assets/plugins/fontawesome/css/all.min.css">
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>
<div id="global-loader">
    <div class="whirly-loader"></div>
</div>

<div class="main-wrapper">

    <!-- HEADER -->
    <div class="header">
        <div class="header-left active">
            <a href="index.html" class="logo">
                <img src="assets/img/logo.png" alt="">
            </a>
            <a href="index.html" class="logo-small">
                <img src="assets/img/logo-small.png" alt="">
            </a>
            <a id="toggle_btn" href="javascript:void(0);"></a>
        </div>

        <a id="mobile_btn" class="mobile_btn" href="#sidebar">
            <span class="bar-icon"><span></span><span></span><span></span></span>
        </a>

        <ul class="nav user-menu">
            <!-- Search -->
            <li class="nav-item">
                <div class="top-nav-search">
                    <a href="javascript:void(0);" class="responsive-search"><i class="fa fa-search"></i></a>
                    <form action="#">
                        <div class="searchinputs">
                            <input type="text" placeholder="Search Here ...">
                            <div class="search-addon">
                                <span><img src="assets/img/icons/closes.svg" alt="img"></span>
                            </div>
                        </div>
                        <a class="btn" id="searchdiv"><img src="assets/img/icons/search.svg" alt="img"></a>
                    </form>
                </div>
            </li>

            <!-- Flag -->
            <li class="nav-item dropdown has-arrow flag-nav">
                <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button">
                    <img src="assets/img/flags/us1.png" alt="" height="20">
                </a>
                <div class="dropdown-menu dropdown-menu-right">
                    <a href="#" class="dropdown-item"><img src="assets/img/flags/us.png" height="16"> English</a>
                    <a href="#" class="dropdown-item"><img src="assets/img/flags/fr.png" height="16"> French</a>
                    <a href="#" class="dropdown-item"><img src="assets/img/flags/es.png" height="16"> Spanish</a>
                    <a href="#" class="dropdown-item"><img src="assets/img/flags/de.png" height="16"> German</a>
                </div>
            </li>

            <!-- Notifications -->
            <li class="nav-item dropdown">
                <a href="#" class="dropdown-toggle nav-link" data-bs-toggle="dropdown">
                    <img src="assets/img/icons/notification-bing.svg" alt="img">
                    <span class="badge rounded-pill">4</span>
                </a>
                <div class="dropdown-menu notifications">
                    <div class="topnav-dropdown-header">
                        <span class="notification-title">Notifications</span>
                        <a href="javascript:void(0)" class="clear-noti">Clear All</a>
                    </div>
                    <div class="noti-content">
                        <ul class="notification-list">
                            <li>No new notifications</li>
                        </ul>
                    </div>
                </div>
            </li>

            <!-- User -->
            <li class="nav-item dropdown has-arrow main-drop">
                <a href="#" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                        <span class="user-img"><img src="assets/img/profiles/avator1.jpg" alt=""><span
                                class="status online"></span></span>
                </a>
                <div class="dropdown-menu menu-drop-user">
                    <div class="profilename">
                        <div class="profileset">
                                <span class="user-img"><img src="assets/img/profiles/avator1.jpg" alt=""><span
                                        class="status online"></span></span>
                            <div class="profilesets">
                                <h6>John Doe</h6>
                                <h5>Admin</h5>
                            </div>
                        </div>
                        <hr class="m-0">
                        <a class="dropdown-item" href="profile.html"><i class="me-2" data-feather="user"></i>My
                            Profile</a>
                        <a class="dropdown-item" href="generalsettings.html"><i class="me-2"
                                                                                data-feather="settings"></i>Settings</a>
                        <hr class="m-0">
                        <a class="dropdown-item logout pb-0" href="signin.html"><img
                                src="assets/img/icons/log-out.svg" class="me-2" alt="img">Logout</a>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <!-- END HEADER -->

    <!-- SIDEBAR -->
    <div class="sidebar" id="sidebar">
        <div class="sidebar-inner slimscroll">
            <div id="sidebar-menu" class="sidebar-menu">
                <ul>
                    <li class="active">
                        <a href="index.html"><img src="assets/img/icons/dashboard.svg"
                                                  alt="img"><span>Dashboard</span></a>
                    </li>

                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/product.svg" alt="img"><span>
                                    Inbound </span><span class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="productlist.html">Inbound</a></li>
                            <li><a href="addproduct.html">Inbound</a></li>
                            <li><a href="categorylist.html">Inbound</a></li>
                            <li><a href="addcategory.html">Inbound</a></li>
                        </ul>
                    </li>

                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/sales1.svg" alt="img"><span> Location
                                </span><span class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="WarehouseAreaController">View Location</a></li>
                            <li><a href="pos.html">ABC</a></li>
                            <li><a href="pos.html">ABC</a></li>
                            <li><a href="salesreturnlists.html">ABC</a></li>
                        </ul>
                    </li>

                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/purchase1.svg" alt="img"><span>
                                    Lot </span><span class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="LotController">View</a></li>
                            <li><a href="addpurchase.html">ABC</a></li>
                            <li><a href="importpurchase.html">ABC</a></li>
                        </ul>
                    </li>

                    <li class="submenu">
                        <a href="javascript:void(0);"><img src="assets/img/icons/expense1.svg" alt="img"><span>
                                    Outbound </span><span class="menu-arrow"></span></a>
                        <ul>
                            <li><a href="expenselist.html">Expense List</a></li>
                            <li><a href="createexpense.html">Add Expense</a></li>
                            <li><a href="expensecategory.html">Expense Category</a></li>
                        </ul>
                    </li>

                    <li><a href="storelist.html"><img src="assets/img/icons/places.svg" alt="img"><span>Store
                                    Management</span></a></li>
                    <li><a href="settings.html"><img src="assets/img/icons/settings.svg"
                                                     alt="img"><span>Settings</span></a></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- END SIDEBAR -->

    <!-- PAGE CONTENT -->
    <div class="page-wrapper">
        <div class="content d-flex justify-content-center align-items-center" style="height:80vh;">
            <h1 style="font-size:48px; color:#1e293b; font-weight:700;">Welcome</h1>
        </div>
    </div>
    <!-- END PAGE CONTENT -->

</div>

<!-- Scripts -->
<script src="assets/js/jquery-3.6.0.min.js"></script>
<script src="assets/js/feather.min.js"></script>
<script src="assets/js/jquery.slimscroll.min.js"></script>
<script src="assets/js/bootstrap.bundle.min.js"></script>
<script src="assets/js/script.js"></script>
</body>

</html>