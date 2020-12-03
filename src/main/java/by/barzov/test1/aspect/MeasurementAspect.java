package by.barzov.test1.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MeasurementAspect
{
    @Pointcut("@annotation(Measurement)")
    public void executeMeasurement()
    {

    }

    @Around("executeMeasurement()")
    public Object measureTime(ProceedingJoinPoint jp) throws Throwable
    {
        long start = System.nanoTime();
        Object returnValue = jp.proceed();
        long end = System.nanoTime();
        System.out.println("runtime of method: " + jp.getSignature().getName() + " " + (end - start));
        return returnValue;
    }
}
