package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.config.AppConfig;
import com.serverless.config.PersistentConfig;
import com.serverless.service.VehicleService;
import com.serverless.util.ResponseObject;
import com.serverless.util.Validate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

public class FetchVehicleByID implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistentConfig.class,
            AppConfig.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        VehicleService vehicleService = applicationContext.getBean(VehicleService.class);
        Validate validate = applicationContext.getBean(Validate.class);
        Map<String, Object> queryStringParameters = (Map<String, Object>) event.get("queryStringParameters");
        String vehicleName = queryStringParameters.get("vehicle_name") != null ?
                String.valueOf(queryStringParameters.get("vehicle_name")) : null;
        if (validate.isValidString(vehicleName)) {
            if (vehicleService.isVehicleExist(vehicleName)) {
                return new ApiGatewayResponse.Builder().setStatusCode(200).setHeaderContentTypeToJSON()
                        .setObjectBody(ResponseObject.builder()
                                .setError(false).setData(vehicleService.getVehicleById(vehicleName))
                                .build()).build();
            }
            return new ApiGatewayResponse.Builder().setStatusCode(404).setHeaderContentTypeToJSON()
                    .setObjectBody(ResponseObject.builder().setError(true)
                            .setMessage("Vehicle with name " + vehicleName + " not found!")
                            .build()).build();
        }
        return new ApiGatewayResponse.Builder().setStatusCode(404).setHeaderContentTypeToJSON()
                .setObjectBody(ResponseObject.builder().setError(true).setMessage("Query Param input is not valid")
                        .build()).build();
    }
}
