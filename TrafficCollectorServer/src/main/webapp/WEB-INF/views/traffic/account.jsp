<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Traffic Collector</title>
        <link rel="stylesheet" href="css/screen.css" type="text/css" media="screen" title="default" />
        <!--[if IE]>
        <link rel="stylesheet" media="all" type="text/css" href="css/pro_dropline_ie.css" />
        <![endif]-->

        <!--  jquery core -->
        <script src="js/jquery/jquery-1.4.1.min.js" type="text/javascript"></script>

        <!--  checkbox styling script -->
        <script src="js/jquery/ui.core.js" type="text/javascript"></script>
        <script src="js/jquery/ui.checkbox.js" type="text/javascript"></script>
        <script src="js/jquery/jquery.bind.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(function() {
                $('input').checkBox();
                $('#toggle-all').click(function() {
                    $('#toggle-all').toggleClass('toggle-checked');
                    $('#mainform input[type=checkbox]').checkBox('toggle');
                    return false;
                });
            });
        </script>

        <![if !IE 7]>

        <!--  styled select box script version 1 -->
        <script src="js/jquery/jquery.selectbox-0.5.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.styledselect').selectbox({inputClass: "selectbox_styled"});
            });
        </script>

        <![endif]>

        <!--  styled select box script version 2 -->
        <script src="js/jquery/jquery.selectbox-0.5_style_2.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.styledselect_form_1').selectbox({inputClass: "styledselect_form_1"});
                $('.styledselect_form_2').selectbox({inputClass: "styledselect_form_2"});
            });
        </script>

        <!--  styled select box script version 3 -->
        <script src="js/jquery/jquery.selectbox-0.5_style_2.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('.styledselect_pages').selectbox({inputClass: "styledselect_pages"});
            });
        </script>

        <!--  styled file upload script -->
        <script src="js/jquery/jquery.filestyle.js" type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8">
            $(function() {
                $("input.file_1").filestyle({
                    image: "images/forms/upload_file.gif",
                    imageheight: 29,
                    imagewidth: 78,
                    width: 300
                });
            });
        </script>

        <!-- Custom jquery scripts -->
        <script src="js/jquery/custom_jquery.js" type="text/javascript"></script>

        <!-- Tooltips -->
        <script src="js/jquery/jquery.tooltip.js" type="text/javascript"></script>
        <script src="js/jquery/jquery.dimensions.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(function() {
                $('a.info-tooltip ').tooltip({
                    track: true,
                    delay: 0,
                    fixPNG: true,
                    showURL: false,
                    showBody: " - ",
                    top: -35,
                    left: 5
                });
            });
        </script>

        <!--  date picker script -->
        <link rel="stylesheet" href="css/datePicker.css" type="text/css" />
        <script src="js/jquery/date.js" type="text/javascript"></script>
        <script src="js/jquery/jquery.datePicker.js" type="text/javascript"></script>
        <script type="text/javascript" charset="utf-8">
            $(function()
            {

                // initialise the "Select date" link
                $('#date-pick')
                        .datePicker(
                                // associate the link with a date picker
                                        {
                                            createButton: false,
                                            startDate: '01/01/2005',
                                            endDate: '31/12/2020'
                                        }
                                ).bind(
                                        // when the link is clicked display the date picker
                                        'click',
                                        function()
                                        {
                                            updateSelects($(this).dpGetSelected()[0]);
                                            $(this).dpDisplay();
                                            return false;
                                        }
                                ).bind(
                                        // when a date is selected update the SELECTs
                                        'dateSelected',
                                        function(e, selectedDate, $td, state)
                                        {
                                            updateSelects(selectedDate);
                                        }
                                ).bind(
                                        'dpClosed',
                                        function(e, selected)
                                        {
                                            updateSelects(selected[0]);
                                        }
                                );

                                var updateSelects = function(selectedDate)
                                {
                                    var selectedDate = new Date(selectedDate);
                                    $('#d option[value=' + selectedDate.getDate() + ']').attr('selected', 'selected');
                                    $('#m option[value=' + (selectedDate.getMonth() + 1) + ']').attr('selected', 'selected');
                                    $('#y option[value=' + (selectedDate.getFullYear()) + ']').attr('selected', 'selected');
                                };

                                // listen for when the selects are changed and update the picker
                                $('#d, #m, #y')
                                        .bind(
                                                'change',
                                                function()
                                                {
                                                    var d = new Date(
                                                            $('#y').val(),
                                                            $('#m').val() - 1,
                                                            $('#d').val()
                                                            );
                                                    $('#date-pick').dpSetSelected(d.asString());
                                                }
                                        );

                                // default the position of the selects to today
                                var today = new Date();
                                updateSelects(today.getTime());

                                // and update the datePicker to reflect it...
                                $('#d').trigger('change');
                            });
        </script>

        <!-- MUST BE THE LAST SCRIPT IN <HEAD></HEAD></HEAD> png fix -->
        <script src="js/jquery/jquery.pngFix.pack.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $(document).pngFix( );
            });

            function validateEmail(email) {
                var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
                return re.test(email);
            }

            function checkForm() {
                var username = $("#username").val();
                var password = $("#password").val();
                var password_re = $("#password_re").val();

                var errStr = "";
                if (username === "")
                {
                    usernameErrStr = "Please provide username!";
                    $("#username_error").html("<div class=\"error-left\"></div><div class=\"error-inner\">" + usernameErrStr + "</div>");
                    return;
                }
                else if (!validateEmail(username))
                {
                    usernameErrStr = "Username must be valid email!";
                    $("#username_error").html("<div class=\"error-left\"></div><div class=\"error-inner\">" + usernameErrStr + "</div>");
                    return;
                }
                else if (username !== "")
                    $("#username_error").html("");
                else if (password !== "")
                {
                    if (password_re === "")
                    {
                        passwordErrStr = "Please re-type password!";
                        $("#password_re_error").html("<div class=\"error-left\"></div><div class=\"error-inner\">" + passwordErrStr + "</div>");
                        return;
                    }
                    else if (password_re !== password)
                    {
                        passwordErrStr = "Passwords do not match!";
                        $("#password_re_error").html("<div class=\"error-left\"></div><div class=\"error-inner\">" + passwordErrStr + "</div>");
                        return;
                    }
                }
                $("#account").submit();
            }
        </script>
    </head>
    <body>
        <!-- Start: page-top-outer -->
        <div id="page-top-outer" style="height: 105px;">

            <!-- Start: page-top -->
            <div id="page-top">

                <!-- start logo -->
                <div id="logo" style="margin-top: 5px;">
                    <a href=""><img src="images/shared/traffic.png" height="100" alt="" /></a>
                </div>
                <!-- end logo -->

                <!--  start top-search -->
                <div id="top-search">
                </div>
                <!--  end top-search -->
                <div class="clear"></div>

            </div>
            <!-- End: page-top -->

        </div>
        <!-- End: page-top-outer -->

        <div class="clear">&nbsp;</div>

        <!--  start nav-outer-repeat START -->
        <div class="nav-outer-repeat" style="height: 37px;">
            <!--  start nav-outer -->
            <div class="nav-outer">

                <!-- start nav-right -->
                <div id="nav-right">
                    <div class="nav-divider">&nbsp;</div>
                    <a href="logout" id="logout"><img src="images/shared/nav/nav_logout.gif" width="64" height="14" alt="" /></a>
                    <div class="clear">&nbsp;</div>
                </div>
                <!-- end nav-right -->


                <!--  start nav -->
                <div class="nav">
                    <div class="table">

                        <ul class="select">
                            <li><a href="table"><b>History</b><!--[if IE 7]><!--></a><!--<![endif]--></li>
                        </ul>

                        <div class="nav-divider">&nbsp;</div>

                        <ul class="select">
                            <li><a href="account"><b>Account</b><!--[if IE 7]><!--></a><!--<![endif]--></li>
                        </ul>

                        <div class="clear"></div>
                    </div>
                    <div class="clear"></div>
                </div>
                <!--  start nav -->

            </div>
            <div class="clear"></div>
            <!--  start nav-outer -->
        </div>
        <!--  start nav-outer-repeat END -->

        <div class="clear"></div>

        <!-- start content-outer -->
        <div id="content-outer">
            <!-- start content -->
            <div id="content">

                <div id="page-heading"><h1>Account</h1></div>

                <table border="0" width="100%" cellpadding="0" cellspacing="0" id="content-table">
                    <tr>
                        <th rowspan="3" class="sized">
                            <img src="images/shared/side_shadowleft.jpg" width="20" height="300" alt="" />
                        </th>
                        <th class="topleft"></th>
                        <td id="tbl-border-top">&nbsp;</td>
                        <th class="topright"></th>
                        <th rowspan="3" class="sized">
                            <img src="images/shared/side_shadowright.jpg" width="20" height="300" alt="" />
                        </th>
                    </tr>
                    <tr>
                        <td id="tbl-border-left"></td>
                        <td>
                            <!--  start content-table-inner -->
                            <div id="content-table-inner">
                                <table border="0" width="100%" cellpadding="0" cellspacing="0">
                                    <tr valign="top">
                                        <td>

                                            <!-- start id-form -->
                                            <form name="account" id="account" method="post" action="account">
                                                <c:if test="${account_try_update!=null}">

                                                    <c:choose>
                                                        <c:when test="${account_update_error==null}">
                                                            <div id="message-green">
                                                                <table border="0" width="100%" cellpadding="0" cellspacing="0">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="green-left">Account saved.</a></td>
                                                                            <td class="green-right">
                                                                                <a class="close-green">
                                                                                    <img src="images/table/icon_close_green.gif" alt="">
                                                                                </a>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div id="message-red">
                                                                <table border="0" width="100%" cellpadding="0" cellspacing="0">
                                                                    <tbody>
                                                                        <tr>
                                                                            <td class="red-left">Error. Please try again.</td>
                                                                            <td class="red-right">
                                                                                <a class="close-red">
                                                                                    <img src="images/table/icon_close_red.gif" alt="">
                                                                                </a>
                                                                            </td>
                                                                        </tr>
                                                                    </tbody>
                                                                </table>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </c:if>

                                                <table border="0" cellpadding="0" cellspacing="0"  id="id-form">
                                                    <tr>
                                                        <th valign="top">Name:</th>
                                                        <td><input type="text" name="name" id="name" value="${name}" class="inp-form" /></td>
                                                        <td></td>
                                                    </tr>
                                                    <tr>
                                                        <th valign="top">Username:</th>
                                                        <td>
                                                            <input type="text" autocomplete="off" name="username" id="username" value="${username}" class="inp-form" /></td>
                                                        <td>
                                                            <span id="username_error">
                                                                </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th valign="top">New Password:</th>
                                                        <td>
                                                            <input type="password" autocomplete="off" name="password" id="password" class="inp-form" />
                                                        </td>
                                                        <td></td>
                                                    </tr>

                                                    <tr>
                                                        <th valign="top">Re-type Password:</th>
                                                        <td>
                                                            <input type="password" autocomplete="off" name="password_re" id="password_re" class="inp-form" />
                                                        </td>
                                                        <td>
                                                            <span id="password_re_error">
                                                                </div>
                                                        </td>
                                                    </tr>

                                                    <tr>
                                                        <th>&nbsp;</th>
                                                        <td valign="top">
                                                            <input type="button" value="" onclick="checkForm();
                                                                    return false;" class="form-submit" />
                                                            <input type="reset" value="" class="form-reset"  />
                                                        </td>
                                                        <td></td>
                                                    </tr>
                                                </table>
                                            </form>
                                            <!-- end id-form  -->
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="images/shared/blank.gif" width="695" height="1" alt="blank" />
                                        </td>
                                        <td></td>
                                    </tr>
                                </table>

                                <div class="clear"></div>
                            </div>
                            <!--  end content-table-inner  -->
                        </td>
                        <td id="tbl-border-right"></td>
                    </tr>
                    <tr>
                        <th class="sized bottomleft"></th>
                        <td id="tbl-border-bottom">&nbsp;</td>
                        <th class="sized bottomright"></th>
                    </tr>
                </table>

                <div class="clear">&nbsp;</div>

            </div>
            <!--  end content -->
            <div class="clear">&nbsp;</div>
        </div>
        <!--  end content-outer END -->

        <div class="clear">&nbsp;</div>

        <!-- start footer -->
        <div id="footer">
            <!--  start footer-left -->
            <div id="footer-left">
                Traffic Collector &copy; Copyright Politehnica University of Bucharest. <span id="spanYear">2012</span> <a href="http://mobiway.hpc.pub.ro">http://mobiway.hpc.pub.ro</a>. All rights reserved.</div>
            <!--  end footer-left -->
            <div class="clear">&nbsp;</div>
        </div>
        <!-- end footer -->

    </body>
</html>
