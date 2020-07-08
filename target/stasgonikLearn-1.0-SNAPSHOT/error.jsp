<%
    final Logger log = Logger.getLogger(application.class);
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
    log.error("Exception occurred " , pageContext.getException)
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Exception</title>
</head>
body bgcolor="#D3D3D3">
<table style="border-collapse: collapse;" border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr style="background-color: #D3D3D3;">
        <td>&#160;</td>
    </tr>
    <tr>
            <td align="center" valign="top" bgcolor="#D3D3D3">
                <table table style="border-collapse: collapse; font-family:Arial; font-size: 14px;
                             border: solid 2px black; border-radius: 2px;"
                       width="550" cellspacing="0" cellpadding="0" bgcolor="#D3D3D3">
                </table>
                <tr>
                <td align="center" valign="top" bgcolor="#ffffff">
                <table style="border-collapse: collapse;" order="0" width="500"
                 cellspacing="0" cellpadding="0" align="center">
                    <tr bgcolor="#ffffff">
                    <td> &#160;</td>
                    </tr>
                    <tr>
                        <td align="left">
                            <h3>Exception occurred while processing the request</h3><br />
                            <h4>Type: <%= exception%></h4><br />
                            <h4>Message: <%= message %></h4><br />
                            <p> <%= pageContext.getException &></p>
                        </td>
                    </tr>
                 </table></td></tr>
            </td>
    </tr>
</table>
</body>
</html>