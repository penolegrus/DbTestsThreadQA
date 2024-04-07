package threadqa.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

/**
 * Сервис для взаимодествия с БД
 */
public abstract class JpaService {
    protected final EntityManager em;

    public JpaService(EntityManager em) {
        this.em = em;
    }

    /**
     * Добавляет новую сущность
     * @param entity
     * @param <T>
     */
    public <T> void persist(T entity) {
        transaction(em -> em.persist(entity));
    }

    /**
     * Обновляет существующую сущность
     * @param entity
     * @param <T>
     */
    public <T> void merge(T entity) {
        transaction(em -> em.merge(entity));
    }

    /**
     * Удаляет сущность в БД
     * @param entity
     * @param <T>
     */
    public <T> void remove(T entity) {
        transaction(em -> em.remove(entity));
    }

    /**
     * Обновляет сущность в БД, так как есть кеширование
     * @param entity
     * @param <T>
     */
    public <T> void refresh(T entity) {
        em.refresh(entity);
    }

    /**
     * Выполняет транщацкию в БД, в случае если была ошибка, то действие не выполняется и откатывается назад
     * @param action
     */
    protected void transaction(Consumer<EntityManager> action) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            action.accept(em);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
