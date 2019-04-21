/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyChiTietPhieuNhap;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhap {

    String ma;
    String maSP;
    int soLuong;
    float donGia;

    public ChiTietPhieuNhap() {

    }

    public ChiTietPhieuNhap(String ma, String maSP, int soLuong, float donGia) {
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

    public int getSoLuong() {
        return soLuong;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

}
