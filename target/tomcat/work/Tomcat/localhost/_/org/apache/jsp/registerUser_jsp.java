/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-08-16 09:43:54 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class registerUser_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

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
      out.write("                        <form action=\"index.jsp\" method=\"post\">\r\n");
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
      out.write("                                    Please fill in all fields to register in GSI bank.\r\n");
      out.write("                                </h2>\r\n");
      out.write("                            </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td align=\"left\">\r\n");
      out.write("                                    <form action=\"registerToDB\" method=\"post\">\r\n");
      out.write("                                        Login: must be 4-20 characters or digits <br/>\r\n");
      out.write("                                        <input required name=\"login\" type=\"text\"\r\n");
      out.write("                                                      pattern=\"^[_A-Za-z0-9]{4,20}$\"\r\n");
      out.write("                                                      oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Must be 4-20 characters or digits')\"\r\n");
      out.write("                                                      oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                                      style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        Password: must be 4-20 characters or digits <br/>\r\n");
      out.write("                                        <input required name=\"password\" type=\"text\"\r\n");
      out.write("                                                         pattern=\"^[_A-Za-z0-9]{4,20}$\"\r\n");
      out.write("                                                         oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Must be 4-20 characters or digits')\"\r\n");
      out.write("                                                         oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                                         style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        First name: must be 1-30 characters  <br/>\r\n");
      out.write("                                        <input required name=\"first_name\" type=\"text\"\r\n");
      out.write("                                               pattern=\"^[_A-Za-z-]{1,30}$\"\r\n");
      out.write("                                               oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Must be 1-30 characters')\"\r\n");
      out.write("                                               oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                               style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        Second name: must be 1-30 characters  <br/>\r\n");
      out.write("                                        <input required name=\"second_name\" type=\"text\"\r\n");
      out.write("                                               pattern=\"^[_A-Za-z-]{1,30}$\"\r\n");
      out.write("                                               oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Must be 1-30 characters')\"\r\n");
      out.write("                                               oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                               style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        Last name: must be 1-30 characters  <br/>\r\n");
      out.write("                                        <input required name=\"last_name\" type=\"text\"\r\n");
      out.write("                                               pattern=\"^[_A-Za-z-]{1,30}$\"\r\n");
      out.write("                                               oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Must be 1-30 characters')\"\r\n");
      out.write("                                               oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                               style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        Age: <br/>\r\n");
      out.write("                                        <input required name=\"age\" type=\"text\"\r\n");
      out.write("                                               pattern=\"^[_0-9]{1,3}$\"\r\n");
      out.write("                                               oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Use only digits')\"\r\n");
      out.write("                                               oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                               style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        Phone number: preferable format +38 068 123-4567<br/>\r\n");
      out.write("                                        <input required name=\"number\" type=\"text\"\r\n");
      out.write("                                               pattern=\"^\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?$\"\r\n");
      out.write("                                               oninvalid=\"this.setCustomValidity\r\n");
      out.write("                                                            ('Unknown number format')\"\r\n");
      out.write("                                               oninput=\"this.setCustomValidity('')\"\r\n");
      out.write("                                               style=\"width:300px; margin: 5px 0px;\"/> <br />\r\n");
      out.write("                                        <br />\r\n");
      out.write("                                        <input type=\"submit\" value=\"Submit\"\r\n");
      out.write("                                               style=\"margin: 5px 0px 5px 90px\"/>\r\n");
      out.write("                                        <input type=\"reset\" value=\"Reset\"\r\n");
      out.write("                                               style=\"margin: 5px 0px 5px 180px\"/>\r\n");
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