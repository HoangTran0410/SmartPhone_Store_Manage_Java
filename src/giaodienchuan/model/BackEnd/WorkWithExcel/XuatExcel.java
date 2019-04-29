/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WorkWithExcel;

import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.QuanLyKhuyenMaiBUS;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.QuanLyQuyenBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.QuanLyTaiKhoanBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.KhuyenMai;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.LoaiSanPham;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.PhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.QuanLyPhieuNhapBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.Quyen;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.TaiKhoan;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Admin
 */
public class XuatExcel {

    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();
    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();
    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();
    QuanLyTaiKhoanBUS qltkBUS = new QuanLyTaiKhoanBUS();
    QuanLyQuyenBUS qlqBUS = new QuanLyQuyenBUS();
    QuanLyKhuyenMaiBUS qlkmBUS = new QuanLyKhuyenMaiBUS();
    QuanLyHoaDonBUS qlhdBUS = new QuanLyHoaDonBUS();
    QuanLyPhieuNhapBUS qlpnBUS = new QuanLyPhieuNhapBUS();
    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();
    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();

    //******************Xuất file Excel Nhà cung cấp    
    public void xuatFileExcelNhaCungCap() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Nhà cung cấp");

        List<NhaCungCap> list = qlnccBUS.getDsncc();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã nhà cung cấp");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên nhà cung cấp");

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Địa chỉ");

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Số điện thoại");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Fax");

        for (NhaCungCap ncc : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(ncc.getMaNCC());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(ncc.getTenNCC());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(ncc.getDiaChi());

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(ncc.getSDT());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(ncc.getFax());
            //Bonus (E)
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);

        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/nhacungcap" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Nhân viên
    public void xuatFileExcelNhanVien() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Nhân viên");

        List<NhanVien> list = qlnvBUS.getDsnv();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã nhân viên");

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên nhân viên");

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Ngày sinh");

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Địa chỉ");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Số điện thoại");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Trạng thái");

        for (NhanVien nv : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(nv.getMaNV());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(nv.getTenNV());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(String.valueOf(nv.getNgaySinh()));

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(nv.getDiaChi());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(nv.getSDT());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue((nv.getTrangThai() == 0 ? "Hiện" : "Ẩn"));
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);

        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/nhanvien" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());

    }

    //******************Xuất file Excel Khách hàng
    public void xuatFileExcelKhachHang() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Khách hàng");

        List<KhachHang> list = qlkhBUS.getDskh();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã khách hàng");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên khách hàng");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Địa chỉ");
//        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Số điện thoại");
//        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Trạng thái");
//        cell.setCellStyle(style);

        for (KhachHang kh : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(kh.getMaKH());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(kh.getTenKH());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(kh.getDiaChi());

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(kh.getSDT());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue((kh.getTrangThai() == 0 ? "Hiện" : "Ẩn"));
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);

        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/khachhang" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());

    }

    //******************Xuất file Excel Tài khoản
    public void xuatFileExcelTaiKhoan() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Tài khoản");

        List<TaiKhoan> list = qltkBUS.getDstk();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Tên tài khoản");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Mật khẩu");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Mã nhân viên");
//        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Mã quyền");
//        cell.setCellStyle(style);

//        cell.setCellStyle(style);
        for (TaiKhoan tk : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(tk.getUsername());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(tk.getPassword());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(tk.getMaQuyen() + " (" + qlnvBUS.getNhanVien(tk.getMaNV()).getTenNV() + ")");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(tk.getMaQuyen() + " (" + qlqBUS.getQuyen(tk.getMaQuyen()).getTenQuyen() + ")");
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);

        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/taikhoan" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Khuyến mãi
    public void xuatFileExcelKhuyenMai() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Khuyến mãi");

        List<KhuyenMai> list = qlkmBUS.getDskm();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue("Điều kiện");
//        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue("Phần trăm");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Ngày bắt đầu");
//        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Ngày kết thúc");

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Trạng thái");
//        cell.setCellStyle(style);

        for (KhuyenMai km : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(km.getMaKM());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(km.getTenKM());

            cell = row.createCell(3, CellType.NUMERIC);
            cell.setCellValue(km.getDieuKhienKM());

            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue(km.getPhanTramKM());

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(String.valueOf(km.getNgayBD()));

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(String.valueOf(km.getNgayKT()));

            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue(km.getTrangThai());
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);        
        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/khuyenmai" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Sản phẩm
    public void xuatFileExcelSanPham() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sản phẩm");

        List<SanPham> list = qlspBUS.getDssp();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã sản phẩm");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Loại sản phẩm");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Tên sản phẩm");
//        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Đơn giá");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Số lượng");
//        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Trạng thái");
//        cell.setCellStyle(style);

        for (SanPham sp : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(sp.getMaSP());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(sp.getMaLSP() + " (" + qllspBUS.getLoaiSanPham(sp.getMaLSP()).getTenLSP() + ")");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(sp.getTenSP());

            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue(sp.getDonGia());

            cell = row.createCell(5, CellType.NUMERIC);
            cell.setCellValue(sp.getSoLuong());

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(sp.getTrangThai() == 0 ? "Hiện" : "Ẩn");

//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);
        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/sanpham" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Loại sản phẩm
    public void xuatFileExcelLoaiSanPham() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Loại sản phẩm");

        List<LoaiSanPham> list = qllspBUS.getDslsp();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã loại sản phẩm");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên loại sản phẩm");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Mô tả");
//        cell.setCellStyle(style);

        for (LoaiSanPham lsp : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(lsp.getMaLSP());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(lsp.getTenLSP());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(lsp.getMoTa());

        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/loaisanpham" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Quyền
    public void xuatFileExcelQuyen() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Quyền");

        List<Quyen> list = qlqBUS.getDsq();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã quyền");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Tên quyền");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Chi tiết quyền");
//        cell.setCellStyle(style);

        for (Quyen q : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(q.getMaQuyen());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(q.getTenQuyen());

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(q.getChiTietQuyen());

        }
        for (int i = 0; i <= rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/quyen" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Hóa đơn
    public void xuatFileExcelHoaDon() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Hóa đơn");

        List<HoaDon> list = qlhdBUS.getDshd();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã hóa đơn");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Mã nhân viên");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Mã khách hàng");
//        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Mã khuyến mãi");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Ngày lập");
//        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Giờ lập");

        cell = row.createCell(7, CellType.NUMERIC);
        cell.setCellValue("Tổng tiền");
//        cell.setCellStyle(style);

        for (HoaDon hd : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(hd.getMaHoaDon());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(hd.getMaNhanVien() + " (" + qlnvBUS.getNhanVien(hd.getMaNhanVien()).getTenNV() + ")");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(hd.getMaKhachHang() + " (" + qlkhBUS.getKhachHang(hd.getMaKhachHang()).getTenKH() + ")");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(hd.getMaKhuyenMai() + " (" + qlkmBUS.getKhuyenMai(hd.getMaKhuyenMai()).getTenKM() + ")");

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(String.valueOf(hd.getNgayLap()));

            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue(String.valueOf(hd.getGioLap()));

            cell = row.createCell(7, CellType.NUMERIC);
            cell.setCellValue(hd.getTongTien());
//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);

        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }

        File file = new File("C:/demo/hoadon" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }

    //******************Xuất file Excel Phiếu nhập
    public void xuatFileExcelPhieuNhap() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Phiếu nhập");

        List<PhieuNhap> list = qlpnBUS.getDspn();
        int rownum = 0;
        Cell cell;
        Row row;

//        HSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);
        cell = row.createCell(0, CellType.NUMERIC);
        cell.setCellValue("STT");
//        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã phiếu nhập");
//        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Mã nhà cung cấp");
//        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Mã nhân viên");
//        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue("Ngày nhập");

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Giờ nhập");
//        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.NUMERIC);
        cell.setCellValue("Tổng tiền");
//        cell.setCellStyle(style);

        for (PhieuNhap pn : list) {
            rownum++;
            row = sheet.createRow(rownum);

            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(rownum);

            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(pn.getMaPN());

            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(pn.getMaNCC() + " (" + qlnccBUS.getNhaCungCap(pn.getMaNCC()).getTenNCC() + ")");

            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(pn.getMaNV() + " (" + qlnvBUS.getNhanVien(pn.getMaNV()).getTenNV() + ")");

            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(String.valueOf(pn.getNgayNhap()));

            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(String.valueOf(pn.getGioNhap()));

            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue(String.valueOf(pn.getTongTien()));

//        String formula = "0.1*C" + (rownum + 1) + "*D" + (rownum + 1);
//        cell = row.createCell(6, CellType.FORMULA);
//        cell.setCellFormula(formula);
        }
        for (int i = 0; i < rownum; i++) {
            sheet.autoSizeColumn(i);
        }
        File file = new File("C:/demo/phieunhap" + getTime() + ".xls");
        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        JOptionPane.showMessageDialog(null, "Created file: " + file.getAbsolutePath());
    }
    
    private String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }
}
