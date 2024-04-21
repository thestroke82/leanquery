package org.frappa.leanquery.config;

import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.ExecuteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticsListener implements ExecuteListener {

    private final static Logger log = LoggerFactory.getLogger(StatisticsListener.class);

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 7399239846062763212L;

    public static final Map<ExecuteType, Integer> STATISTICS = new ConcurrentHashMap<>();

    @Override
    public void start(ExecuteContext ctx) {
        STATISTICS.compute(ctx.type(), (k, v) -> v == null ? 1 : v + 1);
    }

    public static void restart(){
        STATISTICS.clear();
    }

//    public static void logStatistics(){
//        logStatistics(ExecuteType.READ);
//    }
//
//    private static void logStatistics(ExecuteType type){
//        log.debug("# of queries of type "+type+": "+ STATISTICS.get(type));
//    }
    public static int getQueries(){
        Integer ret = STATISTICS.get(ExecuteType.READ);
        return ret== null ? 0 : ret;
    }
}