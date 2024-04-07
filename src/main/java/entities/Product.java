package entities;

import lombok.*;

import javax.persistence.*;

/**
 * Сущность из БД для таблицы Продукт
 */
@Entity(name = "product") //название таблицы в бд
@Getter //гетеры для переменных
@Setter //сеттеры для переменных
@NoArgsConstructor //пустой конструктор
@AllArgsConstructor //полный конструктор
@Builder //билдер чтобы создать модель в тестах
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //само генерирует айди
    private Integer id;
    @Version
    private Integer version;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Integer price;
}


