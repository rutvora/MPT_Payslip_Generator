import ConfigEditor.ConfigEditor;
import com.sap.conn.jco.JCoException;
import com.sun.net.httpserver.*;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.ArrayList;

class SimpleHTTPSServer {

    //Convert a received response from client to JSON (use iff response is expected to be JSON only)
    static private JSONObject responseBodyToJSON(String responseBody) {
        String[] split1 = responseBody.split("&");
        JSONObject object = new JSONObject();
        for (String aSplit1 : split1) {
            String[] temp = aSplit1.split("=");
            object.put(temp[0], StringEscapeUtils.unescapeHtml4(temp[1]).replace("%2F", "/").replace("+", " "));
        }
        return object;
    }

    //Handler to handle app calls for standard data (each call creates an instance)
    private class appHandler implements HttpHandler {


        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            System.out.println("Connection: " + httpExchange.getRemoteAddress().toString());
            HttpsExchange httpsExchange = (HttpsExchange) httpExchange;
            InputStreamReader inputStreamReader = new InputStreamReader(httpsExchange.getRequestBody(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder buffer = new StringBuilder();
            int b;
            while ((b = bufferedReader.read()) != -1) {
                buffer.append((char) b);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String responseBody = buffer.toString();
            String response;
            if (responseBody.length() == 0) {
                httpsExchange.sendResponseHeaders(400, 0);
                return;
            } else {

                JSONObject object = new JSONObject(responseBody);
                try {
                    SAPConnector sapConnector =
                            SAPConnector.execute(object.getString("EMPLOYEENUMBER"), object.getString("MONTH"), object.getString("YEAR"), object.getString("PANCARD"));
                    if (sapConnector != null) {
                        if (sapConnector.getVerify() == 1) response = new String(sapConnector.getJson());
                        else {
                            response = "Verification failed\nDid you enter the correct employee ID and PAN number?";
                            //System.out.println(response);
                            httpsExchange.sendResponseHeaders(400, response.length());
                            return;
                        }
                    } else {
                        response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator";
                        //System.out.println(response);
                        httpsExchange.sendResponseHeaders(500, response.length());
                        return;
                    }

                } catch (JCoException e) {
                    e.printStackTrace();
                    response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator";
                    //System.out.println(response);
                    httpsExchange.sendResponseHeaders(500, response.length());
                    return;
                }
            }
            httpsExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if (response.length() == 0) {
                response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator";
                //System.out.println(response);
                httpsExchange.sendResponseHeaders(500, response.length());
                return;
            }
            httpsExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpsExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    //Handler to handle app calls for pdf data (each call creates an instance)
    private class appHandlerPdf implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Connection: " + httpExchange.getRemoteAddress().toString());
            HttpsExchange httpsExchange = (HttpsExchange) httpExchange;
            InputStreamReader inputStreamReader = new InputStreamReader(httpsExchange.getRequestBody(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder buffer = new StringBuilder();
            int b;
            while ((b = bufferedReader.read()) != -1) {
                buffer.append((char) b);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String responseBody = buffer.toString();
            byte[] response;

            if (responseBody.length() != 0) {
                JSONObject object = new JSONObject(responseBody);
                try {
                    SAPConnector sapConnector = SAPConnector.execute(object.getString("EMPLOYEENUMBER"), object.getString("MONTH"), object.getString("YEAR"), object.getString("PANCARD"));
                    if (sapConnector != null) {
                        if (sapConnector.getVerify() == 1) {
                            ArrayList<String> dummyArray = new ArrayList<>();
                            dummyArray.add("application/pdf");
                            httpsExchange.getResponseHeaders().put("Content-Type", dummyArray);
                            dummyArray = new ArrayList<>();
                            dummyArray.add("attachment; filename=\"zseries.pdf\"");
                            httpsExchange.getResponseHeaders().put("Content-Disposition", dummyArray);
                            response = sapConnector.getPdf();
                        } else {
                            response = "Verification failed\nDid you enter the correct employee ID and PAN number?".getBytes();
                            //System.out.println(response.toString());
                            httpsExchange.sendResponseHeaders(400, response.length);
                            return;
                        }
                    } else {
                        response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator".getBytes();
                        //System.out.println(response.toString());
                        httpsExchange.sendResponseHeaders(500, response.length);
                        return;
                    }
                } catch (JCoException e) {
                    e.printStackTrace();
                    response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator".getBytes();
                    //System.out.println(response.toString());
                    httpsExchange.sendResponseHeaders(500, response.length);
                    return;
                }

                httpsExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                if (response.length == 0) {
                    response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator".getBytes();
                    //System.out.println(response.toString());
                    httpsExchange.sendResponseHeaders(500, response.length);
                    return;
                }
                httpsExchange.sendResponseHeaders(200, response.length);
                OutputStream os = httpsExchange.getResponseBody();
                os.write(response);
                os.close();
            } else {
                httpsExchange.sendResponseHeaders(400, 0);
            }
        }
    }

    //Handler to handle calls for admin configuration (each call creates an instance)
    private class adminHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Connection: " + httpExchange.getRemoteAddress().toString());
            HttpsExchange httpsExchange = (HttpsExchange) httpExchange;
            InputStreamReader inputStreamReader = new InputStreamReader(httpsExchange.getRequestBody(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder buffer = new StringBuilder();
            int b;
            while ((b = bufferedReader.read()) != -1) {
                buffer.append((char) b);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String responseBody = buffer.toString();
            String response;

            if (responseBody.length() == 0) {
                response = HTMLResponses.getAdminLogin();
                HTMLResponses.error(1);
            } else {
                JSONObject object = responseBodyToJSON(responseBody);
                if (object.getString("UID").equals("Rut") && object.getString("password").equals("password")) {
                    response = HTMLResponses.adminPanel;
                } else {
                    HTMLResponses.error(0);
                    response = HTMLResponses.getAdminLogin();
                }
            }

            httpsExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            if (response.equals("")) {
                response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator";
                httpsExchange.sendResponseHeaders(500, response.length());
                return;
            }
            httpsExchange.sendResponseHeaders(200, response.length());
            OutputStream os = httpsExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    //Handler to handle final submission of new config by admin (each call creates an instance)
    private class configChangeHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("Connection: " + httpExchange.getRemoteAddress().toString());
            HttpsExchange httpsExchange = (HttpsExchange) httpExchange;
            InputStreamReader inputStreamReader = new InputStreamReader(httpsExchange.getRequestBody(), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder buffer = new StringBuilder();
            int b;
            while ((b = bufferedReader.read()) != -1) {
                buffer.append((char) b);
            }
            bufferedReader.close();
            inputStreamReader.close();
            String responseBody = buffer.toString();
            String response;
            String rawQuery = httpsExchange.getRequestURI().getRawQuery();

            if (rawQuery.length() != 0 && responseBody.length() != 0) {

                JSONObject object = responseBodyToJSON(responseBody);

                if (rawQuery.equals("serverConfig")) {
                    if (object.getString("keyStoreType").equals("PKCS12")) {
                        object.remove("keyPass");
                        object.put("keyPass", object.getString("storePass"));
                    }
                }

                ConfigEditor configEditor = new ConfigEditor();
                if (configEditor.updateConfig(object, rawQuery)) {
                    System.out.println("Configuration changed: " + rawQuery);
                    response = HTMLResponses.submitSuccessful;
                } else {
                    System.out.println("Error while changing configuration: " + rawQuery);
                    response = "Server has encountered an error, please try again.\nIf problem persists, contact administrator";
                    httpsExchange.sendResponseHeaders(500, response.length());
                    return;
                }

                httpsExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                httpsExchange.sendResponseHeaders(200, response.length());
                OutputStream os = httpsExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                httpsExchange.sendResponseHeaders(400, 0);
            }
        }
    }

    //Initiates the server
    void runServer() {


        try {
            //Get the IP address of the server on which to host
            InetAddress addr = InetAddress.getByName(MainClass.object.getJSONObject("serverConfig").getString("serverIP"));

            //Setup the socket on a specific port
            InetSocketAddress address = new InetSocketAddress(addr, Integer.parseInt(MainClass.object.getJSONObject("serverConfig").getString("listenerPort")));

            // initialise the HTTPS server with default backlog (0)
            HttpsServer httpsServer = HttpsServer.create(address, 0);

            //Get SSLContext for secure transmission of data
            SSLContext sslContext = SSLContext.getInstance(MainClass.object.getJSONObject("serverConfig").getString("SSLContext"));

            //Initialize the keystore
            char[] password = MainClass.object.getJSONObject("serverConfig").getString("storePass").toCharArray();
            KeyStore ks = KeyStore.getInstance(MainClass.object.getJSONObject("serverConfig").getString("keyStoreType"));
            FileInputStream fis = new FileInputStream(MainClass.object.getJSONObject("serverConfig").getString("keystorePath"));
            ks.load(fis, password);

            //Setup KeyManagerFactory (to send out server identification)
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, MainClass.object.getJSONObject("serverConfig").getString("keyPass").toCharArray());

            //Setup TrustManagerFactory (to send out server certificate)
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            //Initialize SSLContext with the given KeyManager/s and TrustManager/s
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            //Set default security parameters for the SSL Handshake and encryption that follows
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL engine
                        SSLEngine engine = sslContext.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // get the default parameters
                        SSLParameters defaultSSLParameters = sslContext.getDefaultSSLParameters();
                        params.setSSLParameters(defaultSSLParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });

            //Create contexts as per the need of the app/client
            httpsServer.createContext(MainClass.object.getJSONObject("serverConfig").getString("context"), new appHandler());
            httpsServer.createContext(MainClass.object.getJSONObject("serverConfig").getString("context") + "/pdf", new appHandlerPdf());
            httpsServer.createContext("/successful", new configChangeHandler());
            httpsServer.createContext("/admin", new adminHandler());

            httpsServer.setExecutor(null); // creates a default executor
            httpsServer.start();        //Starts the server... duh!!
            System.out.println("Server started successfully on port " + MainClass.object.getJSONObject("serverConfig").getString("listenerPort"));

        } catch (Exception exception) {
            System.out.println("Failed to create HTTPS server on port "
                    + MainClass.object.getJSONObject("serverConfig").getString("listenerPort") + " of " + MainClass.object.getJSONObject("serverConfig").getString("serverIP"));
            exception.printStackTrace();

        }
    }

}