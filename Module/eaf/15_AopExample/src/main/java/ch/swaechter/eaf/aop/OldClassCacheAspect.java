package ch.swaechter.eaf.aop;

import ch.swaechter.eaf.cache.Cache;
import ch.swaechter.eaf.cache.CacheManager;
import ch.swaechter.eaf.user.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
public class OldClassCacheAspect {

    @Autowired
    private CacheManager cacheManager;

    //@Around("execution(* *..eaf.user.UserController..*(..))")
    public Object interceptMethods(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        System.out.println("Method called: " + point.getTarget().getClass() + "." + method.getName());
        switch (method.getName()) {
            case "getUsers":
                return getUsers(point);
            case "getUser":
                return point.proceed();
            // TODO: For all other methods
            default:
                System.out.println("Mr. Luthiger...we didn't expect that :(");
                throw new IllegalStateException("Thanks for reusing old MSP exams!");
        }
    }

    private Object getUsers(ProceedingJoinPoint point) throws Throwable {
        Class targetClass = point.getTarget().getClass();
        Cache cache = cacheManager.getCacheByClass(targetClass);
        if (cache == null) {
            cache = cacheManager.createCacheForClass(targetClass);
        }

        if (cache.getAll().isEmpty()) {
            List<Object> objects = (List<Object>) point.proceed();
            for (Object object : objects) {
                User user = (User) object;
                cache.addObjectToCache(user, user.getId());
            }
        }
        return cache.getAll();
    }
}
