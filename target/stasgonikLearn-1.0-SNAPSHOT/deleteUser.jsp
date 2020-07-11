<%@page import="java.lang.Object, java.util.Objects"%>
<%
    Integer usid = (Integer) session.getAttribute("sID");
    if (Objects.equals(usid, null)) {
         String path = "/office.jsp";
         response.sendRedirect(path);
    }
%>

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
                                                        <form action="office.jsp" method="post">
                                                        <input type="submit" value="Back"
                                                        style="margin: 10px 0px 5px 0px"/>
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                    <h3>Are you sure? <br /></h3>
                                                    <h4>Any sum on accounts that was not extracted will be burned
                                                     along with user accounts!</h4>
                                                    </td>
                                                </tr>
                                                <tr style="background-color: #ffffff;">
                                                    <td>&#160;</td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <form action="deleteUserAction?usid=<%=usid%>" method="post">
                                                            <p><input required type="radio"
                                                             name="check" value="42"/>
                                                             I agree to delete my user account.</p>
                                                            <input type="submit" value="Delete user"
                                                             style="margin: 10px 0px"/>
                                                        </form>
                                                    </td>
                                                </tr>
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