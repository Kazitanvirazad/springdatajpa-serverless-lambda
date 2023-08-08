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

public class AddVehicle implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(PersistentConfig.class,
            AppConfig.class);

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        String requestBody = String.valueOf(event.get("body"));
        Validate validate = applicationContext.getBean(Validate.class);
        VehicleService vehicleService = applicationContext.getBean(VehicleService.class);
        String errorMessage = "";
        if (validate.isValidString(requestBody)) {
            VehicleDTO inputVehicleDTO = SerializeUtil.deserialize(requestBody, VehicleDTO.class);
            if (validate.validateVehicleRequestBody(inputVehicleDTO)) {
                String vehicleName = inputVehicleDTO.getName();
                if (!vehicleService.isVehicleExist(vehicleName)) {
                    vehicleService.addVehicle(inputVehicleDTO);
                    return new ApiGatewayResponse.Builder().setStatusCode(200).setHeaderContentTypeToJSON()
                            .setObjectBody(ResponseObject.builder()
                                    .setError(false).setData(vehicleService.getVehicleById(vehicleName))
                                    .build()).build();
                } else
                    errorMessage = "Vehicle with name " + vehicleName + " already exists";
            } else
                errorMessage = "Vehicle name field is mandatory!";
        } else
            errorMessage = "Request body in not valid";
        return new ApiGatewayResponse.Builder().setStatusCode(404).setHeaderContentTypeToJSON()
                .setObjectBody(ResponseObject.builder().setError(true).setMessage(errorMessage)
                        .build()).build();
    }
}
