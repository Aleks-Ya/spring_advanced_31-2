package booking.service.aspects;

import booking.domain.User;
import booking.service.impl.discount.BirthdayStrategy;
import booking.service.impl.discount.TicketsStrategy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class DiscountAspect {
    private static final Logger LOG = LoggerFactory.getLogger(DiscountAspect.class);

    protected static final Map<String, Map<String, Integer>> discountPerUserCounter = new HashMap<>();

    static {
        discountPerUserCounter.put(BirthdayStrategy.class.getSimpleName(), new HashMap<>());
        discountPerUserCounter.put(TicketsStrategy.class.getSimpleName(), new HashMap<>());
    }

    @Pointcut("(execution(* booking.service.DiscountStrategy.calculateDiscount(booking.domain.User)) && args(user))")
    private void calculateDiscount(User user) {
        // This method intended for declaring a @Pointcut
    }

    @AfterReturning(pointcut = "calculateDiscount(user)", returning = "discount", argNames = "joinPoint,user,discount")
    public void countCalculateDiscount(JoinPoint joinPoint, User user, double discount) {
        if (Double.compare(discount, 0.0) > 0) {
            final Class<?> discountStrategy = joinPoint.getTarget().getClass();
            if (discountStrategy.isAssignableFrom(BirthdayStrategy.class)) {
                increaseCounter(BirthdayStrategy.class.getSimpleName(), user);
            } else if (discountStrategy.isAssignableFrom(TicketsStrategy.class)) {
                increaseCounter(TicketsStrategy.class.getSimpleName(), user);
            } else {
                LOG.info("Unknown discount strategy: [" + discountStrategy.getName() + "]");
            }
        }
    }

    private void increaseCounter(String cls, User user) {
        final Map<String, Integer> userDiscountMap = discountPerUserCounter.get(cls);
        userDiscountMap.put(user.getEmail(), userDiscountMap.getOrDefault(user.getEmail(), 0) + 1);
    }

    public static Map<String, Map<String, Integer>> getDiscountStatistics() {
        return Collections.unmodifiableMap(discountPerUserCounter);
    }

}
