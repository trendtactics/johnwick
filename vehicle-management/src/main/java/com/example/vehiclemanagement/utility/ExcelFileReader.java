package com.example.vehiclemanagement.utility;

import com.example.vehiclemanagement.pojo.PfcLayout;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelFileReader {
    public static DataSet<PfcLayout> readExcelFile(ExecutionEnvironment env, String filePath) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        List<Tuple2<Integer, String>> data = new ArrayList<>();

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Skip header row
            int field1 = (int) row.getCell(0).getNumericCellValue();
            String field2 = row.getCell(1).getStringCellValue();
            data.add(new Tuple2<>(field1, field2));
        }

        workbook.close();

        DataSet<Tuple2<Integer, String>> dataSet = env.fromCollection(data);
        return dataSet.map(new MapFunction<Tuple2<Integer, String>, PfcLayout>() {
            @Override
            public PfcLayout map(Tuple2<Integer, String> value) throws Exception {
                return new PfcLayout(value.f0, value.f1);
            }
        });
    }
}
