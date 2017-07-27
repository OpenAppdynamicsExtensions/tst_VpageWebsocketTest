package de.ace.tetst.servle;

import com.appdynamics.apm.appagent.api.AgentDelegate;
import de.ace.tetst.servle.data.ConnectMessage;
import de.ace.tetst.servle.data.MSGCommand;
import de.ace.tetst.servle.data.MyMessage;
import de.ace.tetst.servle.util.JSONUtil;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by stefan.marx on 24.07.17.
 */
@WebSocket(maxTextMessageSize = 64*1024)
public class MyEchoSocket {
    private final CountDownLatch closeLatch;
    @SuppressWarnings("unused")
    private Session session;
    private Map<UUID, Session> _sessionsTable = new Hashtable();




    public MyEchoSocket()
    {
        this.closeLatch = new CountDownLatch(1);
    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException
    {
        return this.closeLatch.await(duration,unit);
    }

    @OnWebSocketClose
    public void onClose(Session session,int statusCode, String reason)
    {

        System.out.printf("Connection closed: %d - %s%n",statusCode,reason);

        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session)
    {
        System.out.printf("Got connect: %s%n",session);
        this.session = session;
        try
        {
            Future<Void> fut;

            UUID sessionUUID = UUID.randomUUID();
            _sessionsTable.put(sessionUUID,session);

            MyMessage msg  = new ConnectMessage(sessionUUID,"connected");
            String externalId = AgentDelegate.getTransactionDemarcator().beginExternalCall("TESTCALL","TESTCALL",true);

            msg.setComment(externalId);
            AgentDelegate.getTransactionDemarcator().endExternalCall(false,"");

            fut = session.getRemote().sendStringByFuture(JSONUtil.encodeObject(msg));
            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.
            System.out.println("Connection established :"+msg.dump());




        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }


    @OnWebSocketMessage
    public void onMessage2(Session session, String msg) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {


        AgentDelegate.getEndUserMonitoringDelegate().filterStart();


        onMessage(session,msg);
        Thread.dumpStack();
//        System.out.printf("OUTSIDE:\n"+AgentDelegate.getEndUserMonitoringDelegate()
//                .getFooter()+"\n\n--\n\n"+AgentDelegate.getEndUserMonitoringDelegate().getFooter()+"\n--");

        AgentDelegate.getEndUserMonitoringDelegate().filterEnd();

    }

    public void onMessage(Session session, String msg)
    {
//        AgentDelegate.getEndUserMonitoringDelegate().filterStart();

        try {
            MSGCommand cmd = JSONUtil.decodeObject(msg, MSGCommand.class);
            System.out.printf("Got msg: %s%n",cmd.dump());

            if ("echo".equals(cmd.getCmd())) {
                MSGCommand   cmd2 = new MSGCommand();
                cmd2.setCmd("reply");

                // Simulate Workload
                Thread.sleep(1000);

                cmd2.setPayload("Hello "+cmd.getPayload().toLowerCase());
                cmd2.setCmdId(cmd.getCmdId());
                cmd2.setAdrumData(wrapAdrum(AgentDelegate.getEndUserMonitoringDelegate()
                        .getFooter()));





                Future<Void> fut = session.getRemote().sendStringByFuture(JSONUtil.encodeObject(cmd2));
                fut.get(2,TimeUnit.SECONDS); // wait for send to complete.

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {

//            AgentDelegate.getEndUserMonitoringDelegate().filterEnd();

        }

    }

    private List<String> wrapAdrum(String footer) {
        /* need to remove the html coding from
          <script type='text/javascript'>
            //<![CDATA[
            if (window.ADRUM) { ADRUM.footerMetadataChunks = ["g%3A4dc431f0-c0c3-4b04-a120-3c17a85f2698","n%3Acustomer1_7ed95886-faaa-4cc2-83cb-10212a6c852d","i%3A150","e%3A1010","d%3A1005"]; }
            //]]>
            </script>
        */
        if (footer == null) return null;
        else {
            footer = footer.replaceAll("\n","").replaceAll("^[^\\\"]+(.*)\\];.*","$1")
            .replaceAll("\"","");
            return Arrays.asList((String[]) footer.split(","));
        }
    }


    private static Object invokeMethodOnClass(Class<?> clazz, String methodName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //If java agent is not added to startup, then object will be null
        if (clazz == null)
            return null;
        Method method = clazz.getMethod(methodName, new Class[] {});
        method.setAccessible(true);
        return method.invoke(clazz);
    }

    private static Object invokeMethodOnObject(Object object, String methodName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //If java agent is not added to startup, then object will be null
        if (object == null)
            return null;
        Method method = object.getClass().getMethod(methodName, new Class[] {});
        method.setAccessible(true);
        return method.invoke(object);
    }


}
