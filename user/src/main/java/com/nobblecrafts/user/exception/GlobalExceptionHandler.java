package com.nobblecrafts.user.exception;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

/**
 * Por padrão, o SpringWebFlux tem uma implementação do
 * "DefaultErrorWebExceptionHandler" que é classe filha de
 * "AbstractErrorWebExceptionHandler". Então precisaremos dizer à aplicação que
 * o nosso Handler precisa vir primeiro do "DefaultErrorWebExceptionHandler".
 * Por isso utilizaremos a anotação @Order.
 * 
 * De acordo com o Baeldung.com/spring-order:
 * 
 * "A Anotação @Order define a ordem de classificação de um componente ou bean
 * anotado." "Ele possui um valor como argumento opcional o qual determina a
 * ordem do componente." 
 * 
 * Isso significa que durante a injeção do bean, o
 * componente com o maior valor será inicializado antes.
 * 
 * O "DefaultErrorWebExceptionHandler" possui um valor -1 em sua order, por isso
 * a nossa Order aqui será -2. ou seja, esse handler será o último entre os três
 * a ser inicializado.
 */

/**
 * Com essa classe, nós estamos retornando um Mono de todos os erros em json.
 * Com isso, a aplicação não receberá mais o whitelabel default
 */

@Component
@Order(-2)
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
            ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer) {

        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
    }

    /**
     * Aqui nós precisamos dizer ao spring para qual tipo de protocolo http nós
     * vamos aplicar esse GlobalExceptionHandler. Aqui nós usaremos todos
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    /**
     * Aqui, para pegar os errorAttributes nós estamos usando o
     * ErrorAttributesOptions.defaults(). Mas nós temos a opção de usar por exemplo
     * o ErrorAttributesOptions.of(Include.STACK_TRACE) que vai incluir o trace como
     * parte da response.json
     * 
     * @param request
     * @return
     */

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        Map<String, Object> errorAttributesMap = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);
        return ServerResponse.status(status).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributesMap));
    }

}
