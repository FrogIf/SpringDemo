package sch.frog.learn.spring.client;

public class ServerInfo {

    private String serverId;

    private String host;

    private int port = 80;

    private String addr;

    public String getServerId() {
        return serverId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getAddr() {
        return addr;
    }

    public static ServerInfo parse(String infoStr){
        String[] server = infoStr.split(",");
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.serverId = server[0];
        serverInfo.addr = server[1];
        String[] addrInfo = serverInfo.addr.split(":");
        serverInfo.host = addrInfo[0];
        serverInfo.port = Integer.parseInt(addrInfo[1]);
        return serverInfo;
    }
}
