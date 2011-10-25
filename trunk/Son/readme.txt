Danh sách class và các chức năng

Class Main : Class chính của chương trình. Khởi tạo ServerSocket và khởi tạo GUI
Class GUI : 
Class này có chức năng tạo giao diện cùng một số đối tượng để thao tác với người dùng
Các đối tượng : 
Bảng (fileBrowser) chứa danh sách các file đang chạy (down hay seed)
Có các nút với các hàm sự kiện sau :
ADD  : hàm addButtonActionPerformed - thêm 1 dòng vào bảng
START : hàm startButtonActionPerformed - chuyển status 1 file thành 
STOP : hàm stopButtonActionPerformed - dừng seed 1 file đang seed
REMOVE : hàm removeButtonActionPerformed - xóa 1 file trong bảng
ADD HASH : hàm addHashButtonActionPerformed - thêm hash của file cần download

Class FileUpload :
Class này là một thread gọi từ hàm sự kiện của nút START, 
Có chức năng tạo 1 thread để seed 1 file trong bảng

Class FileDownload :
Class này là một thread gọi từ hàm sự kiện của nút ADD HASH
Có chức năng tạo 1 thread để download 1 file với hash nhập vào tương ứng

Class Server :
Tạo server socket với port 5555

Class Listener : 
Thread có chức năng chờ kết nối từ client tới