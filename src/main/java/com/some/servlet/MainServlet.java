package com.some.servlet;

import com.some.servlet.message.resp.TextMessage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mqddb on 14-4-1.
 */
public class MainServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //Util.LOG("Hello word111!", resp.getWriter());

        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        //Util.LOG("signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" echostr:"+echostr, resp.getWriter());
        PrintWriter out = resp.getWriter();

        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.close();
        out = null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String respMessage = processRequest(req, resp);
        PrintWriter out = resp.getWriter();
        out.print(respMessage);
        out.close();

    }

    /**
     * ����΢�ŷ���������
     *
     * @param request
     * @return
     */
    private String processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String respMessage = null;
        try {
            // Ĭ�Ϸ��ص��ı���Ϣ����
            String respContent = "�������쳣�����Ժ��ԣ�";

            // xml�������
            Map<String, String> requestMap = MessageUtil.parseXml(request, response);
            String r = "";
            StringBuilder sb = new StringBuilder(r);
            if (Util.DEBUG) {
                Iterator datas = requestMap.entrySet().iterator();
                while (datas.hasNext()) {
                    Map.Entry data = (Map.Entry) datas.next();
                    sb.append("[" + data.getKey() + " ," + data.getValue()+"]\n");
                    //response.getWriter().append("[" + data.getKey() + " ," + data.getValue()+"]\n");
                }
            }

            // ���ͷ��ʺţ�open_id��
            String fromUserName = requestMap.get("FromUserName");
            // �����ʺ�
            String toUserName = requestMap.get("ToUserName");
            // ��Ϣ����
            String msgType = requestMap.get("MsgType");


            // �ظ��ı���Ϣ
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            // �ı���Ϣ
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respContent = "�����͵����ı���Ϣ��\n";
                respContent += sb.toString();
            }
            // ͼƬ��Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "�����͵���ͼƬ��Ϣ��";
            }
            // ����λ����Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "�����͵��ǵ���λ����Ϣ��";
            }
            // ������Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "�����͵���������Ϣ��";
            }
            // ��Ƶ��Ϣ
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "�����͵�����Ƶ��Ϣ��";
            }
            // �¼�����
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // �¼�����
                String eventType = requestMap.get("Event");
                // ����
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "лл���Ĺ�ע��";
                }
                // ȡ������
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
                }
                // �Զ���˵�����¼�
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
                }
            }

            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
            //respMessage = respContent;
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().append("============");
            response.getWriter().append(e.getLocalizedMessage());
        }

        return respMessage;
    }

}
