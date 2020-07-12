<%@page import="java.lang.Object, java.util.Objects"%>

<html>
    <body bgcolor="#D3D3D3">
        <table style="border-collapse: collapse;" border="0" width="100%" cellspacing="0" cellpadding="0">
            <tr style="background-color: #D3D3D3;">
                <td>&#160;</td>
            </tr>
                <tr>
                    <td align="center" valign="top" bgcolor="#D3D3D3">
                        <table style="border-collapse: collapse; font-family:Arial; font-size: 14px;
                         border: solid 2px black; border-radius: 2px;"
                         width="500" cellspacing="0" cellpadding="0" bgcolor="#D3D3D3">
                                <tr>
                                    <td align="center" valign="top" bgcolor="#ffffff">
                                        <table style="border-collapse: collapse; "
                                         border="0" width="450"
                                         cellspacing="0" cellpadding="0" align="center">
                                                <tr>
                                                    <td align="center">
                                                        <form action="index.jsp" method="post">
                                                        <input type="submit" value="Back"
                                                        style="margin: 10px 0px 5px 0px"/>
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center"><h3>Please, type your login
                                                     and password to enter GSI.</h3></td>
                                                </tr>
                                                <tr style="background-color: #ffffff;">
                                                    <td>&#160;</td>
                                                </tr>
                                                <tr>
                                                    <td align="right">
                                                        <form action="loginToOffice" method="post">
                                                            Login: <input required name="login" type="text"
                                                            pattern="^[_A-Za-z0-9]{4,20}$"
                                                            oninvalid="this.setCustomValidity
                                                            ('Must be 4-20 characters or digits')"
                                                            oninput="this.setCustomValidity('')"
                                                            style="width:300px; margin: 2px 30px;"/> <br />
                                                            <br />
                                                            Password: <input required name="password" type="text"
                                                            pattern="^[_A-Za-z0-9]{4,20}$"
                                                            oninvalid="this.setCustomValidity
                                                            ('Must be 4-20 characters or digits')"
                                                            oninput="this.setCustomValidity('')"
                                                             style="width:300px; margin: 2px 30px;"/> <br />
                                                            <br />
                                                            <input type="submit" value="Submit"
                                                             style="margin: 5px 190px"/>
                                                        </form>
                                                    </td>
                                                </tr>
<tr><td align="center"><h5>
                                                    <%
                                                    String check = request.getParameter("check");
                                                    if (!Objects.equals(check, null)) {
                                                        if (check.equals("1"))
                                                         {
                                                         out.println("Invalid login or password used.");
                                                         out.println("Please, try again.");
                                                         }

                                                         else if (check.equals("2"))
                                                         {
                                                         out.println("Unauthorised access to office.");
                                                         out.println("Please, enter office using your login and password.");
                                                         }
                                                    }
                                                    %>
                                                    </h5></td></tr>
                                                <tr style="background-color: #ffffff;">
                                                    <td>&#160;</td>
                                                </tr>
                                        </table>
                                    </td>
                                </tr>
                        </table>
                    </td>
                </tr>
        </table>
    </body>
</html>