/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-07-11 10:54:44 UTC
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
import java.lang.Object;
import java.util.Objects;

public final class registerAccount_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\r\n");
      out.write("\r\n");

    userDB userDB = new userDB();
    loginDB loginDB = new loginDB();
    Integer usid = (Integer) session.getAttribute("sID");
    if (Objects.equals(usid, null) || Objects.equals(usid, constants.bank)) {
         String path = "/office.jsp";
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
      out.write("                         border: solid 2px black; border-radius: 2px;\"\r\n");
      out.write("                   width=\"550\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#D3D3D3\">\r\n");
      out.write("                <tr bgcolor=\"#ffffff\">\r\n");
      out.write("                    <td align=\"center\" >\r\n");
      out.write("                        <form action=\"office.jsp\" method=\"post\">\r\n");
      out.write("                            <input type=\"submit\" value=\"Back\"\r\n");
      out.write("                                   style=\"margin: 10px 0px 5px 0px\"/>\r\n");
      out.write("                        </form>\r\n");
      out.write("                    </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                <tr>\r\n");
      out.write("                    <td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\">\r\n");
      out.write("                        <table style=\"border-collapse: collapse; \"\r\n");
      out.write("                               border=\"0\" width=\"500\"\r\n");
      out.write("                               cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n");
      out.write("\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; line-height: 5px;\">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr align=\"center\">\r\n");
      out.write("                            <td>\r\n");
      out.write("                                <h2>\r\n");
      out.write("                                    Please choose currency for your new account.\r\n");
      out.write("                                </h2>\r\n");
      out.write("                            </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td align=\"center\">\r\n");
      out.write("                                    <form action=\"registerAccountAction\" method=\"post\">\r\n");
      out.write("                                        <p><select required name=\"chooseCurrency\">\r\n");
      out.write("                                            <option selected disabled>Choose your currency</option>\r\n");
      out.write("                                            ");

                                            currency[] currencies = currencyDB.viewCurrencies();
                                            for(currency cur: currencies) {
                                            if (cur.getValue() != 0) {
out.println("<option value='" + currencyDB.currencyGetID(cur) + "'>" + cur.getName() + "</option>");
                                            }}
      out.write("\r\n");
      out.write("                                           </select></p>\r\n");
      out.write("                                        <input type=\"submit\" value=\"Submit\"\r\n");
      out.write("                                               style=\"margin: 5px 0px\"/>\r\n");
      out.write("\r\n");
      out.write("                                    </form>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td align=\"center\">\r\n");
      out.write("                                    <h4 style=\"color:red\">\r\n");
      out.write("                                    ");

                                        String login_check = request.getParameter("login_check");
                                        if (login_check != null)
                                        {
                                            out.println("This login is already in database.");
                                            out.println("Please, use another login.");
                                        }
                                    
      out.write("\r\n");
      out.write("                                    <br />\r\n");
      out.write("                                    ");

                                        String number_check = request.getParameter("number_check");
                                        if (number_check != null)
                                        {
                                            out.println("This phone number is already in database.");
                                            out.println("Please, use another number.");
                                        }
                                     
      out.write("\r\n");
      out.write("                                    </h4>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; line-height: 5px;\">\r\n");
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
