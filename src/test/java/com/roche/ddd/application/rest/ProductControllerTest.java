package com.roche.ddd.application.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roche.ddd.domain.Product;
import com.roche.ddd.domain.repository.OrderRepository;
import com.roche.ddd.domain.service.DomainOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ProductControllerTest {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    private ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_get_no_product() throws Exception {
        deleteAll();
        String contentResponse = listProducts();
        Assert.assertEquals("[]", contentResponse);
    }

    @Test
    public void should_add_product() throws Exception {

        deleteAll();
        String contentResponse = listProducts();
        Assert.assertEquals("[]", contentResponse);

        createProduct("sandokan");

        contentResponse = listProducts();
        log.info("contentResponse : {}", contentResponse);

        final List<Product> products = objectMapper.readValue(contentResponse, new TypeReference<List<Product>>() {
        });

        Assert.assertEquals(1, products.size());
        Product product = products.get(0);
        Assert.assertEquals("sandokan", product.getSku());

    }

    @Test
    public void should_delete_product() throws Exception {

        deleteAll();
        String contentResponse = listProducts();
        Assert.assertEquals("[]", contentResponse);

        createProduct("maradona");
        MvcResult mvcResult = this.mockMvc.perform(
            delete("/products/delete/maradona")
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();

        contentResponse = listProducts();
        log.info("contentResponse : {}", contentResponse);

        final List<Product> products = objectMapper.readValue(contentResponse, new TypeReference<List<Product>>() {
        });

        Assert.assertEquals(0, products.size());

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

    private void deleteProduct(String sku) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
            delete("/products/delete/"+sku)
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();
    }

    void deleteAll() throws Exception {

        log.info("ProductControllerTest.setUp");

        String contentResponse = listProducts();
        log.info("contentResponse : {}", contentResponse);

        final List<Product> products = objectMapper.readValue(contentResponse, new TypeReference<List<Product>>() {
        });

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            deleteProduct(product.getSku());
        }
    }

    private void createProduct(String sku) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(
            post("/products/add")
                .param("name", "diego")
                .param("sku", sku)
                .param("price", "1.00")
                .contentType(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8))
                .accept(MediaType.parseMediaType(APPLICATION_JSON_CHARSET_UTF_8)))
            .andExpect(status().isOk()).andReturn();
    }



}