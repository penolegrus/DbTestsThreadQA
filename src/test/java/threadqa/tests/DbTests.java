package threadqa.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import threadqa.db.JpaExtension;
import threadqa.db.entities.Product;
import threadqa.services.ProductDbService;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

@ExtendWith(JpaExtension.class)
public class DbTests {
    private final ProductDbService productDbService = new ProductDbService();
    private final Random random = new Random();

    private Product createRandomProduct() {
        String name = "randomName " + Math.abs(random.nextInt(10000));
        return Product.builder()
                .name(name)
                .price(random.nextInt(500))
                .productId(String.valueOf(random.nextInt(1000)))
                .version(0).build();
    }

    @Test
    public void addProductTest() {
        Product product = createRandomProduct();
        productDbService.save(product);
        Assertions.assertNotNull(productDbService.getProductByName(product.getName()));
    }

    @Test
    public void getProductByIdTest() {
        Product product = productDbService.get(1);
        Assertions.assertEquals("testedited", product.getName());
    }

    @Test
    public void getProductByNameTest() {
        Product product = productDbService.getProductByName("testedited");
        Assertions.assertEquals("testedited", product.getName());
    }

    @Test
    public void updateProductTest() {
        String expectedName = "new name" + random.nextInt(555);

        Product product = createRandomProduct();
        productDbService.save(product);

        Product productFromDb = productDbService.getProductByName(product.getName());
        Assertions.assertNotNull(productFromDb);

        productDbService.updateProductName(productFromDb.getId(), expectedName);
        productFromDb = productDbService.getProductByName(product.getName());

        Assertions.assertEquals(productFromDb.getName(), expectedName);
    }
    @Test
    public void deleteTest() {
        Product product = createRandomProduct();
        productDbService.save(product);

        Product productFromDb = productDbService.get(product.getId());
        Assertions.assertNotNull(productFromDb);

        productDbService.delete(product.getId());

        Assertions.assertThrows(EntityNotFoundException.class, () -> productDbService.refresh(product));
    }
}
