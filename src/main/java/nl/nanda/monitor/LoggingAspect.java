package nl.nanda.monitor;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jamonapi.Monitor;

// TODO: Auto-generated Javadoc
/**
 * The Class LoggingAspect.
 */
@Aspect
@Component
public class LoggingAspect {

    /** The logger. */
    private final Logger logger = Logger.getLogger(getClass());
    
    /** The monitor factory. */
    private final JamonMonitor monitorFactory;

    /**
     * Instantiates a new logging aspect.
     *
     * @param monitorFactory the monitor factory
     */
    @Autowired
    public LoggingAspect(final JamonMonitor monitorFactory) {
        super();
        this.monitorFactory = monitorFactory;
    }

    /**
     * Impl logging.
     *
     * @param joinPoint the join point
     */
    @Before("execution(public * nl.nanda.*.*Repository.find*(..))")
    public void implLogging(final JoinPoint joinPoint) {
        logger.info("'Before' advice implementation - "
                + joinPoint.getTarget().getClass() + "; Executing before "
                + joinPoint.getSignature().getName() + "() method");
    }

    /**
     * Monitor.
     *
     * @param repositoryMethod the repository method
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("execution(public * nl.nanda.service.TransferServiceImpl.doTransfer(..))")
    public Object monitor(final ProceedingJoinPoint repositoryMethod)
            throws Throwable {
        final String name = createJoinPointTraceName(repositoryMethod);
        final Monitor monitor = monitorFactory.start(name);
        try {
            return repositoryMethod.proceed();
        } finally {
            monitor.stop();
            logger.info("'Around' advice implementation - " + monitor);
        }
    }

    /**
     * Creates the join point trace name.
     *
     * @param joinPoint the join point
     * @return the string
     */
    private String createJoinPointTraceName(final JoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        final StringBuilder sb = new StringBuilder();
        sb.append(signature.getDeclaringType().getSimpleName());
        sb.append('.').append(signature.getName());
        return sb.toString();
    }
}