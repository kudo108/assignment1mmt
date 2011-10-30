



/**
 *
 * @author CrazyTeam
 * Assignment 1 Computer Network - BitCrazy Application
 */

import javax.swing.*;
import java.io.*;
import javax.swing.table.*;
import java.util.Vector;
import java.awt.Component;
// java.util.ArrayList;

// This class renders a JProgressBar in a table cell.
public final class GUI extends javax.swing.JFrame {

    private static final String SAVE = "persist.bin";
    public static final int ID_COL = 0;
    public static final int NAME_COL = 1;
    public static final int SIZE_COL = 2;
    public static final int STATUS_COL = 3;
    public static final int SPEED_COL = 4;
    public static final int PROGRESS_COL = 5;
    public static final int LOCATION_COL = 6;
    public static final int LIMIT_COL = 7;
    public static final int OK_CLICK = JOptionPane.OK_OPTION;
    public static final int CANCEL_CLICK = JOptionPane.CANCEL_OPTION;
    
    
    public UploadManager seeder;
    private DefaultTableModel model;
//    private ProgressRenderer renderer;
    private int sltRow = 0;
    private int isExist = JOptionPane.OK_OPTION;
    private File selectedFile = null;
    private DownloadManager dl ;
    
    
    /** Creates new form GUI */
    GUI(UploadManager seeder, DownloadManager dl){ 
        super();
        this.seeder = seeder;
        this.dl = dl;
        setLookandFeel();
        initComponents();
//        setDefaultIP();
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        removeButton.setEnabled(false);
        addButton.setEnabled(false);
        addIDButton.setEnabled(false);
        //if(this.countRow() > 0)
            //restore();
    }
   
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileOpen = new javax.swing.JFileChooser();
        fileSave = new javax.swing.JFileChooser();
        addButton = new javax.swing.JButton();
        startButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        FileTable = new javax.swing.JTable(model){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                if(colIndex == LIMIT_COL){
                    return true;
                }
                return false; //Disallow the editing of any cell
            }
        };
        removeButton = new javax.swing.JButton();
        addIDButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        IPField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        connectButton = new javax.swing.JButton();
        limitButton = new javax.swing.JButton();

        fileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileOpenActionPerformed(evt);
            }
        });

        fileSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileSaveActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BitCrazy | Computer Network | HCMUT");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        addButton.setFont(new java.awt.Font("Arial", 1, 11));
        addButton.setText("Upload...");
        addButton.setToolTipText("Click to add new file");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        startButton.setFont(new java.awt.Font("Arial", 1, 11));
        startButton.setText("Start");
        startButton.setToolTipText("Start seeding");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        try {
            FileInputStream file = new FileInputStream(SAVE);
            ObjectInputStream input = new ObjectInputStream(file);
            Vector dataVector = (Vector) input.readObject();
            Vector columnVector = new Vector();
            columnVector.add("ID");
            columnVector.add("Name");
            columnVector.add("Size");
            columnVector.add("Status");
            columnVector.add("Speed");
            columnVector.add("Progress");
            columnVector.add("Location");
            columnVector.add("Limit");
            model = new DefaultTableModel(dataVector, columnVector);
            input.close();
            file.close();
            System.out.println("Saved GUI loaded!");
        } catch (IOException ioe) {
            model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                    "ID", "Name", "Size", "Status", "Speed", "Progress", "Location", "Limit"
                }
            );
            System.out.println("WELCOME!");
        } catch (ClassNotFoundException cnfe) {
            model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {
                    "ID", "Name", "Size", "Status", "Speed", "Progress", "Location", "Limit"
                }
            );
            System.out.println("Loading failed: " + cnfe.getMessage());
        }
        FileTable.setModel(model );
        FileTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        FileTable.setName(""); // NOI18N
        FileTable.getTableHeader().setReorderingAllowed(false);
        FileTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FileTableMouseClicked(evt);
            }
        });
        FileTable.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FileTablePropertyChange(evt);
            }
        });
        FileTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                FileTableKeyReleased(evt);
            }
        });
        FileTable.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                FileTableVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(FileTable);
        FileTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        removeButton.setFont(new java.awt.Font("Arial", 1, 11));
        removeButton.setForeground(new java.awt.Color(204, 0, 0));
        removeButton.setText("Remove");
        removeButton.setToolTipText("Remove the current row");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        addIDButton.setFont(new java.awt.Font("Arial", 1, 11));
        addIDButton.setText("Download...");
        addIDButton.setToolTipText("Add new ID to download");
        addIDButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addIDButtonActionPerformed(evt);
            }
        });

        stopButton.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        stopButton.setText("Stop");
        stopButton.setToolTipText("Stop seeding");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        IPField.setFont(new java.awt.Font("Courier New", 0, 11));
        IPField.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 11));
        jLabel2.setText("Server:");

        connectButton.setFont(new java.awt.Font("Arial", 1, 11));
        connectButton.setText("Set");
        connectButton.setToolTipText("Connect to server");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });

        limitButton.setText("Limit");
        limitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addIDButton, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stopButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(removeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(limitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(IPField, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(connectButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addButton, addIDButton, connectButton, limitButton, removeButton, startButton, stopButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectButton)
                    .addComponent(IPField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(addButton)
                        .addComponent(addIDButton)
                        .addComponent(startButton)
                        .addComponent(stopButton)
                        .addComponent(removeButton)
                        .addComponent(limitButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {IPField, addButton, addIDButton, connectButton, jLabel2, limitButton, removeButton, startButton, stopButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
 * Hàm xử lí sự kiện nút ADD
 * Chức năng :
     * Mở cửa sổ duyệt file (fileBrowser)
 */
private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
    //Create a file chooser
    fileOpen.showOpenDialog(this);
    //And let it do the rest :D
}//GEN-LAST:event_addButtonActionPerformed
/*
 * Hàm xử lí sự kiện nút REMOVE
 * Chức năng :
 * + Xóa 1 dòng trong bảng danh sách các file (FileTable)
 */
private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
    int removedRow = FileTable.getSelectedRow();
    if(removedRow >= 0){
        
        String IPServer = IPField.getText();
            //Notice to server that you stopped this hash
        if(getStatusCol(removedRow).equals("Seeding")){
            try{
                Client client = new Client(IPServer);
                //stop thread upload
                int id = this.getIDCol(removedRow);
                if(id != -1) seeder.stopThread(id);
                //notice to server that client stop seeding
                boolean success = client.stopSeed(getIDCol(removedRow));
                if(success) client.finish();
            }catch(Exception e){
                showErrorMess(this,e.toString());
            }
        }else if(getStatusCol(removedRow).equals("Downloading")){
            int id = this.getIDCol(removedRow);
            if(id != -1) dl.stopThead(id);
            
        }
            //just remove if status = Stopped...
        model.removeRow(removedRow); 
    }
}//GEN-LAST:event_removeButtonActionPerformed

/*
 * Hàm xử lí sự kiện nút START
 * Chức năng :
 * + Đăng kí với server để seed
 * + Listen trên ServerSocket Server.server để chờ request từ peer khác tới
 */
private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startButtonActionPerformed
       
    sltRow = FileTable.getSelectedRow();
    String status = null;
    String IPServer = IPField.getText();
    if(sltRow != -1){
        Client client = new Client(IPServer);
        if(!IPServer.equals("")){
            if(getStatusCol(sltRow).isEmpty()){
                //if not seeding or done 
                try{
                    System.out.println("Start seeding");
                    //get ID of the file
                    int ID = client.startSeed(getSizeCol(sltRow));
                    if(ID != -1){
                        
                        startButton.setEnabled(false);
                        stopButton.setEnabled(true);
                        IPField.setEditable(false);
                        setStatus("Seeding",sltRow);
                        setID(ID,sltRow);
                     } else{
                        setStatus("Error!",sltRow);
                     }
                    client.finish();
                }catch(Exception e){
                    showErrorMess(this,"SeedFileError : " + e.toString());
                }
            } else if(getStatusCol(sltRow).equals("Done!")||getStatusCol(sltRow).equals("StoppedSeeding")){
                try{
                    boolean success = client.reSeed(getIDCol(sltRow));
                    
                    if(success){
                        setStatus("Seeding",sltRow);
                        startButton.setEnabled(false);
                        stopButton.setEnabled(true);
                    }
                    else showErrorMess(this,"Cannot reseed this file!");
                    client.finish();
                    
                }catch(Exception e){
                    showErrorMess(this,"Reseed Error : " + e.getMessage());
                }
            }
            else if(getStatusCol(sltRow).equals("StoppedDownloading"))
            {
                //resume download
                this.resume(sltRow, IPServer);
                
            }
        }else{
            showWarningMess(this,"Please fill in Server IP field");
        }
        
    }
}//GEN-LAST:event_startButtonActionPerformed

/*
 * Hàm xử lí sự kiện nút ADD HASH
 * Chức năng : 
 * + Mở một ô để nhập mã hash của file muốn down vào
 * + Nếu hash hợp lệ thì bắt đầu chạy thread FileDownload để gửi request cho peer
 * đang giữ file đó.
 */
private void addIDButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addIDButtonActionPerformed
    String str = JOptionPane.showInputDialog(this,"Enter ID : ");
    if(str != null){
        try{
            int ID = Integer.parseInt(str);
            String IPServer = IPField.getText();
            if(!IPServer.equals("")){
                if(this.checkExist(ID) == -1){//if there is no row has this ID
                    IPField.setEditable(false);
                    FileDownload down = new FileDownload(ID,this,IPServer,false);
                    this.dl.addThread(down);
                }else showWarningMess(this,"This file has already exist!");
            }else{
                showWarningMess(this,"Please fill in the Server IP field");
            }
            
        }catch(Exception e){
            showWarningMess(this,"Invalid ID (number only) : "+str);
        }
    }
    
}//GEN-LAST:event_addIDButtonActionPerformed
/*
 * Hàm xử lí sự kiện cửa sổ duyệt file 
 * Chức năng :
 * + Lấy file cần add vô bảng từ cửa sổ duyệt file
 * + Thêm 1 dòng vào bảng với các filed : Name, Size, Hash, Path
 */
private void fileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileOpenActionPerformed
    if (JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())) {
            // Open or Save was clicked
        //Get information of selectedFile
        File sltFile = fileOpen.getSelectedFile();
        boolean already = false;
        String path = sltFile.getAbsolutePath();
        for(int i = 0; i < countRow(); i++){
            if(path.equals(getPathCol(i) + "\\" + getNameCol(i))){
                showWarningMess(this,"This file has already added!");
                already = true;
                break;
            }
        }
        if(!already){
            //Add a row into the table
            addRow();
            setName(sltFile.getName(),getCurrentRow());
            setSize(sltFile.length(),getCurrentRow());
            setPath(sltFile.getParent(),getCurrentRow());
        }
    }
}//GEN-LAST:event_fileOpenActionPerformed
/*
 * Hàm xử lí nút STOP
 * Chức năng :
 * 
 */
private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
    int selectRow = FileTable.getSelectedRow();
    if(selectRow > -1){
        if(getStatusCol(selectRow).equals("Seeding")){
            setStatus("",selectRow);
            String IPServer = IPField.getText();
            //Notice to server that you stopped this hash
            try{
                Client client = new Client(IPServer);
                //Quan
                //kiem tra trong seed List - stopseeding
                int id = this.getIDCol(selectRow);
                if(id != -1) seeder.stopThread(id);
                //end
                boolean success = client.stopSeed(getIDCol(selectRow));
                if(success){
                    stopButton.setEnabled(false);
                    startButton.setEnabled(true);
                    setStatus("StoppedSeeding",selectRow);
                    client.finish();
                }else showErrorMess(this,"Cannot stop this file!");
                
            }catch(Exception e){
                showErrorMess(this,e.toString());
            }
        }
        else if(getStatusCol(selectRow).equals("Downloading")){
            int id = this.getIDCol(selectRow);
            if(id != -1) if(dl.stopThead(id)){
                    stopButton.setEnabled(false);
                    startButton.setEnabled(true);
                    setStatus("StoppedDownloading",selectRow);
            }
            else showErrorMess(this,"Cannot stop this file!");
        }
    }
}//GEN-LAST:event_stopButtonActionPerformed

private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
    String IPServer = IPField.getText();
    if(!IPServer.equals("")){
        try{
            Client client = new Client(IPServer);
            boolean success = client.testServer();
            if(success){
                IPField.setEditable(false);
                IPField.setEnabled(false);
                connectButton.setEnabled(false);
                addButton.setEnabled(true);
                addIDButton.setEnabled(true);
                showInfMess(this,"Connect successfully to server");
            } else{
                showWarningMess(this,"Server doesn't exist");
            }
        }catch(Exception e){
            showErrorMess(this,"Cannot connect to server!");
        }
    }
    
}//GEN-LAST:event_connectButtonActionPerformed

private void fileSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileSaveActionPerformed
   
    if(JFileChooser.APPROVE_SELECTION.equals(evt.getActionCommand())){
        selectedFile = fileSave.getSelectedFile();
    }
}//GEN-LAST:event_fileSaveActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    String IPServer = IPField.getText();
    //Stop seed every file in the table
    try{
        Client client = new Client(IPServer);
        client.stopSeed();
        client.finish();
        for(int i = 0; i < seeder.threadlist.size(); i++) {
            if(seeder.threadlist.get(i).stillAlive()){
                seeder.threadlist.get(i).stop();
            }
            seeder.threadlist.remove(i);
        }
        seeder.close();
        
    }catch(Exception e){
//        showMess(this,"Stop seeding all file : "+e.getMessage());
        System.exit(0);
    }

    /*try {           
            FileOutputStream  file = new   FileOutputStream(SAVE);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(model.getDataVector());
            out.flush();
            out.close();
            file.close();
            System.out.println("GUI saved!");
    } catch (IOException ioe) {
            System.out.println("Error GUI saving: " + ioe.getMessage());
    }*/

}//GEN-LAST:event_formWindowClosing

private void FileTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FileTableMouseClicked
    TableAction();
}//GEN-LAST:event_FileTableMouseClicked

private void FileTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FileTableKeyReleased
    TableAction();
}//GEN-LAST:event_FileTableKeyReleased

private void FileTablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FileTablePropertyChange
    
//    evt.getNewValue()
//    model.
}//GEN-LAST:event_FileTablePropertyChange

private void FileTableVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_FileTableVetoableChange
    System.out.println("DADSA");
}//GEN-LAST:event_FileTableVetoableChange

private void limitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitButtonActionPerformed
    int selectRow = FileTable.getSelectedRow();
    if(selectRow != -1 && getStatusCol(selectRow).equals("Downloading")){
        
        stopButton.doClick();
        startButton.doClick();
    }
}//GEN-LAST:event_limitButtonActionPerformed

/*
 * This function is used for action on table
 */
private void TableAction(){
    int selectRow = FileTable.getSelectedRow();
    if(selectRow != -1){//if there is at least 1 row is selected
        removeButton.setEnabled(true);
        if(getStatusCol(selectRow).equals("Seeding") || getStatusCol(selectRow).equals("Downloading")){
            //if file is inactived or done
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        } else{
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
//       FileTable.s
    }else{
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
        removeButton.setEnabled(false);
    }
}
// Restore the previous session
private void restore(){
    
    for(int i = 0; i < countRow(); i++){
        String IPServer = IPField.getText();
        String status = getStatusCol(i);
        if(status.equals("Seeding")){
            stopButton.setEnabled(true);
            removeButton.setEnabled(true);
            this.setProgress(100,i);
            //if seeding then tell server that seeder want to reseed
            Client client = new Client(IPServer);
            try{
                boolean success = client.reSeed(getIDCol(i));
                if(success){
                    setStatus("Seeding",sltRow);
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                } else showErrorMess(this,"Cannot reseed file : "+getNameCol(i));
                client.finish();
            }catch(Exception e){
                showErrorMess(this,"Reseed error : "+e.toString());
            }
        }else if(status.equals("Downloading")){
            this.resume(i, IPServer);
        }else if(status.equals("StoppedSeeding") || status.equals("StopDownloading")){
            
        }
    }
    System.out.println("GUI restore!");
}
//resume download
private void resume(int row, String IPServer){
    try{
        int ID = this.getIDCol(row);
        FileDownload down = new FileDownload(ID,this,IPServer,true);
        this.dl.addThread(down);
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }catch(Exception e){
        showErrorMess(this,"Resume error : "+e.toString());
    }
}
/*
 * These func use for show message on GUI
 */
public void showErrorMess(GUI parent,String str){
    JOptionPane.showMessageDialog(parent, str, "ERROR",JOptionPane.ERROR_MESSAGE);
}
public void showWarningMess(GUI parent, String str){
    JOptionPane.showMessageDialog(parent, str, "WARNING",JOptionPane.WARNING_MESSAGE);
}
public void showInfMess(GUI parent, String str){
    JOptionPane.showMessageDialog(parent, str, "INFORMATION",JOptionPane.INFORMATION_MESSAGE);
}

//--------------------------
public int getCurrentRow(){
    return countRow() - 1;
}
public int getSelectedRow(){
    return this.sltRow;
}
public File getSelectFile(){
    return this.selectedFile;
}
public int fileExist(){
    if(selectedFile.exists()){//if selectedFile has already existed
        isExist = JOptionPane.showConfirmDialog(this, "Overwrite existing file?", 
           "Confirm overwrite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE); 
     }
    return isExist;
}

/*public void setDefaultIP(){
    IPField.setText("localhost");
}*/
public void setDefaultName(String name){
    fileSave.setSelectedFile(new File(name));
}
public void showSaveDlg(){
    fileSave.showSaveDialog(this);
}
//--------------------------------------------------------------
/*
 * These func use for file table
 */
//check whether a file exist in table
public int checkExist(int ID){
    if(countRow() == 0) return -1;
    for(int i = 0; i < countRow(); i++){
        if (getIDCol(i) == ID){
            return i;
        }
    }
    return -1;
}
public int countRow(){
    return FileTable.getRowCount();
}

public void addRow(){
    model.addRow(new Object[]{null,null,null,null,null,null,null});
}
public String getNameCol(int row){
    Object t = FileTable.getValueAt(row, NAME_COL);
    if(t == null) return null;
    return t.toString();
}

public long getSizeCol(int row){
    Object t = FileTable.getValueAt(row, SIZE_COL);
    if(t == null) return -1;
    return Long.parseLong(t.toString());
}

public String getStatusCol(int row){
    Object t = FileTable.getValueAt(row, STATUS_COL);
    if(t == null) return "";
    return t.toString();
}

public int getIDCol(int row){
    Object t = FileTable.getValueAt(row, ID_COL);
    if(t == null) return -1;
    return Integer.parseInt(t.toString());
}

public String getPathCol(int row){
    Object t = FileTable.getValueAt(row, LOCATION_COL);
    if(t == null) return null;
    return t.toString();
}

public int getLimitCol(int ID){
    int row = checkExist(ID);
    if(row != -1){
        Object t = FileTable.getValueAt(row,LIMIT_COL);
        if(t == null) return 0;
        else {
            try{
                int limit = Integer.parseInt(t.toString());
                if(limit <=0 ) limit=0;
                return limit;
            }catch(Exception e){
                setLimit(row);
                return 0;
            }
        }
    }
    return 0;
}

public void setName(String name,int sltRow){
    FileTable.setValueAt(name, sltRow, NAME_COL);
}
public void setSize(long size, int sltRow){
    FileTable.setValueAt(size, sltRow, SIZE_COL);
}
public void setStatus(String status, int sltRow){
    FileTable.setValueAt(status, sltRow, STATUS_COL);
}
public void setID(int hash, int sltRow){
    FileTable.setValueAt(hash, sltRow, ID_COL);
}
public void setPath(String loc, int sltRow){
    FileTable.setValueAt(loc, sltRow, LOCATION_COL);
}
public void setProgress(int percent, int sltRow){
    FileTable.setValueAt(percent + "%", sltRow, PROGRESS_COL);
}
public void setSpeed(int speed, int sltRow){
    String calcSpeed = null;
    if(speed >= 1024*1024*1024) calcSpeed = Integer.toString(speed/(1024*1024*1024)) + "GB/s";
    else if(speed >= 1024*1024) calcSpeed = Integer.toString(speed/(1024*1024)) + "MB/s";
    else if(speed >= 1024) calcSpeed = Integer.toString(speed/1024) + "KB/s";
    else calcSpeed = Integer.toString(speed) + "B/s";
    FileTable.setValueAt(calcSpeed, sltRow, SPEED_COL);
}
public void setLimit(int sltRow){
    FileTable.setValueAt(0, sltRow, LIMIT_COL);
}
//------------------------------------------------------------



private void setLookandFeel(){
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    }catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
}

                    
                    
                
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable FileTable;
    private javax.swing.JTextField IPField;
    private javax.swing.JButton addButton;
    private javax.swing.JButton addIDButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JFileChooser fileOpen;
    private javax.swing.JFileChooser fileSave;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limitButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables
}
