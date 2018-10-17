
package form;

import tool.koneksiDB;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class IfrProdi extends javax.swing.JInternalFrame {
    
        koneksiDB getCnn = new koneksiDB();
        Connection _Cnn;
        String sqlselect, sqlinsert, sqldelete;
        String vkd_prodi, vprodi, vkd_jur, vjurusan;
        private DefaultTableModel tblprodi;
        
 
    public IfrProdi() {
        initComponents();
        
        formTengah();
        listJurusan();
        clearForm();
        disableInput();
        setTabelProdi();
        showDataProdi();
    }
    
    private void clearForm(){
        cmbJurusan.setSelectedIndex(0);
        txtKdProdi.setText("");
        txtProdi.setText("");
        btnSimpan.setText("");
    }
    
    private void disableInput(){
        cmbJurusan.setEnabled(false);
        txtKdProdi.setEnabled(false);
        txtProdi.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }

    private void enableInput(){
        cmbJurusan.setEnabled(false);
        txtKdProdi.setEnabled(false);
        btnSimpan.setEnabled(false);
        btnHapus.setEnabled(false);
    }
    
    private void setTabelProdi(){
        String[] kolom1 = {"Jurusan", "KD. Prosi", "Program Studi"};
        tblprodi = new DefaultTableModel(null, kolom1){
          Class[] types = new Class[]{
              java.lang.String.class,
              java.lang.String.class,
              java.lang.String.class
          };  
          public Class getColumnClass(int columnIndex){
              return types [columnIndex];
          }
          //Agar tabel tidak bisa di edit
          public boolean isCellEditable(int row, int col){
              int cola = tblprodi.getColumnCount();
              return (col < cola)?false:true;
          }
        };
        tbDataProdi.setModel(tblprodi);
        tbDataProdi.getColumnModel().getColumn(0).setPreferredWidth(200);
        tbDataProdi.getColumnModel().getColumn(1).setPreferredWidth(75);
        tbDataProdi.getColumnModel().getColumn(1).setPreferredWidth(250);
    }
    
    private void clearTabelProdi(){
        int row = tblprodi.getRowCount();
        for(int i = 0; i < row; i++){
            tblprodi.removeRow(0);
        }
    }
    
    private void showDataProdi(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = "select * from tbprodi order by kd_prodi asc";
            Statement stat = _Cnn.createStatement();
            ResultSet res = stat.executeQuery(sqlselect);
            while(res.next()){
                vjurusan = res.getString("jurusan");
                vkd_prodi = res.getString("kd_jurusan");
                vprodi = res.getString("prodi");
                Object[] data = {vjurusan, vkd_prodi, vprodi};
                tblprodi.addRow(data);
            }
            lbRecord.setText("Record : "+tbDataProdi.getRowCount());            
        }catch(SQLException ex){
            JOptionPane.showConfirmDialog(this, "Error method showDataTabel() :"+ex);
        }
    }
    
    private void aksiSimpan(){
        vkd_jur = KeyJurusan[cmbJurusan.getSelectedIndex()];
        vkd_prodi = txtKdProdi.getText();
        vprodi = txtProdi.getText();
        
       
        if(btnSimpan.getText().equals("Simpan")){
            sqlinsert = "insert into tbprodi values " + " ('"+vkd_prodi+"','"+vprodi+"', '"+vkd_jur+"') ";
        }else{
            sqlinsert = "update tbprodi set prodi='"+vprodi+"' "
                    + " where kd_prodi='"+vkd_prodi+"' ";
        }
        
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            Statement stat = _Cnn.createStatement();
            stat.executeUpdate(sqlinsert);
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "informasi", JOptionPane.INFORMATION_MESSAGE);
            clearForm(); disableInput(); showDataProdi();  
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "Error method aksiSimpan() : "+ex);
            
        }
    }
 
    private void aksiHapus(){
        int jawab = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin akan menhapus data ini? ID. User : "+vkd_prodi,
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(jawab == JOptionPane.YES_OPTION){
            try{
                _Cnn = null;
                _Cnn = getCnn.getConnection();
                sqldelete = "delete from tbprodi where kd_prodi='"+vkd_prodi+"'";
                Statement stat = _Cnn.createStatement();
                stat.executeUpdate(sqldelete);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "informasi", JOptionPane.INFORMATION_MESSAGE);
                 showDataProdi(); clearForm(); disableInput();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "Error method aksihapus() : "+ex);
                
              }
        }  
    }
    
    private void formTengah(){
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension framesize = this.getSize();
        if(framesize.height < screensize.height){
            framesize.height = screensize.height;
        }
        if(framesize.width < screensize.width){
            framesize.width = screensize.width;
        }
        this.setLocation((screensize.width - framesize.width)/2, (screensize.height - framesize.height)/2);
    
    }
    
    
    String[] KeyJurusan;
    private void listJurusan(){
        try{
            _Cnn = null;
            _Cnn = getCnn.getConnection();
            String sql = "select * from tbjurusan order by jurusan asc";
            Statement state = _Cnn.createStatement();
            ResultSet res=state.executeQuery(sql);
            cmbJurusan.removeAllItems();
            cmbJurusan.repaint();
            cmbJurusan.addItem("-- Pilih--");
            int i = 1;
            while(res.next()){
                cmbJurusan.addItem(res.getString(1));
                i++;
            }
            res.first();
            KeyJurusan = new String[i+1];
            for(Integer x = 1;x <i;x++) {
                KeyJurusan[x] = res.getString(1);
                res.next();
            }
        }catch(SQLException ex){
            System.out.println("Error method listJurusan() "+ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cmbJurusan = new javax.swing.JComboBox<>();
        txtKdProdi = new javax.swing.JTextField();
        txtProdi = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbDataProdi = new javax.swing.JTable();
        lbRecord = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        jButton2.setText("jButton2");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setTitle(".: Form User");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Admin-Schoolar-Icon.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), " Input Data"));
        jPanel1.setOpaque(false);

        cmbJurusan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));
        cmbJurusan.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Jurusan"));
        cmbJurusan.setOpaque(false);
        cmbJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbJurusanActionPerformed(evt);
            }
        });

        txtKdProdi.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Kode Prodi  :"));
        txtKdProdi.setOpaque(false);

        txtProdi.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Program Studi :"));
        txtProdi.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbJurusan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtKdProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProdi, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 28, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmbJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKdProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProdi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi"));
        jPanel3.setOpaque(false);

        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/trans-add.png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/save-black.png"))); // NOI18N
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/trans-hapus.png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Tabel Data Prodi : klik 2x untuk mengubah/menghapus data"));

        tbDataProdi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Jurusan", "Kode Prodi", "Program Studi"
            }
        ));
        tbDataProdi.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tbDataProdiAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tbDataProdi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDataProdiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbDataProdi);

        lbRecord.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbRecord.setText("Record : 0");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/logo.png"))); // NOI18N
        jLabel2.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Form Prodi");

        jLabel5.setText("Form ini digunakan untuk mengolah data program studi");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 137, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbRecord)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
       enableInput();
       cmbJurusan.requestFocus(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
       if(cmbJurusan.getSelectedIndex()<=0){
            JOptionPane.showMessageDialog(this, "Jurusan harus dipilih ", "informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        }else if(txtKdProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "kode prodi harus diisi ", "informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        }else if(txtProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "program studi harus dipilih ", "informasi",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiSimpan();
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void cmbJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbJurusanActionPerformed
          
    }//GEN-LAST:event_cmbJurusanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
       if(txtKdProdi.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Anda belum memilih data yang akan dihapus", 
                    "Informssi", JOptionPane.INFORMATION_MESSAGE);
        }else{
            aksiHapus();
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void tbDataProdiAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tbDataProdiAncestorAdded
      
    }//GEN-LAST:event_tbDataProdiAncestorAdded

    private void tbDataProdiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDataProdiMouseClicked
         if(evt.getClickCount()==2){        
            int brs = tbDataProdi.getSelectedRow();
            vjurusan = tbDataProdi.getValueAt(brs, 0).toString();
            vkd_prodi = tbDataProdi.getValueAt(brs, 1).toString();
            vprodi = tbDataProdi.getValueAt(brs, 2).toString();

            cmbJurusan.setSelectedItem(vjurusan);
            txtKdProdi.setText(vkd_prodi); 
            txtProdi.setText(vprodi);
            enableInput();
            txtKdProdi.setEnabled(false);
            btnSimpan.setText("Ubah");
            btnHapus.setEnabled(true);
            txtProdi.requestFocus(true);  
      }
    }//GEN-LAST:event_tbDataProdiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbJurusan;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbRecord;
    private javax.swing.JTable tbDataProdi;
    private javax.swing.JTextField txtKdProdi;
    private javax.swing.JTextField txtProdi;
    // End of variables declaration//GEN-END:variables
}
