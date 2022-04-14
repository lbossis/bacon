package org.jboss.pnc.bacon.pig.impl.addons.scanservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PostBuildScanSkeleton {
    private static final Logger log = LoggerFactory.getLogger(PostBuildScanSkeleton.class);
    private Map<String, Object> mapData;

    private String buildId = "xxxxx1";
    private String productIdDesc;
    private boolean isProductIdRequired;
    private boolean isManagedService;
    private String cpaasVersion;
    private String jobUrl;

    private String script;
    private String scmUrl;
    private String output;
    private String logFile;

    public PostBuildScanSkeleton(String jsonCfgFilePath) {
        mapData = ScanConfigUtil.jsonFileToConfigMap(jsonCfgFilePath);
        assert (mapData != null);
    }

    public String getProductIdDesc() {
        if (productIdDesc == null) {
            Map<String, Object> propertiesMap = (Map<String, Object>) mapData.get("properties");
            Map<String, String> prodIdMap = (Map<String, String>) propertiesMap.get("product-id");
            productIdDesc = prodIdMap.get("description");
        }
        return productIdDesc;
    }

    public String getCpaasVersion() {
        if (cpaasVersion == null) {
            Map<String, Object> propertiesMap = (Map<String, Object>) mapData.get("properties");
            Map<String, String> jobUrlMap = (Map<String, String>) propertiesMap.get("cpaas-version");
            cpaasVersion = jobUrlMap.get("description");
        }
        return cpaasVersion;
    }

    public boolean isManagedService() {
        if (!isManagedService) {
            Map<String, Object> propertiesMap = (Map<String, Object>) mapData.get("properties");
            Map<String, String> serviceRequiredMap = (Map<String, String>) propertiesMap.get("is-managed-service");
            isManagedService = serviceRequiredMap.get("description") != null ? true : false;
        }
        return isManagedService;
    }

    public String getJobUrl() {
        if (jobUrl == null) {
            Map<String, Object> propertiesMap = (Map<String, Object>) mapData.get("properties");
            Map<String, String> jobUrlMap = (Map<String, String>) propertiesMap.get("job-url");
            jobUrl = jobUrlMap.get("description");
        }
        return jobUrl;
    }

    public boolean isProductIdRequired() {
        if (!isProductIdRequired) {
            List<String> lst = (List) mapData.get("required");
            isProductIdRequired = lst.get(0).equals("product-id") ? true : false;
        }
        return isProductIdRequired;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getScript() {
        if (script == null) {
            script = (String) mapData.get("script");
        }
        return script;
    }

    public String getScmUrl() {
        if (scmUrl == null) {
            scmUrl = (String) mapData.get("scmUrl");
        }
        return scmUrl;
    }

    public String getOutput() {
        if (output == null) {
            output = (String) mapData.get("output");
        }
        return output;
    }

    public String getLogFile() {
        if (logFile == null) {
            logFile = (String) mapData.get("logFile");
        }
        return logFile;
    }

    public void showCfgMap() {
        log.info("++++++ Entire Scan Config Map ++++++");
        try {
            for (Map.Entry<String, Object> entry : mapData.entrySet()) {
                log.info("{} = {}", entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void showIndividualFields() {
        log.info("++++++++++++ Scan Config Map Fields ++++++++++++");
        log.info("buildId             = {}", getBuildId());
        log.info("isManagedService    = {}", isManagedService());
        log.info("isProductIdRequired = {}", isProductIdRequired());
        log.info("productIdDesc       = {}", getProductIdDesc());
        log.info("cpaasVersion        = {}", getCpaasVersion());
        log.info("jobUrl              = {}", getJobUrl());
        log.info("++++++++++++ Shell script to run with its arguments ++++++++++++");
        log.info("script              = {}", getScript());
        log.info("scmUrl              = {}", getScmUrl());
        log.info("jsonoutput          = {}", getOutput());
        log.info("logFile             = {}", getLogFile());
    }
}
