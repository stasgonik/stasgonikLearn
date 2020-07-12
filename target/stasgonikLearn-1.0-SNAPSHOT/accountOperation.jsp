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
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.epam.GSI.currency"%>
<%@page import="com.epam.GSI.currencyDB"%>


<%
    Integer usid = (Integer) session.getAttribute("sID");
    if (Objects.equals(usid, null)) {
        String path = "/office.jsp";
        response.sendRedirect(path);
    }
    String acidString = request.getParameter("acid");
    int acid = Integer.parseInt(acidString);
    Integer usidCheck = (Integer) accountDB.usidFromDB(acid);
        if (!(Objects.equals(usid, usidCheck))) {
            String path = "/office.jsp";
            response.sendRedirect(path);
        }
    String action = (String) request.getParameter("action");
    if (!(action.equals("transfer") || action.equals("charge") ||
     action.equals("extraction") || action.equals("repayLoan") || action.equals("takeCredit"))) {
        String path = "/office.jsp";
        response.sendRedirect(path);
    }
    account ac = accountDB.accountFromDB(acid);
    if(action.equals("repayLoan") && (ac.getLoan() <=0 || acid == constants.bank)) {
        String path = "/office.jsp";
        response.sendRedirect(path);
    }
    else if (action.equals("takeCredit") && acid == constants.bank) {
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
<td align="center"><h4>Please, fill in forms for
<%
if (action.equals("charge")) {
    out.println("charge account operation.</h4></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for charge sum less than 5000 " +
     currencyDB.currencyFromDB(1).getName() + "is " + constants.chargeLess5String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for charge sum less than 100000 " +
     currencyDB.currencyFromDB(1).getName() + "is " + constants.chargeLess100String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for charge sum more than 100000 " +
     currencyDB.currencyFromDB(1).getName() + " is " + constants.chargeMore100String + "%</h5></td></tr>");
}
if (action.equals("takeCredit")) {
    out.println("credit operation.</h4></td></tr>");
    out.println("<tr><td align='center'><h5>Credit rate for " + currencyDB.currencyFromDB(1).getName() +
     " is " + constants.creditBaseString + "%.</h5></td></tr>");
     out.println("<tr><td align='center'><h5>Credit rate for other currencies" +
     " is " + constants.creditOtherString + "%.</h5></td></tr>");
}
if (action.equals("transfer")) {
    out.println("transfer operation.</h4></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for transfer sum less than 10000 " +
     currencyDB.currencyFromDB(1).getName() + " is "+ constants.transferLess10String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for transfer sum less than 100000 " +
     currencyDB.currencyFromDB(1).getName() + " is "+ constants.transferLess100String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for transfer sum less than 500000 " +
     currencyDB.currencyFromDB(1).getName() + " is "+ constants.transferLess500String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for transfer sum more than 500000 " +
     currencyDB.currencyFromDB(1).getName() + " is "+ constants.transferMore500String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Warning! If you transfer money between accounts with different currency," +
     " you must pay additional exchange commission sum (50% of transfer commission sum).</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Warning! If you transfer money between accounts that both belong to you," +
     " you do not need to pay transfer commission.</h5></td></tr>");
}
if (action.equals("repayLoan")) {
    out.println("loan repayment operation.</h4></td></tr>");
}
if (action.equals("extraction")) {
    out.println("extract from account operation.</h4></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for extraction sum less than 5000 " +
     currencyDB.currencyFromDB(1).getName() + " is "+ constants.extractLess5String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for extraction sum less than 100000 " +
     currencyDB.currencyFromDB(1).getName() + "is " + constants.extractLess100String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Commission for extraction sum more than 100000 " +
     currencyDB.currencyFromDB(1).getName() + " is " + constants.extractMore100String + "%</h5></td></tr>");
    out.println("<tr><td align='center'><h5>Warning! If you extract money not from " +
     currencyDB.currencyFromDB(1).getName() + " account, your commission sum is doubled.</h5></td></tr>");

}
%>

                                                <tr style="background-color: #ffffff;">
                                                    <td>&#160;</td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
<%
if (action.equals("charge")) {
     out.println("<form action='accountAction?acid=" + acid + "&action=charge' method='post'>");
     out.println("Charge sum (in UAH): <br /><input required name='sum' type='text'pattern='^[_.0-9]{1,10}$'");
     out.println(" oninvalid='this.setCustomValidity('Must be 1-10 digits and . ')' oninput='this.setCustomValidity('')'");
     out.println("style='width:250px; margin: 10px 0px;' /> <br /><input type='submit' value='Submit' style='margin: 5px 0px' /></form>");
}
else if (action.equals("extraction")) {
     out.println("<form action='accountAction?acid=" + acid + "&action=extraction' method='post'>");
     out.println("Extraction sum: <br /><input required name='sum' type='text'pattern='^[_.0-9]{1,10}$'");
     out.println(" oninvalid='this.setCustomValidity('Must be 1-10 digits and . ')' oninput='this.setCustomValidity('')'");
     out.println("style='width:250px; margin: 10px 0px;' /> <br /><input type='submit' value='Submit' style='margin: 5px 0px' /></form>");
     String check = request.getParameter("check");
          if (!Objects.equals(check, null)) {
            out.println("<h5>Insufficient sum on account!</h5>");
          }
}
else if (action.equals("repayLoan")) {
     out.println("<form action='accountAction?acid=" + acid + "&action=repayLoan' method='post'>");
     out.println("Loan repayment sum: <br /><input required name='sum' type='text'pattern='^[_.0-9]{1,10}$'");
     out.println(" oninvalid='this.setCustomValidity('Must be 1-10 digits and . ')' oninput='this.setCustomValidity('')'");
     out.println("style='width:250px; margin: 10px 0px;' /> <br />");
     out.println("<input type='submit' value='Submit' style='margin: 5px 0px' /></form>");
     String check = request.getParameter("check");
          if (!Objects.equals(check, null)) {
            out.println("<h5>Insufficient sum on account!</h5>");
          }
}
else if (action.equals("transfer")) {
     out.println("<form action='accountAction?acid=" + acid + "&action=transfer' method='post'>");
     out.println("Transfer sum: <br /><input required name='sum' type='text'pattern='^[_.0-9]{1,10}$'");
     out.println(" oninvalid='this.setCustomValidity('Must be 1-10 digits and . ')' oninput='this.setCustomValidity('')'");
     out.println("style='width:250px; margin: 10px 0px;' /> <br />");
     out.println("Recipient account ID: <br /><input required name='acidTo' type='text'pattern='^[_0-9]{1,12}$'");
     out.println(" oninvalid='this.setCustomValidity('Must be 1-12 digits')' oninput='this.setCustomValidity('')'");
     out.println("style='width:250px; margin: 10px 0px;' /> <br />");
     out.println("<input type='submit' value='Submit' style='margin: 5px 0px' /></form>");
     String check = request.getParameter("check");
     if (!Objects.equals(check, null)) {
        if (Objects.equals(check, "1")) {
            out.println("<h5>You cant transfer money on the same account!</h5>");
        }
        if (Objects.equals(check, "2")) {
            out.println("<h5>Insufficient sum on account!</h5>");
        }
     }
}
else if (action.equals("takeCredit")) {
     out.println("<form action='accountAction?acid=" + acid + "&action=takeCredit' method='post'>");
     out.println("Credit sum: <br /><input required name='sum' type='text'pattern='^[_.0-9]{1,10}$'");
     out.println(" oninvalid='this.setCustomValidity('Must be 1-10 digits and . ')' oninput='this.setCustomValidity('')'");
     out.println("style='width:250px; margin: 10px 0px;' /> <br />");
     out.println("<input type='submit' value='Submit' style='margin: 5px 0px' /></form>");
     String check = request.getParameter("check");
          if (!Objects.equals(check, null)) {
            out.println("<h5>Attention user! Our bank credit line is currently unavailable.</h5>");
          }
}

%>
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