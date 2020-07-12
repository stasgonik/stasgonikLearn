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
            String courseString = request.getParameter("curCourse");
            double course = Double.parseDouble(courseString);
            if (!Objects.equals(usid, null) && Objects.equals(usid, constants.bank)) {
                    try {
                            currencyDB.currencyUpdateValue(crid, course);
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