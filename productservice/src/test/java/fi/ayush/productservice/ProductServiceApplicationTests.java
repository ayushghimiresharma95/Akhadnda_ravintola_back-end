package fi.ayush.productservice;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import fi.ayush.productservice.Repository.ProductRepository;
import fi.ayush.productservice.dto.ProductRequest;
import fi.ayush.productservice.dto.ProductResponse;
import fi.ayush.productservice.model.Product;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @Autowired
    private MockMvc mockMvc ;

    @Autowired
    private ObjectMapper objectMapper ;

     @Autowired
     private ProductRepository productRepository ;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    

    @Test
    void createProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productString = objectMapper.writeValueAsString(productRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productString))
                .andExpect(status().isCreated());
        Assertions.assertEquals(1,productRepository.findAll().size()) ;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.length()").value(1));
    } 
    
    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Oppo f50")
                .description("Oppo f50")
                .price(BigDecimal.valueOf(1200))
                .build();
    }
}
