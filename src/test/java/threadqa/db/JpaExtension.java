package threadqa.db;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;

import static java.lang.System.currentTimeMillis;

/**
 * Расширение для Junit5, который позволяет навешать взаимодействие с БД только для определенных тестов
 */
public class JpaExtension implements BeforeAllCallback {
    /**
     * Логер для вывода инфы в консоль
     */
    private static final Logger log = LoggerFactory.getLogger(JpaExtension.class);

    /**
     * Перед всеми тестами будет добавлен наблюдатель для отключения от БД после завершения тестов
     * @param context the current extension context; never {@code null}
     */
    @Override
    public void beforeAll(ExtensionContext context) {
        context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).getOrComputeIfAbsent(JpaExtensionCallback.class);
    }

    /**
     * Закрывает подключение к БД после выполнения тестов
     */
    static class JpaExtensionCallback implements ExtensionContext.Store.CloseableResource {
        @Override
        public void close() {
            //перебираем все подключения к БД
            for (EntityManagerFactory emf : EmfContext.INSTANCE.storedEmf()) {
                //если подключение не пустое и все еще открыто
                if (emf != null && emf.isOpen()) {
                    long start = currentTimeMillis();
                    //логируем время отключения от БД
                    log.info("Close start on " + start);
                    //закрываем подключение
                    emf.close();
                }
            }
        }
    }
}