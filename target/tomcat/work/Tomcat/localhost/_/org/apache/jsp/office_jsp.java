/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-07-10 10:48:08 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.epam.GSI.user;
import com.epam.GSI.userDB;
import com.epam.GSI.account;
import com.epam.GSI.accountDB;
import com.epam.GSI.login;
import com.epam.GSI.loginDB;
import com.epam.GSI.currency;
import com.epam.GSI.currencyDB;
import com.epam.GSI.constants;
import javax.servlet.http.HttpSession;

public final class office_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");

    userDB userDB = new userDB();
    loginDB loginDB = new loginDB();
    Integer usid = (Integer) session.getAttribute("sID");
    if (usid == null) {
         String path = "/login.jsp?check=2";
         response.sendRedirect(path);
    }

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<body bgcolor=\"#D3D3D3\">\r\n");
      out.write("<table style=\"border-collapse: collapse;\" border=\"0\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">\r\n");
      out.write("    <tr style=\"background-color: #D3D3D3;\">\r\n");
      out.write("        <td>&#160;</td>\r\n");
      out.write("    </tr>\r\n");
      out.write("    <tr>\r\n");
      out.write("        <td align=\"center\" valign=\"top\" bgcolor=\"#D3D3D3\">\r\n");
      out.write("            <table table style=\"border-collapse: collapse; font-family:Arial; font-size: 14px;\r\n");
      out.write("                         border: solid 3px black; border-radius: 2px;\"\r\n");
      out.write("                   width=\"600\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#D3D3D3\">\r\n");
      out.write("\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\" style=\"padding: 5px;\">\r\n");
      out.write("                        <table style=\"border-collapse: collapse;\"\r\n");
      out.write("                               border=\"0\" width=\"540\"\r\n");
      out.write("                               cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n");
      out.write("\r\n");
      out.write("                            <tr style=\"background-color: #ffffff;\">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr bgcolor=\"#ffffff\">\r\n");
      out.write("                                <td align=\"center\">\r\n");
      out.write("                                    <form action=\"index.jsp\" method=\"post\">\r\n");
      out.write("                                        <input type=\"submit\" value=\"EXIT\"\r\n");
      out.write("                                               style=\"margin: 10px 0px 15px 0px\" />\r\n");
      out.write("                                    </form>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td valign=\"top\">\r\n");
      out.write("                                    <table style=\"border-collapse: collapse; padding: 5px; border-left: solid black 2px;\"\r\n");
      out.write("                                     border=\"0\" width=\"300\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" height=\"430\">\r\n");
      out.write("                                           <tr align=\"center\"><td>\r\n");
      out.write("                                                <h3 style=\"margin: 10px 0px 10px 5px\">User Details</h3>\r\n");
      out.write("                                           </td></tr>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <h4 style=\"margin: 5px 0px 5px 5px\">Last name :\r\n");
      out.write("                                                ");
 user current = userDB.userFromDB(usid);
                                                out.println(current.getLastName()); 
      out.write("</h4>\r\n");
      out.write("                                                <form action=\"changeUser.jsp?change=last\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Change last name\"\r\n");
      out.write("                                                           style=\"margin: 5px 0px 5px 5px\" />\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                                <br />\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <h4 style=\"margin: 5px 0px 5px 5px\">First name :\r\n");
      out.write("                                                ");
  out.println(current.getFirstName()); 
      out.write("</h4>\r\n");
      out.write("                                                <form action=\"changeUser.jsp?change=first\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Change first name\"\r\n");
      out.write("                                                           style=\"margin: 5px 0px 5px 5px\" />\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                                <br />\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <h4 style=\"margin: 5px 0px 5px 5px\">Second name :\r\n");
      out.write("                                                ");
out.println(current.getSecondName()); 
      out.write("</h4>\r\n");
      out.write("                                                <form action=\"changeUser.jsp?change=second\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Change second name\"\r\n");
      out.write("                                                           style=\"margin: 5px 0px 5px 5px\" />\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                                <br />\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <h4 style=\"margin: 5px 0px 5px 5px\">Age :\r\n");
      out.write("                                                ");
out.println(current.getAge()); 
      out.write("</h4>\r\n");
      out.write("                                                <form action=\"changeUser.jsp?change=age\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Change age\"\r\n");
      out.write("                                                           style=\"margin: 5px 0px 5px 5px\" />\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                                <br />\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td>\r\n");
      out.write("                                                <h4 style=\"margin: 5px 0px 5px 5px\">Phone number :\r\n");
      out.write("                                                ");
out.println(current.getNumber()); 
      out.write("</h4>\r\n");
      out.write("                                                <form action=\"deleteUser.jsp\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Delete user\"\r\n");
      out.write("                                                           style=\"margin: 20px 0px 5px 200px\" />\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                                <br />\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                    </table>\r\n");
      out.write("                                    <table style=\"border-collapse: collapse; border-left: solid black 2px;\"\r\n");
      out.write("                                     border=\"0\" width=\"140\"\r\n");
      out.write("                                           cellspacing=\"0\" height=\"430\" cellpadding=\"0\" align=\"right\">\r\n");
      out.write("                                        <tr align=\"center\" valign=\"top\"><td>\r\n");
      out.write("                                        <h3 style=\"margin: 5px 0px 0px 10px\">Currency courses</h3>\r\n");
      out.write("                                        </td></tr>\r\n");
      out.write("                                        ");
  currencyDB cDB = new currencyDB();
                                        currency[] currencies = currencyDB.viewCurrencies();
                                        for(currency cur: currencies) {
                                            if (cur.getValue() != 0) {
out.println("<tr><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-left:10px;' width='65'>" + cur.getName() +
 " : </h5></td><td align='left'><h5 style='margin-top: 5px; margin-bottom: 5px; margin-right: 5px;' width='65'>" + cur.getValue() + "</h5></td></tr>");
                                            }
                                            }
      out.write("\r\n");
      out.write("                                    </table>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; \">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td align=\"center\">\r\n");
      out.write("                                    <h3 style=\"margin: 5px 0px 10px 10px\">User Accounts:</h3>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            ");

                                accountDB accountDB = new accountDB();
                                int[] acids = accountDB.searchUserAccounts(usid);
                                for (int acid:acids)
                                {
                                account ac = accountDB.accountFromDB(acid);
out.println("<tr><td valign='top'><br/><table style='border-collapse: collapse;' border='0' width='250' cellspacing='0' cellpadding='0' align='left'>" +
"<tr style='margin-bottom:5px;'><td align='center' >Account ID : " +  acid + "<br style='line-height: 5px;'/>" +
 "</td></tr><tr style='margin-bottom:5px;'><td align='center' >Account currency : " + ac.getAccountCurrency().getName() +
"<br style='line-height: 5px;'/></td></tr><tr><td align='center'> <form action='account.jsp?acid=" + acid +
 "' method='post'> <input type='submit' value='Operations'" +
" style='margin: 10px 0px 5px 0px;'/></form></td></tr></table>" +
"<table style='border-collapse: collapse;' border='0' width='250' cellspacing='0' cellpadding='0' align='right'>" +
 "<tr style='margin-bottom:5px;'><td align='center'>Sum : " +  ac.getMoney() + ac.getAccountCurrency().getName() +
  "<br style='line-height: 5px;'/></td></tr><tr style='margin-bottom:5px;'><td align='center' >Loan : " +
 ac.getLoan() + ac.getAccountCurrency().getName() +
  "<br style='line-height: 5px;'/></td></tr><tr><td align='center'> <form action='operations.jsp?acid=" + acid +
  "' method='post'> <input type='submit' value='Operation statistics'" +
 " style='margin: 10px 0px 5px 0px;'/></form></td></tr></table><br/><hr/></td></tr><tr><td>" +
  "<hr style='line-height: 2px; color: black; border: 1px solid black;'/></td></tr>");
                                }
                            
      out.write("\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; \">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            ");

                                if (accountDB.countAccountCheck(usid) && usid != constants.bank) {
out.println("<tr><td align='center'><form action='registerAccount.jsp' method='post'> <input type='submit'" +
 "value='Register new account' style='margin: 10px 0px 5px 0px;'/></form></tr></td>");
                                }
                            
      out.write("\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; \">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                        </table>\r\n");
      out.write("                    </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("        </td>\r\n");
      out.write("    </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
