/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaodienchuan.model.FrontEnd.FormQuanLy;

import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class NhapHangForm extends JPanel {

    public NhapHangForm(int width, int height) {
        setLayout(null);

        ChonSanPhamPanel cspnh = new ChonSanPhamPanel(0, 0, width - 555, height);
        this.add(cspnh);

        PhieuNhapHang pnh = new PhieuNhapHang(width - 550, 0, 550, height);
        this.add(pnh);

        pnh.setTarget(cspnh);
        cspnh.setTarget(pnh);
    }
}
