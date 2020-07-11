<%@page import="java.lang.Object, java.util.Objects"%>
<%
    Integer usid = (Integer) session.getAttribute("sID");
    String change = request.getParameter("change");
    if (Objects.equals(usid, null) || (!change.equals("first") && !change.equals("last") &&
     !change.equals("second") && !change.equals("age"))) {
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
                                                    <td align="center"><h3>Please, set changes for your user account.</h3></td>
                                                </tr>
                                                <tr style="background-color: #ffffff;">
                                                    <td>&#160;</td>
                                                </tr>
                                                <tr>
                                                    <td align="right">
                                                    <% if (change.equals("first"))
                                                    {
out.println("<form action='changeUserAction?change=first' method='post'>");
out.println("First name: <input required name='first_name' type='text'  pattern='^[_A-Za-z-]{1,30}$'" +
 " oninvalid='this.setCustomValidity('Must be 1-30 characters')' oninput='this.setCustomValidity('')'" +
 " style='width:260px; margin: 2px 30px;'/>");
                                                    }%>
                                                    <% if (change.equals("second"))
                                                    {
out.println("<form action='changeUserAction?change=second' method='post'>");
out.println("Second name: <input required name='second_name' type='text'  pattern='^[_A-Za-z-]{1,30}$'" +
" oninvalid='this.setCustomValidity('Must be 1-30 characters')' oninput='this.setCustomValidity('')'" +
" style='width:260px; margin: 2px 30px;'/>");
                                                     }%>
                                                     <% if (change.equals("last"))
                                                     {
out.println("<form action='changeUserAction?change=last' method='post'>");
out.println("Last name: <input required name='last_name' type='text'  pattern='^[_A-Za-z-]{1,30}$'" +
" oninvalid='this.setCustomValidity('Must be 1-30 characters')' oninput='this.setCustomValidity('')'" +
" style='width:260px; margin: 2px 30px;'/>");
                                                     }%>
                                                     <% if (change.equals("age"))
                                                     {
 out.println("<form action='changeUserAction?change=age' method='post'>");
out.println("Age: <input required name='age' type='text'  pattern='^[_0-9]{1,3}$'" +
" oninvalid='this.setCustomValidity('Use only digits')' oninput='this.setCustomValidity('')'" +
" style='width:260px; margin: 2px 30px;'/>");
                                                     }%>

                                                            <br />
                                                            <br />
                                                            <input type="submit" value="Change"
                                                             style="margin: 5px 190px"/>
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