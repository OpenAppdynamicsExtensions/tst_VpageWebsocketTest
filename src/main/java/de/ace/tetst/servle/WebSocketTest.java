package de.ace.tetst.servle;

import com.appdynamics.apm.appagent.api.AgentDelegate;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by stefan.marx on 24.07.17.
 */


@SuppressWarnings("serial")
@WebServlet(name = "My WebSocket Servlet", urlPatterns = { "/wes" })
public class WebSocketTest extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        // set a 10 second timeout
        factory.getPolicy().setIdleTimeout(10000*60);

        // register MyEchoSocket as the WebSocket to create on Upgrade
        factory.register(MyEchoSocket.class);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AgentDelegate.getEndUserMonitoringDelegate().filterStart();
        super.service(request, response);
        AgentDelegate.getEndUserMonitoringDelegate().filterEnd();
    }
}
