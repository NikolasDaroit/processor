package com.processor.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    

    public FileReader(){

    }

    /**
     * 
     * @param filePath
     */
    public List<String> readFileContents(Path filePath){
        List<String> lines = new ArrayList<String>();
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    
   
}