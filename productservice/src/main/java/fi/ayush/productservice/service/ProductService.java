package fi.ayush.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fi.ayush.productservice.Repository.ProductRepository;
import fi.ayush.productservice.dto.ProductRequest;
import fi.ayush.productservice.dto.ProductResponse;
import fi.ayush.productservice.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository ;
    public void createProduct(ProductRequest productRequest){
        Product product = Product
        .builder()
        .name(productRequest.getName())
        .description(productRequest.getDescription())
        .price(productRequest.getPrice()).build() ;
        productRepository.save(product) ;
        log.info("Product {} is saved",product.getId());
    }
    public List<ProductResponse> getAllProducts(){
        List<Product> products  = productRepository.findAll() ;
        log.info(products.toString());
        
        return products
                .stream().map( product -> mapTpProductResponse(product)).toList() ;

    }
    public ProductResponse mapTpProductResponse(Product product){
        return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .description(product.getDescription())
        .price(product.getPrice()).build() ;
    }
}
