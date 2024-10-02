package com.app.core;

import com.intuit.karate.Constants;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KarateRunner
{
    @Test
    void testParallel()
    {
        String env = System.getProperty(Constants.KARATE_ENV, "dev").trim();
        Runner.Builder rb = Runner.builder();
        rb.path("classpath:com/app/tests");
        rb.tags("~@ignore");
        rb.hook(new KarateHook());

        Results results = rb.parallel(1);

        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}