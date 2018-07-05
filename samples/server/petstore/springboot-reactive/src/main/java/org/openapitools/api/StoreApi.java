/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (3.1.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package org.openapitools.api;

import java.util.Map;
import org.openapitools.model.Order;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Validated
@Api(value = "store", description = "the store API")
public interface StoreApi {

    @ApiOperation(value = "Delete purchase order by ID", nickname = "deleteOrder", notes = "For valid response try integer IDs with value < 1000. Anything above 1000 or nonintegers will generate API errors", tags={ "store", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Order not found") })
    @RequestMapping(value = "/store/order/{order_id}",
        method = RequestMethod.DELETE)
    default ResponseEntity<Mono<Void>> deleteOrder(@ApiParam(value = "ID of the order that needs to be deleted",required=true) @PathVariable("order_id") String orderId, ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(result);

    }


    @ApiOperation(value = "Returns pet inventories by status", nickname = "getInventory", notes = "Returns a map of status codes to quantities", response = Integer.class, responseContainer = "Map", authorizations = {
        @Authorization(value = "api_key")
    }, tags={ "store", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Map.class, responseContainer = "Map") })
    @RequestMapping(value = "/store/inventory",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Mono<Map<String, Integer>>> getInventory(ServerWebExchange exchange) {
        Mono<Map<String, Integer>> result = Mono.empty();
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(result);

    }


    @ApiOperation(value = "Find purchase order by ID", nickname = "getOrderById", notes = "For valid response try integer IDs with value <= 5 or > 10. Other values will generated exceptions", response = Order.class, tags={ "store", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Order.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Order not found") })
    @RequestMapping(value = "/store/order/{order_id}",
        produces = { "application/xml", "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Mono<Order>> getOrderById(@Min(1L) @Max(5L) @ApiParam(value = "ID of pet that needs to be fetched",required=true) @PathVariable("order_id") Long orderId, ServerWebExchange exchange) {
        Mono<Order> result = Mono.empty();
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                result = ApiUtil.getExampleResponse(exchange, "{  \"petId\" : 6,  \"quantity\" : 1,  \"id\" : 0,  \"shipDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"complete\" : false,  \"status\" : \"placed\"}")
                        .then(Mono.empty());
                break;
            }
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                result = ApiUtil.getExampleResponse(exchange, "<Order>  <id>123456789</id>  <petId>123456789</petId>  <quantity>123</quantity>  <shipDate>2000-01-23T04:56:07.000Z</shipDate>  <status>aeiou</status>  <complete>true</complete></Order>")
                        .then(Mono.empty());
                break;
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(result);

    }


    @ApiOperation(value = "Place an order for a pet", nickname = "placeOrder", notes = "", response = Order.class, tags={ "store", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "successful operation", response = Order.class),
        @ApiResponse(code = 400, message = "Invalid Order") })
    @RequestMapping(value = "/store/order",
        produces = { "application/xml", "application/json" }, 
        method = RequestMethod.POST)
    default ResponseEntity<Mono<Order>> placeOrder(@ApiParam(value = "order placed for purchasing the pet" ,required=true )  @Valid @RequestBody Mono<Order> order, ServerWebExchange exchange) {
        Mono<Order> result = Mono.empty();
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                result = ApiUtil.getExampleResponse(exchange, "{  \"petId\" : 6,  \"quantity\" : 1,  \"id\" : 0,  \"shipDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"complete\" : false,  \"status\" : \"placed\"}")
                        .then(Mono.empty());
                break;
            }
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
                result = ApiUtil.getExampleResponse(exchange, "<Order>  <id>123456789</id>  <petId>123456789</petId>  <quantity>123</quantity>  <shipDate>2000-01-23T04:56:07.000Z</shipDate>  <status>aeiou</status>  <complete>true</complete></Order>")
                        .then(Mono.empty());
                break;
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(order.then(result));

    }

}
