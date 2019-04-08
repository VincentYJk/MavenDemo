package demo.config;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import java.net.InetAddress;

/**
 * ElasticSearch配类
 * @author 梁明辉
 * @date 2019/4/8 9:35
 */
@Configuration
@Slf4j
public class ElasticSearchConfiguration implements FactoryBean<TransportClient>, InitializingBean, DisposableBean {
    /**
     * es集群地址
     */
    @Value("${elasticsearch.ip}")
    private String hostName;
    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;
    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    private TransportClient client;

    @Override
    public void destroy() throws Exception {
        try {
            log.info("Closing elasticSearch client");
            if (client != null) {
                client.close();
            }
        } catch (final Exception e) {
            log.error("Error closing ElasticSearch client: ", e);
        }
    }

    @Override
    public TransportClient getObject() throws Exception {
        return client;
    }

    @Override
    public Class<TransportClient> getObjectType() {
        return TransportClient.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            /* 配置信息
             * 增加嗅探机制，找到ES集群
             * 增加线程池个数，暂时设为5
             */

            Settings esSetting = Settings.builder().put("cluster.name", clusterName).put("client.transport.sniff", true)
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))
                    .build();

            client = new PreBuiltTransportClient(esSetting);
            InetSocketTransportAddress inetSocketTransportAddress = new InetSocketTransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
            client.addTransportAddresses(inetSocketTransportAddress);

        } catch (Exception e) {
            log.error("elasticsearch TransportClient create error!!!", e);
        }
    }
}