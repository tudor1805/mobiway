<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Traffic Collector</title>
        <link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />

        <!--  jquery core -->
        <script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>

        <!-- Custom jquery scripts -->
        <script src="js/jquery/custom_jquery.js" type="text/javascript"></script>

        <!-- MUST BE THE LAST SCRIPT IN <HEAD></HEAD> png fix -->
        <script src="js/jquery/jquery.pngFix.pack.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $(document).pngFix( );
            });
        </script>
    </head>
    <body id="login-bg">

        <!-- Start: login-holder -->
        <div id="login-holder">

            <!-- start logo -->
            <div id="logo-login" style="height: 100px;">
                <a href="login">
                    <img src="images/shared/traffic.png" height="100" alt="" />
                </a>
            </div>
            <!-- end logo -->

            <div class="clear"></div>

            <!--  start loginbox -->
            <div id="loginbox">

                <!--  start login-inner -->
                <div id="login-inner" style="width: 80%;">

                    <!-- display error message -->
                    <c:if test="${login_error!=null}">
                        <div id="message-red">
                            <table border="0" width="100%" cellpadding="0" cellspacing="0">
                                <tbody>
                                    <tr>
                                        <td class="red-left">Login Error. Please try again.</td>
                                        <td class="red-right">
                                            <a class="close-red">
                                                <img src="images/table/icon_close_red.gif" alt="">
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </c:if>

                    <form action="login" method="post">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <th>Username</th>
                                <td>
                                    <input type="text" name="username" class="login-inp" />
                                </td>
                            </tr>
                            <tr>
                                <th>Password</th>
                                <td>
                                    <input type="password"  onfocus="this.value = ''"  name="password" class="login-inp" />
                                </td>
                            </tr>
                            <tr>
                                <th></th>
                                <td>
                                    <input type="submit" class="submit-login"/>
                                </td>
                            </tr>
                        </table>
                    </form>

                </div>
            </div>
            <!--  end loginbox -->

        </div>
        <!-- End: login-holder -->
    </body>
</html>