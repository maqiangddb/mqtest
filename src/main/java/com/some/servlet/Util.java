package com.some.servlet;

import java.io.PrintWriter;

/**
 * Created by mqddb on 14-4-8.
 */
public class Util {
    public static final boolean DEBUG = true;

    public static void LOG(String msg, PrintWriter writer) {

        String content = "<script language=\"JavaScript\">" +
                "console.log('"+msg+"');" +
                "</script>\n";
        writer.append(content);
        //writer.flush();
        //writer.close();
        //writer = null;
    }
}
