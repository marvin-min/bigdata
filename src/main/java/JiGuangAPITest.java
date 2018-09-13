import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jmessage.api.JMessageClient;
import cn.jmessage.api.common.model.RegisterInfo;
import cn.jmessage.api.common.model.message.MessageBody;
import cn.jmessage.api.common.model.message.MessagePayload;
import cn.jmessage.api.message.MessageListResult;
import cn.jmessage.api.message.MessageResult;
import cn.jmessage.api.message.MessageType;
import cn.jmessage.api.message.SendMessageResult;
import cn.jmessage.api.sensitiveword.SensitiveWordListResult;
import cn.jmessage.api.user.UserInfoResult;
import cn.jmessage.api.user.UserListResult;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by 中旌影视 (c) 2018 Inc.
 * 参考
 * https://docs.jiguang.cn/jmessage/server/rest_api_im/#_61
 *
 * @描述
 * @Author Minxiarong
 * @创建时间 2018/7/30 14:28
 */
public class JiGuangAPITest {
    public static void main(String[] args) throws Exception {
        String appKey = "300b475e58a3dcaf12ad784c";
        String securityKey = "81e189588b32250d0c5bbd59";

        JMessageClient client = new JMessageClient(appKey, securityKey);
        tstSendMessage(client);
//        sensitiveWordDemo(client);


//        getMessages(client);

    }

    /**
     * 获取消息列表
     *
     * @param client
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    private static void getMessages(JMessageClient client) throws APIConnectionException, APIRequestException {
        MessageListResult result = client.getMessageList(100, "2018-07-30 15:00:00", "2018-07-30 16:00:00");
        for (MessageResult msg : result.getMessages()) {
            String name = StringUtils.isBlank(msg.getFromName()) ? msg.getFromId() : msg.getFromName();
            System.out.println(name + ":" + msg.getMsgBody().getText());
        }
    }

    /**
     * 敏感词操作
     * 敏感词无法发送
     *
     * @param client
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    private static void sensitiveWordDemo(JMessageClient client) throws APIConnectionException, APIRequestException {
        client.addSensitiveWords("敏感词1", "敏感词2", "傻逼");

        SensitiveWordListResult wordList = client.getSensitiveWordList(0, 100);

        //更新敏感词
        client.updateSensitiveWord("**", "敏感词1");
    }

    /**
     * 测试法送信息
     * 含有敏感词的无法正确发送
     *
     * @param client
     * @throws APIConnectionException
     * @throws APIRequestException
     */
    private static void tstSendMessage(JMessageClient client) throws APIConnectionException, APIRequestException {
        for (int i = 0; i < 1; i++) {
            String text = "测试";
            if (i % 2 == 0) {
                text += "傻逼-===";
            } else {
                text += "hello";
            }
            MessageBody messageBody = MessageBody.newBuilder().setText(text).build();
            //单个发送
//        client.sendSingleTextByAdmin("user1", "admin", messageBody);
            //获取admin用户
//        UserListResult result = client.getAdminListByAppkey(0,10);

            UserListResult result = client.getUserList(0, 10);
            for (UserInfoResult userInfoResult : result.getUsers()) {
                System.out.println(userInfoResult.getUsername());
            }
            //聊天室的形式发送消息
            //消息只能由admin用户发送出去,
            MessagePayload payload = MessagePayload.newBuilder().setTargetType("chatroom").setVersion(1)
                    .setFromType("admin").setMessageType(MessageType.TEXT)
                    //设置用户显示的名称
                    .setFromName("用户1")
                    .setTargetId("10094576").setFromId("ssss").setMessageBody(messageBody).build();
            SendMessageResult rs = client.sendMessage(payload);
            System.out.println(rs.getMsgCtime() + "---" + rs.getMsg_id());
        }
    }


    private static void registerUser(JMessageClient client) throws Exception {
        //注册用户
        List<RegisterInfo> registerInfos = new ArrayList<>();
        registerInfos.add(RegisterInfo.newBuilder().setNickname("user1").setPassword("123456").
                setUsername("user1").build());
        RegisterInfo infs[] = registerInfos.toArray(new RegisterInfo[0]);
        String res = client.registerUsers(infs);
    }
}
