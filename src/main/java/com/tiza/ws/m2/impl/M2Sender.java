package com.tiza.ws.m2.impl;

import com.tiza.common.util.JacksonUtil;
import com.tiza.protocol.m2.M2DataProcess;
import com.tiza.ws.m2.IM2Sender;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.io.IOException;
import java.util.HashMap;

/**
 * Description: M2Sender
 * Author: DIYILIU
 * Update: 2016-03-29 15:54
 */

@WebService
public class M2Sender implements IM2Sender {

    private String address;

    @Resource
    private M2DataProcess m2DataProcess;

    @Override
    public void send(int id, int cmd, String terminalId, String content) {

        Object param = content;
        if (StringUtils.isNotBlank(content)){
            try {
                param = JacksonUtil.toObject(content, HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        m2DataProcess.send(id, cmd, terminalId, param);
    }

    @Override
    public void init() {

        Endpoint.publish(address, this);
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
