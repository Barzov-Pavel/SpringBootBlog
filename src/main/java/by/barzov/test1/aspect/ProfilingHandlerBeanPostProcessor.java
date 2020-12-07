package by.barzov.test1.aspect;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor
{
    private Map<String, Class> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException
    {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class))
        {
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {
        Class beanClass = map.get(beanName);
        if (beanClass != null)
        {
            return Enhancer.create(beanClass, new InvocationHandler()
            {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
                {
                    long start = System.nanoTime();
                    Object retVal = method.invoke(bean, args);
                    long end = System.nanoTime();
                    System.out.println("runtime of method: " + method.getName() + " " + (end - start));
                    return retVal;
                }
            });
        }
        return bean;
    }
}
