package nl.nanda.monitor;

import javax.validation.ConstraintViolationException;

import nl.nanda.exception.AnanieException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jamonapi.Monitor;

/**
 * The Class LoggingAspect for monitoring the AAT application.
 */
@Aspect
@Component
public class LoggingAspect {

    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /** The monitor factory. */
    private final AnanieMonitor monitorFactory;

    /**
     * Instantiates a new logging aspect.
     *
     * @param monitorFactory
     *            the monitor factory
     */
    @Autowired
    public LoggingAspect(final AnanieMonitor monitorFactory) {
        super();
        this.monitorFactory = monitorFactory;
    }

    // CGLIB Proxy
    @Pointcut("target(nl.nanda.service.TransferService)")
    public void serviceMethod() {
    }

    @Pointcut("execution(* *..*(..))")
    public void allMethods() {
    }

    @Pointcut("execution(* *..createAccount(..))")
    public void createAccount() {
    }

    @Pointcut("execution(* *..doTransfer(..))")
    public void doTransfer() {

    }

    @Pointcut("serviceMethod() && createAccount()")
    public void doServiceAccountLogging() {

    }

    @Pointcut("serviceMethod() && doTransfer()")
    public void doServiceTransferLogging() {

    }

    @Pointcut("serviceMethod() && allMethods()")
    public void all() {

    }

    /**
     * Logging the parameters and values when trying a transfer.
     * 
     * @param joinPoint
     */
    @Before("doTransfer() && args(from,to,amount)")
    public void implLogging(final JoinPoint joinPoint, final String from, final String to, final double amount) {
        final StringBuilder sb = new StringBuilder();
        sb.append("'Before' advice implementation - ");
        sb.append(joinPoint.getTarget().getClass());
        sb.append("; Executing before ");
        sb.append(joinPoint.getSignature().getName());
        sb.append("() method with + arguments = ");
        sb.append("from = ");
        sb.append(from);
        sb.append("; to = ");
        sb.append(to);
        sb.append("; amount = ");
        sb.append(amount);
        logger.info(sb.toString());
    }

    /**
     * Report the error ConstraintViolationException.
     * 
     * @param joinPoint
     */
    @AfterThrowing(value = "all()", throwing = "e")
    public void reportConstraintViolationException(final JoinPoint joinPoint, final ConstraintViolationException e) {

        logger.info("'Report' Constraint Violation - " + joinPoint.getTarget().getClass() + "; Executing error "
                + joinPoint.getSignature().getName() + "() method", e);
    }

    /**
     * Report the error AnanieException.
     * 
     * @param joinPoint
     */
    @AfterThrowing(value = "all()", throwing = "e")
    public void reportAnanieException(final JoinPoint joinPoint, final AnanieException e) {

        logger.info("'Report' Ananie Exception - " + joinPoint.getTarget().getClass() + "; Executing error "
                + joinPoint.getSignature().getName() + "() method", e);
    }

    /**
     * Monitor how long it takes to complete a transfer.
     * 
     * @param repositoryMethod
     * @return
     * @throws Throwable
     */
    @Around("doTransfer()")
    public Object monitor(final ProceedingJoinPoint repositoryMethod) throws Throwable {
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
     * Creates the join point trace (method) name.
     *
     * @param joinPoint
     *            the join point
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