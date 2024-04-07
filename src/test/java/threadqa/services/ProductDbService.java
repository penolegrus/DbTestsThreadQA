package threadqa.services;


import entities.Product;
import threadqa.db.EmfBuilder;
import threadqa.db.JpaService;

/**
 * Сервис для взаимодействя с БД только для сущностей, связанных с Продуктом на основе файла persistence
 */
public class ProductDbService extends JpaService {
    public ProductDbService() {
        super(new EmfBuilder()
                .mySql() //выбираем тип бд
                .jdbcUrl("jdbc:mysql://localhost:3306/springbootdb") //ссылка на бд
                .username("root") //логин бд
                .password("") //пароль бд
                .persistenceUnitName("test") //название persistence-unit из xml файла
                .build() // билдим конфиг
                .createEntityManager()); //создаем менеджер
    }

    /**
     * Получает продукт по айди
     * @param id айди продукта
     * @return
     */
    public Product get(int id) {
        return em.find(Product.class, id);
    }

    /**
     * Сохраняет продукт в БД
     * @param product
     */
    public void save(Product product) {
        persist(product);
    }

    /**
     * JPA запросом достает продукт по его имени
     * @param productName название продукта
     * @return
     */
    public Product getProductByName(String productName) {
        return em.createQuery(
                        "from product where name=:productName", Product.class)
                .setParameter("productName", productName)
                .getSingleResult();
    }

    /**
     * Обновляет имя у продукта
     * @param id айди продукта, у которого надо обновить
     * @param newName новое имя
     */
    public void updateProductName(int id, String newName) {
        Product product = get(id);
        product.setName(newName);
        merge(product);
    }

    /**
     * JPA запросом удаляет продукт из БД
     * @param id айди продукта
     */
    public void delete(int id) {
        transaction(x -> x.createQuery("delete from product p where p.id =:id")
                .setParameter("id", id)
                .executeUpdate());
    }
}
