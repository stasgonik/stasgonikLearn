package com.epam.GSI.servlets;

import com.epam.GSI.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class createDatabaseAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");


            final Logger log = Logger.getLogger(deleteUserAction.class);
                    try {
                            createDatabase.creation();
                    }
                    catch (Exception ex) {
                            ex.printStackTrace();
                            log.error("Exception occurred", ex);
                    }




            String path = "/index.jsp?data=1";
            response.sendRedirect(path);

    }
}