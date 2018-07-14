import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;
import org.json.JSONObject;

import java.util.Properties;

class SAPConnector {

    //Responses (non-static) for each query sent to the SAP Server
    //Can only be accessed by execute method (and no outside method can change them)
    private byte[] json = null;
    private byte[] pdf = null;
    private int verify = 0;
    private static JCoDestination destination;

    //Stores Destination config of the SAP server
    private static class MyDestinationDataProvider implements DestinationDataProvider {
        private final Properties connectProperties = new Properties();

        private MyDestinationDataProvider() {
            connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, MainClass.object.getJSONObject("SAPConfig").getString("ASHOST"));
            connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, MainClass.object.getJSONObject("SAPConfig").getString("SYSNR"));
            connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, MainClass.object.getJSONObject("SAPConfig").getString("CLIENT"));
            connectProperties.setProperty(DestinationDataProvider.JCO_USER, MainClass.object.getJSONObject("SAPConfig").getString("USER"));
            connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, MainClass.object.getJSONObject("SAPConfig").getString("PASSWORD"));
            connectProperties.setProperty(DestinationDataProvider.JCO_LANG, MainClass.object.getJSONObject("SAPConfig").getString("LANG"));
            connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, MainClass.object.getJSONObject("SAPConfig").getString("POOL_CAPACITY"));
            connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, MainClass.object.getJSONObject("SAPConfig").getString("PEAK_LIMIT"));

        }

        static MyDestinationDataProvider getDestinationProvider() {
            return new MyDestinationDataProvider();
        }

        @Override
        public Properties getDestinationProperties(String s) throws DataProviderException {
            //Because we have only one destination to go to
            //Else use a Map and return from it
            return connectProperties;
        }

        @Override
        public boolean supportsEvents() {
            return false;
        }

        @Override
        public void setDestinationDataEventListener(DestinationDataEventListener destinationDataEventListener) {
            //nothing to do
        }
    }

    static void setEnvironment() throws JCoException {
        //Register the Destination with the SAP environment
        Environment.registerDestinationDataProvider(MyDestinationDataProvider.getDestinationProvider());
        destination = JCoDestinationManager.getDestination("ABAP_AS_WITH_POOL");
    }

    //Make a query call to SAP, fetch data and store in an instance of this class, returning the class
    static SAPConnector execute(String employeeNumber, String month, String year, String pancard) throws JCoException {

        //Create instance of this class to store the returning data
        SAPConnector sapConnector = new SAPConnector();

        //Access an RFC enabled function
        JCoFunction function = destination.getRepository().getFunction(MainClass.object.getJSONObject("SAPConfig").getString("FunctionModule")); //BAPI_GET_PAYROLL_RESULT_LIST


        if (function == null)
            throw new RuntimeException("Function not found in SAP.");
        try {
            //Get import parameters and set values for them before calling the function to run
            JCoParameterList importParams = function.getImportParameterList();
            importParams.setValue("EMPLOYEENUMBER", employeeNumber);
            importParams.setValue("MONTH", month);
            importParams.setValue("YEAR", year);
            importParams.setValue("PANCARD", pancard.toCharArray());
            function.execute(destination);  //Run function with given set of input parameters
        } catch (AbapException e) {
            System.out.println(e.toString());
            return null;
        }

        //Get returning table and iterate over it to get the data
        JCoTable returningTable = function.getTableParameterList().getTable("RETURNINGTABLE");
        if (returningTable.getNumRows() != 0) {
            JSONObject object = new JSONObject();
            for (int i = 0; i < returningTable.getNumRows(); i++) {
                returningTable.setRow(i);
                object.put(returningTable.getString(0).trim(), returningTable.getString(1).trim());
            }

            sapConnector.json = object.toString().getBytes();
            sapConnector.pdf = function.getExportParameterList().getByteArray("PAYSLIP");
            sapConnector.verify = function.getExportParameterList().getInt("VERIFY");
        }

        return sapConnector;

    }

    byte[] getJson() {
        return json;
    }

    byte[] getPdf() {
        return pdf;
    }

    int getVerify() {
        return verify;
    }


}
