package dnode.webbit;

import dnode.DNode;
import webbit.WebSocketConnection;
import webbit.WebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class DNodeWebSocketHandler implements WebSocketHandler {
    private final DNode dnode;

    private final Map<WebSocketConnection, WebbitConnection> connections = new HashMap<WebSocketConnection, WebbitConnection>();

    public DNodeWebSocketHandler(DNode dnode) {
        this.dnode = dnode;
    }

    public void onOpen(WebSocketConnection connection) throws Exception {
        WebbitConnection c = getFor(connection);
        dnode.onOpen(c);
    }

    public void onMessage(WebSocketConnection connection, String msg) throws Exception {
        WebbitConnection c = getFor(connection);
        dnode.onMessage(c, msg);
    }

    public void onClose(WebSocketConnection connection) throws Exception {
        connections.remove(connection);
    }

    private WebbitConnection getFor(WebSocketConnection connection) {
        WebbitConnection wc = connections.get(connection);
        if (wc == null) {
            wc = new WebbitConnection(connection);
            connections.put(connection, wc);
        }
        return wc;
    }
}


