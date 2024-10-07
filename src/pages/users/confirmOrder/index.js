import "./style.scss";
import { getBookByID } from "services/user/bookService";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { Formatter } from "utils/formatter";


const ConfirmOrder = () => {
  const { id, quantity } = useParams(); 
  const [book, setBook] = useState(null);

    // Fetch thông tin sách khi component được mount hoặc id thay đổi
  useEffect(() => {
    const fetchBookDetails = async () => {
      const bookData = await getBookByID(id);
      setBook(bookData.data);
    };

    fetchBookDetails();
  }, [id]); // Chỉ gọi lại khi id thay đổi

  // Nếu book vẫn chưa được lấy, hiển thị loading hoặc thông báo
  if (!book) {
    return <p>Đang tải thông tin sản phẩm...</p>;
  }
  return (
    <div className="container mt-4">
      <div className="row">
        {/* Phần bên trái chiếm 8/12 chiều rộng */}
        <div className="col-8 box boxConfirm" style={{ padding: "20px" }}>
          <div className="header-container">
            <h1>Đơn hàng của bạn</h1>
          </div>
          <div className="product-infor">
            <h5>Thông tin sản phẩm</h5>

            <div className="product-container custom-product-container p-2 mb-2">
              <div className="product-item custom-product-item">
                <img
                  src={book.imageLink}
                  alt="Sản phẩm"
                  className="img-fluid"
                  style={{ width: "auto", height: "100px" }}
                />
                <div className="product-title">
                  <h6>
                  {book.title}
                  </h6>
                </div>
                <span className="sluong">Số lượng: x{quantity}</span>
              </div>
              <span className="za ms-3">Giá: {Formatter(book.discountedPrice)}</span>
            </div>

          </div>

          <div className="shipping-info">
            <h5>Thông tin giao hàng</h5>

            {/* Tên */}
            <div className="mb-3 d-flex justify-content-between">
              <label htmlFor="name" className={`form-label label-width`}>
                Tên:
              </label>
              <input
                type="text"
                className="form-control input-width"
                id="name"
                placeholder="Nhập tên người nhận"
              />
            </div>

            {/* Số điện thoại */}
            <div className="mb-3 d-flex justify-content-between">
              <label htmlFor="phone" className={`form-label label-width`}>
                Số điện thoại:
              </label>
              <input
                type="tel"
                className="form-control input-width"
                id="phone"
                placeholder="Nhập số điện thoại"
              />
            </div>

            {/* Email */}
            <div className="mb-3 d-flex justify-content-between">
              <label className={`form-label label-width`}>Email:</label>
              <input
                type="email"
                className="form-control input-width"
                id="email"
                placeholder="Nhập email của bạn"
              />
            </div>

            {/* Địa chỉ nhận hàng */}
            <div className="mb-3 d-flex justify-content-between">
              <label htmlFor="address" className={`form-label label-width`}>
                Địa chỉ nhận hàng:
              </label>
              <input
                type="text"
                className="form-control input-width"
                id="address"
                placeholder="Nhập địa chỉ"
              />
            </div>
          </div>

          <div className="payment-info">
            <h5>Phương thức thanh toán</h5>
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="paymentMethod"
                id="cashPayment"
                value="cash"
              />
              <label className="form-check-label" htmlFor="cashPayment">
                Thanh toán bằng tiền mặt
              </label>
            </div>

            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="paymentMethod"
                id="momoPayment"
                value="momo"
              />
              <label className="form-check-label" htmlFor="momoPayment">
                Thanh toán bằng MoMo
              </label>
            </div>

            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="paymentMethod"
                id="vnpayPayment"
                value="vnpay"
              />
              <label className="form-check-label" htmlFor="vnpayPayment">
                Thanh toán qua VNPAY
              </label>
            </div>
          </div>
        </div>

        {/* Phần bên phải chiếm 4/12 chiều rộng */}
        <div className="col-4 box boxOrder">
          <div className="order-infor">
            <h4> Xác nhận đơn hàng </h4> 
            <div>
              <p>40 sản phẩm</p>
              <span> THUY LINH</span>
              <span>0397201947</span>
              <p>Giao tới:</p>
              <span> KM25 Phong An, Phong Điền, Thừa Thiên Huế.</span>
            </div>
          </div>
          <div>
            <div className="payment-container">
              <div className="roww">
                <span>Tạm tính</span>
                <span>{Formatter(quantity * book.discountedPrice)}</span>
              </div>
              <div className="roww">
                <span>Giảm giá</span>
                <span>0</span>
              </div>
              <hr className="aaa"/>
              <div className="roww">
                <span>Thành tiền</span>
                <span className="highlight-price">{Formatter(quantity * book.discountedPrice - 0)}</span>
              </div>
              <button className="btn-buy">Đặt hàng</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConfirmOrder;
