package org.example.websocket;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;

import javax.servlet.ServletException;

/**
 * http://undertow.io/undertow-docs/undertow-docs-1.3.0/#websockets
 *
 * @author Stuart Douglas
 */
public class UndertowServer {

    public static void main(final String[] args) {
        PathHandler path = Handlers.path();


        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(path)
                .build();
        server.start();

        WebSocketDeploymentInfo webSocket = new WebSocketDeploymentInfo();
        webSocket.setBuffers(new DefaultByteBufferPool(true, 100));
        webSocket.addEndpoint(JsrChatServerEndpoint.class);


        final ServletContainer container = ServletContainer.Factory.newInstance();

        DeploymentInfo deploymentInfo = new DeploymentInfo()
                .setClassLoader(UndertowServer.class.getClassLoader())
                .setContextPath("/")
                .addWelcomePage("index.html")
                .setResourceManager(new ClassPathResourceManager(UndertowServer.class.getClassLoader(), UndertowServer.class.getPackage()))
                .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME,webSocket)
                .setDeploymentName("chat.war");

        DeploymentManager manager = container.addDeployment(deploymentInfo);
        manager.deploy();
        try {
            path.addPrefixPath("/", manager.start());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }


    }


}