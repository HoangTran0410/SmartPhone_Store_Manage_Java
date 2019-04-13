package giaodienchuan.model.FrontEnd.Test.QLSV;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class SinhVienDTO {
    String ma;
    String ho;
    String ten;
}

class SinhVienDAO {

    String user = "root";
    String pass = "";
    String url = "jdbc:mysql://localhost:3306/QLSV";

    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;
    
    SinhVienDAO() {
        if(conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Không thể kết nối tới DB");
            }
        }
    }
    
    ArrayList docDSSV() {
        ArrayList<SinhVienDTO> dssv = new ArrayList<>();
        
        try {
            String qry = "select * from SinhVien";
            st = conn.createStatement();
            rs = st.executeQuery(qry);
            while(rs.next()) {
                SinhVienDTO sv = new SinhVienDTO();
                sv.ma = rs.getString(1);
                sv.ho = rs.getString(2);
                sv.ten = rs.getString(3);
                dssv.add(sv);
            }
            
        } catch (SQLException e) {
            System.err.println("Lỗi đọc dữ liệu từ DB");
        }
        
        return dssv;
    }
    
    void them(SinhVienDTO sv) {
        try {
            String qry = "insert into SinhVien Values (";
            qry += "'" + sv.ma + "',";
            qry += "'" + sv.ho + "',";
            qry += "'" + sv.ten + "');";
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (SQLException e) {
            System.err.println("Lỗi thêm sinh viên vào DB");
        }
    }
    
    void xoa(String ma) {
        try {
            String qry = "delete from SinhVien where MaSV='" + ma + "'";
            
            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (SQLException e) {
            System.err.println("Lỗi xóa sinh viên mã '" + ma + "' từ DB");
        }
    }
    
    void sua(SinhVienDTO sv) {
        try {
            String qry = "Update SinhVien Set ho='" + sv.ho + "',ten='" + sv.ten + "' where";

            st = conn.createStatement();
            st.executeUpdate(qry);
        } catch (SQLException e) {
            System.err.println("Lỗi thêm sinh viên vào DB");
        }
    }
}

class SinhVienBUS {
    static ArrayList<SinhVienDTO> dssv;
    
    SinhVienBUS() {}
    
    void docDSSV() {
        SinhVienDAO data = new SinhVienDAO();
        if(dssv == null) dssv = new ArrayList<>();
        dssv = data.docDSSV();
    }
    
    void them(SinhVienDTO sv) {
        // kiem tra du lieu hop le
        // kiem tra ma sinh vien duy nhat
        SinhVienDAO data = new SinhVienDAO();
        data.them(sv);
        dssv.add(sv);
    }
    
    SinhVienDTO timkiem(String ma) {
        SinhVienDTO sv = new SinhVienDTO();
        // duyet ArryList dssv
        return sv;
    }
}

class SinhVienForm {

    JTextField txMaSV = new JTextField();
    JTextField txHo = new JTextField();
    JTextField txTen = new JTextField();
    DefaultTableModel model;
    JTable tblDSSV = new JTable();
    
    SinhVienForm() {}
    
    private void btnDocDSSVMouseClicked(MouseEvent me) {
        SinhVienBUS bus = new SinhVienBUS();
        
        if(SinhVienBUS.dssv == null) bus.docDSSV();
        
        Vector header = new Vector();
        header.add("MSSV");
        header.add("Họ");
        header.add("Tên");
        
        model = new DefaultTableModel(header, 0);
        
        for(SinhVienDTO sv : SinhVienBUS.dssv) {
            Vector row = new Vector();
            row.add(sv.ma);
            row.add(sv.ho);
            row.add(sv.ten);
            model.addRow(row);
        }
        tblDSSV.setModel(model);
    }
    
    private void btnThemDSSVMouseClicked(MouseEvent me) {
        SinhVienDTO sv = new SinhVienDTO();
        
        sv.ma = txMaSV.getText();
        sv.ho = txHo.getText();
        sv.ten = txTen.getText();
        
        SinhVienBUS bus = new SinhVienBUS();
        bus.them(sv);
        
        Vector row = new Vector();
        row.add(sv.ma);
        row.add(sv.ho);
        row.add(sv.ten);
        
        model.addRow(row);
        tblDSSV.setModel(model);
    }
}