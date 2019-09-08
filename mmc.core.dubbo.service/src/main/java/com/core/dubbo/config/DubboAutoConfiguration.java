package com.core.dubbo.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.Exporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;

/**
 * 多端口提供dubbo服务
 *     当你使用多端口提供服务，使用默认端口提供服务：需要加入在service上加上defaultProvider
 * @author jokin
 *
 */
@Configuration
@ConditionalOnClass(Exporter.class)
public class DubboAutoConfiguration {

    /**
     * a.@Resource默认是按照名称来装配注入的，只有当找不到与名称匹配的bean才会按照类型来装配注入；
     * b.@Autowired默认是按照类型装配注入的，如果想按照名称来转配注入，则需要结合@Qualifier一起使用；
     * c.@Resource注解是由JDK提供，而@Autowired是由Spring提供
     * */
    @Resource(name="protocolConfig1")
    private ProtocolConfig protocolConfig;

    @Resource(name="protocolConfig2")
    private ProtocolConfig protocolConfig2;

    /**
     * 默认基于dubbo协议提供服务
     *
     * @return
     */
    @Bean(name = "protocolConfig1")
    public ProtocolConfig protocolConfig() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);
        protocolConfig.setThreads(200);
        System.out.println("protocolConfig1的hashCode: " + protocolConfig.hashCode());

        return protocolConfig;
    }

    /**
     * dubbo服务提供
     *
     * @param applicationConfig
     * @param registryConfig
     * @return
     */
    @Bean(name = "providerConfig1")
    @Primary
    public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig) {
        ProviderConfig providerConfig = getProvider(applicationConfig,registryConfig);
        providerConfig.setProtocol(this.protocolConfig);
        return providerConfig;
    }

    /**
     * 默认基于dubbo协议提供服务
     *
     * @return
     */
    @Bean(name = "protocolConfig2")
    public ProtocolConfig protocolConfig2() {
        // 服务提供者协议配置
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("rmi");
        protocolConfig.setPort(20881);
        protocolConfig.setThreads(200);

        System.out.println("protocolConfig2的hashCode: " + protocolConfig.hashCode());

        return protocolConfig;
    }

    /**
     * dubbo服务提供
     *
     * @param applicationConfig
     * @param registryConfig
     * @return
     */
    @Bean(name = "providerConfig2")
    public ProviderConfig providerConfig2(ApplicationConfig applicationConfig, RegistryConfig registryConfig) {
        ProviderConfig providerConfig = getProvider(applicationConfig,registryConfig);
        providerConfig.setProtocol(protocolConfig2);
        return providerConfig;
    }

    private ProviderConfig getProvider(ApplicationConfig applicationConfig, RegistryConfig registryConfig){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        providerConfig.setRetries(1);
        providerConfig.setDelay(-1);
        providerConfig.setApplication(applicationConfig);
        providerConfig.setRegistry(registryConfig);
        return providerConfig;
    }
}
