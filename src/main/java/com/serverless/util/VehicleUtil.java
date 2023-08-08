package com.serverless.util;

import com.serverless.dao.Vehicle;
import com.serverless.dto.VehicleDTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class VehicleUtil {

    public VehicleUtil() {
        super();
    }

    public Vehicle createVehicleDAO(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        if (vehicleDTO != null) {
            vehicle.setName(vehicleDTO.getName());
            vehicle.setCc(vehicleDTO.getCc());
            vehicle.setType(vehicleDTO.getType());
            vehicle.setTyreBrand(vehicleDTO.getTyreBrand());
        }
        return vehicle;
    }

    public <T> void updateClassField(String fieldName, String value, T object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getName().equals(fieldName)) {
                field.set(object, value);
                break;
            }
        }
    }
}
