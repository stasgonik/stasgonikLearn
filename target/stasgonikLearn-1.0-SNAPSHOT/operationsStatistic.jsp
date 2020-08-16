<%@page import="java.lang.Object, java.util.Objects"%>
<%@page import="com.epam.GSI.currency"%>
<%@page import="com.epam.GSI.user"%>
<%@page import="com.epam.GSI.userDB"%>
<%@page import="com.epam.GSI.account"%>
<%@page import="com.epam.GSI.accountDB"%>
<%@page import="com.epam.GSI.constants"%>
<%@page import="com.epam.GSI.operation"%>
<%@page import="com.epam.GSI.operationDB"%>
<%@page import="com.epam.GSI.operationType"%>
<%@page import="com.epam.GSI.subtype"%>
<%@page import="java.time.LocalDateTime;"%>


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
                                                <tr><td align="center">
                                                    <form action="office.jsp" method="post">
                                                    <input type="submit" value="Back"
                                                    style="margin: 10px 0px 5px 0px"/>
                                                    </form>
                                                </td></tr>
                                                <tr style="background-color: #ffffff; line-height: 10px;">
                                                    <td>&#160;</td>
                                                </tr>
                       <tr><td align="center"><h3>Operation statistics of account with ID <%=acid%></h3></td></tr>
                       <tr><td><hr style='line-height: 2px; color: black; border: 1px solid black;'/></td></tr>
                       <tr style="background-color: #ffffff; line-height: 10px;"><td>&#160;</td></tr>
                                                <%
                                                operation[] operations = operationDB.selectOperations(acid);
                                                int[] ids = operationDB.selectIDs(acid);
                                                int i = 0;
                                                for(operation op: operations) {
                                                int id = ids[i];
                                                i++;
                                                int acidFrom = ids[i];
                                                user from = userDB.userFromDB(accountDB.usidFromDB(acidFrom));
                                                i++;
                                                int acidTo = ids[i];
                                                user to = userDB.userFromDB(accountDB.usidFromDB(acidTo));
                                                i++;
                                                operationType type = op.getType();
                                                subtype subType = op.getSubtype();
                                                LocalDateTime opTime = op.getDateTime();
                                                double sum = op.getSum();
                                                currency cur = op.getOperationCurrency();
if ((type.name().equals(operationType.Input.name()) || type.name().equals(operationType.Output.name()) ||
type.name().equals(operationType.Exchange.name())) || ((type.name().equals(operationType.Commission.name()) ||
type.name().equals(operationType.Loan_repayment.name()) || type.name().equals(operationType.Transfer.name())) &&
acid == acidFrom && subType.name().equals(subtype.Withdraw.name())) ||
((type.name().equals(operationType.Transfer.name()) || type.name().equals(operationType.Credit.name())) &&
acid == acidTo && subType.name().equals(subtype.Charge.name())))
{
out.println("<tr><td align='center'>ID of operation: " + id + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Operation type: " + type.name() + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Subtype: " + subType.name() + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 20px;'><td>&#160;</td></tr>");
out.println("<tr><td valign='top'><table style='border-collapse: collapse; padding: 5px;' border='0' width='210' height='90' cellspacing='0' cellpadding='0' align='left'>" +
 "<tr><td align='center'>Sender</td></tr>");
 out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
if (acidFrom == constants.bank) {
out.println("<tr><td align='center'>Sender account: GSI Bank Administration.</td></tr></table>");}
else if (acidFrom == 0) {
out.println("</table>");
}
else {
out.print("<tr><td align='center'>ID of sender account: " + acidFrom + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Owner of this account is: " + from.getLastName() + " " + from.getFirstName() +
" " + from.getSecondName() + "</td></tr></table>");
}
 out.println("<table style='border-collapse: collapse;' border='0' width='210' cellspacing='0' cellpadding='0' align='right' height='90'>" +
  "<tr><td align='center'>Recipient</td></tr>");

out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
if (acidTo == constants.bank) {
out.println("<tr><td align='center'>Recipient account: GSI Bank Administration.</td></tr></table></td></tr>");}
else if (acidTo == 0) {
out.println("</table></td></tr>");
}
else {
out.print("<tr><td align='center'>ID of recipient account: " + acidTo + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Owner of this account is: " + to.getLastName() + " " + to.getFirstName() +
" " + to.getSecondName() + "</td></tr></table></td></tr>");
}
out.println("<tr style='background-color: #ffffff; line-height: 20px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Sum of operation: " + sum + " " + cur.getName() + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Currency course : " + cur.getCourse_buy() + "/" + cur.getCourse_sell() + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
out.println("<tr><td align='center'>Operation registered : " + opTime + "</td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 20px;'><td>&#160;</td></tr>");
out.println("<tr><td><hr style='line-height: 2px; color: black; border: 1px solid black;'/></td></tr>");
out.println("<tr style='background-color: #ffffff; line-height: 8px;'><td>&#160;</td></tr>");
}
                                                }
                                                %>
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