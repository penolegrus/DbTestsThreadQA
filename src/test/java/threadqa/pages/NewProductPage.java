package threadqa.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница с добавлением нового продукта
 */
public class NewProductPage {
    private SelenideElement productIdField = $(byName("productId"));
    private SelenideElement nameField = $(byName("name"));
    private SelenideElement priceField = $(byName("price"));
    private SelenideElement submitBtn = $x("//button[@type='submit']");

    /**
     * Устанавливает айди продукта не по индексу из БД
     * @param id
     * @return
     */
    public NewProductPage setProductId(String id){
        productIdField.setValue(id);
        return this;
    }

    /**
     * Устанавливает название продукта
     * @param name название продукта
     * @return
     */
    public NewProductPage setName(String name){
        nameField.setValue(name);
        return this;
    }

    /**
     * Устанавливает цену продукту
     * @param price цена продукта
     * @return
     */
    public NewProductPage setPrice(int price){
        priceField.setValue(String.valueOf(price));
        return this;
    }

    /**
     * Нажимает кнопку Submit
     * @return
     */
    public ProductPage submit(){
        submitBtn.click();
        return page(ProductPage.class);
    }
}
