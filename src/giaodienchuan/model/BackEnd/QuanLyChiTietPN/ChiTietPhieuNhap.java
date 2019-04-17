/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyChiTietPN;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhap {

    String ma;
    String maSP;
    Integer soLuong;
    Float donGia;

    public ChiTietPhieuNhap() {

    }

    public ChiTietPhieuNhap(String ma, String maSP, Integer soLuong, Float donGia) {
        this.ma = ma;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMa() {
        return ma;
    }

    public String getMaSP() {
        return maSP;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public Float getDonGia() {
        return donGia;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(Float donGia) {
        this.donGia = donGia;
    }

}
