package com.example.dataexplorer.controllers;

import com.example.dataexplorer.mqtt.MQTTPublisher;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MqttController {

    private static final Logger logger = LoggerFactory.getLogger(MqttController.class);

    @Autowired
    private MQTTPublisher mqttPublisher;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/reset", method = RequestMethod.GET)
    public void resetAllSniffers(){
        logger.info("Received reset request!");
        try{
            mqttPublisher.resetSniffers();
            logger.info("Reset request completed!");
        }
        catch (MqttException e){
            logger.error("An exception occurred {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error publishing mqtt message");
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @RequestMapping(value= "/reset/{id}", method = RequestMethod.GET)
    public void resetAllSniffers(@PathVariable String id){
        logger.info("Received reset request!");
        try{
            mqttPublisher.resetSniffersById(id);
            logger.info("Reset request completed!");
        }
        catch (MqttException e){
            logger.error("An exception occurred {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error publishing mqtt message");
        }
    }
}
