package com.core.zookeeper.zkclient.master;

import com.core.zookeeper.zkclient.ZkClientUtils;
import org.I0Itec.zkclient.ZkClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Master 选择实例
 * @author jokin
 */
public class LeaderSelectorZkClient {
    /**
     * 启动的服务个数
     * */
    private static final int CLIENT_QTY = 10;

    public static void main(String[] args) throws Exception{
        //保存所有zkClient的列表
        List<ZkClient> clients = new ArrayList<>();
        //保存所有服务的列表
        List<MasterSelectorService> workServers = new ArrayList<>();

        try{
            for ( int i = 0; i < CLIENT_QTY; ++i ){
                //创建zkClient
                ZkClient client = ZkClientUtils.getInstanceSer();
                clients.add(client);
                //创建serverData
                RunningData runningData = new RunningData();
                runningData.setCid(Long.valueOf(i));

                runningData.setName("Client Server #" + i);
                //创建服务
                MasterSelectorService  workServer = new MasterSelectorService(runningData);
                workServer.setZkClient(client);
                workServers.add(workServer);
                workServer.start();
            }
            System.out.println("敲回车键退出！\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }finally{
            System.out.println("Shutting down...");
            for ( MasterSelectorService workServer : workServers ){
                try {
                    workServer.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for ( ZkClient client : clients ){
                try {
                    client.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
