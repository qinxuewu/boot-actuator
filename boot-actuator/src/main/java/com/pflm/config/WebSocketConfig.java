package com.pflm.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * webscoket 配置
 * 通过EnableWebSocketMessageBroker 开启使用STOMP协议来传输基于代理(message broker)的消息,
 * 此时浏览器支持使用@MessageMapping 就像支持@RequestMapping一样。
 * @author qinxuewu
 * @create 18/12/1上午9:19
 * @since 1.0.0
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig  implements WebSocketMessageBrokerConfigurer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 配置消息代理(message broker)
     * @param config
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        logger.debug("************** WebSocket启动OK *************");
        //点对点式增加一个/queue 消息代理
        config.enableSimpleBroker("/topic");
       // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        config.setApplicationDestinationPrefixes("/app");

        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
//         config.setUserDestinationPrefix("/user/");

    }

    /**
     * 注册协议节点,并映射指定的URl
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //注册一个名字为"websocket" 的endpoint,并指定 SockJS协议。   点对点-用
        registry.addEndpoint("/socket").withSockJS();
        // 这一行代码用来注册STOMP协议节点，同时指定使用SockJS协议。
//        registry.addEndpoint("/gs-guide-websocket").setAllowedOrigins("*").withSockJS();

    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration channelRegistration) {
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration channelRegistration) {
    }

}
