package com.example.dataexplorer.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MQTTPublisher implements MqttCallback, DisposableBean, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(MQTTPublisher.class);

    @Autowired
    private DiscoveryClient discoveryClient;
    private MqttClient mqttClient;

    private String broker;
    @Value("${mqtt.client-id}")
    private String clientId;
    @Value("${mqtt.topic}")
    private String topic;
    @Value("${mqtt.qos}")
    private int qos;
    @Value("${mqtt.ssl}")
    private Boolean hasSSL;
    @Value("${mqtt.auto-reconnect}")
    private Boolean autoReconnect;
    @Value("${mqtt.port}")
    private Integer port;
    @Value("${mqtt.use-credentials}")
    private boolean useCredentials;
    @Value("${mqtt.username}")
    private String userName;
    @Value("${mqtt.password}")
    private String password;
    @Value("${mqtt.keep-alive-seconds}")
    private int keepAliveInterval;

    private void config() {
        String brokerUrl = "tcp://" + this.broker + ":" + this.port;
        MemoryPersistence persistence = new MemoryPersistence();
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        try {
            this.mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            this.mqttClient.setCallback(this);
        } catch (MqttException me) {
            logger.error("resason "+ me.getReasonCode());
            logger.error("message "+ me.getMessage());
            logger.error("cause "+ me.getCause());
            me.printStackTrace();
        }
    }

    private void connect() {
        boolean success = false;
        while(!success) {
            try {
                MqttConnectOptions connectionOptions = new MqttConnectOptions();
                connectionOptions.setCleanSession(true);
                connectionOptions.setAutomaticReconnect(autoReconnect); //try to reconnect to server from 1 second after fail up to 2 minutes delay
                if(useCredentials){
                    connectionOptions.setUserName(userName);
                    connectionOptions.setPassword(password.toCharArray());
                }
                connectionOptions.setKeepAliveInterval(keepAliveInterval);
                connectionOptions.setConnectionTimeout(0); //wait until connection successful or fail
                this.mqttClient.connect(connectionOptions);
                success = true;
            } catch (MqttException me) {
                logger.error("resason "+ me.getReasonCode());
                logger.error("message "+ me.getMessage());
                logger.error("cause "+ me.getCause());
                me.printStackTrace();
                logger.info("Reconnection in 30 seconds");
                try{
                    Thread.sleep(30000);
                }
                catch(Exception e) {
                    logger.error("Eccezzione nella thread sleep");
                }
            }
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        logger.error("Connection with broker lost!");
        logger.info("Try to reconnect");
        connect();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }

    @Override
    public void destroy() throws Exception {
        this.mqttClient.disconnect();
        logger.info("Shutting down service, disconnecting from the broker");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.getBrokerInstance(); //uses Discovery client so it must be called after eureka setup
        this.config();
        this.connect();
    }

    private void getBrokerInstance(){
        boolean success = false;
        List<ServiceInstance> instances = null;
        while(!success){
            instances = this.discoveryClient.getInstances("moquette");
            if(instances.size() == 0){
                try{
                    Thread.sleep(2500);
                }
                catch(Exception e){
                    logger.error("Exception in thread sleep");
                }
                logger.info("Impossible to get moquette instance....trying...");
            } else{
                success = true;
            }
        }
        this.broker = instances.get(0).getHost();
    }

    public void resetSniffers() throws MqttException{
        this.mqttClient.publish("commands", "reset".getBytes(), 2, false);
    }

    public void resetSniffersById(String id) throws MqttException{
        this.mqttClient.publish("commands/" + id, "reset".getBytes(), 2, false);
    }
}
