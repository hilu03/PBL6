import React from "react";
import "./style.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import { MdDelete } from "react-icons/md";
import { RiDeleteBin6Line } from "react-icons/ri";
import { FaMinus, FaPlus } from "react-icons/fa";
import { Button } from "@mui/material";

const CartItems = () => {
  return (
    <div className="container mt-4">
      <div className="row">
        <div className="col-8 box box1">
          <h2>Giỏ hàng</h2>
          <div className="header d-flex align-items-center">
            <div className="first_checkbox">
              <input type="checkbox" id="item1" />
            </div>
            <div>
              <span className="select-all">Chọn tất cả sản phẩm</span>
            </div>

            <div className="sl">Số lượng</div>
            <div className="total">Thành tiền</div>
          </div>

          {/* Sản phẩm 1 */}  
          <div className="product-container p-2 mb-2">
            <div className="product-item d-flex align-items-center justify-content-between">
              <div className="checkbox me-3">
                <input type="checkbox" id="item1" />
              </div>
              <img
                src="https://product.hstatic.net/200000343865/product/11-db_789778a7e2b1407aafe3f8e5667fc566.jpg"
                alt="Sản phẩm"
                className="img-fluid"
                style={{ width: "auto", height: "100px" }}
              />
              <div className="title">
                <h5>
                  "Cậu" ma nhà xí Hanako - Tập 11 - Bản đặc biệt (Tặng kèm 01
                  sách tranh màu độc quyền Ghost Hotel's Cafe)
                </h5>
              </div>

              <div className="quantity align-items-center">
                <Button className="ms-3">
                  <FaMinus />
                </Button>
                <input type="text" value="12" />
                <Button className="me-3">
                  <FaPlus />
                </Button>
              </div>
           <div className="price"> <span className="ms-3">450.000</span></div>
            
             
            </div>
            <button className="remove">
                <MdDelete />
              </button>
          </div>

          {/* 

          // <div className="product-container border p-2 mb-2">
            <div className="product-item d-flex align-items-center">
              <div className="checkbox me-2">
                <input type="checkbox" id="item1" />
              </div>
              <img
                src="https://product.hstatic.net/200000343865/product/tuoi-mui_77f921997bd144909822432079c1eb75.jpg"
                alt="Sản phẩm"
                className="img-fluid"
                style={{ width: "auto", height: "100px" }}
              />
              <div className="title ms-3">
                <h5>Hồ sơ tính cách 12 con giáp - Bí mật tuổi Mùi</h5>
              </div>
              <button className="ms-auto">
                <MdDelete />
              </button>
            </div>
          </div>

          <div className="product-container border p-2 mb-2">
            <div className="product-item d-flex align-items-center">
              <div className="checkbox me-2">
                <input type="checkbox" id="item1" />
              </div>
              <img
                src="https://product.hstatic.net/200000343865/product/nobita-va-me-cung-thiec_bf6071b0975b4eb487ed1bcd5db641f9.jpg"
                className="img-fluid"
                style={{ width: "auto", height: "100px" }}
              />
              <div className="title ms-3">
                <h5>Doraemon - Phiên bản điện ảnh màu - Tập 14 (2017)</h5>
              </div>
              <button className="ms-auto">
                <MdDelete />
              </button>
            </div>
          </div>

          {/* Sản phẩm 2 */}
          {/* <div className="product-container border p-2 mb-2">
            <div className="product-item d-flex align-items-center">
              <div className="checkbox me-2">
                <input type="checkbox" id="item2" />
              </div>
              <img
                src="https://product.hstatic.net/200000343865/product/3_db7d9150d9784287b49f105426d1e472.jpg"
                alt="Sản phẩm"
                className="img-fluid"
                style={{ width: "auto", height: "100px" }}
              />
              <div className="title ms-3">
                <h5>Chú thuật hồi chiến - Tập 3</h5>
              </div>
              <button className="ms-auto">
              <MdDelete />
              </button>
            </div>
          </div> */}
        </div>

        <div className="col-4 box box2">
          <div>
            <div className="test">
              <div className="roww">
                <span>Tạm tính</span>
                <span>10.000</span>
              </div>
              <div className="roww">
                <span>Giảm giá</span>
                <span>0</span>
              </div>

              <hr />
              <div className="roww">
                <span>Thành tiền</span>
                <span>20.000</span>
              </div>

              <button className="btn">Mua hàng</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CartItems;
