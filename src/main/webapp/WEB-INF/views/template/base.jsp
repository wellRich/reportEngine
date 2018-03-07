<%@ taglib prefix="ex" uri="/WEB-INF/tld/tpl.tld"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta content="width=device-width, initial-scale=1" name="viewport">
    <meta http-equiv="X-UA-Compatible" content="IE=9; IE=edge,chrome=1">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/public/assets/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/public/assets/vendor/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/vendor/AdminLTE/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/assets/vendor/AdminLTE/css/skins/_all-skins.min.css">

    <script type="text/javascript">
        var Globals = {
            ctx: '${pageContext.request.contextPath}',
            assetRoot: '/public/assets/vendor/',
            locale: '${locale.toString().replace("_","-")}'
        };
        Globals.parse = function (str) {
            return JSON.parse(str);
        };
        window.console = window.console || (function () {
            var c = {};
            c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
                = c.clear = c.exception = c.trace = c.assert = function () {
            };
            return c;
        })();
    </script>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/public/assets/vendor/bootstrap/css/bootstrap.min.css">
</head>
<body class="skin-black-light" style="height: auto; min-height: 100%;">
<div class="wrapper" style="height: auto; min-height: 100%;">

    <!-- Main Header -->
    <header class="main-header">
        <!-- Logo -->
        <a href="#" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>W R</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Well Rich</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li><a href="#">郭先生AdminLTE.IO</a></li>
                    <li><a href="#">Premium Templates</a></li>
                </ul>
            </div>
        </nav>
    </header>
        <!-- Left side column. contains the sidebar -->

    <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

           <%-- <!-- search form -->
            <form method="get" class="sidebar-form" id="sidebar-form">
                <div class="input-group">
                    <input type="text" name="q" class="form-control" placeholder="Search..." id="search-input">
                    <span class="input-group-btn">
                    <button type="submit" name="search" id="search-btn" class="btn btn-flat">
                        <i class="fa fa-search"></i>
                    </button>
                </span>
                </div>
            </form>--%>
            <!-- /.search form -->

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu tree" data-widget="tree">
                <li class="header">TABLE OF CONTENTS</li>
                <li>
                    <a href="#"><i class="fa fa-microchip"></i> <span>Installation</span></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-handshake-o"></i>
                        <span>Dependencies &amp; plugins</span></a></li>
                <li class="active">
                    <a href="#"><i class="fa fa-files-o"></i> <span>Layout</span></a></li>
                <li class="treeview ">
                    <a href="#"><i class="fa fa-th"></i> <span>Components</span>
                        <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                    </a>
                    <ul class="treeview-menu">
                        <li>
                            <a href="#"><i class="fa fa-circle-o"></i> Main Header</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-circle-o"></i> Sidebar</a>
                        </li>

                    </ul>
                </li>
                <li class="treeview ">
                    <a href="#"><i class="fa fa-code"></i> <span>JavaScript</span>
                        <span class="pull-right-container">
                        <i class="fa fa-angle-left pull-right"></i>
                    </span>
                    </a>
                    <ul class="treeview-menu">
                        <li>
                            <a href="#"><i class="fa fa-circle-o"></i> Layout</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-circle-o"></i> Push Menu</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#"><i class="fa fa-chrome"></i><span>Browser Support</span></a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-hand-o-up"></i><span>Upgrade Guide</span></a>
                </li>

                <li class="bg-green"><a href="#" style="background-color: rgb(0, 166, 90);"><i class="fa fa-star-o" style="color: rgb(255, 255, 255);"></i><span style="color: rgb(255, 255, 255);">Premium Templates</span></a></li></ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <ex:block name="content">

    </ex:block>
</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/public/assets/vendor/requireJS/main.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/assets/vendor/requireJS/require.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/assets/vendor/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/assets/vendor/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/public/assets/vendor/AdminLTE/js/adminlte.min.js"></script>
</html>
