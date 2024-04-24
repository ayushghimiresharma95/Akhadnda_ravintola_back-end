package fi.ayush.productservice.Repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import fi.ayush.productservice.model.Product; ;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findByName(String name);
}
