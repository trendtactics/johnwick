//package com.example.vehiclemanagement;
//
//
//import java.util.List;
//
//public class POJOGenerator {
//    public static Class<?> generatePOJO(List<FieldDefinition> fieldDefinitions) throws Exception {
//        StringBuilder classBuilder = new StringBuilder();
//        classBuilder.append("public class DynamicPOJO {\n");
//
//        for (FieldDefinition fieldDef : fieldDefinitions) {
//            classBuilder.append("private ");
//            switch (fieldDef.getInputType()) {
//                case "C":
//                    classBuilder.append("String ");
//                    break;
//                case "N":
//                    classBuilder.append("int ");
//                    break;
//                case "BS":
//                case "PS":
//                    classBuilder.append("String ");
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown input type: " + fieldDef.getInputType());
//            }
//            classBuilder.append(fieldDef.getName()).append(";\n");
//        }
//
//        classBuilder.append("}\n");
//
//        // Compile the generated class using Janino
//        org.codehaus.janino.SimpleCompiler compiler = new org.codehaus.janino.SimpleCompiler();
//        compiler.cook(classBuilder.toString());
//        return compiler.getClassLoader().loadClass("DynamicPOJO");
//    }
//}
