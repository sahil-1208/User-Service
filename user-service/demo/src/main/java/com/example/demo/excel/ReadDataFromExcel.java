package com.example.demo.excel;

import com.example.demo.entity.UserEntity;
import com.example.demo.enums.Gender;
import com.example.demo.enums.Role;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkExcelFormat(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<UserEntity> convertExcelToListOfUser(InputStream is) {

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
                                userEntity.setPassword(passwordEncoder.encode(cell.getStringCellValue()));
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setPassword(passwordEncoder.encode(String.valueOf((int) cell.getNumericCellValue())));
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
                        case 8:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setFirstName(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setFirstName(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 9:
                            if (cell.getCellType() == CellType.STRING) {
                                userEntity.setSecondName(cell.getStringCellValue());
                            } else if (cell.getCellType() == CellType.NUMERIC) {
                                userEntity.setSecondName(String.valueOf((int) cell.getNumericCellValue()));
                            }
                            break;
                        case 10:
                            userEntity.setRole(Role.USER);
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