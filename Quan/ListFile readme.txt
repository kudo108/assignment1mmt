//Chỉ cần gọi từ hàm chính
ListFile manager = new ListFile();
//muốn thêm 1 file vào list
manager.addFile(name, size, hash);
//muốn xóa 1 file khỏi list
manager.removeFile(hash);
//lấy info 1 file
File_Info f = manager.getFileInfo(hash)
//lấy tên file
f.getFileName();
//lấy file size
f.getFileSize();
//lấy file hash
f.getFileHash();
//lấy dãy IP
f.getIPList();
//thêm IP vào list
f.addIP(String IP);
//xóa 1 IP
f.removeIP(String IP);
//lấy số Block
f.getBlockNum();
//lấy trạng thái các Block của File
f.getBlockStatus();
//cập nhật 1 block đang download
f.setDownloading(int BlockID)
//cập nhật 1 block download xong
f.setDownloaded(int BlockID)