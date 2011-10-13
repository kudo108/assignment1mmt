/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package listfile;

/**
 * Class ListFile to generate a list to manager file list
 * @author KuDo
 */
import java.util.ArrayList;
public class File_Info {
    public static final int BLOCK = 65536;
    private String FileName;
        //set FileName
        private void setFileName(String name){
            this.FileName = name;
        }
        //get FileName
        public String getFileName(){
            return this.FileName;
        }
    private String FileHash;
        private void setFileHash(String hash){
            this.FileHash = hash;
        }
        public String getFileHash(){
            return this.FileHash;
        }
    private int FileSize;// don vi kB
        //set FileSize
        private void setFileSize(int size){
            this.FileSize = size;
            this.BlockNumber = this.getBlockNumber();
            this.setupBlock();
        }
        //get FileSize
        public int getFileSize(){
            return this.FileSize;
        }
        //get Number of Block
        // 1 block = 64kB = 65536B
        private int getBlockNumber(){
            if(this.FileSize % BLOCK == 0)
                return (int) (this.FileSize/BLOCK) ;
            else return (int) (this.FileSize/BLOCK) + 1;
        }
    private ArrayList <String> IPList ;//= new ArrayList <String>();
        /* IPList contain IP which client can download from this IP 
         */
        //fucntion to addIP to IPList
        public void addIP(String ip){
            IPList.add(ip);
        }
        //fuction to getIpList
        public ArrayList getIpList(){
            return this.IPList;
        }
        //function to remove an IP from IPList
        public void removeIp(String ip){
            IPList.remove(ip);
        }
        //function to get size of IPList
        public int getIPNum(){
            return IPList.size();
        }
    private Integer BlockNumber;
        public int getBlockNum(){
            return this.BlockNumber;
        }
    private int[] BlockStatus ;
    
    /*
     * Block status can has 3 value
     * 0 - if block had never downloaded
     * 1 - if block is downloading
     * 2 - if block downloaded.
     */
        public void setDownloading(int BlockID){
            BlockStatus[BlockID] = 1;
        }
        public void setDownloaded(int BlockID){
            BlockStatus[BlockID] = 2;
        }
        private void setupBlock(){
            for(int i = 0 ; i < this.BlockNumber; i ++) BlockStatus[i] = 0;
        }
        public int[] getBlockStatus(){
            return this.BlockStatus;
        }
        //constructor
    File_Info(String name, String size, String hash) {
        IPList = new ArrayList <String>();
        BlockStatus = new int[BLOCK];
        for(int i = 0; i < BLOCK; i++) BlockStatus[i] = -1;
        this.setFileName(name);
        this.setFileSize(Integer.parseInt(size));
        this.setFileHash(hash);
    }
}
