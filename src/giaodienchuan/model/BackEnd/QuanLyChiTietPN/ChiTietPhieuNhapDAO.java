/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.BackEnd.QuanLyChiTietPN;

import giaodienchuan.model.BackEnd.ConnectionDB.ConnectionDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhapDAO {

    ConnectionDB qlctpnConnection;

    public ArrayList<ChiTietPhieuNhap> readDB() {
        ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();
        qlctpnConnection = new ConnectionDB();
        try {

            String query = "SELECT * FROM chitietphieunhap";
            ResultSet r = qlctpnConnection.sqlQuery(query);
            if(r!=null){
            while(r.next())
            {
             String ma=r.getString(1);
             String maSP=r.getString(2);
             Integer soLuong=r.getInt(3);
             Float donGia=r.getFloat(4);
             
             ChiTietPhieuNhap ctpn= new ChiTietPhieuNhap(ma,maSP,soLuong,donGia);
             dsctpn.add(ctpn);
            }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");

        } finally {
            qlctpnConnection.closeConnect();
        }
        return dsctpn;

    }
    public ArrayList<ChiTietPhieuNhap> search(String columName,String value) {
        ArrayList<ChiTietPhieuNhap> dsctpn = new ArrayList<>();
        qlctpnConnection = new ConnectionDB();
        try {

            String query = "SELECT * FROM chitietphieunhap WHERE"+columName+"LIKE '%"+value+"%'";
            ResultSet r = qlctpnConnection.sqlQuery(query);
            if(r!=null){
            while(r.next())
            {
             String ma=r.getString(1);
             String maSP=r.getString(2);
             Integer soLuong=r.getInt(3);
             Float donGia=r.getFloat(4);
             
             ChiTietPhieuNhap ctpn= new ChiTietPhieuNhap(ma,maSP,soLuong,donGia);
             dsctpn.add(ctpn);
            }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Không thấy data cần tìm trong ResultSet");

        } finally {
            qlctpnConnection.closeConnect();
        }
        return dsctpn;

    
    }
    public boolean add(ChiTietPhieuNhap ctpn)
    {
        Boolean ok= qlctpnConnection.sqlUpdate("INSERT INTO `chitietphieunhap`(`MaPN`,`MaSP`,`SoLuong`,`DonGia`) VALUE('"
              +ctpn.getMa()+ "', '" + ctpn.getMaSP() + "','" + ctpn.getSoLuong() + "','" + ctpn.getDonGia() + "')");
        return ok;
    }
    public boolean delete(String mactpn)
    {
        Boolean ok= qlctpnConnection.sqlUpdate("DELETE *FROM `chitietphieunhap` WHERE `chitietphieunhap`.`MaPN`='"+mactpn+"'");
        return ok;
    }
    public boolean update(String mactpn,String masp,Integer soluong,Float dongia)
    {
        Boolean ok= qlctpnConnection.sqlUpdate("UPDATE `chitietphieunhap` SET MaPN='"+mactpn+"',MaSP='"+masp+"',SoLuong='"+soluong+"',DonGia='"+dongia+"'");
        return ok;
    }
}
