package com.serverless.util;

import com.serverless.dto.VehicleDTO;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Validate {
    public Validate() {
        super();
    }

    /**
     * Validates String passed in the method argument with null check and length greater than zero
     *
     * @param str
     * @return true if it passes the validation or false
     */
    public boolean isValidString(String str) {
        boolean isValidString = false;
        if (str != null && !str.isEmpty()) {
            isValidString = true;
        }
        return isValidString;
    }

    public boolean validateVehicleRequestBody(VehicleDTO vehicle) {
        if (vehicle != null && isValidString(vehicle.getName())) {
            return true;
        }
        return false;
    }

    public <T> boolean validateFieldName(Class<T> clazz, String fieldName) {
        boolean flag = false;
        for (Field field : clazz.getDeclaredFields()) {
            if(field.getName().equals(fieldName)){
                flag = true;
                break;
            }
        }
        return flag;
    }
}
