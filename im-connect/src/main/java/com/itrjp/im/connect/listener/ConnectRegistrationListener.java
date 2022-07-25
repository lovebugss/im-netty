//package com.itrjp.im.connect.listener;
//
//import com.itrjp.im.connect.event.WebSocketInitializedEvent;
//import com.itrjp.im.connect.websocket.WebSocketProperties;
//import org.springframework.cloud.consul.serviceregistry.ConsulAutoServiceRegistration;
//import org.springframework.context.ApplicationEvent;
//import org.springframework.context.event.SmartApplicationListener;
//import org.springframework.stereotype.Component;
//
///**
// * TODO
// *
// * @author <a href="mailto:r979668507@gmail.com">renjp</a>
// * @date 2022/7/24 12:23
// */
////@Component
//public class ConnectRegistrationListener implements SmartApplicationListener {
//    private final ConsulAutoServiceRegistration autoServiceRegistration;
//
//    public ConnectRegistrationListener(ConsulAutoServiceRegistration autoServiceRegistration) {
//        this.autoServiceRegistration = autoServiceRegistration;
//    }
//
//    @Override
//    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
//        System.out.println("eventType: " + eventType);
//        System.out.println("autoServiceRegistration:" + autoServiceRegistration);
//        return true;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationEvent event) {
//
//        if (event instanceof WebSocketInitializedEvent webSocketInitializedEvent) {
//            WebSocketProperties webSocketProperties = webSocketInitializedEvent.getWebSocketProperties();
//            autoServiceRegistration.start();
//        }
//    }
//}
