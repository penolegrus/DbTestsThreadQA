package repositories;

import entities.Product;
import org.springframework.data.jpa.repository.*;

/**
 * Репозиторий с продуктами из БД по модели и айди
 */
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
