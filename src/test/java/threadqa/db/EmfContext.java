package threadqa.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Контекст для подключения к БД
 */
public enum EmfContext {
    /**
     * Текущий экземпляр, чтобы не создавать их много и использовать Singletone
     */
    INSTANCE;
    /**
     * Логер чтобы отслеживать действия в БД
     */
    private final Logger log = LoggerFactory.getLogger(EmfContext.class);
    /**
     * Конфиги для подключений к разным БД
     */
    private final Map<ConnConfig, EntityManagerFactory> container = new HashMap<>();

    /**
     * Получает существующий или новый экземпляр EntityManagerFactory.
     */
    synchronized EntityManagerFactory get(ConnConfig connConfig) {
        //если уже конфиг есть, то достает его
        if (container.containsKey(connConfig))
            return container.get(connConfig);
        else {
            log.warn("### Init EntityManagerFactory ###");

            //стандартные настройки от hibernate для подключения к БД
            Map<String, String> settings = new HashMap<>();
            settings.put(
                    "hibernate.connection.provider_class",
                    "org.hibernate.hikaricp.internal.HikariCPConnectionProvider"
            );
            settings.put("hibernate.hikari.dataSourceClassName", connConfig.jdbcClass);
            settings.put("hibernate.hikari.maximumPoolSize", "32");
            settings.put("hibernate.hikari.minimumIdle", "0");
            settings.put("hibernate.hikari.idleTimeout", "240000");
            settings.put("hibernate.hikari.maxLifetime", "270000");
            settings.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
            settings.put("hibernate.hikari.dataSource.url", Objects.requireNonNullElseGet(connConfig.jdbcUrl, () -> connConfig.jdbcPrefix + "://" + connConfig.dbHost + ":" + connConfig.dbPort + "/" + connConfig.dbName));
            settings.put("hibernate.hikari.dataSource.user", connConfig.username);
            settings.put("hibernate.hikari.dataSource.password", connConfig.password);
            settings.put("hibernate.dialect", connConfig.dialect);

            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(connConfig.persistenceUnitName, settings);
            container.put(connConfig, entityManagerFactory);
            return entityManagerFactory;
        }
    }

    /**
     * Получает все EntityManagerFactories.
     */
    Collection<EntityManagerFactory> storedEmf() {
        return container.values();
    }
}
