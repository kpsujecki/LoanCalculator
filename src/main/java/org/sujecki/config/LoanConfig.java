package org.sujecki.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Data
public class LoanConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoanConfig.class);

    private int maxCreditPeriod;
    private int minCreditPeriod;
    private double minCreditAmount;
    private double maxCreditAmount;
    private double maxEngagement;
    private Map<String, Double> interestRates;
    private Map<String, Double> dti;

    public static LoanConfig readFromFile(String filePath) {
        try (InputStream input = new FileInputStream(filePath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> configMap = yaml.load(input);

            LoanConfig configuration = new LoanConfig();
            configuration.maxCreditPeriod = (int) configMap.get("maxCreditPeriod");
            configuration.minCreditPeriod = (int) configMap.get("minCreditPeriod");
            configuration.minCreditAmount = (double) configMap.get("minCreditAmount");
            configuration.maxCreditAmount = (double) configMap.get("maxCreditAmount");
            configuration.maxEngagement = (double) configMap.get("maxEngagement");
            configuration.interestRates = (Map<String, Double>) configMap.get("interestRates");
            configuration.dti = (Map<String, Double>) configMap.get("dti");

            return configuration;
        } catch (IOException e) {
            logger.error("Error while reading configuration from file: {}", e.getMessage(), e);
            return null;
        }
    }
}