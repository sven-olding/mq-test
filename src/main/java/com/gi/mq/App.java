package com.gi.mq;

import com.ibm.mq.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args) {

        MQQueue queue = null;
        MQMessage msg = new MQMessage();
        msg.messageId = "99820192819829182".getBytes();
        msg.correlationId = "1098212981928190385938".getBytes();

        try {
            msg.write(("<?xml version=\"1.0\"?><test>\n" +
                    "<daten key=\"@UserName>\"Daten\"</daten>\n" +
                    "</test>").getBytes(StandardCharsets.UTF_8));

            MQEnvironment.hostname = "172.16.5.28";
            MQEnvironment.port = 1414;
            MQEnvironment.channel = "Dokumentendruck";
            MQEnvironment.userID = "app";
            MQEnvironment.password = "pass$w0rd";

            MQQueueManager queueManager = new MQQueueManager("QM_SOL");

            int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_OUTPUT;
            queue = queueManager.accessQueue("Test_Dokumentendruck",
                    openOptions);

            MQPutMessageOptions pmo = new MQPutMessageOptions();
            queue.put(msg, pmo);
        } catch (IOException | MQException e) {
            e.printStackTrace();
        }

    }
}
