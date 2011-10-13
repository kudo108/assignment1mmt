/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//package listfile;

/**
 *
 * @author KuDo
 */
import java.util.ArrayList;
public class ListFile {
    private ArrayList<File_Info> file;
    ListFile(){
        file = new ArrayList<File_Info>();
    }
    //function to add new file to List
    public void addFile(String name, String size, int hash){
        File_Info x = new File_Info(name, size, hash);
        file.add(x);
    }
    //function to remove a File from List
    //return true if list has that file & remove ok
    //return false if list doesn't have that file 
    public boolean removeFile(int hash){
        int i = this.findFile(hash);
        if( i == -1 ) return false;
        file.remove(i);
        return true;
    }
    //find index of a File from hash
    private int findFile(int hash){
        for(int i = 0; i < file.size(); i++){
            File_Info x = file.get(i);
            if(x.getFileHash() == hash){
                return i;
            }
        }
        return -1;
    }
    public int getFileNum(){
        return file.size();
    }
    public File_Info getFileInfo(int hash){
        int i = this.findFile(hash);
        return file.get(i);
    }
}
