package ch.swaechter.eaf.aop;

import ch.swaechter.eaf.cache.Cache;
import ch.swaechter.eaf.cache.CacheManager;
import ch.swaechter.eaf.user.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class CacheAspect {

    @Autowired
    private CacheManager cacheManager;

    @Around("execution(* *..eaf.user.UserService.getUsers())")
    public Object interceptGetUsers(ProceedingJoinPoint point) throws Throwable {
        Cache cache = getOrCreateCache(User.class);
        if (cache.getAll().isEmpty()) {
            List<Object> objects = (List<Object>) point.proceed();
            for (Object object : objects) {
                User user = (User) object;
                cache.addObjectToCache(user, user.getId());
            }
        }
        return cache.getAll();
    }

    @Around("execution(* *..eaf.user.UserService.getUser(..)) && args(id)")
    public Object interceptGetUser(ProceedingJoinPoint point, Long id) throws Throwable {
        Cache cache = getOrCreateCache(User.class);
        if (cache.getObjectFromCache(id) == null) {
            Optional<User> user = (Optional<User>) point.proceed();
            if (user.isPresent()) {
                cache.addObjectToCache(user.get(), id);
            }
        }
        return Optional.of(cache.getObjectFromCache(id));
    }

    @Around("execution(* *..eaf.user.UserService.saveUser(..)) || execution(* *..eaf.user.UserService.updateUser(..))")
    public Object interceptUpdateUser(ProceedingJoinPoint point) throws Throwable {
        Cache cache = getOrCreateCache(User.class);
        User updatedUser = (User) point.proceed();
        cache.addObjectToCache(updatedUser, updatedUser.getId());
        return updatedUser;
    }

    @Around("execution(* *..eaf.user.UserService.deleteUser(..)) && args(id)")
    public void interceptDeleteUser(ProceedingJoinPoint point, Long id) throws Throwable {
        Cache cache = getOrCreateCache(User.class);
        point.proceed();
        cache.removeObject(id);
    }

    private Cache getOrCreateCache(Class targetClass) {
        Cache cache = cacheManager.getCacheByClass(targetClass);
        return cache != null ? cache : cacheManager.createCacheForClass(targetClass);
    }
}
