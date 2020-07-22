<html>
<body bgcolor="#D3D3D3">
<table style="border-collapse: collapse;" border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr style="background-color: #D3D3D3;">
        <td>&#160;</td>
    </tr>
    <tr>
        <td align="center" valign="top" bgcolor="#D3D3D3">
            <table table style="border-collapse: collapse; font-family:Arial; font-size: 14px;
                         border: solid 2px black; border-radius: 2px;"
                   width="550" cellspacing="0" cellpadding="0" bgcolor="#D3D3D3">
                <tr>
                    <td align="center" valign="top" bgcolor="#ffffff">
                        <table style="border-collapse: collapse; "
                               border="0" width="500"
                               cellspacing="0" cellpadding="0" align="center">
                            <tr style="background-color: #ffffff;">
                                <td>&#160;</td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <h2>Welcome to GSI bank</h2>
                                    <h3>Proceed to our login page or use register form to create new account.</h3>
                                </td>
                            </tr>
                            <tr style="background-color: #ffffff; ">
                                <td>&#160;</td>
                            </tr>
                            <tr>
                                <td>
                                    <table style="border-collapse: collapse; "
                                           border="0" width="230"
                                           cellspacing="0" cellpadding="0" align="left">
                                        <tr>
                                            <td align="center" width="230px">
                                                <form action="login.jsp" method="post">
                                                    <input type="submit" value="Login" style="margin: 10px 20px 5px 20px"/>
                                                </form>
                                            </td>
                                        </tr>
                                    </table>
                                    <table style="border-collapse: collapse; "
                                           border="0" width="230"
                                           cellspacing="0" cellpadding="0" align="right">
                                        <tr>
                                            <td align="center" width="230px">
                                                <form action="registerUser.jsp" method="post">
                                                    <input type="submit" value="Register" style="margin: 10px 20px 0px 20px"/>
                                                </form>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td align="center>
                                <form action="createDatabaseAction" method="post">
                                <input type="submit" value="Create Database" style="margin: 10px 20px 0px 20px"/>
                                </form>
                            </td></tr>
                            <tr>
                                <td align="center">
                                    <h5>
                                        <%
                                        String reg = request.getParameter("reg");
                                        if (reg != null)
                                            {
                                            out.println("You are successfully registered to GSI bank.");
                                            out.println("Please, use your login to enter our bank.");
                                            }
                                        String data = request.getParameter("data");
                                        if (data != null)
                                            {
                                            out.println("Database ready for work!")
                                            }
                                         %>
                                    </h5>
                                </td>
                            </tr>
                            <tr style="background-color: #ffffff; ">
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