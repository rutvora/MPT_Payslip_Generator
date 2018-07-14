package ConfigEditor;

import Encryption.AESAlgorithm;
import Encryption.KeyStoreUtils;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import java.io.File;

public class ConfigEditor {

    //Update the configuration
    public boolean updateConfig(JSONObject object, String toEdit) {

        //Only configure SAP or server setups
        if (!(toEdit.equals("serverConfig") || toEdit.equals("SAPConfig"))) {
            return false;
        }

        AESAlgorithm aesAlgorithm = new AESAlgorithm();


        File encryptedConf = new File("/home/bits/default.conf");
        File keyFile = new File("/home/bits/key");


        //Define old setup object to copy over remaining data
        JSONObject mainObjOld;

        //Check if the file exists and is not a directory
        if (encryptedConf.exists() && !encryptedConf.isDirectory()) {
            try {
                //Define older object from the current config file
                mainObjOld = new JSONObject(aesAlgorithm.decryptFromFile(encryptedConf, keyFile));
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        } else {

            //If current config doesn't exist or can't be accessed, define these default values
            mainObjOld = new JSONObject();

            JSONObject SAPConfig = new JSONObject();
            //TODO: Setup default config here


            JSONObject serverConfig = new JSONObject();
            //TODO: Setup default config here

            mainObjOld.put("SAPConfig", SAPConfig);
            mainObjOld.put("serverConfig", serverConfig);

        }

        //Define new object and change the required object/s while copying the other data as it is
        JSONObject mainObjNew = new JSONObject();
        mainObjNew.put(toEdit, object);
        if (toEdit.equals("serverConfig")) {
            mainObjNew.put("SAPConfig", mainObjOld.getJSONObject("SAPConfig"));
        } else {
            mainObjNew.put("serverConfig", mainObjOld.getJSONObject("serverConfig"));
        }

        //Delete the old key file and config file
        //Generate new key and encrypt config with it
        try {
            if (keyFile.delete()) {
                SecretKey key = KeyStoreUtils.generateKey();
                KeyStoreUtils.saveKey(key, keyFile);
            }
            if (aesAlgorithm.encrypt(mainObjNew.toString(), keyFile, encryptedConf) == null) {
                System.out.println("error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

