package org.karthikps.testautomation.infra;

import org.karthikps.testautomation.infra.pojo.UserData;
import org.karthikps.testautomation.infra.pojo.UserSignupDataAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Storage {
    public static List<UserData> userDataMap = new ArrayList<UserData>();

    public static List<UserSignupDataAPI> userSignupDataAPI = new ArrayList<UserSignupDataAPI>();

    public static Map<String, Object> randomStorageMap = new HashMap<String, Object>();
}
