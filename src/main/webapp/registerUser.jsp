<html>
<body bgcolor="#D3D3D3">
<table style="border-collapse: collapse;" border="0" width="100%" cellspacing="0" cellpadding="0">
    <tr style="background-color: #D3D3D3;">
        <td>&#160;</td>
    </tr>
    <tr>
        <td align="center" valign="top" bgcolor="#D3D3D3">
            <table table style="border-collapse: collapse; font-family:Arial; font-size: 14px;
                         border: solid 2px black; border-radius: 2px;"
                   width="550" cellspacing="0" cellpadding="0" bgcolor="#D3D3D3">
                <tr bgcolor="#ffffff">
                    <td align="center" >
                        <form action="index.jsp" method="post">
                            <input type="submit" value="Back"
                                   style="margin: 10px 0px 5px 0px"/>
                        </form>
                    </td>
                </tr>
                <tr>
                    <td align="center" valign="top" bgcolor="#ffffff">
                        <table style="border-collapse: collapse; "
                               border="0" width="500"
                               cellspacing="0" cellpadding="0" align="center">

                            <tr style="background-color: #ffffff; line-height: 5px;">
                                <td>&#160;</td>
                            </tr>
                            <tr align="center">
                            <td>
                                <h2>
                                    Please fill in all fields to register in GSI bank.
                                </h2>
                            </td>
                            </tr>
                            <tr>
                                <td align="left">
                                    <form action="registerToDB" method="post">
                                        Login: must be 4-20 characters or digits <br/>
                                        <input required name="login" type="text"
                                                      pattern="^[_A-Za-z0-9]{4,20}$"
                                                      oninvalid="this.setCustomValidity
                                                            ('Must be 4-20 characters or digits')"
                                                      oninput="this.setCustomValidity('')"
                                                      style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        Password: must be 4-20 characters or digits <br/>
                                        <input required name="password" type="text"
                                                         pattern="^[_A-Za-z0-9]{4,20}$"
                                                         oninvalid="this.setCustomValidity
                                                            ('Must be 4-20 characters or digits')"
                                                         oninput="this.setCustomValidity('')"
                                                         style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        First name: must be 1-30 characters  <br/>
                                        <input required name="first_name" type="text"
                                               pattern="^[_A-Za-z-]{1,30}$"
                                               oninvalid="this.setCustomValidity
                                                            ('Must be 1-30 characters')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        Second name: must be 1-30 characters  <br/>
                                        <input required name="second_name" type="text"
                                               pattern="^[_A-Za-z-]{1,30}$"
                                               oninvalid="this.setCustomValidity
                                                            ('Must be 1-30 characters')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        Last name: must be 1-30 characters  <br/>
                                        <input required name="last_name" type="text"
                                               pattern="^[_A-Za-z-]{1,30}$"
                                               oninvalid="this.setCustomValidity
                                                            ('Must be 1-30 characters')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        Age: <br/>
                                        <input required name="age" type="text"
                                               pattern="^[_0-9]{1,3}$"
                                               oninvalid="this.setCustomValidity
                                                            ('Use only digits')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        Phone number: preferable format +38 068 123-4567<br/>
                                        <input required name="number" type="text"
                                               pattern="^\(?\+[0-9]{1,3}\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?$"
                                               oninvalid="this.setCustomValidity
                                                            ('Unknown number format')"
                                               oninput="this.setCustomValidity('')"
                                               style="width:300px; margin: 5px 0px;"/> <br />
                                        <br />
                                        <input type="submit" value="Submit"
                                               style="margin: 5px 0px 5px 90px"/>
                                        <input type="reset" value="Reset"
                                               style="margin: 5px 0px 5px 180px"/>

                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <h4 style="color:red">
                                    <%
                                        String login_check = request.getParameter("login_check");
                                        if (login_check != null)
                                        {
                                            out.println("This login is already in database.");
                                            out.println("Please, use another login.");
                                        }
                                    %>
                                    <br />
                                    <%
                                        String number_check = request.getParameter("number_check");
                                        if (number_check != null)
                                        {
                                            out.println("This phone number is already in database.");
                                            out.println("Please, use another number.");
                                        }
                                     %>
                                    </h4>
                                </td>
                            </tr>
                            <tr style="background-color: #ffffff; line-height: 5px;">
                                <td>&#160;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>