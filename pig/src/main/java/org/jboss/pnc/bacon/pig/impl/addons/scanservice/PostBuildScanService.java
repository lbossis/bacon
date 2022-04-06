package org.jboss.pnc.bacon.pig.impl.addons.scanservice;

import org.jboss.pnc.bacon.pig.impl.addons.AddOn;
import org.jboss.pnc.bacon.pig.impl.config.PigConfiguration;
import org.jboss.pnc.bacon.pig.impl.pnc.PncBuild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PostBuildScanService extends AddOn {

    public static final String NAME = "postBuildScanService";

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
        String rs = " Post Build SCAN Service started";
        log.info(rs);
    }
}
