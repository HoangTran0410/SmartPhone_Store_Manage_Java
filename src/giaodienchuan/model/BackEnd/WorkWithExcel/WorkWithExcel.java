/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WorkWithExcel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author DELL
 */
public class WorkWithExcel {

    String fileSeparator = System.getProperty("file.separator");

    public WorkWithExcel() {

    }
    
    public void writeFileExcel() {
        
    }

    public void createFileExcel(String filename) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet 1");

        FileOutputStream outFile = null;
        try {
            File file = new File(filename);
            String relativePath = getRelativePath(filename);
            if (file.createNewFile()) {
                System.out.println(relativePath + " File Created in Project root directory");
            } else {
                System.out.println("File " + relativePath + " already exists in the project root directory");
            }
            outFile = new FileOutputStream(file);
            workbook.write(outFile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(WorkWithExcel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WorkWithExcel.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (outFile != null) {
                    outFile.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(WorkWithExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getRelativePath(String filename) {
        return "tmp" + fileSeparator + filename;
    }
}
