package threadqa.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.page;

/**
 * Страница с продуктами
 */
public class ProductsPage {
    /**
     * Список с айдишками в таблице
     */
    private ElementsCollection productsList = $$x("//tbody/tr/td[1]");

    /**
     * Получает последнюю айди продукта из таблицы
     * @return
     */
    public int getLastProductId() {
        return Integer.parseInt(productsList.last().getText());
    }

    /**
     * Ищет продукт по айди в таблице и нажимает кнопку View
     * @param id айди продукта
     * @return
     */
    public ProductPage getProductById(int id) {
        productsList.find(Condition.text(String.valueOf(id)))
                .find(By.xpath("./following::a[text()='View']"))
                .click();
        return page(ProductPage.class);
    }

    /**
     * Ищет продукт по айди в таблице и нажимает кнопку Edit
     * @param id айди продукта
     * @return
     */
    public NewProductPage editProductById(int id) {
        productsList.find(Condition.text(String.valueOf(id)))
                .find(By.xpath("./following::a[text()='Edit']"))
                .click();
        return page(NewProductPage.class);
    }

    /**
     * Ищет продукт по айди в таблице и нажимает кнопку Delete
     * @param id айди продукта
     * @return
     */
    public ProductsPage deleteProductById(int id) {
        productsList.find(Condition.text(String.valueOf(id)))
                .find(By.xpath("./following::a[text()='Delete']"))
                .click();
        return this;
    }
}
