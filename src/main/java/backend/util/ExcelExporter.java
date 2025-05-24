package backend.util;

import backend.entity.Order;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

// 엑셀 저장
public class ExcelExporter {

    public static void exportToExcel(List<Order> orders, File file) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("주문내역");

        // 헤더 작성
        String[] columns = {"수신자명", "주소", "전화번호", "수량", "상품명", "보내는사람", "보내는사람 전화번호"};
        Row header = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            header.createCell(i).setCellValue(columns[i]);
        }

        // 데이터 작성
        int rowNum = 1;
        for (Order o : orders) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(o.getReceiverName());
            row.createCell(1).setCellValue(o.getAddress());
            row.createCell(2).setCellValue(o.getPhone());
            row.createCell(3).setCellValue(o.getQuantity());
            row.createCell(4).setCellValue(o.getItemName());
            row.createCell(5).setCellValue(o.getSenderName());
            row.createCell(6).setCellValue(o.getSenderPhone());
        }

        // 파일 저장
        try (FileOutputStream out = new FileOutputStream(file)) {
            workbook.write(out);
        }
        workbook.close();
    }
}
