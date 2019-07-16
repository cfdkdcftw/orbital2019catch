package com.example.orbital2019catch.personal.survey.workinprogress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public CSVReader() {
    }

    public List<String[]> read(InputStream inputStream) {
        List<String[]> resultList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    String[] temp = line.split(",");
                    resultList.add(temp);
                }
            }
        } catch(Exception e) {
            throw new RuntimeException("Error in reading CSV file: " + e);
        } finally {
            try {
                inputStream.close();
            } catch(IOException ex) {
                throw new RuntimeException("Error while closing input stream: " + ex);
            }
        }
        return resultList;
    }
}