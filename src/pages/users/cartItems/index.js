import React from "react";
import "./style.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import { MdDelete } from "react-icons/md";
import { RiDeleteBin6Line } from "react-icons/ri";
import { FaMinus, FaPlus } from "react-icons/fa";
import { Button } from "@mui/material";

const CartItems = () => {
  return (

    <div className="container sumary mt-4">
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
              <div className="choose-item me-3">
                <input type="checkbox" id="item1" />
              </div>
              <img
                src="https://product.hstatic.net/200000343865/product/11-db_789778a7e2b1407aafe3f8e5667fc566.jpg"
                alt="Sản phẩm"
                className="img-fluid"
                style={{ width: "auto", height: "100px" }}
              />
              <div className="product-title">
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
              <div className="price">
                <span className="ms-3">4500000</span>
              </div>
            </div>
            <button className="remove">
              <MdDelete />
            </button>
          </div>
        </div>

        <div className="col-4 box box2">
          <div>
            <div className="testt">
              <div className="roww-gia">
                <span>Tạm tính</span>
                <span>10.00000</span>
              </div>
              <div className="roww-gia">
                <span>Giảm giá</span>
                <span>0</span>
              </div>
              <hr />
              <div className="roww-gia">
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
