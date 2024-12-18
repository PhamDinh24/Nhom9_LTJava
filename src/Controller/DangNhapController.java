package Controller;

import Repo.DangNhapRepo;

public class DangNhapController {
    public boolean DangNhap(String username, String password, Long role) {
        return DangNhapRepo.getInstance().DangNhap(username, password, role);
    }
}
