package com.example.demo.excel;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ReadDataFromExcel {

    public static boolean checkExcelFormat(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static List<UserEntity> convertExcelToListOfUser(InputStream is) {
        List<UserEntity> userEntityData = new ArrayList<UserEntity>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                UserEntity userEntity = new UserEntity();
                Iterator<Cell> cells = row.iterator();
                int cid = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cid) {
                        case 0:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setMobile(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setMobile(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 1:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setEmail(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setEmail(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setPassword(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setPassword(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 3:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setName(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setName(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 4:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setUsername(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setUsername(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 5:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setLocation(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setLocation(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 6:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setDate(LocalDate.parse(cell.getStringCellValue()));
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                // Handle date parsing from numeric cell value
                                userEntity.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
                            }
                            break;
                        case 7:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setGender(Gender.valueOf(cell.getStringCellValue()));
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setGender(Gender.valueOf(String.valueOf((int) cell.getNumericCellValue())));
                            }
                            break;
                        default:
                            break;
                    }
                    cid++;
                }
                userEntityData.add(userEntity);
            }
            return userEntityData;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return userEntityData;
    }
}