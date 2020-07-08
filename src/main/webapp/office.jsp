<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.epam.GSI.user"%>
<%@page import="com.epam.GSI.userDB"%>
<%@page import="com.epam.GSI.account"%>
<%@page import="com.epam.GSI.accountDB"%>
<%@page import="com.epam.GSI.login"%>
<%@page import="com.epam.GSI.loginDB"%>
<%@page import="com.epam.GSI.currency"%>
<%@page import="com.epam.GSI.currencyDB"%>
<%@page import="javax.servlet.http.HttpSession"%>


<%
    loginDB loginDB = new loginDB();
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    Integer usid = (Integer) session.getAttribute("sID");
    if (loginDB.checkLoginPassword(login, password)) {
        int id = loginDB.getID(login, password);
        if (usid != id) {
            String path = "/login.jsp?check=1";
            response.sendRedirect(path);
        }
    }
    else {
        String path = "/login.jsp?check=1";
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
            <table table style="border-collapse: collapse; font-family:Arial; font-size: 14px;
                         border: solid 3px black; border-radius: 2px;"
                   width="600" cellspacing="0" cellpadding="0" bgcolor="#D3D3D3">

                <tr>
                    <td align="center" valign="top" bgcolor="#ffffff" style="padding: 5px;">
                        <table style="border-collapse: collapse;"
                               border="0" width="540"
                               cellspacing="0" cellpadding="0" align="center">

                            <tr style="background-color: #ffffff;">
                                <td>&#160;</td>
                            </tr>
                            <tr bgcolor="#ffffff">
                                <td align="center">
                                    <form action="index.jsp" method="post">
                                        <input type="submit" value="EXIT"
                                               style="margin: 10px 0px 15px 0px" />
                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">
                                    <table style="border-collapse: collapse; padding: 5px; border-left: solid black 2px;"
                                     border="0" width="300" cellspacing="0" cellpadding="0" align="left" height="420">
                                           <tr align="center"><td>
                                                <h3 style="margin: 10px 0px 10px 5px">User Details</h3>
                                           </td></tr>
                                        <tr>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">Last name : <% userDB userDB = new userDB();
                                                            user current = userDB.userFromDB(usid);
                                                            out.println(current.getLastName()); %></h4>
                                                <form action="changeUser.jsp?change=last" method="post">
                                                    <input type="submit" value="Change last name"
                                                           style="margin: 5px 0px 5px 5px" />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">First name :
                                                <%out.println(current.getFirstName()); %></h4>
                                                <form action="changeUser.jsp?change=first" method="post">
                                                    <input type="submit" value="Change first name"
                                                           style="margin: 5px 0px 5px 5px" />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">Second name :
                                                <%out.println(current.getSecondName()); %></h4>
                                                <form action="changeUser.jsp?change=second" method="post">
                                                    <input type="submit" value="Change second name"
                                                           style="margin: 5px 0px 5px 5px" />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">Age :
                                                <%out.println(current.getAge()); %></h4>
                                                <form action="changeUser.jsp?change=age" method="post">
                                                    <input type="submit" value="Change age"
                                                           style="margin: 5px 0px 5px 5px" />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">Phone number :
                                                <%out.println(current.getNumber()); %></h4>
                                                <form action="changeUser.jsp?change=number" method="post">
                                                    <input type="submit" value="Change number"
                                                           style="margin: 5px 0px 5px 5px" />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                    </table>
                                    <table style="border-collapse: collapse; border-left: solid black 2px;"
                                     border="0" width="140"
                                           cellspacing="0" height="420" cellpadding="0" align="right">
                                        <tr align="center" valign="top"><td>
                                        <h3 style="margin: 5px 0px 0px 10px">Currency courses</h3>
                                        </td></tr>
                                        <%  currencyDB cDB = new currencyDB();
                                        currency[] currencies = currencyDB.viewCurrencies();
                                        for(currency cur: currencies) {
                                            if (cur.getValue() != 0) {
out.println("<tr><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-left:10px;' width='65'>" + cur.getName() +
 " : </h5></td><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-right: 5px;' width='65'>" + cur.getValue() + "</h5></td></tr>");
                                            }
                                            }%>
                                    </table>
                                </td>
                            </tr>
                            <tr style="background-color: #ffffff; ">
                                <td>&#160;</td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <h3>User Accounts:</h3>
                                </td>
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