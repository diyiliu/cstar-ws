package com.tiza.protocol.m2.cmd;

import com.tiza.common.bean.Header;
import com.tiza.common.bean.M2Header;
import com.tiza.common.util.CommonUtil;
import com.tiza.protocol.m2.M2DataProcess;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description: CMD_07
 * Author: DIYILIU
 * Update: 2016-03-30 11:40
 */

@Service
public class CMD_07 extends M2DataProcess {

    public CMD_07() {
        this.cmd = 0x07;
    }

    @Override
    public byte[] pack(Header header, Object... argus) {
        M2Header m2Header = (M2Header) header;
        Map paramMap = (Map) argus[0];

        int paramId = (int) paramMap.get("id");
        byte[] bytes = CommonUtil.longToBytes(paramId, 2);

        return headerToSendBytes(bytes, cmd, m2Header);
    }
}
