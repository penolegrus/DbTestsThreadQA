package threadqa.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * Главная страница
 */
public class MainPage {
    private SelenideElement productsBtn = $(byText("Продукты"));
    private SelenideElement createNewProductBtn = $(byText("Создать новый продукт"));

    /**
     * Открывает страницу с продуктами
     * @return
     */
    public ProductsPage openProducts(){
        productsBtn.click();
        return page(ProductsPage.class);
    }

    /**
     * Открывает страницу с созданием нового продукта
     * @return
     */
    public NewProductPage openCreateNewProduct(){
        createNewProductBtn.click();
        return page(NewProductPage.class);
    }
}
