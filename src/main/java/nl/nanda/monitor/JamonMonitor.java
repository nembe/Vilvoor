package nl.nanda.monitor;

import java.util.Date;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorComposite;
import com.jamonapi.MonitorFactory;
import com.jamonapi.MonitorFactoryInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class JamonMonitor.
 */
public class JamonMonitor {

    /** The monitor factory. */
    private final MonitorFactoryInterface monitorFactory = MonitorFactory
            .getFactory();

    /**
     * Instantiates a new jamon monitor.
     */
    public JamonMonitor() {

    }

    /**
     * Start.
     *
     * @param name the name
     * @return the monitor
     */
    public Monitor start(final String name) {
        return monitorFactory.start(name);
    }

    /**
     * Gets the calls count.
     *
     * @return the calls count
     */
    public long getCallsCount() {
        return (long) getMonitors().getHits();
    }

    /**
     * Gets the total call time.
     *
     * @return the total call time
     */
    public long getTotalCallTime() {
        return (long) getMonitors().getTotal();
    }

    /**
     * Gets the last access time.
     *
     * @return the last access time
     */
    public Date getLastAccessTime() {
        return getMonitors().getLastAccess();
    }

    /**
     * Gets the monitors.
     *
     * @return the monitors
     */
    public MonitorComposite getMonitors() {
        return monitorFactory.getRootMonitor();
    }

    /**
     * Average call time.
     *
     * @param methodName the method name
     * @return the long
     */
    public long averageCallTime(final String methodName) {
        return (long) monitorFactory.getMonitor(methodName, "ms.").getAvg();
    }

    /**
     * Call count.
     *
     * @param methodName the method name
     * @return the long
     */
    public long callCount(final String methodName) {
        return (long) monitorFactory.getMonitor(methodName, "ms.").getHits();
    }

    /**
     * Last call time.
     *
     * @param methodName the method name
     * @return the long
     */
    public long lastCallTime(final String methodName) {
        return (long) monitorFactory.getMonitor(methodName, "ms.")
                .getLastValue();
    }

    /**
     * Maximum call time.
     *
     * @param methodName the method name
     * @return the long
     */
    public long maximumCallTime(final String methodName) {
        return (long) monitorFactory.getMonitor(methodName, "ms.").getMax();
    }

    /**
     * Minimum call time.
     *
     * @param methodName the method name
     * @return the long
     */
    public long minimumCallTime(final String methodName) {
        return (long) monitorFactory.getMonitor(methodName, "ms.").getMin();
    }

    /**
     * Total call time.
     *
     * @param methodName the method name
     * @return the long
     */
    public long totalCallTime(final String methodName) {
        return (long) monitorFactory.getMonitor(methodName, "ms.").getTotal();
    }

}
