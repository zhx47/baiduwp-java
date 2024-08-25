package top.zhx47.baiduwp.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 缓存接口实现类  二级缓存
 * @author valarchie
 */
@Slf4j
public abstract class AbstractGuavaCacheTemplate<T> {

    private final LoadingCache<String, Optional<T>> guavaCache = CacheBuilder.newBuilder()
        .maximumSize(102400)
        .expireAfterWrite(1, TimeUnit.DAYS)
        .concurrencyLevel(16)
        .initialCapacity(128)
        .build(new CacheLoader<>() {
            @Override
            public Optional<T> load(String key) {
                return Optional.empty();
            }
        });

    public T get(String key) {
        try {
            if (!StringUtils.hasText(key)) {
                return null;
            }

            Optional<T> optional = guavaCache.get(key);
            return optional.orElse(null);
        } catch (ExecutionException e) {
            log.error("get cache object from guava cache failed.", e);
            return null;
        }
    }

    public void put(String key, T value) {
        guavaCache.put(key, Optional.ofNullable(value));
    }


    public void invalidate(String key) {
        if (!StringUtils.hasText(key)) {
            return;
        }

        guavaCache.invalidate(key);
    }
}
