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
    if (Objects.equals(usid, null) || !Objects.equals(usid, constants.bank)) {
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
            <table table style="border-collapse: collapse; font-family:Arial; font-size: 14px;
                         border: solid 2px black; border-radius: 2px;"
                   width="550" cellspacing="0" cellpadding="0" bgcolor="#D3D3D3">
                <tr bgcolor="#ffffff">
                    <td align="center" >
                        <form action="office.jsp" method="post">
                            <input type="submit" value="Back"
                                   style="margin: 10px 0px 5px 0px"/>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td align="center" valign="top" bgcolor="#ffffff">
                        <table style="border-collapse: collapse; "
                               border="0" width="500"
                               cellspacing="0" cellpadding="0" align="center">

                            <tr style="background-color: #ffffff; line-height: 5px;">
                                <td>&#160;</td>
                            </tr>
                            <tr align="center">
                            <td>
                                <h2>
                                    Please choose currency for change and set new course.
                                </h2>
                            </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form action="changeCurCourseAction" method="post">
                                        <p><select required name="chooseCurrency">
                                            <option selected disabled>Choose your currency</option>
                                            <%
                                            currency[] currencies = currencyDB.viewCurrencies();
                                            for(currency cur: currencies) {
                    if (cur.getCourse_buy() != 0 && cur.getCourse_sell() != 0 && currencyDB.currencyGetID(cur) != 1) {
out.println("<option value='" + currencyDB.currencyGetID(cur) + "'>" + cur.getName() + "</option>");
                                            }}%>
                                            </select></p><br/>
                                            Buy course: <br/>
                                        <input required name="course_buy" type="text"
                                               pattern="^[_.0-9]{1,8}$"
                                               oninvalid="this.setCustomValidity
                                                            ('Use only digits and .')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:200px; margin: 5px 0px;"/> <br />
                                        <br />
                                            Sell course: <br/>
                                        <input required name="course_sell" type="text"
                                               pattern="^[_.0-9]{1,8}$"
                                               oninvalid="this.setCustomValidity
                                                            ('Use only digits and .')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:200px; margin: 5px 0px;"/> <br />
                                        <br />
                                        <input type="submit" value="Submit"
                                               style="margin: 5px 0px"/>

                                    </form>
                                </td>
                            </tr>
                            <tr style="background-color: #ffffff; line-height: 5px;">
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