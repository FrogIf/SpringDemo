package sch.frog.learn.spring.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class FrogDiscoveryClient implements DiscoveryClient {

    private static final Logger log = LoggerFactory.getLogger(FrogDiscoveryClient.class);

    private final Map<String, ServerInfo> registryServer = new HashMap<>();

    private void loadRegistryServer(){
        if(registryServer.isEmpty()){
            try(
                    InputStream inputStream = FrogServerList.class.getClassLoader().getResourceAsStream("serverlist.txt");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            ){
                String line;
                while ((line = reader.readLine()) != null){
                    ServerInfo serverInfo = ServerInfo.parse(line);
                    registryServer.put(serverInfo.getServerId(), serverInfo);
                }
            }catch (IOException e){
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public String description() {
        return "Frog Discovery Client";
    }

    @Override
    public List<ServiceInstance> getInstances(String serviceId) {
        log.info("frog discovery find {}", serviceId);
        loadRegistryServer();
        if(!registryServer.containsKey(serviceId)){
            return Collections.emptyList();
        }
        ServerInfo serverInfo = registryServer.get(serviceId);
        return Collections.singletonList(new DefaultServiceInstance(serverInfo.getAddr(), serviceId, serverInfo.getHost(), serverInfo.getPort(), false));
    }

    @Override
    public List<String> getServices() {
        loadRegistryServer();
        return new ArrayList<>(registryServer.keySet());
    }
}
