package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.config.AppConfig;
import com.serverless.config.PersistentConfig;
import com.serverless.dto.VehicleDTO;
import com.serverless.service.VehicleService;
import com.serverless.util.ResponseObject;
import com.serverless.util.SerializeUtil;
import com.serverless.util.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class UpdateVehicleById implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistentConfig.class,
            AppConfig.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        String body = event.containsKey("body") && event.get("body") != null ?
                String.valueOf(event.get("body")) : null;
        Validate validate = applicationContext.getBean(Validate.class);
        VehicleService vehicleService = applicationContext.getBean(VehicleService.class);
        if (validate.isValidString(body)) {
            Map<String, Object> requestBody = SerializeUtil.deserialize(body, Map.class);
            if (requestBody != null && requestBody.containsKey("vehicle_name")
                    && requestBody.containsKey("field_name")
                    && requestBody.containsKey("value")
                    && validate.isValidString((String) requestBody.get("vehicle_name"))
                    && validate.isValidString((String) requestBody.get("field_name"))
                    && validate.isValidString((String) requestBody.get("value"))
                    && validate.validateFieldName(VehicleDTO.class, (String) requestBody.get("field_name"))) {
                String vehicleName = (String) requestBody.get("vehicle_name");
                String fieldName = (String) requestBody.get("field_name");
                String value = (String) requestBody.get("value");
                ResponseObject responseObject = null;
                try {
                    responseObject = vehicleService.updateVehicleById(fieldName, value, vehicleName);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                return new ApiGatewayResponse.Builder().setStatusCode(200)
                        .setObjectBody(responseObject).setHeaderContentTypeToJSON().build();
            }
        }
        return new ApiGatewayResponse.Builder().setStatusCode(404)
                .setObjectBody(ResponseObject.builder()
                        .setError(true).setMessage("Invalid Request body JSON!").build())
                .setHeaderContentTypeToJSON().build();
    }
}
