package com.tiza.protocol;


import com.tiza.common.bean.Header;

/**
 * Description: IDataProcess
 * Author: DIYILIU
 * Update: 2017-08-04 14:12
 */

public interface IDataProcess {

    Header dealHeader(byte[] bytes);

    void parse(byte[] content, Header header);

    byte[] pack(Header header, Object... argus);

    void init();
}
