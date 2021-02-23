package com.nobblecrafts.zanalyticsclient.exception;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;

/**
 * Nessa classe nós estamos customizando todos as respostas aos erros cujo
 * Throwble seja do tipo ResponseStatusException
 */

@Component
public class CustomAttributes extends DefaultErrorAttributes {
    
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

        Map<String, Object> errorAttributesMap = super.getErrorAttributes(request, options);

        Throwable throwable = getError(request);
        if(throwable instanceof ResponseStatusException) {
            ResponseStatusException res = (ResponseStatusException) throwable;
            errorAttributesMap.put("message", res.getMessage());
            errorAttributesMap.put("devMessage", "A ResponseStatusException Happened");
        }

        /**
         * Esse super.getError attributes é o método que irá ser chamado sempre que uma excessão ocorrer
         */
        return errorAttributesMap;
    }
}
