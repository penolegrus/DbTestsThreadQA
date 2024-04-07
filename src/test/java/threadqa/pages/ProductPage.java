package threadqa.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Страница с продуктом
 */
public class ProductPage {
    private SelenideElement productIdValue = $x("//label[text()='Product Id:']//following::p[1]");
    private SelenideElement nameValue = $x("//label[text()='Name:']//following::p[1]");
    private SelenideElement priceValue = $x("//label[text()='Price:']//following::p[1]");

    /**
     * Получает айди продукта по индексу БД и конвертирует в Integer
     * @return
     */
    public int getProductId(){
        return Integer.parseInt(productIdValue.getText());
    }

    /**
     * Получает название продукта
     * @return
     */
    public String getName(){
        return nameValue.getText();
    }

    /**
     * Получает цену у продукта и конвертирует в Integer
     * @return
     */
    public int getPrice(){
        return Integer.parseInt(priceValue.getText());
    }
}
