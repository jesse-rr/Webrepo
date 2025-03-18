package com.example.vpn.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Configuration
public class ExtractionFactory {

    public void parseToJSON(HashMap<String, Object> data, String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(fileName+".json"), data);
    }

    // TODO CHECK IF SIZES DIFFERENCE MATTER
    public void parseToCSV(List<String> recordNames, String fileName, List<String> data) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName + ".csv");
             CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            printer.printRecord(recordNames);
            printer.printRecords(data);
            printer.close(true);
        }
    }

    public void parseToTXT(List<String> data, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName+".txt");
        for (String line : data) {
            writer.write(line+"\n");
        }
        writer.close();
    }
}
