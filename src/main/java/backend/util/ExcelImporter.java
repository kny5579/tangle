package backend.util;

import backend.entity.Order;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelImporter {
    public static List<Order> importFromExcel(File file) throws IOException {
        List<Order> orders = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);  // 첫 번째 시트 사용
            int rowCount = sheet.getPhysicalNumberOfRows();

            for (int i = 1; i < rowCount; i++) { // 0번은 헤더니까 1번부터
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String receiverName = getStringCellValue(row, 0);
                String address = getStringCellValue(row, 1);
                String phone = getStringCellValue(row, 2);
                int quantity = (int) row.getCell(3).getNumericCellValue();
                String itemName = getStringCellValue(row, 4);
                String senderName = getStringCellValue(row, 5);
                String senderPhone = getStringCellValue(row, 6);

                Order order = new Order(receiverName, address, phone, quantity, itemName, senderName, senderPhone);
                orders.add(order);
            }
        }

        return orders;
    }

    private static String getStringCellValue(Row row, int index) {
        Cell cell = row.getCell(index);
        return (cell == null) ? "" : cell.toString().trim();
    }
}

