package com.tiza.common.task.impl;

import cn.com.tiza.tstar.datainterface.client.TStarStandardClient;
import cn.com.tiza.tstar.datainterface.client.entity.ClientCmdSendResult;
import cn.com.tiza.tstar.datainterface.service.ServerException;
import com.tiza.common.bean.SendMSG;
import com.tiza.common.cache.ICache;
import com.tiza.common.config.Constant;
import com.tiza.common.task.ITask;
import com.tiza.common.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: MSGSenderTask
 * Author: DIYILIU
 * Update: 2016-03-21 13:55
 */

public class MSGSenderTask implements ITask {

    private static ConcurrentLinkedQueue<SendMSG> msgPool = new ConcurrentLinkedQueue<SendMSG>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ICache waitRespCacheProvider;

    @Resource
    private TStarStandardClient tStarClient;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void execute() {

        while (!msgPool.isEmpty()) {

            SendMSG msg = msgPool.poll();

            String terminalId = msg.getTerminalId();
            int cmd = msg.getCmd();
            byte[] content = msg.getContent();

            logger.info("下发消息，终端[{}], 命令[{}], 原始数据[{}]", terminalId, CommonUtil.toHex(cmd), CommonUtil.bytesToString(content));

            int status;
            try {
                // TStar 指令下发
                ClientCmdSendResult sendResult = tStarClient.cmdSend(msg.getTerminalType(), msg.getTerminalId(),
                        cmd, msg.getSerial(), content, 1);

                // 等待处理指令返回结果
                msg.setSendTime(new Date());
                msg.setSendResult(sendResult);

                // 指令下发成功
                if (sendResult.getIsSuccess()) {
                    status = Constant.SendStatus.SENT;
                    waitRespCacheProvider.put(msg.getSerial(), msg);
                } else {
                    status = Constant.SendStatus.FAIL;
                    int errorCode = sendResult.getErrorCode();
                    logger.error("指令下发失败，错误代码[{}]", errorCode);
                }

                String sql = "UPDATE bs_instructionlog t" +
                        " SET t.sendstatus = ?, t.sendtime = ?" +
                        " WHERE t.id = ?";

                jdbcTemplate.update(sql, new Object[]{status, new Date(), msg.getId()});
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }
    }

    public static void send(SendMSG msg) {

        msgPool.add(msg);
    }
}
