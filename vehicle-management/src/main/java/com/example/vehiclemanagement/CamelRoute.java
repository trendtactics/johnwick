//package com.example.vehiclemanagement;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.main.Main;
//
//public class CamelRoute extends RouteBuilder {
//
//    @Override
//    public void configure() throws Exception {
//        String excelFilePath = "data/path_to_excel_file.xlsx"; // Adjust path if necessary
//        from("file:data/input?fileName=sample.txt&noop=true")
//                .process(new SampleDataProcessor(ExcelReader.readFieldDefinitions(excelFilePath)))
//                .log("${body}");
//    }
//
//    public static void main(String[] args) throws Exception {
//        Main main = new Main();
//        main.configure().addRoutesBuilder(new CamelRoute());
//        main.run(args);
//    }
//}
