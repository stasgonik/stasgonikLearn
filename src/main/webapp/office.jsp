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
<%@page import="com.epam.GSI.constants"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="java.lang.Object, java.util.Objects"%>



<%
    userDB userDB = new userDB();
    loginDB loginDB = new loginDB();
    Integer usid = (Integer) session.getAttribute("sID");
    if (Objects.equals(usid, null)) {
         String path = "/login.jsp?check=2";
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
                                     border="0" width="300" cellspacing="0" cellpadding="0" align="left" height="430">
                                           <tr align="center"><td>
                                                <h3 style="margin: 10px 0px 10px 5px">User Details</h3>
                                           </td></tr>
                                        <tr>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">Last name :
                                                <% user current = userDB.userFromDB(usid);
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
                                                <%  out.println(current.getFirstName()); %></h4>
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
                                       <%-- <tr>
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
                                        <tr> --%>
                                            <td>
                                                <h4 style="margin: 5px 0px 5px 5px">Phone number :
                                                <%out.println(current.getNumber()); %></h4>
                                                <form action="deleteUser.jsp" method="post">
                                                    <input type="submit" value="Delete user"
                                                           style="margin: 20px 0px 5px 200px;
                                                           <% if(usid == constants.bank){
                                                            out.println("display: none;");
                                                           }%>
                                                           " />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <form action="changeCurrencyCourse.jsp" method="post">
                                                    <input type="submit" value="Change currency courses"
                                                           style="margin: 20px 0px 5px 100px;
                                                           <% if(usid != constants.bank){
                                                            out.println("display: none;");
                                                           }%>
                                                           " />
                                                </form>
                                                <br />
                                            </td>
                                        </tr>
                                    </table>
                                    <table style="border-collapse: collapse; border-left: solid black 2px;"
                                     border="0" width="150"
                                           cellspacing="0" height="430" cellpadding="0" align="right">
                                        <tr align="center" valign="top"><td>
                                        <h3 style="margin: 5px 0px 0px 20px">Currency courses</h3>
                                        </td></tr>
                                        <tr valign="top"><td align="left">
                                        <h5 style='margin-top: 5px; margin-bottom: 5px; margin-left:10px;' width='45'>
                                        Currency</h5></td>
                                        <td align="left">
                                        <h5 style='margin-top: 5px; margin-bottom: 5px; margin-right: 15px;' width='45'>
                                        Buy</h5></td>
                                        <td align="left">
                                        <h5 style='margin-top: 5px; margin-bottom: 5px; margin-right: 15px;' width='45'>
                                        Sell</h5></td></tr>

                                        <%  currencyDB cDB = new currencyDB();
                                        currency[] currencies = currencyDB.viewCurrencies();
                                        for(currency cur: currencies) {
                                            if (cur.getCourse_buy() != 0 && cur.getCourse_sell() != 0) {
out.println("<tr><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-left:10px;' width='45'>" + cur.getName() +
 " : </h5></td><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-right: 5px;' width='45'>" + cur.getCourse_buy() +
 " </h5></td><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-right: 5px;' width='45'>" + cur.getCourse_sell() + "</h5></td></tr>");
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
                                    <h3 style="margin: 5px 0px 10px 10px">User Accounts:</h3>
                                </td>
                            </tr>
                            <%
                                accountDB accountDB = new accountDB();
                                int[] acids = accountDB.searchUserAccounts(usid);
                                for (int acid:acids)
                                {
                                account ac = accountDB.accountFromDB(acid);
out.println("<tr><td valign='top'><br/><table style='border-collapse: collapse;' border='0' width='250' cellspacing='0' cellpadding='0' align='left'>" +
"<tr style='margin-bottom:5px;'><td align='center' >Account ID : " +  acid + "<br style='line-height: 5px;'/>" +
 "</td></tr><tr style='margin-bottom:5px;'><td align='center' >Account currency : " + ac.getAccountCurrency().getName() +
"<br style='line-height: 5px;'/></td></tr><tr><td align='center'> <form action='account.jsp?acid=" + acid +
 "' method='post'> <input type='submit' value='Account operation'" +
" style='margin: 10px 0px 5px 0px;'/></form></td></tr></table>" +
"<table style='border-collapse: collapse;' border='0' width='250' cellspacing='0' cellpadding='0' align='right'>" +
 "<tr style='margin-bottom:5px;'><td align='center'>Sum : " +  ac.getMoney() + ac.getAccountCurrency().getName() +
  "<br style='line-height: 5px;'/></td></tr><tr style='margin-bottom:5px;'><td align='center' >Loan : " +
 ac.getLoan() + ac.getAccountCurrency().getName() +
  "<br style='line-height: 5px;'/></td></tr><tr><td align='center'> <form action='operationsStatistic.jsp?acid=" + acid +
  "' method='post'> <input type='submit' value='Operation statistics'" +
 " style='margin: 10px 0px 5px 0px;'/></form></td></tr></table><br/><hr/></td></tr><tr><td>" +
  "<hr style='line-height: 2px; color: black; border: 1px solid black;'/></td></tr>");
                                }
                            %>
                            <tr style="background-color: #ffffff; ">
                                <td>&#160;</td>
                            </tr>
                            <%
                                if (accountDB.countAccountCheck(usid) && usid != constants.bank) {
out.println("<tr><td align='center'><form action='registerAccount.jsp' method='post'> <input type='submit'" +
 "value='Register new account' style='margin: 10px 0px 5px 0px;'/></form></tr></td>");
                                }
                            %>
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