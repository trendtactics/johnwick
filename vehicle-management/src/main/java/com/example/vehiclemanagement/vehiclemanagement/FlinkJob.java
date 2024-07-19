package com.example.vehiclemanagement.vehiclemanagement;

import com.example.vehiclemanagement.pojo.PfcLayout;
import com.example.vehiclemanagement.utility.CsvFileReader;
import com.example.vehiclemanagement.utility.ExcelFileReader;
import com.example.vehiclemanagement.utility.TextFileReader;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class FlinkJob {
    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // Reading CSV file
        DataSet<PfcLayout> csvData = CsvFileReader.readCsvFile(env, "data/sample.csv");
        csvData.print();

        // Reading Excel file
        DataSet<PfcLayout> excelData = ExcelFileReader.readExcelFile(env, "data/sample.xlsx");
        excelData.print();

        // Reading Text file
        DataSet<PfcLayout> textData = TextFileReader.readTextFile(env, "data/sample.txt");
        textData.print();
    }
}
