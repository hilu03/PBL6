import React from "react";
import "./style.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import { MdDelete } from "react-icons/md";
import { FaMinus, FaPlus } from "react-icons/fa";
import { Button } from "@mui/material";
import { useState, useEffect } from "react";
import { FaCartShopping } from "react-icons/fa6";
import { Formatter } from "utils/formatter";
import { getDetailedCart } from "services/user/cartService";
import { useNavigate } from "react-router-dom";

const CartItems = () => {
  const [cartItems, setCartItems] = useState([]); // Lưu dữ liệu giỏ hàng từ API
  const [quantityDistinctItems, setquantityDistinctItems] = useState([]);
  const [selectedItemsFromCart, setSelectedItemsFromCart] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchCartData = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await getDetailedCart(token);
        console.log(response.data.items);
        setCartItems(response.data.items);
        setquantityDistinctItems(response.data.totalDistinctItems);
      } catch (error) {
        console.error("Error fetching cart data:", error);
      }
    };
    fetchCartData();
  }, []);

  const handleSelectItem = (itemId, addedQuantity) => {
    setSelectedItemsFromCart((prevSelected) => {
      if (prevSelected.some((item) => item.id === itemId)) {
        // Nếu sản phẩm đã được chọn, thì bỏ chọn
        return prevSelected.filter((item) => item.id !== itemId);
      } else {
        // Thêm sản phẩm vào danh sách đã chọn
        return [...prevSelected, { id: itemId, quantity: addedQuantity }];
      }
    });
  };

  const handleConfirmOrder = () => {
    if (selectedItemsFromCart.length > 0) {
      console.log("id và quantity từ cartItem: ", selectedItemsFromCart);
      navigate("/confirm-order", { state: { items: selectedItemsFromCart } }); // Truyền mảng items
    } else {
      console.error("No items selected");
    }
  };

  const calculateTotalPrice = () => {
    return selectedItemsFromCart.reduce(
      (total, item) => total + item.discountedPrice * item.addedQuantity,
      0
    );
  };

  return (
    <div className="container sumary mt-4">
      <div className="row">
        <div className="col-9 box box1">
          <div className="icon-cart">
            {" "}
            <FaCartShopping />
            <span className="quantityDistinctItems ms-2">
              ({quantityDistinctItems} sản phẩm)
            </span>
          </div>
          <div className="header d-flex align-items-center">
            <div className="first_checkbox">
              <input type="checkbox" id="selectAll" />
            </div>
            <div>
              <span className="select-all">Chọn tất cả sản phẩm</span>
            </div>
            <div className="sl">Số lượng</div>
            <div className="total">Thành tiền</div>
          </div>

          {/* Render các sản phẩm từ API */}
          {cartItems.map((item) => (
            <div className="product-container p-2 mb-2" key={item.id}>
              <div className="product-item d-flex align-items-center justify-content-between">
                <div className="choose-item me-3">
                  <input
                    type="checkbox"
                    id={`item-${item.id}`}
                    onChange={() =>
                      handleSelectItem(item.id, item.addedQuantity)
                    }
                  />
                </div>
                <img
                  src={item.imageLink}
                  alt={item.title}
                  className="img-fluid"
                  style={{ width: "70px", height: "auto" }}
                />
                <div className="product-title">
                  <p>{item.title}</p>
                </div>
                <div className="quantity align-items-center">
                  <Button className="ms-3">
                    <FaMinus />
                  </Button>
                  <input
                    type="text"
                    value={item.addedQuantity}
                    readOnly
                    style={{ width: "50px", textAlign: "center" }}
                  />
                  <Button className="me-3">
                    <FaPlus />
                  </Button>
                </div>
                <div className="price">
                  <span className="ms-3">
                    {Formatter(item.discountedPrice * item.addedQuantity)}
                  </span>
                </div>
              </div>
              <button className="remove">
                <MdDelete />
              </button>
            </div>
          ))}
        </div>

        <div className="col-3 box box2">
          <div>
            <div className="testt">
              <div className="content-money">
                <div className="roww-gia">
                  <span>Tạm tính</span>
                  <span>
                    {Formatter(
                      selectedItemsFromCart.reduce((acc, selectedItem) => {
                        const cartItem = cartItems.find(
                          (item) => item.id === selectedItem.id
                        );
                        return (
                          acc +
                          (cartItem
                            ? cartItem.discountedPrice * selectedItem.quantity
                            : 0)
                        );
                      }, 0)
                    )}
                  </span>
                </div>
                <div className="roww-gia">
                  <span>Giảm giá</span>
                  <span>0</span>
                </div>
                <hr />
                <div className="roww-gia">
                  <span>Thành tiền</span>
                  <span>
                    {Formatter(
                      selectedItemsFromCart.reduce((acc, selectedItem) => {
                        const cartItem = cartItems.find(
                          (item) => item.id === selectedItem.id
                        );
                        return (
                          acc +
                          (cartItem
                            ? cartItem.discountedPrice * selectedItem.quantity
                            : 0)
                        );
                      }, 0)
                    )}
                  </span>
                </div>
                <button className="btn mua-hang" onClick={handleConfirmOrder}>
                  Mua hàng
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CartItems;
