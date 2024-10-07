import logo from "../../../../assets/users/transparent.png";
import qr from "../../../../assets/users/qr.jpg";
import "./style.scss";
import {
  FaFacebook,
  FaInstagramSquare,
  FaYoutube,
  FaTwitterSquare,
} from "react-icons/fa";
import { FaSquareThreads } from "react-icons/fa6";
import { IoMdMailUnread } from "react-icons/io";

const Footer = () => {
  return (
    <div className="footer">
      <div className="container-footer">
        <div className="logo-address">
          <img src={logo} className="footer-logo" alt="Logo" />
          <p>
            54 Nguyễn Lương Bằng, Hòa Khánh Bắc, Liên Chiểu, Đà Nẵng.
            <br />
            Công Ty Cổ Phần Phát Hành Sách Đà Nẵng - VOVIBOOK <br />
            0397201947
          </p>
          <p className="icon">
            <FaFacebook /> <FaInstagramSquare /> <FaYoutube />{" "}
            <FaSquareThreads /> <FaTwitterSquare />
          </p>
        </div>

        <div className="service">
          <div className="service1">
            <h3>Dịch vụ</h3>
            <p>Điều khoản sử dụng</p>
            <p>Chính sách bảo mật thông tin cá nhân</p>
            <p>Chính sách bảo mật thanh toán</p>
          </div>
          <div className="service2">
            <h4>Chính sách hỗ trợ khách hàng</h4>
            <p>Gửi yêu cầu hỗ trợ</p>
            <p>Chính sách đổi trả</p>
          </div>

          <div className="service3">
            <IoMdMailUnread /> <p>cskh@vivobook.com.vn</p>
          </div>
        </div>

        <div className="remain">
          <div className="remain1">
            <h3>VIVOBOOK</h3>
            <p>Giới thiệu VIVOBOOK</p>
            <p>VIVOBOOK Blog</p>
            <p>Tuyển dụng</p>
          </div>
          <div className="remain2">
            <h3>Tải ứng dụng trên điện thoại</h3>
            <img
              src="https://cdn0.fahasa.com/media/wysiwyg/Logo-NCC/android1.png"
              className="ch-play"
            />
          </div>
          <div className="remain3">
            <img src={qr} className="qr" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default Footer;
