



/*
 * Assignment 1 Computer Network 1 - University of Technology
 */

/**
 * @author BitCrazy Team
 * 
 * Members :
    * Đinh Trần Thái Sơn
    * Phạm Minh Thành
    * Nguyễn Văn Quân
    * Đặng Ngọc Vũ
    * Nguyễn Duy Tài
 */
public class Main {

    public static final int LISTENING_PORT = 22222;
    public static void main(String[] args) {
        UploadManager seeder = new UploadManager(LISTENING_PORT);
        DownloadManager dl = new DownloadManager();
        GUI UI = new GUI(seeder, dl);
        UI.setVisible(true);
        seeder.listen(UI);
    }
    
}
