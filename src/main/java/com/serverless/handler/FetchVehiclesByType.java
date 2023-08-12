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

public class FetchVehiclesByType implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistentConfig.class,
            AppConfig.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        VehicleService vehicleService = applicationContext.getBean(VehicleService.class);
        Validate validate = applicationContext.getBean(Validate.class);
        Map<String, Object> queryStringParameters = (Map<String, Object>) event.get("queryStringParameters");
        if (queryStringParameters != null && !queryStringParameters.isEmpty()
                && validate.isValidString((String) queryStringParameters.get("vehicletype"))) {
            String vehicleType = String.valueOf(queryStringParameters.get("vehicletype"));
            ResponseObject responseObject = vehicleService.getVehiclesByType(vehicleType);
            return new ApiGatewayResponse.Builder().setStatusCode(responseObject.isError() ? 404 : 200)
                    .setHeaderContentTypeToJSON()
                    .setObjectBody(responseObject)
                    .build();
        }
        return new ApiGatewayResponse.Builder().setStatusCode(404)
                .setHeaderContentTypeToJSON()
                .setObjectBody(ResponseObject.builder().setError(true)
                        .setMessage("Invalid query paramater!").build())
                .build();
    }
}
