package org.jboss.pnc.bacon.pig.impl.addons.scanservice;

import org.jboss.pnc.bacon.pig.impl.addons.AddOn;
import org.jboss.pnc.bacon.pig.impl.config.PigConfiguration;
import org.jboss.pnc.bacon.pig.impl.pnc.PncBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PostBuildScanService extends AddOn {

    public static final String NAME = "postBuildScanService";
    public static final String JSON_CFG_PATH = "/home/lbossis/tmp/bacon-test/build-config.json";

    private static final Logger log = LoggerFactory.getLogger(PostBuildScanService.class);

    public PostBuildScanService(
            PigConfiguration pigConfiguration,
            Map<String, PncBuild> builds,
            String releasePath,
            String extrasPath) {
        super(pigConfiguration, builds, releasePath, extrasPath);
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    public void trigger() {
        log.info("=============== TRIGGER Entry Point ===============");
        log.info("releasePath: {}", releasePath);
        log.info("extrasPath: {}", extrasPath);

        PostBuildScanSkeleton scanSkeleton = new PostBuildScanSkeleton(JSON_CFG_PATH);
        scanSkeleton.showCfgMap();
        scanSkeleton.showIndividualFields();

        List<String> toRun = new ArrayList<>();
        toRun.add(scanSkeleton.getScript());
        toRun.add(scanSkeleton.getScmUrl());
        toRun.add(scanSkeleton.getLogFile());
        toRun.add(scanSkeleton.getOutput());
        runTrigger(toRun);
    }

    private void runTrigger(List<String> toRun) {
        log.info("----------- Trigger has been fired -----------");
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(toRun);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
            process.waitFor(300, TimeUnit.SECONDS);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
