package sch.frog.learn.spring.client;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 一个serverList中所有的server 都属于同一个server Id, 用于做load balance
 */
public class FrogServerList implements ServerList<Server> {

    private static final Logger log = LoggerFactory.getLogger(FrogServerList.class);

    public FrogServerList() {
    }

    public FrogServerList(IClientConfig clientConfig) {
        this.serverId = clientConfig.getClientName();
    }

    @Autowired
    private FrogDiscoveryClient frogDiscoveryClient;

    private String serverId;

    @Override
    public List<Server> getInitialListOfServers() {
        return frogDiscoveryClient.getInstances(this.serverId).stream().map(si -> new Server(si.getHost(), si.getPort())).collect(Collectors.toList());
    }

    @Override
    public List<Server> getUpdatedListOfServers() {
        return frogDiscoveryClient.getInstances(this.serverId).stream().map(si -> new Server(si.getHost(), si.getPort())).collect(Collectors.toList());
    }
}
