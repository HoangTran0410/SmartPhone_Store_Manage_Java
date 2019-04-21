/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.Test.QLChucVu.ChucVu;

/**
 *
 * @author DELL
 */
public class ChucVu {
    String MaCV;
    String TenCV;

    public ChucVu(String MaCV, String TenCV) {
        this.MaCV = MaCV;
        this.TenCV = TenCV;
    }

    public String getMaCV() {
        return MaCV;
    }

    public void setMaCV(String MaCV) {
        this.MaCV = MaCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String TenCV) {
        this.TenCV = TenCV;
    }
    
}
