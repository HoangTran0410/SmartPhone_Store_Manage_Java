/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyPhieuNhap;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author Admin
 */
public class PhieuNhap {

    String maPN;
    String maNCC;
    String maNV;
    LocalDate ngayNhap;
    LocalTime gioNhap;
    float tongTien = 0;

    public PhieuNhap() {
        this.ngayNhap = LocalDate.now();
        this.gioNhap = LocalTime.now();
    }

    public PhieuNhap(String maPN, String maNCC, String maNV, LocalDate ngayNhap, LocalTime gioNhap, float tongTien) {

        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.gioNhap = gioNhap;
        this.tongTien = tongTien;
    }

    public String getMaPN() {
        return maPN;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public LocalTime getGioNhap() {
        return gioNhap;
    }

    public Float getTongTien() {
        return tongTien;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public void setGioNhap(LocalTime gioNhap) {
        this.gioNhap = gioNhap;
    }

    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }

}
