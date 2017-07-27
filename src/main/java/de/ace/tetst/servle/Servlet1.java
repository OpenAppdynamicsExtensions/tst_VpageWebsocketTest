package de.ace.tetst.servle;

import com.appdynamics.apm.appagent.api.AgentDelegate;
import com.appdynamics.apm.appagent.api.IEndUserMonitoringDelegate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by stefan.marx on 22.07.17.
 */
@WebServlet("/test/test1")
public class Servlet1 extends HttpServlet{

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        try {
            Thread.sleep(243);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pw.write("<html><head></head>");
        pw.write ("<script charset='UTF-8'>\n" +
                "window['adrum-start-time'] = new Date().getTime();\n" +
                "(function(config){\n" +
                "    config.appKey = 'AD-AAB-AAE-PUD';\n" +
                "    config.adrumExtUrlHttp = 'http://cdn.appdynamics.com';\n" +
                "    config.adrumExtUrlHttps = 'https://cdn.appdynamics.com';\n" +
                "    config.beaconUrlHttp = 'http://col.eum-appdynamics.com';\n" +
                "    config.beaconUrlHttps = 'https://col.eum-appdynamics.com';\n" +
                "    config.xd = {enable : false};\n" +
                "})(window['adrum-config'] || (window['adrum-config'] = {}));\n" +
                "if ('https:' === document.location.protocol) {\n" +
                "    document.write(unescape('%3Cscript')\n" +
                " + \" src='https://cdn.appdynamics.com/adrum/adrum-4.3.3.0.js' \"\n" +
                " + \" type='text/javascript' charset='UTF-8'\" \n" +
                " + unescape('%3E%3C/script%3E'));\n" +
                "} else {\n" +
                "    document.write(unescape('%3Cscript')\n" +
                " + \" src='http://cdn.appdynamics.com/adrum/adrum-4.3.3.0.js' \"\n" +
                " + \" type='text/javascript' charset='UTF-8'\" \n" +
                " + unescape('%3E%3C/script%3E'));\n" +
                "}\n" +
                "</script>");

        System.out.println("TESTER :");
        IEndUserMonitoringDelegate eum = AgentDelegate.getEndUserMonitoringDelegate();
        System.out.println("Header:"+eum.getHeader());

        String ff = eum.getFooter();
        System.out.println("Footer:"+ff);


        pw.write("<body><h1>Hello World</h1></body>");
        pw.write(ff+" \n</html>");

        pw.flush();
        pw.close();
    }
}
