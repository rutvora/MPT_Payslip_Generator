//Just HTML responses to be sent to the caller
class HTMLResponses {
    static void error(int i) {
        if (i == 0) {
            paddingTop = "25px";
            display = "inline";
        } else {
            paddingTop = "45px";
            display = "none";
        }
    }

    static String getAdminLogin() {
        return "<html>\n" +
                "\t<head>\n" +
                "\t\t<script type=\"text/javascript\">\n" +
                "\t\t</script>\n" +
                "\t\t<style>\n" +
                "\t\t\t@import url(https://fonts.googleapis.com/css?family=Roboto:300);\n" +
                "\t\t\t.login-page {\n" +
                "\t\t\twidth: 360px;\n" +
                "\t\t\tpadding: 8% 0 0;\n" +
                "\t\t\tmargin: auto;\n" +
                "\t\t\t}\n" +
                "\t\t\t.form h5{\n" +
                "\t\t\t\tcolor: red;\n" +
                "\t\t\t\tbackground-color: white;\n" +
                "\t\t\t\tfloat: left;\n" +
                "\t\t\t\tmargin: 10px;\n" +
                "\t\t\t\tdisplay: " + display + ";\n" +
                "\t\t\t}\n" +
                "\t\t\t.form {\n" +
                "\t\t\tposition: relative;\n" +
                "\t\t\tz-index: 1;\n" +
                "\t\t\tbackground: #FFFFFF;\n" +
                "\t\t\tmax-width: 360px;\n" +
                "\t\t\tmargin: 0 auto 100px;\n" +
                "\t\t\tpadding: 45px;\n" +
                "\t\t\tpadding-top: " + paddingTop + ";\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\tbox-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);\n" +
                "\t\t\t}\n" +
                "\t\t\t.form input {\n" +
                "\t\t\tfont-family: \"Roboto\", sans-serif;\n" +
                "\t\t\toutline: 0;\n" +
                "\t\t\tbackground: #f2f2f2;\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\tborder: 0;\n" +
                "\t\t\tmargin: 0 0 15px;\n" +
                "\t\t\tpadding: 15px;\n" +
                "\t\t\tbox-sizing: border-box;\n" +
                "\t\t\tfont-size: 14px;\n" +
                "\t\t\t}\n" +
                "\t\t\t.form button {\n" +
                "\t\t\tfont-family: \"Roboto\", sans-serif;\n" +
                "\t\t\ttext-transform: uppercase;\n" +
                "\t\t\toutline: 0;\n" +
                "\t\t\tbackground: #1976D2;\n" +
                "\t\t\twidth: 100%;\n" +
                "\t\t\tborder: 0;\n" +
                "\t\t\tpadding: 15px;\n" +
                "\t\t\tcolor: #FFFFFF;\n" +
                "\t\t\tfont-size: 14px;\n" +
                "\t\t\t-webkit-transition: all 0.3 ease;\n" +
                "\t\t\ttransition: all 0.3 ease;\n" +
                "\t\t\tcursor: pointer;\n" +
                "\t\t\t}\n" +
                "\t\t\t.form button:hover,.form button:active,.form button:focus {\n" +
                "\t\t\tbackground: #1565C0;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container {\n" +
                "\t\t\tposition: relative;\n" +
                "\t\t\tz-index: 1;\n" +
                "\t\t\tmax-width: 300px;\n" +
                "\t\t\tmargin: 0 auto;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container:before, .container:after {\n" +
                "\t\t\tcontent: \"\";\n" +
                "\t\t\tdisplay: block;\n" +
                "\t\t\tclear: both;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container .info {\n" +
                "\t\t\tmargin: 50px auto;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container .info h1 {\n" +
                "\t\t\tmargin: 0 0 15px;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t\tfont-size: 36px;\n" +
                "\t\t\tfont-weight: 300;\n" +
                "\t\t\tcolor: #1a1a1a;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container .info span {\n" +
                "\t\t\tcolor: #4d4d4d;\n" +
                "\t\t\tfont-size: 12px;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container .info span a {\n" +
                "\t\t\tcolor: #000000;\n" +
                "\t\t\ttext-decoration: none;\n" +
                "\t\t\t}\n" +
                "\t\t\t.container .info span .fa {\n" +
                "\t\t\tcolor: #EF3B3A;\n" +
                "\t\t\t}\n" +
                "\t\t\tbody {\n" +
                "\t\t\tbackground: #2196F3;\n" +
                "\t\t\tfont-family: \"Roboto\", sans-serif;\n" +
                "\t\t\t-webkit-font-smoothing: antialiased;\n" +
                "\t\t\t-moz-osx-font-smoothing: grayscale;      \n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t\t<title>\n" +
                "\t\t\tAdmin Panel\n" +
                "\t\t</title>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t\t<div class=\"login-page\">\n" +
                "\t\t\t<div id=\"formDiv\" class=\"form\">\n" +
                "\t\t\t\t<h5 id=\"error\">Error logging in, please try again</h5>\n" +
                "\t\t\t\t<form id=\"login\" class=\"login-form\" action=\"https://" +
                MainClass.object.getJSONObject("serverConfig").getString("serverIP") + ":" +
                MainClass.object.getJSONObject("serverConfig").getString("listenerPort") + "/admin\" target=\"_self\" method=\"post\">\n" +
                "\t\t\t\t\t<input name=\"UID\" type=\"text\" placeholder=\"username\"/>\n" +
                "\t\t\t\t\t<input name=\"password\" type=\"password\" placeholder=\"password\"/>\n" +
                "\t\t\t\t\t<button type=\"submit\" form=\"login\">login</button>\n" +
                "\t\t\t\t</form>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";
    }

    static private String paddingTop = "45px";
    static private String display = "none";

    static String submitSuccessful = "<html>\n" +
            "\t<head>\n" +
            "\t\t<title>Config updated</title>\n" +
            "\t</head>\n" +
            "\t<body style=\"background-color: #2196F3;\">\n" +
            "\t\t<h2 style=\"text-align: center;color: #FFFFFF;padding-top: 25px\">Submit Successful</h2>\n" +
            "\t</body>\n" +
            "</html>";

    static String adminPanel = "<html>\n" +
            "\t<head>\n" +
            "\t\t<script>\n" +
            "\t\t\tfunction setSAPVisible(){\n" +
            "\t\t\t\tvar serverConfig = document.getElementById(\"serverConfig\");\n" +
            "\t\t\t \t\t\tvar SAPConfig = document.getElementById(\"SAPConfig\");\n" +
            "\t\t\t \t\t\tserverConfig.style.display = \"none\";\n" +
            "\t\t\t \t\t\tSAPConfig.style.display = \"block\";\n" +
            "\t\t\t \t\t\tdocument.getElementById(\"SAPToggle\").style.backgroundColor = '0288D1';\n" +
            "\t\t\t \t\t\tdocument.getElementById(\"serverToggle\").style.backgroundColor = '#03A9F4';\n" +
            "\t\t\t}\n" +
            "\t\t\tfunction setServerVisible(){\n" +
            "\t\t\t\tvar serverConfig = document.getElementById(\"serverConfig\");\n" +
            "\t\t\t \t\t\tvar SAPConfig = document.getElementById(\"SAPConfig\");\n" +
            "\t\t\t \t\t\tSAPConfig.style.display = \"none\";\t\n" +
            "\t\t\t \t\t\tserverConfig.style.display = \"block\";\n" +
            "\t\t\t \t\t\tdocument.getElementById(\"serverToggle\").style.backgroundColor = '0288D1';\n" +
            "\t\t\t \t\t\tdocument.getElementById(\"SAPToggle\").style.backgroundColor = '#03A9F4';\n" +
            "\t\t\t}\n" +
            "\t\t</script>\n" +
            "\t\t<style>\n" +
            "\t\t\t@import url(https://fonts.googleapis.com/css?family=Roboto:300);\n" +
            "\t\t\t.login-page {\n" +
            "\t\t\twidth: 360px;\n" +
            "\t\t\tpadding: 8% 0 0;\n" +
            "\t\t\tmargin: auto;\n" +
            "\t\t\t}\n" +
            "\t\t\t.serverForm,.SAPForm {\n" +
            "\t\t\tposition: relative;\n" +
            "\t\t\tz-index: 1;\n" +
            "\t\t\tbackground: #FFFFFF;\n" +
            "\t\t\tmax-width: 360px;\n" +
            "\t\t\tmargin: 0 auto 100px;\n" +
            "\t\t\tpadding: 45px;\n" +
            "\t\t\ttext-align: center;\n" +
            "\t\t\tbox-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);\n" +
            "\t\t\t}\n" +
            "\t\t\t.serverForm input,.SAPForm input {\n" +
            "\t\t\tfont-family: \"Roboto\", sans-serif;\n" +
            "\t\t\toutline: 0;\n" +
            "\t\t\tbackground: #f2f2f2;\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\tborder: 0;\n" +
            "\t\t\tmargin: 0 0 15px;\n" +
            "\t\t\tpadding: 15px;\n" +
            "\t\t\tbox-sizing: border-box;\n" +
            "\t\t\tfont-size: 14px;\n" +
            "\t\t\t}\n" +
            "\t\t\t.btn {\n" +
            "\t\t\tfont-family: \"Roboto\", sans-serif;\n" +
            "\t\t\ttext-transform: uppercase;\n" +
            "\t\t\toutline: 0;\n" +
            "\t\t\tbackground: #03A9F4;\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\tborder: 0;\n" +
            "\t\t\tpadding: 15px;\n" +
            "\t\t\tcolor: #FFFFFF;\n" +
            "\t\t\tfont-size: 14px;\n" +
            "\t\t\t-webkit-transition: all 0.3 ease;\n" +
            "\t\t\ttransition: all 0.3 ease;\n" +
            "\t\t\tcursor: pointer;\n" +
            "\t\t\t}\n" +
            "\t\t\t.btn:hover,.btn:active,.btn:focus {\n" +
            "\t\t\tbackground: #0288D1;\n" +
            "\t\t\t}\n" +
            "\t\t\t.serverToggle button {\n" +
            "\t\t\twidth: 50%;\n" +
            "\t\t\tposition: relative;\n" +
            "\t\t\tfloat: left;\t\n" +
            "\t\t\t}\n" +
            "\t\t\t.SAPToggle button {\n" +
            "\t\t\twidth: 50%;\n" +
            "\t\t\tposition: relative;\n" +
            "\t\t\tdisplay: inline-block;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container {\n" +
            "\t\t\tposition: relative;\n" +
            "\t\t\tz-index: 1;\n" +
            "\t\t\tmax-width: 300px;\n" +
            "\t\t\tmargin: 0 auto;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container:before, .container:after {\n" +
            "\t\t\tcontent: \"\";\n" +
            "\t\t\tdisplay: block;\n" +
            "\t\t\tclear: both;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container .info {\n" +
            "\t\t\tmargin: 50px auto;\n" +
            "\t\t\ttext-align: center;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container .info h1 {\n" +
            "\t\t\tmargin: 0 0 15px;\n" +
            "\t\t\tpadding: 0;\n" +
            "\t\t\tfont-size: 36px;\n" +
            "\t\t\tfont-weight: 300;\n" +
            "\t\t\tcolor: #1a1a1a;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container .info span {\n" +
            "\t\t\tcolor: #4d4d4d;\n" +
            "\t\t\tfont-size: 12px;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container .info span a {\n" +
            "\t\t\tcolor: #000000;\n" +
            "\t\t\ttext-decoration: none;\n" +
            "\t\t\t}\n" +
            "\t\t\t.container .info span .fa {\n" +
            "\t\t\tcolor: #EF3B3A;\n" +
            "\t\t\t}\n" +
            "\t\t\tbody {\n" +
            "\t\t\tbackground: #4FC3F7;\n" +
            "\t\t\tfont-family: \"Roboto\", sans-serif;\n" +
            "\t\t\t-webkit-font-smoothing: antialiased;\n" +
            "\t\t\t-moz-osx-font-smoothing: grayscale;      \n" +
            "\t\t\t}\n" +
            "\t\t</style>\n" +
            "\t\t<title>\n" +
            "\t\t\tAdmin Panel\n" +
            "\t\t</title>\n" +
            "\t</head>\n" +
            "\t<body onload=\"setServerVisible()\">\n" +
            "\t\t<div class=\"login-page\">\n" +
            "\t\t\t<div class=\"serverToggle\">\n" +
            "\t\t\t\t<button id=\"serverToggle\" class=\"btn\" onclick=\"setServerVisible()\">Server</button>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div class=\"SAPToggle\">\n" +
            "\t\t\t\t<button id=\"SAPToggle\" class=\"btn\" onclick=\"setSAPVisible()\">SAP</button>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div class=\"serverForm\" id=\"serverConfig\">\n" +
            "\t\t\t\t<form id=\"serverForm\" action=\"successful?serverConfig\" target=\"_self\" method=\"post\">\n" +
            "\t\t\t\t\t<input name=\"serverIP\" type=\"text\" placeholder=\"Host server IP\"/>\n" +
            "\t\t\t\t\t<input name=\"listenerPort\" type=\"number\" placeholder=\"port\"/>\n" +
            "\t\t\t\t\t<input name=\"SSLContext\" type=\"text\" placeholder=\"SSL Context\"/>\n" +
            "\t\t\t\t\t<input name=\"keyStoreType\" type=\"text\" placeholder=\"keyStore Type (e.g. JKS/PKCS12)\"/>\n" +
            "\t\t\t\t\t<input name=\"storePass\" type=\"password\" placeholder=\"storePass\"/>\n" +
            "\t\t\t\t\t<input name=\"keyPass\" type=\"password\" placeholder=\"keyPass (enter null for PKCS12)\"/>\n" +
            "\t\t\t\t\t<input name=\"keystorePath\" type=\"text\" placeholder=\"Absolute path of the keyStore\"/>\n" +
            "\t\t\t\t\t<input name=\"context\" type=\"text\" placeholder=\"Context (e.g. /test)\"/>\n" +
            "\t\t\t\t\t<button class=\"btn\" type=\"submit\" form=\"serverForm\">Save</button>\n" +
            "\t\t\t\t</form>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<div class=\"SAPForm\" id=\"SAPConfig\">\n" +
            "\t\t\t\t<form id=\"SAPForm\" action=\"successful?SAPConfig\" target=\"_self\" method=\"post\">\n" +
            "\t\t\t\t<input name=\"ASHOST\" type=\"text\" placeholder=\"SAP server IP\"/>\n" +
            "\t\t\t\t<input name=\"SYSNR\" type=\"number\" placeholder=\"System Number (for login)\"/>\n" +
            "\t\t\t\t<input name=\"CLIENT\" type=\"number\" placeholder=\"Client number for login\"/>\n" +
            "\t\t\t\t<input name=\"USER\" type=\"text\" placeholder=\"Username\"/>\n" +
            "\t\t\t\t<input name=\"PASSWORD\" type=\"password\" placeholder=\"password\"/>\n" +
            "\t\t\t\t<input name=\"LANG\" type=\"text\" placeholder=\"Language Code\"/>\n" +
            "\t\t\t\t<input name=\"POOL_CAPACITY\" type=\"number\" placeholder=\"Pool Capacity\"/>\n" +
            "\t\t\t\t<input name=\"PEAK_LIMIT\" type=\"number\" placeholder=\"Peak Limit of Pool Capacity\"/>\n" +
            "\t\t\t\t<input name=\"FunctionModule\" type=\"text\" placeholder=\"Name of Function Module to call\"/>\n" +
            "\t\t\t\t<button class=\"btn\" type=\"submit\" form=\"SAPForm\">Save</button>\n" +
            "\t\t\t</div>\n" +
            "\t\t</div>\n" +
            "\t</body>\n" +
            "</html>";
}
