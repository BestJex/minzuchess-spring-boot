package work.eanson.configuraton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Long live freedom and fraternity, No 996
 * <pre>
 *
 * </pre>
 *
 * @author eanson
 * @date 2020/6/14
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * new cachedThreadPool
     *
     * @return
     */
    @Bean
    ExecutorService cachedThreadPool() {
        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), new MessageThreadFactory());
    }

    private class MessageThreadFactory implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger nextId = new AtomicInteger(1);

        /**
         * 定义线程组名称，在 jstack 问题排查时，非常有帮助
         */
        MessageThreadFactory() {
            namePrefix = "From MsgThreadFactory-Worker-";
        }

        @Override
        public Thread newThread(Runnable task) {
            return new Thread(task, namePrefix + nextId.getAndIncrement());
        }
    }
}
