package com.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.serverless.ApiGatewayResponse;
import com.serverless.config.AppConfig;
import com.serverless.config.PersistentConfig;
import com.serverless.dto.VehicleDTO;
import com.serverless.service.VehicleService;
import com.serverless.util.ResponseObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class FetchVehicle implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistentConfig.class,
            AppConfig.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        VehicleService vehicleService = applicationContext.getBean(VehicleService.class);
        List<VehicleDTO> vehicles = vehicleService.getVehicles();
        ResponseObject responseObject = null;
        int statusCode;
        if (!CollectionUtils.isEmpty(vehicles)) {
            responseObject = ResponseObject.builder().setError(false).setData(vehicles).build();
            statusCode = 200;
        } else {
            responseObject = ResponseObject.builder().setError(true).setMessage("Vehicles not found!").build();
            statusCode = 404;
        }

        return new ApiGatewayResponse.Builder().setHeaderContentTypeToJSON().setStatusCode(statusCode).setObjectBody(responseObject).build();
    }
}
