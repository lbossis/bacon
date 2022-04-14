package org.jboss.pnc.bacon.pig.impl.addons.scanservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

public class ScanConfigUtil {
    private static final Logger log = LoggerFactory.getLogger(ScanConfigUtil.class);

    @SuppressWarnings("unchecked")
    public static Map<String, Object> jsonFileToConfigMap(String jsonCfgFilePath) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(Paths.get(jsonCfgFilePath).toFile(), Map.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Collections.emptyMap();
    }

    private ScanConfigUtil() {
    }
}
