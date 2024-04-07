package threadqa.db;

import javax.persistence.EntityManagerFactory;

/**
 * Билдер для EMF
 */
public class EmfBuilder {
    private final ConnConfig connConfig = new ConnConfig();

    /**
     * Заготовка для БД postgres
     * @return
     */
    public EmfBuilder postgres() {
        connConfig.jdbcClass = "org.postgresql.ds.PGSimpleDataSource";
        connConfig.jdbcPrefix = "jdbc:postgresql";
        connConfig.dialect = "org.hibernate.dialect.PostgreSQL94Dialect";
        return this;
    }

    /**
     * Заготовка для БД mySql
     * @return
     */
    public EmfBuilder mySql() {
        connConfig.jdbcClass = "com.mysql.cj.jdbc.MysqlDataSource";
        connConfig.jdbcPrefix = "jdbc:mysql";
        connConfig.dialect = "org.hibernate.dialect.MySQL8Dialect";
        return this;
    }

    public EmfBuilder jdbcUrl(String jdbcUrl) {
        connConfig.jdbcUrl = jdbcUrl;
        return this;
    }

    public EmfBuilder dbHost(String dbHost) {
        connConfig.dbHost = dbHost;
        return this;
    }

    public EmfBuilder dbName(String dbName) {
        connConfig.dbName = dbName;
        return this;
    }

    public EmfBuilder username(String username) {
        connConfig.username = username;
        return this;
    }

    public EmfBuilder password(String password) {
        connConfig.password = password;
        return this;
    }

    public EmfBuilder persistenceUnitName(String persistenceUnitName) {
        connConfig.persistenceUnitName = persistenceUnitName;
        return this;
    }

    public EmfBuilder dbPort(int dbPort) {
        connConfig.dbPort = dbPort;
        return this;
    }

    public EmfBuilder hibernateDialect(String dialect) {
        connConfig.dialect = dialect;
        return this;
    }

    /**
     * Билдит объект с параметрами EntityManagerFactory
     */
    public EntityManagerFactory build() {
        return new ThreadSafeEntityManagerFactory(EmfContext.INSTANCE.get(connConfig.validate()));
    }
}
