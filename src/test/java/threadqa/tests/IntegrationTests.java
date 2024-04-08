package threadqa.tests;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import threadqa.db.JpaExtension;
import threadqa.db.entities.Product;
import threadqa.pages.MainPage;
import threadqa.pages.ProductPage;
import threadqa.pages.ProductsPage;
import threadqa.services.ProductDbService;

import javax.persistence.EntityNotFoundException;
import java.util.Random;

@ExtendWith(JpaExtension.class)
public class IntegrationTests {
    private static MainPage mainPage;
    private final ProductDbService productDbService = new ProductDbService();
    private final Random random = new Random();

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
        mainPage = new MainPage();
    }

    @BeforeEach
    public void open() {
        Selenide.open("http://127.0.0.1:8080/");
    }

    @AfterEach
    public void close() {
        Selenide.closeWebDriver();
    }

    private Product createRandomProduct() {
        String name = "randomName " + Math.abs(random.nextInt(10000));
        return Product.builder()
                .name(name)
                .price(random.nextInt(500))
                .productId(String.valueOf(random.nextInt(1000)))
                .version(0).build();
    }

    @Test
    public void integrationAddProductTest() {
        Product product = createRandomProduct();
        mainPage.openCreateNewProduct()
                .setProductId(product.getProductId())
                .setName(product.getName())
                .setPrice(product.getPrice())
                .submit();

        Assertions.assertDoesNotThrow(() -> {
            productDbService.getProductByName(product.getName());
        });
    }

    @Test
    public void integrationDeleteProductTest() {
        ProductsPage productsPage = mainPage.openProducts();
        int lastIdInTable = productsPage.getLastProductId();

        Assertions.assertDoesNotThrow(() -> productDbService.get(lastIdInTable));

        productsPage.deleteProductById(lastIdInTable);

        Product productFromDb = productDbService.get(lastIdInTable);

        Assertions.assertThrows(EntityNotFoundException.class, () -> productDbService.refresh(productFromDb));
    }

    @Test
    public void integrationUpdateProductTest() {
        String expectedName = "new name" + random.nextInt(555);

        ProductsPage productsPage = mainPage.openProducts();
        int lastIdInTable = productsPage.getLastProductId();

        Assertions.assertDoesNotThrow(() -> productDbService.get(lastIdInTable));

        Product productFromDb = productDbService.get(lastIdInTable);

        productsPage.editProductById(lastIdInTable)
                .setName(expectedName)
                .submit();

        productDbService.refresh(productFromDb);
        productFromDb = productDbService.get(lastIdInTable);

        Assertions.assertEquals(productFromDb.getName(), expectedName);
    }

    @Test
    public void integrationGetProductTest() {
        Product product = createRandomProduct();
        productDbService.save(product);

        ProductPage productPage = mainPage.openProducts()
                .getProductById(product.getId());

        Assertions.assertEquals(product.getName(), productPage.getName());
        Assertions.assertEquals(product.getId(), productPage.getProductId());
        Assertions.assertEquals(product.getPrice(), productPage.getPrice());
    }
}
