package com.learning.service.impl;

import com.learning.entity.UserEntity;
import com.learning.enums.Gender;
import com.learning.enums.Role;
import lombok.extern.slf4j.Slf4j;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class ReadDataFromExcel {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean checkExcelFormat(MultipartFile file) {
        return file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public List<UserEntity> convertExcelToListOfUser(InputStream is) {

        List<UserEntity> userEntityData = new ArrayList<>();

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
                int cellId = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();
                    setCellValue(userEntity, cellId, cell);
                    cellId++;
                }
                userEntityData.add(userEntity);
                log.info("Data Added: {}", userEntity);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            log.error("Error while reading Excel file: {}", exception.getMessage());
        }
        return userEntityData;
    }

    private void setCellValue(UserEntity userEntity, int cellId, Cell cell) {
        if (cell.getCellType() == CellType.STRING) {
            setColumnValue(userEntity, cellId, cell.getStringCellValue().trim());
            log.info("String Data Display: {}", cell.getStringCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                LocalDate date = cell.getLocalDateTimeCellValue().toLocalDate();
                setColumnValue(userEntity, cellId, date.toString());
                log.info("Date Data Display: {}", date.toString());
            } else {
                setColumnValue(userEntity, cellId, String.valueOf((int) cell.getNumericCellValue()));
                log.info("Numeric Data Display: {}", cell.getNumericCellValue());
            }
        }
    }

    private void setColumnValue(UserEntity userEntity, int cellId, String value) {

        switch (cellId) {
            case 0:
                userEntity.setMobile(value);
                break;
            case 1:
                userEntity.setEmail(value);
                break;
            case 2:
                userEntity.setPassword(passwordEncoder.encode(value));
                break;
            case 3:
                userEntity.setUsername(value);
                break;
            case 4:
                userEntity.setLocation(value);
                break;
            case 5:
                LocalDate date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                userEntity.setDate(date);
                break;
            case 6:
                userEntity.setGender(Gender.valueOf(value.toUpperCase()));
                break;
            case 7:
                userEntity.setFirstName(value);
                break;
            case 8:
                userEntity.setSecondName(value);
                break;
            case 9:
                userEntity.setRole(Role.USER);
                break;
            default:
                break;
        }
    }
}
