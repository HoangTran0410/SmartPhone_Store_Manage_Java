/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WorkWithExcel;

import giaodienchuan.model.BackEnd.QuanLyKhachHang.KhachHang;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.KhuyenMai;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.QuanLyKhuyenMaiBUS;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.LoaiSanPham;
import giaodienchuan.model.BackEnd.QuanLyLoaiSanPham.QuanLyLoaiSanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLyNCC.NhaCungCap;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.NhanVien;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.QuanLyQuyenBUS;
import giaodienchuan.model.BackEnd.QuanLyQuyen.Quyen;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.SanPham;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.QuanLyTaiKhoanBUS;
import giaodienchuan.model.BackEnd.QuanLyTaiKhoan.TaiKhoan;
import giaodienchuan.model.FrontEnd.GiaoDienChuan.MyTable;
import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author Admin
 */
public class DocExcel {

    FileDialog fd = new FileDialog(new JFrame(), "Đọc excel", FileDialog.LOAD);

    public DocExcel() {

    }

    private String getFile() {
        fd.setFile("*.xls");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("nullnull")) {
            return null;
        }
        return url;
    }

    //Đọc file excel Nhà cung cấp
    public void docFileExcelNhaCungCap() {
        fd.setTitle("Nhập dữ liệu nhà cung cấp từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String diachi = cellIterator.next().getStringCellValue();
                    String sdt = cellIterator.next().getStringCellValue();
                    String fax = cellIterator.next().getStringCellValue();

                    QuanLyNhaCungCapBUS qlnccBUS = new QuanLyNhaCungCapBUS();

                    NhaCungCap nccOld = qlnccBUS.getNhaCungCap(ma);
                    if (nccOld != null) {
                        if (!hanhDongKhiTrung.contains("tất cả")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "Mã", "Tên", "Địa chỉ", "SDT", "Fax"});
                            mtb.addRow(new String[]{
                                "Cũ:", nccOld.getMaNCC(),
                                nccOld.getTenNCC(),
                                nccOld.getDiaChi(),
                                nccOld.getSDT(),
                                nccOld.getFax()
                            });
                            mtb.addRow(new String[]{
                                "Mới:", ma, ten, diachi, sdt, fax
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi đè")) {
                            qlnccBUS.update(ma, ten, diachi, sdt, fax);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        NhaCungCap ncc = new NhaCungCap(ma, ten, diachi, sdt, fax);
                        qlnccBUS.add(ncc);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem 
                    + "; Ghi đè " + countGhiDe
                    + "; Bỏ qua " + countBoQua
                    + ". Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    // Đọc file excel quyền
    public void docFileExcelQuyen() {
        fd.setTitle("Nhập dữ liệu quyền từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();
            
            String hanhDongKhiTrung = "";
            int countThem = 0;
            int countGhiDe = 0;
            int countBoQua = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String chitiet = cellIterator.next().getStringCellValue();

                    QuanLyQuyenBUS qlqBUS = new QuanLyQuyenBUS();
                    
                    Quyen qOld = qlqBUS.getQuyen(ma);
                    if (qOld != null) {
                        if (!hanhDongKhiTrung.contains("tất cả")) {
                            MyTable mtb = new MyTable();
                            mtb.setHeaders(new String[]{"", "Mã", "Tên", "Chi tiết quyển"});
                            mtb.addRow(new String[]{
                                "Cũ:", qOld.getMaQuyen(),
                                qOld.getTenQuyen(),
                                qOld.getChiTietQuyen()
                            });
                            mtb.addRow(new String[]{
                                "Mới:", ma, ten, chitiet
                            });

                            MyJOptionPane mop = new MyJOptionPane(mtb, hanhDongKhiTrung);
                            hanhDongKhiTrung = mop.getAnswer();
                        }
                        if (hanhDongKhiTrung.contains("Ghi đè")) {
                            qlqBUS.update(ma, ten, chitiet);
                            countGhiDe++;
                        } else {
                            countBoQua++;
                        }
                    } else {
                        Quyen q = new Quyen(ma, ten, chitiet);
                        qlqBUS.add(q);
                        countThem++;
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, "
                    + "Thêm " + countThem 
                    + "; Ghi đè " + countGhiDe
                    + "; Bỏ qua " + countBoQua
                    + ". Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    //Đọc file excel Tài khoản
    public void docFileExcelTaiKhoan() {
        fd.setTitle("Nhập dữ liệu tài khoản từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String taikhoan = cellIterator.next().getStringCellValue();
                    String matkhau = cellIterator.next().getStringCellValue();
                    String manv = cellIterator.next().getStringCellValue().split(" - ")[0];
                    String maquyen = cellIterator.next().getStringCellValue();

                    QuanLyTaiKhoanBUS qltkBUS = new QuanLyTaiKhoanBUS();

                    if (qltkBUS.getTaiKhoan(taikhoan) != null) {
                        qltkBUS.update(taikhoan, matkhau, manv, maquyen);
                    } else {
                        TaiKhoan tk = new TaiKhoan(taikhoan, matkhau, manv, maquyen);
                        qltkBUS.add(tk);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    //Đọc file excel Khách hàng
    public void docFileExcelKhachhang() {
        fd.setTitle("Nhập dữ liệu khách hàng từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String diachi = cellIterator.next().getStringCellValue();
                    String sdt = cellIterator.next().getStringCellValue();
                    int trangthai = (cellIterator.next().getStringCellValue().equals("Ẩn") ? 1 : 0);

                    QuanLyKhachHangBUS qlkhBUS = new QuanLyKhachHangBUS();

                    if (qlkhBUS.getKhachHang(ma) != null) {
                        qlkhBUS.update(ma, ten, diachi, sdt, trangthai);
                    } else {
                        KhachHang kh = new KhachHang(ma, ten, diachi, sdt, trangthai);
                        qlkhBUS.add(kh);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    //Đọc file excel Nhân viên
    public void docFileExcelNhanVien() {
        fd.setTitle("Nhập dữ liệu nhân viên từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    LocalDate ngaysinh = LocalDate.parse(cellIterator.next().getStringCellValue());
                    String diachi = cellIterator.next().getStringCellValue();
                    String sdt = cellIterator.next().getStringCellValue();
                    int trangthai = (cellIterator.next().getStringCellValue().equals("Ẩn") ? 1 : 0);

                    QuanLyNhanVienBUS qlnvBUS = new QuanLyNhanVienBUS();

                    if (qlnvBUS.getNhanVien(ma) != null) {
                        qlnvBUS.update(ma, ten, ngaysinh, diachi, sdt, trangthai);
                    } else {
                        NhanVien nv = new NhanVien(ma, ten, ngaysinh, diachi, sdt, trangthai);
                        qlnvBUS.add(nv);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    //Đọc file excel Khuyến mãi
    public void docFileExcelKhuyenMai() {
        fd.setTitle("Nhập dữ liệu khuyến mãi từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    float dieukien = (float) cellIterator.next().getNumericCellValue();
                    float phantram = (float) cellIterator.next().getNumericCellValue();
                    LocalDate ngaybatdau = LocalDate.parse(cellIterator.next().getStringCellValue());
                    LocalDate ngayketthuc = LocalDate.parse(cellIterator.next().getStringCellValue());

                    QuanLyKhuyenMaiBUS qlkmBUS = new QuanLyKhuyenMaiBUS();

                    if (qlkmBUS.getKhuyenMai(ma) != null) {
                        qlkmBUS.update(ma, ten, dieukien, phantram, ngaybatdau, ngayketthuc);
                    } else {
                        KhuyenMai km = new KhuyenMai(ma, ten, dieukien, phantram, ngaybatdau, ngayketthuc);
                        qlkmBUS.add(km);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    //Đọc file excel Sản phẩm
    public void docFileExcelSanPham() {
        fd.setTitle("Nhập dữ liệu sản phẩm từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String masp = cellIterator.next().getStringCellValue();
                    String maloai = cellIterator.next().getStringCellValue().split(" - ")[0];
                    String tensp = cellIterator.next().getStringCellValue();
                    float dongia = (float) cellIterator.next().getNumericCellValue();
                    int soluong = (int) cellIterator.next().getNumericCellValue();
                    String hinhanh = cellIterator.next().getStringCellValue();
                    int trangthai = (cellIterator.next().getStringCellValue().equals("Ẩn") ? 1 : 0);

                    QuanLySanPhamBUS qlspBUS = new QuanLySanPhamBUS();

                    if (qlspBUS.getSanPham(masp) != null) {
                        qlspBUS.update(masp, maloai, tensp, dongia, soluong, hinhanh, trangthai);
                    } else {
                        SanPham sp = new SanPham(masp, maloai, tensp, dongia, soluong, hinhanh, trangthai);
                        qlspBUS.add(sp);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }

    //Đọc file excel Loại sản phẩm
    public void docFileExcelLoaiSanPham() {
        fd.setTitle("Nhập dữ liệu loại sản phẩm từ excel");
        String url = getFile();
        if (url == null) {
            return;
        }

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));

            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            Row row1 = rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {

                    int stt = (int) cellIterator.next().getNumericCellValue();
                    String ma = cellIterator.next().getStringCellValue();
                    String ten = cellIterator.next().getStringCellValue();
                    String mota = cellIterator.next().getStringCellValue();

                    QuanLyLoaiSanPhamBUS qllspBUS = new QuanLyLoaiSanPhamBUS();

                    if (qllspBUS.getLoaiSanPham(ma) != null) {
                        qllspBUS.update(ma, ten, mota);
                    } else {
                        LoaiSanPham lsp = new LoaiSanPham(ma, ten, mota);
                        qllspBUS.add(lsp);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Đọc thành công, Vui lòng làm mới để thấy kết quả");

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Không tìm thấy file: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi đọc file " + url);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lỗi khi đóng inputstream: " + ex.getMessage());
            }
        }
    }
}