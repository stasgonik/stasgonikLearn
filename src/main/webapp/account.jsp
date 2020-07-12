<%@page import="java.lang.Object, java.util.Objects"%>
<%@page import="com.epam.GSI.currency"%>
<%@page import="com.epam.GSI.user"%>
<%@page import="com.epam.GSI.userDB"%>
<%@page import="com.epam.GSI.account"%>
<%@page import="com.epam.GSI.accountDB"%>
<%@page import="com.epam.GSI.constants"%>


<%
    Integer usid = (Integer) session.getAttribute("sID");
        if (Objects.equals(usid, null)) {
             String path = "/office.jsp";
             response.sendRedirect(path);
        }
    String acidString = request.getParameter("acid");
    int acid = Integer.parseInt(acidString);
    Integer usidCheck = (Integer) accountDB.usidFromDB(acid);
    if (!Objects.equals(usid, usidCheck)) {
        String path = "/office.jsp";
        response.sendRedirect(path);
    }
    account ac = accountDB.accountFromDB(acid);
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
                                <td align="center"><h3>Choose operation for current account (ID <%=acid%>)</h3></td>
                                                </tr>


                                                <tr><td align="center">
                                <%out.println("<form action='accountOperation.jsp?action=transfer&acid="+ acid +"' method='post'>");%>
                                <input type="submit" value="Transfer" style="margin: 10px 0px"/></form>
                                </td></tr>


                         <tr <% if(acid == constants.bank) {out.println("style='display: none;'");}%>><td align="center">
                         <%out.println("<form action='accountOperation.jsp?action=takeCredit&acid="+ acid +"' method='post'>");%>
                                <input type="submit" value="Take Credit"style="margin: 10px 0px"/></form>
                                </td></tr>


                         <tr <% if(ac.getLoan() <=0 || acid == constants.bank) {out.println("style='display: none;'");}%>><td align="center">
                                <%out.println("<form action='accountOperation.jsp?action=repayLoan&acid="+ acid +"' method='post'>");%>
                                <input type="submit" value="Loan Repayment" style="margin: 10px 0px"/></form>
                                </td></tr>


                                <tr><td align="center">
                                <%out.println("<form action='accountOperation.jsp?action=extraction&acid="+ acid +"' method='post'>");%>
                                <input type="submit" value="Extraction" style="margin: 10px 0px"/></form>
                                </td></tr>


                                <tr><td align="center">
                                <%out.println("<form action='accountOperation.jsp?action=charge&acid="+ acid +"' method='post'>");%>
                                <input type="submit" value="Charge" style="margin: 10px 0px"/></form>
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