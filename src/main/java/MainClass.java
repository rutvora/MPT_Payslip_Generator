import ConfigEditor.ConfigEditor;
import Encryption.AESAlgorithm;
import com.sap.conn.jco.JCoException;
import org.json.JSONObject;

import java.io.File;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainClass {

    //Encrypted Configuration file
    private static File conf = new File("/home/bits/default.conf");

    //Stores decrypted configuration data
    static JSONObject object;

    static {
        try {

            //Decrypt the configuration file
            String json = new AESAlgorithm().decryptFromFile(conf, new File("/home/bits/key"));
            if (json != null) {
                object = new JSONObject(json);
            }
        } catch (Exception e) {
            System.out.println("Error reading configuration file\nSetting up default admin panel on localnet.....(port 8080)\n");
        }
    }


    public static void main(String args[]) {


        //If unable to read the conf, fallback to default conf.
        if (object == null) {

            String ip;

            try (final DatagramSocket socket = new DatagramSocket()) {
                socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
                ip = socket.getLocalAddress().getHostAddress();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            JSONObject serverConfig = new JSONObject();
            //TODO: Setup default server here
            if (!(new ConfigEditor().updateConfig(serverConfig, "serverConfig"))) {
                System.out.println("Fatal Error.... Exiting");
                return;
            }
            System.out.println("Setup complete\nPlease go to https://" + ip + ":8080/admin to setup SAP and other network configurations\n");

            try {

                //Decrypt the configuration file
                String json = new AESAlgorithm().decryptFromFile(conf, new File("/home/bits/key"));
                if (json != null) {
                    object = new JSONObject(json);
                }
            } catch (Exception e) {
                System.out.println("Fatal Error");
            }

        }

        //Set SAP Environment variables
        try {
            SAPConnector.setEnvironment();
        } catch (JCoException e) {
            e.printStackTrace();
        }

        //Run server
        SimpleHTTPSServer simpleHTTPSServer = new SimpleHTTPSServer();
        simpleHTTPSServer.runServer();

    }

}
