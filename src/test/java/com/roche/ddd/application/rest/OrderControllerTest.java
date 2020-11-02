package com.roche.ddd.application.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.ddd.application.request.AddProductRequest;
import com.roche.ddd.application.request.CreateOrderRequest;
import com.roche.ddd.application.response.CreateOrderResponse;
import com.roche.ddd.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class OrderControllerTest {
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    protected ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_create_one_order() throws Exception {
        deleteAll();

        Product product1 = createProduct("product1");

        CreateOrderRequest request = new CreateOrderRequest(product1, "me@gmail.com");

        MvcResult mvcResult = this.mockMvc.perform(
            post("/orders")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();

        String contentResponse = mvcResult.getResponse().getContentAsString();

        CreateOrderResponse createOrderResponse = objectMapper.readValue(contentResponse, CreateOrderResponse.class);

        UUID id = createOrderResponse.getId();
        log.info("ORDERID = " + id);

        // add a product to the order
        Product product2 = createProduct("product2");

        log.info("product2 : {}", product2);

        AddProductRequest addProductRequest = new AddProductRequest(product2);
        log.info("addProductRequest : {}", addProductRequest);

        mvcResult = this.mockMvc.perform(
            post("/orders/"+id+"/products")
                .content(objectMapper.writeValueAsString(addProductRequest))
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();


        // TODO retrieve order in a data range
//        mvcResult = this.mockMvc.perform(
//            get("/orders/"+id+"/products")
//                .content(objectMapper.writeValueAsString(addProductRequest))
//                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
//                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
//            .andExpect(status().isOk()).andReturn();

    }

    protected Product createProduct(String sku) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
            post("/products/add")
                .param("name", "diego")
                .param("sku", sku)
                .param("price", "1.00")
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();
        String contentResponse = mvcResult.getResponse().getContentAsString();
        log.info("############ contentResponse : {}", contentResponse);
        Product product = objectMapper.readValue(contentResponse, Product.class);
        return product;
    }


    protected void deleteAll() throws Exception {
        String contentResponse = listProducts();
        final List<Product> products = objectMapper.readValue(contentResponse, new TypeReference<List<Product>>() {
        });
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            deleteProduct(product.getSku());
        }
    }

    protected void deleteProduct(String sku) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
            delete("/products/delete/"+sku)
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();
    }


    private String listProducts() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
            get("/products/")
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();

        String contentResponse = mvcResult.getResponse().getContentAsString();
        log.info("contentResponse = " + contentResponse);
        return contentResponse;
    }
}