/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2020-07-22 07:51:32 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("                <tr>\r\n");
      out.write("                    <td align=\"center\" valign=\"top\" bgcolor=\"#ffffff\">\r\n");
      out.write("                        <table style=\"border-collapse: collapse; \"\r\n");
      out.write("                               border=\"0\" width=\"500\"\r\n");
      out.write("                               cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\r\n");
      out.write("                            <tr style=\"background-color: #ffffff;\">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td align=\"center\">\r\n");
      out.write("                                    <h2>Welcome to GSI bank</h2>\r\n");
      out.write("                                    <h3>Proceed to our login page or use register form to create new account.</h3>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; \">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td>\r\n");
      out.write("                                    <table style=\"border-collapse: collapse; \"\r\n");
      out.write("                                           border=\"0\" width=\"230\"\r\n");
      out.write("                                           cellspacing=\"0\" cellpadding=\"0\" align=\"left\">\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td align=\"center\" width=\"230px\">\r\n");
      out.write("                                                <form action=\"login.jsp\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Login\" style=\"margin: 10px 20px 5px 20px\"/>\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                    </table>\r\n");
      out.write("                                    <table style=\"border-collapse: collapse; \"\r\n");
      out.write("                                           border=\"0\" width=\"230\"\r\n");
      out.write("                                           cellspacing=\"0\" cellpadding=\"0\" align=\"right\">\r\n");
      out.write("                                        <tr>\r\n");
      out.write("                                            <td align=\"center\" width=\"230px\">\r\n");
      out.write("                                                <form action=\"registerUser.jsp\" method=\"post\">\r\n");
      out.write("                                                    <input type=\"submit\" value=\"Register\" style=\"margin: 10px 20px 0px 20px\"/>\r\n");
      out.write("                                                </form>\r\n");
      out.write("                                            </td>\r\n");
      out.write("                                        </tr>\r\n");
      out.write("                                    </table>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr><td align=\"center\">\r\n");
      out.write("                                <form action=\"createDatabaseAction\" method=\"post\">\r\n");
      out.write("                                <input type=\"submit\" value=\"Create Database\" style=\"margin: 10px 0px 0px 0px\"/>\r\n");
      out.write("                                </form>\r\n");
      out.write("                            </td></tr>\r\n");
      out.write("                            <tr>\r\n");
      out.write("                                <td align=\"center\">\r\n");
      out.write("                                    <h5>\r\n");
      out.write("                                        ");

                                        String reg = request.getParameter("reg");
                                        if (reg != null)
                                            {
                                            out.println("You are successfully registered to GSI bank.");
                                            out.println("Please, use your login to enter our bank.");
                                            }
                                        String data = request.getParameter("data");
                                        if (data != null)
                                            {
                                            out.println("Database ready for work!");
                                            }
                                         
      out.write("\r\n");
      out.write("                                    </h5>\r\n");
      out.write("                                </td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("                            <tr style=\"background-color: #ffffff; \">\r\n");
      out.write("                                <td>&#160;</td>\r\n");
      out.write("                            </tr>\r\n");
      out.write("\r\n");
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
