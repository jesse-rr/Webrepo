package com.example.vpn.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportService {

    public void parseStringToJson(List<String> data, String f_name, String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(f_name+value), data);
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO PARSE MAP TO JSON :: "+e);
        }

    }

    public void parseJsonToString(List<String> json, String f_name, String value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(f_name + value);
            log.info("Writing to file :: {} ",file.getAbsolutePath());
            FileWriter writer = new FileWriter(file);
            writer.write(objectMapper.writeValueAsString(json));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("FAILED TO PARSE JSON TO STRING OR WRITE FILE:: " + e);
        }
    }

    public void parseStringToCsv(List<String> csvHeaders, List<String> csvData, String fName, String parseType) {
        try (FileWriter fileWriter = new FileWriter(fName+parseType); CSVPrinter printer = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            printer.printRecords(csvHeaders);
            for (String data : csvData) {
                printer.printRecord(data);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
