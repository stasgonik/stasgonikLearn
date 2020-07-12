package com.epam.GSI.servlets;


import com.epam.GSI.account;
import com.epam.GSI.accountDB;
import com.epam.GSI.constants;
import com.epam.GSI.currencyDB;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

public class accountAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
            response.setContentType("text/html");

            HttpSession session = request.getSession();
            Integer usid = (Integer) session.getAttribute("sID");
            String acidString = request.getParameter("acid");
            int acidFrom = Integer.parseInt(acidString);
            String action = request.getParameter("action");
            String sumString = request.getParameter("sum");
            double sum = Integer.parseInt(sumString);
            String path;
            if (!Objects.equals(usid, null) && Objects.equals(usid, accountDB.usidFromDB(acidFrom))) {
                switch (action) {
                    case "charge":
                        account.charging(acidFrom, sum);
                        path = "/office.jsp";

                        break;

                    case "extraction":
                        account sender = accountDB.accountFromDB(acidFrom);
                        double payBase = sum * sender.getAccountCurrency().getValue();
                        double commit = 0;
                        if (payBase < 5000) {
                            commit = payBase * constants.extractLess5k;
                        }
                        else if (payBase >= 5000 && payBase < 100000) {
                            commit = payBase * constants.extractLess100k;
                        }
                        else if (payBase >= 100000) {
                            commit = payBase * constants.extractMore100k;
                        }
                        if (acidFrom == constants.bank) {
                            commit = 0;
                        }
                        if (sender.getMoney() < sum + commit / sender.getAccountCurrency().getValue() &&
                                currencyDB.currencyGetID(sender.getAccountCurrency()) == 1 && acidFrom != constants.bank) {
                            path = "/accountOperation.jsp?action=extraction&acid=" +acidFrom + "&check=1";
                        }
                        else  if (sender.getMoney() < sum + commit * 2 / sender.getAccountCurrency().getValue() &&
                                currencyDB.currencyGetID(sender.getAccountCurrency()) != 1 && acidFrom != constants.bank) {
                            path = "/accountOperation.jsp?action=extraction&acid=" +acidFrom + "&check=1";
                        }
                        else  if (sender.getMoney() < sum) {
                            path = "/accountOperation.jsp?action=extraction&acid=" +acidFrom + "&check=1";
                        }
                        else {
                            account.extraction(acidFrom, sum);
                            path = "/office.jsp";

                        }

                        break;

                    case "takeCredit":
                        account bank = accountDB.accountFromDB(constants.bank);
                        account credited = accountDB.accountFromDB(acidFrom);
                        if (bank.getMoney() < 2000000 ||
                                bank.getMoney()/credited.getAccountCurrency().getValue() + 500000 < sum) {
                            path = "/accountOperation.jsp?action=takeCredit&acid=" +acidFrom + "&check=1";
                        }
                        else {
                            account.takeCredit(acidFrom, sum);
                            path = "/office.jsp";
                        }

                        break;

                    case "transfer":
                        String acidToString = request.getParameter("acidTo");
                        int acidTo = Integer.parseInt(acidToString);
                        account transferFrom = accountDB.accountFromDB(acidFrom);
                        account transferTo = accountDB.accountFromDB(acidTo);
                        double transferSum = sum * transferFrom.getAccountCurrency().getValue();
                        commit = 0;
                        double exchangeCommit = 0;
                        if (transferSum < 10000){
                            commit = transferSum * constants.transferLess10k;
                            exchangeCommit = transferSum * constants.transferLess10k / 2;
                        }
                        else if(transferSum >= 10000 && transferSum < 100000) {
                            commit = transferSum * constants.transferLess100k;
                            exchangeCommit = transferSum * constants.transferLess100k / 2;
                        }
                        else if(transferSum >= 100000 && transferSum < 500000){
                            commit = transferSum * constants.transferLess500k;
                            exchangeCommit = transferSum * constants.transferLess500k / 2;
                        }
                        else if( transferSum >= 500000){
                            commit = transferSum * constants.transferMore500k;
                            exchangeCommit = transferSum * constants.transferMore500k / 2;
                        }

                        if (accountDB.usidFromDB(acidTo) == accountDB.usidFromDB(acidFrom)) {
                            commit = 0;
                        }

                        if (currencyDB.currencyGetID(transferFrom.getAccountCurrency()) ==
                                currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                            exchangeCommit = 0;
                        }
                        if (acidFrom == constants.bank || acidTo == constants.bank) {
                            commit = 0;
                            exchangeCommit = 0;
                        }

                        if (transferFrom.getMoney() < sum + commit / transferFrom.getAccountCurrency().getValue() &&
                                accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
                            path = "/accountOperation.jsp?action=transfer&acid=" +acidFrom + "&check=2";

                        }
                        else if (acidTo == acidFrom) {
                            path = "/accountOperation.jsp?action=transfer&acid=" +acidFrom + "&check=1";
                        }
                        else if(transferFrom.getMoney() < sum + (commit * exchangeCommit) /
                                transferFrom.getAccountCurrency().getValue() &&
                                currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                                        currencyDB.currencyGetID(transferTo.getAccountCurrency()) &&
                                accountDB.usidFromDB(acidFrom) != accountDB.usidFromDB(acidTo)) {
                            path = "/accountOperation.jsp?action=transfer&acid=" +acidFrom + "&check=2";
                        }
                        else if (transferFrom.getMoney() < sum + exchangeCommit /
                                transferFrom.getAccountCurrency().getValue() &&
                                accountDB.usidFromDB(acidFrom) == accountDB.usidFromDB(acidTo) &&
                                currencyDB.currencyGetID(transferFrom.getAccountCurrency()) !=
                                        currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                            path = "/accountOperation.jsp?action=transfer&acid=" +acidFrom + "&check=2";
                        }
                        else if (transferFrom.getMoney() < sum &&
                                accountDB.usidFromDB(acidFrom) == accountDB.usidFromDB(acidTo) &&
                                currencyDB.currencyGetID(transferFrom.getAccountCurrency()) ==
                                        currencyDB.currencyGetID(transferTo.getAccountCurrency())) {
                            path = "/accountOperation.jsp?action=transfer&acid=" +acidFrom + "&check=2";
                        }
                        else {
                            account.transferMoney(sum, acidFrom, acidTo);
                            path = "/office.jsp";
                        }
                        break;

                    case "repayLoan":
                        credited = accountDB.accountFromDB(acidFrom);
                        if (sum > credited.getMoney()) {
                            path = "/accountOperation.jsp?action=repayLoan&acid=" +acidFrom + "&check=1";
                        }
                        else {
                            account.payCredit(acidFrom, sum);
                            path = "/office.jsp";
                        }
                        break;

                    default:
                        path = "/office.jsp";
                        break;
                }
            }
            else {
                path = "/office.jsp";
            }
        response.sendRedirect(path);



    }
}