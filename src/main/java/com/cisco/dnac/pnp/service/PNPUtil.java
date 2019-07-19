package com.cisco.dnac.pnp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class PNPUtil {



    public static void main(String[] args) throws Exception {
        File input = new File("src/main/resources/sample.csv");

        List<Map<String, String>> data = readObjectsFromCsv(input);
        System.out.println(data);
    }

    public static List<Map<String, String>> readObjectsFromCsv(File file) throws IOException {
        CsvSchema bootstrap = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Map<String, String>> mappingIterator = csvMapper.reader(Map.class).with(bootstrap).readValues(file);

        return mappingIterator.readAll();
    }

  
}
