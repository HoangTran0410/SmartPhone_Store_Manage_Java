/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyNCC;

/**
 *
 * @author Admin
 */
public class NhaCungCap {
    private String maNCC;
    private String tenNCC;
    private String diaChi;
    private String SDT;
    private String Fax;
    
    public NhaCungCap(){
        
    }
    public NhaCungCap(String maNCC,String tenNCC, String diaChi, String SDT, String Fax)
    {
        this.maNCC=maNCC;
        this.tenNCC=tenNCC;
        this.diaChi=diaChi;
        this.SDT=SDT;
        this.Fax=Fax;
    }
    public NhaCungCap(NhaCungCap n)
    {
        this.maNCC=n.maNCC;
        this.tenNCC=n.tenNCC;
        this.diaChi=n.diaChi;
        this.SDT=n.SDT;
        this.Fax=n.Fax;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public String getFax() {
        return Fax;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public void setTenNCC(String tenNCC) {
        this.tenNCC = tenNCC;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setFax(String Fax) {
        this.Fax = Fax;
    }
        
    
}
