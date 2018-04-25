package com.core.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * master选择
 * */
public class MasterSelector {
    @Autowired
    CuratorFramework curatorFramework;

    private final static String MASTER_PATH="/curator_master_path";
    public void demo(){

        LeaderSelector leaderSelector = new LeaderSelector(curatorFramework, MASTER_PATH, new LeaderSelectorListenerAdapter() {
            @Override
            public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
                System.out.println("获得Master成功");
            }
        });

        leaderSelector.autoRequeue();
        leaderSelector.start();
    }
}
