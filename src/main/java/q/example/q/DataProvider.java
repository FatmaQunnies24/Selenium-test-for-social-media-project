package q.example.q;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    public static List<String[]> getLoginData(String filePath) throws IOException, CsvException {
        List<String[]> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            csvReader.skip(1); 
            records = csvReader.readAll();
        }
        return records;
    }
}
