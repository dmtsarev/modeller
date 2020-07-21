package ru.ase.ims.enomanager.service;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.ase.ims.enomanager.model.EnoviaEntity;
import ru.ase.ims.enomanager.model.Tag;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;

public class ExportEntitiesService {
    public static ByteArrayInputStream entitiesExcelReport(List<EnoviaEntity> entities) throws IOException {
        String[] columns = {"id", "name", "type", "tags"};
        try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()){

            Sheet sheet = workbook.createSheet("Entities");
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(headerFont);

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < columns.length; col++){
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(columns[col]);
                cell.setCellStyle(cellStyle);
            }

            int rowIndex = 1;
            for (EnoviaEntity enoviaEntity: entities){
                Row row = sheet.createRow(rowIndex++);

                StringJoiner stringJoiner = new StringJoiner(", ");
                for(Tag tag : enoviaEntity.getTags()){
                    stringJoiner.add(tag.getName());
                }

                row.createCell(0).setCellValue(enoviaEntity.getId());
                row.createCell(1).setCellValue(enoviaEntity.getEntityName());
                row.createCell(2).setCellValue(enoviaEntity.getType());
                row.createCell(3).setCellValue(stringJoiner.toString());
            }

            HSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
            for (int i = 0; i < 4; i++){
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
