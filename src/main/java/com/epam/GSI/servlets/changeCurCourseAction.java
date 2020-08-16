package com.epam.GSI.servlets;

import com.epam.GSI.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class changeCurCourseAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");


            final Logger log = Logger.getLogger(deleteUserAction.class);
            HttpSession session = request.getSession();
            Integer usid = (Integer) session.getAttribute("sID");
            String cridString = request.getParameter("chooseCurrency");
            int crid = Integer.parseInt(cridString);
            String courseBuyString = request.getParameter("course_buy");
            double course_buy = Double.parseDouble(courseBuyString);
            String courseSellString = request.getParameter("course_sell");
            double course_sell = Double.parseDouble(courseSellString);
            if (!Objects.equals(usid, null) && Objects.equals(usid, constants.bank)) {
                    try {
                            currencyDB.currencyUpdateValue(crid, course_buy, course_sell);
                    }
                    catch (Exception ex) {
                            ex.printStackTrace();
                            log.error("Exception occurred", ex);
                    }

            }


            String path = "/office.jsp";
            response.sendRedirect(path);

    }
}