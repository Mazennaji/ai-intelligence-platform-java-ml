package org.example.aiintelligenceplatformjavaml.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class CSVUtils {

    private static final Logger log = LoggerFactory.getLogger(CSVUtils.class);

    public static List<Map<String, String>> readCsv(String filePath) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) return records;

            String[] headers = headerLine.split(",");
            for (int i = 0; i < headers.length; i++) {
                headers[i] = headers[i].trim().replaceAll("^\"|\"$", "");
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                Map<String, String> record = new LinkedHashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    record.put(headers[i], values[i].trim().replaceAll("^\"|\"$", ""));
                }
                records.add(record);
            }
        }

        log.debug("Read {} records from {}", records.size(), filePath);
        return records;
    }

    public static void writeCsv(String filePath, List<String> headers, List<List<String>> rows) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            pw.println(String.join(",", headers));
            for (List<String> row : rows) {
                pw.println(row.stream()
                        .map(val -> val.contains(",") ? "\"" + val + "\"" : val)
                        .reduce((a, b) -> a + "," + b)
                        .orElse(""));
            }
        }
        log.debug("Wrote {} rows to {}", rows.size(), filePath);
    }
}
