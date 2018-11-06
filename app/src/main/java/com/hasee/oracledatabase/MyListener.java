package com.hasee.oracledatabase;

import net.sf.json.JSONObject;

public interface MyListener {
    void sendMessageToServer(String message);
    void sendMessage(JSONObject jsonObject);
}
