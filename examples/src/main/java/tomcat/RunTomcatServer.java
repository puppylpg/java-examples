package tomcat;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.tomcat.util.net.NioEndpoint;

import java.io.File;
import java.io.IOException;

/**
 * @author puppylpg on 2023/09/24
 */
public class RunTomcatServer {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        var endPoint = new NioEndpoint();
        endPoint.setPort(8080);
        var connector = new Connector(new Http11NioProtocol(endPoint));
        tomcat.setConnector(connector);
        var ctx = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(ctx, "Servlet", new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                try (var writer = resp.getWriter()) {
                    writer.println("Hello world! request path=" + req.getPathInfo());
                }
            }

        });
        ctx.addServletMappingDecoded("/*", "Servlet");
        tomcat.start();
        tomcat.getServer().await();
    }
}
