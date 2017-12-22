package com.samyuktatech.amqp;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samyuktatech.mongodb.document.Location;
import com.samyuktatech.mongodb.repository.LocationRepository;
import com.samyuktatech.util.Utility;

@Component
public class AMQPLocationListener {

	@Autowired
	private LocationRepository locationRepository;
	
	ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * Listen to Receive Location from AMQP broker client
	 * 
	 * @param message
	 */
	public void receiveMessage(byte[] message) {
    
		Utility.consoleLog("Received Location <" + new String(message) + ">");
		
		Location location = null;
		
		try {
			location = mapper.readValue(message, Location.class);
			location.setTime(new Date());
		} catch (IOException e) {			
			e.printStackTrace();
		}       
		
		locationRepository.save(location);
    }
}
