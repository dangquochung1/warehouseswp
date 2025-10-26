<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0">
  <title>Dreams POS | Dashboard</title>

  <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.jpg">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dataTables.bootstrap4.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/fontawesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/plugins/fontawesome/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>
<div id="global-loader"><div class="whirly-loader"></div></div>

<div class="main-wrapper">
  <div class="header">
    <div class="header-left active">
      <a href="${pageContext.request.contextPath}/index.jsp" class="logo">
        <img src="${pageContext.request.contextPath}/assets/img/logo.png" alt="">
      </a>
      <a href="${pageContext.request.contextPath}/index.jsp" class="logo-small">
        <img src="${pageContext.request.contextPath}/assets/img/logo-small.png" alt="">
      </a>
      <a id="toggle_btn" href="javascript:void(0);"></a>
    </div>

    <a id="mobile_btn" class="mobile_btn" href="#sidebar">
      <span class="bar-icon"><span></span><span></span><span></span></span>
    </a>

    <ul class="nav user-menu">
      <li class="nav-item dropdown has-arrow main-drop">
        <a href="#" class="dropdown-toggle nav-link userset" data-bs-toggle="dropdown">
                  <span class="user-img"><img src="${pageContext.request.contextPath}/assets/img/profiles/avator1.jpg" alt="">
                  <span class="status online"></span></span>
        </a>
        <div class="dropdown-menu menu-drop-user">
          <a class="dropdown-item logout pb-0" href="signin.html">
            <img src="${pageContext.request.contextPath}/assets/img/icons/log-out.svg" class="me-2" alt="img">Logout
          </a>
        </div>
      </li>
    </ul>
  </div>
</div>
</body>
</html>
