package com.serverless.handler;

import java.util.Map;

import com.serverless.ApiGatewayResponse;
import com.serverless.util.ResponseObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class HelloHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> event, Context context) {
        return ApiGatewayResponse.builder().setStatusCode(200)
                .setObjectBody(ResponseObject.builder()
                        .setError(false).setMessage("Hello World!").build())
                .setHeaderContentTypeToJSON().build();
    }
}
