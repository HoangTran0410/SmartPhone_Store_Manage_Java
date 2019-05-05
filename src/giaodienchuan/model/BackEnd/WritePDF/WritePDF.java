/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.WritePDF;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import giaodienchuan.model.BackEnd.PriceFormatter;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.ChiTietHoaDon;
import giaodienchuan.model.BackEnd.QuanLyChiTietHoaDon.QuanLyChiTietHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.ChiTietPhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyChiTietPN.QuanLyChiTietPhieuNhapBUS;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.HoaDon;
import giaodienchuan.model.BackEnd.QuanLyHoaDon.QuanLyHoaDonBUS;
import giaodienchuan.model.BackEnd.QuanLyKhachHang.QuanLyKhachHangBUS;
import giaodienchuan.model.BackEnd.QuanLyKhuyenMai.QuanLyKhuyenMaiBUS;
import giaodienchuan.model.BackEnd.QuanLyNCC.QuanLyNhaCungCapBUS;
import giaodienchuan.model.BackEnd.QuanLyNhanVien.QuanLyNhanVienBUS;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.PhieuNhap;
import giaodienchuan.model.BackEnd.QuanLyPhieuNhap.QuanLyPhieuNhapBUS;
import giaodienchuan.model.BackEnd.QuanLySanPham.QuanLySanPhamBUS;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author nguye
 */
public class WritePDF {

    Document document;
    FileOutputStream file;
    Font fontData;
    Font fontTitle;
    Font fontHeader;

    QuanLyHoaDonBUS qlhdBUS;
    QuanLyChiTietHoaDonBUS qlcthdBUS;

    QuanLyPhieuNhapBUS qlpnBUS;
    QuanLyChiTietPhieuNhapBUS qlctpnBUS;

    QuanLyNhanVienBUS qlnvBUS;

    QuanLyKhachHangBUS qlkhBUS;

    QuanLySanPhamBUS qlspBUS;

    QuanLyKhuyenMaiBUS qlkmBUS;
    
    QuanLyNhaCungCapBUS qlnccBUS;

//    public WritePDF() {
//        document = new Document();
//    }
    public WritePDF(String url) {
        qlhdBUS = new QuanLyHoaDonBUS();
        qlcthdBUS = new QuanLyChiTietHoaDonBUS();
        qlpnBUS = new QuanLyPhieuNhapBUS();
        qlctpnBUS = new QuanLyChiTietPhieuNhapBUS();
        qlnvBUS = new QuanLyNhanVienBUS();
        qlkhBUS = new QuanLyKhachHangBUS();
        qlspBUS = new QuanLySanPhamBUS();
        qlkmBUS = new QuanLyKhuyenMaiBUS();
        qlnccBUS = new QuanLyNhaCungCapBUS();
        try {
            fontData = new Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
            fontTitle = new Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontHeader = new Font(BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 15, Font.NORMAL);
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Khong tim thay duong dan file " + url);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontTitle));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
//            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
            ex.printStackTrace();
        }
    }

    public void writeObject(String[] data) {
        Paragraph pdfData = new Paragraph();
        for (int i = 0; i < data.length; i++) {
            pdfData.add(data[i] + "  ");
        }
        try {
            document.add(pdfData);
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void writeTable(JTable _table) {
//        setTitle("ư ừ ử ữ ự");
        PdfPTable pdfTable = new PdfPTable(_table.getColumnCount());
        for (int i = 0; i < _table.getRowCount(); i++) {
            for (int j = 0; j < _table.getColumnCount(); j++) {
                String data = String.valueOf(_table.getValueAt(i, j));
                PdfPCell cell = new PdfPCell(new Phrase(data, fontData));
                pdfTable.addCell(cell);
            }
        }
        try {
            document.add(pdfTable);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
        }
    }

    public void writeHoaDon(String mahd) {
        setTitle("Thông tin hóa đơn");
        //Hien thong tin cua hoa don hien tai
        HoaDon hd = qlhdBUS.getHoaDon(mahd);

        Chunk glue = new Chunk(new VerticalPositionMark());// Khoang trong giua hang
        Paragraph paraMaHoaDon = new Paragraph(new Phrase("Hóa đơn: " + String.valueOf(hd.getMaHoaDon()), fontData));
        Paragraph para1 = new Paragraph();
        para1.setFont(fontData);
        para1.add(String.valueOf("Khách hàng:" + qlkhBUS.getKhachHang(hd.getMaKhachHang()).getTenKH() + "  -  " + hd.getMaKhachHang()));
        para1.add(glue);
        para1.add("Ngày lập:" + String.valueOf(hd.getNgayLap()));

        Paragraph para2 = new Paragraph();
        para2.setPaddingTop(30);
        para2.setFont(fontData);
        para2.add(String.valueOf("Nhân viên:" + qlnvBUS.getNhanVien(hd.getMaNhanVien()).getTenNV() + "  -  " + hd.getMaNhanVien()));
        para2.add(glue);
        para2.add("Giờ lập:" + String.valueOf(hd.getGioLap()));

        Paragraph paraKhuyenMai = new Paragraph();
        paraKhuyenMai.setPaddingTop(30);
        paraKhuyenMai.setFont(fontData);
        paraKhuyenMai.add("Khuyến mãi:" + qlkmBUS.getKhuyenMai(hd.getMaKhuyenMai()).getTenKM());

        try {
            document.add(paraMaHoaDon);
            document.add(para1);
            document.add(para2);
            document.add(paraKhuyenMai);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Tao table cho cac chi tiet cua hoa don
        PdfPTable pdfTable = new PdfPTable(5);
        float tongKhuyenMai = 0;
        float tongThanhTien = 0;
        PdfPCell cell;

        //Set headers cho table chi tiet
        cell = new PdfPCell(new Phrase("Mã sản phẩm", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Tên sản phẩm", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Đơn giá", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Số lượng", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Tổng tiền", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        for (int i = 0; i < 5; i++) {
            cell = new PdfPCell(new Phrase(""));
            pdfTable.addCell(cell);
        }

        //Truyen thong tin tung chi tiet vao table    
        for (ChiTietHoaDon cthd : qlcthdBUS.getAllChiTiet(mahd)) {
            cell = new PdfPCell(new Phrase(cthd.getMaSanPham(), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(qlspBUS.getSanPham(cthd.getMaSanPham()).getTenSP(), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(new PriceFormatter().format(cthd.getDonGia()), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(cthd.getSoLuong()), fontData));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(new PriceFormatter().format(cthd.getDonGia() * cthd.getSoLuong()), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            tongThanhTien += cthd.getDonGia() * cthd.getSoLuong();
        }

        try {
            document.add(pdfTable);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            tongKhuyenMai = hd.getTongTien() - tongThanhTien;
            Paragraph paraTongThanhTien = new Paragraph(new Phrase("Tổng thành tiền: " + new PriceFormatter().format(tongThanhTien), fontData));
            paraTongThanhTien.setIndentationLeft(300);
            document.add(paraTongThanhTien);
            Paragraph paraTongKhuyenMai = new Paragraph(new Phrase("Tổng khuyến mãi: " + new PriceFormatter().format(tongKhuyenMai), fontData));
            paraTongKhuyenMai.setIndentationLeft(300);
            document.add(paraTongKhuyenMai);
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + new PriceFormatter().format(hd.getTongTien()), fontData));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void writePhieuNhap(String mapn) {
        setTitle("Thông tin phiếu nhập");
        //Hien thong tin cua hoa don hien tai
        PhieuNhap pn = qlpnBUS.getPhieuNhap(mapn);

        Chunk glue = new Chunk(new VerticalPositionMark());// Khoang trong giua hang
        Paragraph paraMaHoaDon = new Paragraph(new Phrase("Phiếu nhập: " + String.valueOf(pn.getMaPN()), fontData));
        Paragraph para1 = new Paragraph();
        para1.setFont(fontData);
        para1.add(String.valueOf("Nhà cung cấp:" + qlnccBUS.getNhaCungCap(pn.getMaNCC()).getTenNCC()+ "  -  " + pn.getMaNCC()));
        para1.add(glue);
        para1.add("Ngày lập:" + String.valueOf(pn.getNgayNhap()));

        Paragraph para2 = new Paragraph();
        para2.setPaddingTop(30);
        para2.setFont(fontData);
        para2.add(String.valueOf("Nhân viên:" + qlnvBUS.getNhanVien(pn.getMaNV()).getTenNV() + "  -  " + pn.getMaNV()));
        para2.add(glue);
        para2.add("Giờ lập:" + String.valueOf(pn.getGioNhap()));

//        Paragraph paraKhuyenMai = new Paragraph();
//        paraKhuyenMai.setPaddingTop(30);
//        paraKhuyenMai.setFont(fontData);
//        paraKhuyenMai.add("Khuyến mãi:" + qlkmBUS.getKhuyenMai(hd.getMaKhuyenMai()).getTenKM());

        try {
            document.add(paraMaHoaDon);
            document.add(para1);
            document.add(para2);
//            document.add(paraKhuyenMai);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Tao table cho cac chi tiet cua hoa don
        PdfPTable pdfTable = new PdfPTable(5);
        PdfPCell cell;

        //Set headers cho table chi tiet
        cell = new PdfPCell(new Phrase("Mã sản phẩm", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Tên sản phẩm", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Đơn giá", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Số lượng", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        cell = new PdfPCell(new Phrase("Tổng tiền", fontHeader));
        cell.setBorder(0);
        pdfTable.addCell(cell);

        for (int i = 0; i < 5; i++) {
            cell = new PdfPCell(new Phrase(""));
            pdfTable.addCell(cell);
        }

        //Truyen thong tin tung chi tiet vao table    
        for (ChiTietPhieuNhap ctpn : qlctpnBUS.getAllChiTiet(mapn)) {
            cell = new PdfPCell(new Phrase(ctpn.getMaSP(), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(qlspBUS.getSanPham(ctpn.getMaSP()).getTenSP(), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(new PriceFormatter().format(ctpn.getDonGia()), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong()), fontData));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            pdfTable.addCell(cell);
            cell = new PdfPCell(new Phrase(new PriceFormatter().format(ctpn.getDonGia() * ctpn.getSoLuong()), fontData));
            cell.setBorder(0);
            pdfTable.addCell(cell);
        }

        try {
            document.add(pdfTable);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + new PriceFormatter().format(pn.getTongTien()), fontData));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
        } catch (DocumentException ex) {
            Logger.getLogger(WritePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void closeFile() {
        document.close();
    }
}
