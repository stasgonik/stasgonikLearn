package com.epam.GSI.servlets;

import com.epam.GSI.account;
import com.epam.GSI.accountDB;
import com.epam.GSI.currencyDB;
import com.epam.GSI.userDB;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class registerAccountAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");


            final Logger log = Logger.getLogger(deleteUserAction.class);
            HttpSession session = request.getSession();
            Integer usid = (Integer) session.getAttribute("sID");
            String cridString = request.getParameter("chooseCurrency");
            int crid = Integer.parseInt(cridString);
            if (!Objects.equals(usid, null)) {
                    try {
                            account newAccount = new account(userDB.userFromDB(usid),
                                    currencyDB.currencyFromDB(crid), 0,0);
                            accountDB.accountToDB(newAccount);
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