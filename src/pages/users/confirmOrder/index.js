import "./style.scss";
import { getBookByID } from "services/user/bookService";
import { useState, useEffect, useContext} from "react";
import { Formatter } from "utils/formatter";
import { getProvinces } from "services/user/orderService";
import { getDistricts } from "services/user/orderService";
import { getWards } from "services/user/orderService";
import { addNewAddress } from "services/user/orderService";
import { getAllMyAddress } from "services/user/orderService";
import { IoIosCloseCircle } from "react-icons/io";
import { createCodOrder } from "services/user/orderService";
import { useLocation,useNavigate } from "react-router-dom";
import { removeItems } from "services/user/cartService";
import { CartContext } from "context/CartContext";
import { createPaymentLink } from "services/user/orderService";  

const ConfirmOrder = () => {
  const [books, setBooks] = useState([]);
  const [name, setName] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [provinces, setProvinces] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);

  const [selectedDistrict, setSelectedDistrict] = useState("");
  const [selectedWard, setSelectedWard] = useState("");

  const [hasAddress, setHasAddress] = useState(false);
  const [showShippingForm, setShowShippingForm] = useState(false);

  const token = localStorage.getItem("token");

  const [allAddress, setAllAddress] = useState("");
  const [selectedAddressId, setDefaultAddressId] = useState(null);
  const [defaultAddress, setDefaultAddress] = useState(null); // Địa chỉ mặc định từ DB
  const [selectedAddress, setSelectedAddress] = useState(null); // Địa chỉ được chọn từ radio button
  const [shippingAddressID, setShippingAddressID] = useState(null); 

  const [selectedDistrictId, setSelectedDistrictId] = useState("");
  const [selectedWardId, setSelectedWardId] = useState("");

  const [selectedProvince, setSelectedProvince] = useState(""); 
  const [selectedProvinceId, setSelectedProvinceId] = useState(""); 
  const [paymentMethodID, setPaymentMethodID] = useState(null); 
  const [items, setItems] = useState([]);// Tạo state cho items

  const location = useLocation(); // Lấy thông tin state khi điều hướng
  const [formattedItems, setFormattedItems] = useState([]);
  const navigate = useNavigate();
  const { fetchCartCount } = useContext(CartContext); 
  const [totalPrice, setTotalPrice] = useState(0); 


  useEffect(() => {
    if (location.state && location.state.items) {
      setItems(location.state.items); // Nhận mảng items từ state
      console.log("Items nhận được: ", location.state.items); // In ra items nhận được
      const convertedItems = location.state.items.map(item => ({
        bookID: item.id, // Chuyển đổi 'id' thành 'bookID'
        quantity: parseInt(item.quantity, 10) // Đảm bảo quantity là số nguyên
      }));
      
      setFormattedItems(convertedItems); // Lưu trữ mảng đã chuyển đổi
      console.log("Formatted items:", convertedItems); // In ra để kiểm tra
    }
  }, [location.state]);

  //lấy toàn bộ địa chỉ của user
  useEffect(() => {
    const fetchAllAddress = async () => {
      try {
        const response = await getAllMyAddress(token); 
        const allAddress = response.data;
        console.log(allAddress); 
        setAllAddress(allAddress); 
        if (allAddress && allAddress.length > 0) {
          setHasAddress(true);
          const defaultAddr = allAddress.find((addr) => addr.default === true);
          console.log("Default Address:", defaultAddr);
          if (defaultAddr) {
            setDefaultAddress(defaultAddr); 
            setDefaultAddressId(defaultAddr.id); 
          }
        } else {
          setHasAddress(false); 
        }
      } catch (error) {
        console.error("Error fetching addresses:", error); 
        setHasAddress(false); 
      }
    };

    fetchAllAddress(); 
  }, [token]);
  //thêm địa chỉ mới
  const saveAddress = async () => {
    console.log("địa chỉ" + selectedProvince);
    const isDefault = false;
    if (
      !name ||
      !phone ||
      !address ||
      !selectedProvince ||
      !selectedDistrict ||
      !selectedWard
    ) {
      alert("Vui lòng điền đầy đủ thông tin.");
      return;
    }
    try {
      const response = await addNewAddress(
        token,
        name,
        phone,
        address,
        selectedProvince,
        selectedDistrict,
        selectedWard,
        isDefault
      );
      // console.log(response);

      if (response) {
        alert("Địa chỉ đã được lưu thành công!");
        const updatedResponse = await getAllMyAddress(token);
        setAllAddress(updatedResponse.data);
      } else {
        alert("Có lỗi xảy ra khi lưu địa chỉ.");
      }
    } catch (error) {
      console.error("Error saving address", error);
      alert("Có lỗi xảy ra khi lưu địa chỉ.");
    }
  };
  // lấy thông tin chi tiết của từng sp từ mảng item 
  useEffect(() => {
    const fetchBookDetails = async () => {
      try {
        const booksData = await Promise.all(
          items.map(async (item) => {
            const bookData = await getBookByID(item.id); // Gọi API lấy chi tiết sách
            return { ...bookData.data, quantity: item.quantity }; // Thêm số lượng vào dữ liệu sách
          })
        );
        setBooks(booksData); // Cập nhật state với danh sách chi tiết sách
        const total = booksData.reduce(
          (acc, book) => acc + book.discountedPrice * book.quantity, // Nhân giá sách với số lượng
          0
        );
        setTotalPrice(total); // Cập nhật tổng tiền vào state

      } catch (error) {
        console.error("Error fetching book details:", error);
      }
    };

    if (items.length > 0) {
      fetchBookDetails(); // Gọi API lấy thông tin chi tiết sản phẩm
    }
  }, [items]);


  // lấy danh sách tỉnh/thành
  useEffect(() => {
    const fetchProvinces = async () => {
      try {
        const response = await getProvinces(); // Gọi API lấy danh sách tỉnh/thành
        console.log("Provinces data:", response.data); // Kiểm tra dữ liệu trả về từ API
        setProvinces(response.data); // Lưu dữ liệu vào state
      } catch (error) {
        console.error("Error fetching provinces:", error);
      }
    };

    fetchProvinces(); // Gọi hàm fetchProvinces khi component được mount
  }, []);
  //lấy danh sách quận/huyện theo tỉnh
  useEffect(() => {
    if (selectedProvinceId) {
      const fetchDistrict = async () => {
        // console.log("Fetching districts for province:", selectedProvinceId);
        const data = await getDistricts(selectedProvinceId);
        // console.log("Districts data:", data);
        setDistricts(data.data);
        setWards([]); // Clear wards when province changes
      };
      fetchDistrict();
    }
  }, [selectedProvinceId]);
  //lấy danh sách phường/xã theo quận/huyện
  useEffect(() => {
    if (selectedDistrictId) {
      const fetchWards = async () => {
        const data = await getWards(selectedDistrictId);
        setWards(data.data);
      };
      fetchWards();
    }
  }, [selectedDistrictId]);
  //chọn shippingAddressID
  const handleAddressSelect = (id) => {
    const selectedAddr = allAddress.find((addr) => addr.id === id);
    if (selectedAddr) {
      setSelectedAddress(selectedAddr); // Cập nhật địa chỉ đã chọn
    }
    console.log("shipping id"+id)
    setShippingAddressID(id);

  };
  //chọn PaymendMethod
  const handlePaymentMethodChange = (e) => {
    const selectedPaymentMethod = e.target.value;
    if (selectedPaymentMethod === "cash") {
      setPaymentMethodID(1); // Thanh toán tiền mặt
    } else if (selectedPaymentMethod === "vnpay") {
      setPaymentMethodID(2); // Thanh toán qua VNPAY
    }
    console.log("Selected payment method ID:", paymentMethodID);
  };


  const handleOrder = async () => {
    if (!paymentMethodID) {
      alert("Vui lòng chọn phương thức thanh toán.");
      return;
    }
    if (!shippingAddressID) {
      alert("Vui lòng chọn địa chỉ giao hàng.");
      return;
    }
    try {
       // Sau khi đặt hàng, gọi lại fetchCartData để cập nhật số lượng trong giỏ hàng
      
       if (paymentMethodID === 1) {
        const response = await createCodOrder(token, formattedItems, paymentMethodID, shippingAddressID);
        console.log("Order created successfully:", response);
        navigate("/order-success");
      } else if (paymentMethodID === 2) {
        const response2 = await createPaymentLink(token, formattedItems, paymentMethodID, shippingAddressID);
        console.log("Order payment online successfully:", response2);
        if (response2 && response2.data.checkoutUrl) {
          window.location.href = response2.data.checkoutUrl;
      } else {
          console.error("Checkout URL không tồn tại");
      }
      }

      const bookIDs = formattedItems.map(item => item.bookID);
      // console.log("Book IDs:", bookIDs);  
      const removeResponse = await removeItems(token, bookIDs);
      console.log("Items removed from cart:", removeResponse);
      await fetchCartCount();
      alert("Đặt hàng thành công!");
    } catch (error) {
      console.error("Error creating order:", error);
      alert("Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại.");
    }
  };

  // if (!book) {
  //   return <p>Đang tải thông tin sản phẩm...</p>;
  // }

  return (
    <div className="container mt-4">
      <div className="row confirm">
        {/* Phần bên trái chiếm 8/12 chiều rộng */}
        <div className="col-8 box boxConfirm" style={{ padding: "20px" }}>
          <div className="header-container">
            <h1>Đơn hàng của bạn</h1>
          </div>
          <div className="product-infor">
            <h5>Thông tin sản phẩm</h5>

{books.length > 0 ? (
        books.map((book, index) => (
            <div className="product-container custom-product-container p-2 mb-2">
              <div className="product-item custom-product-item">
                <img
                  src={book.imageLink}
                  alt="Sản phẩm"
                  className="img-fluid"
                  style={{ width: "auto", height: "100px" }}
                />
                <div className="product-title">
                  <h6>{book.title}</h6>
                </div>
                <span className="sluong">Số lượng: x{book.quantity}</span>
              </div>
              <span className="za ms-3">
                Giá: {Formatter(book.discountedPrice)}
              </span>
            </div>
              ))
            ) : (
              <p>Không có sản phẩm nào được chọn.</p>
            )}
          </div>

          <div className="shipping-info">
            <h5>Thông tin giao hàng</h5>

            {hasAddress ? (
              <div>
                {/* <p>Địa chỉ mặc định: {address}</p> */}
                <p>
                  {selectedAddress
                    ? `Địa chỉ được chọn: ${selectedAddress.receiver}, ${selectedAddress.phoneNumber}, ${selectedAddress.address}, ${selectedAddress.ward}, ${selectedAddress.district}, ${selectedAddress.city}`
                    : defaultAddress
                    ? `Địa chỉ mặc định: ${defaultAddress.receiver}, ${defaultAddress.phoneNumber}, ${defaultAddress.address}, ${defaultAddress.ward}, ${defaultAddress.district}, ${defaultAddress.city}`
                    : "Không có địa chỉ mặc định."}
                </p>
                <a
                  href="#"
                  className="a-change-address"
                  onClick={(e) => {
                    e.preventDefault();
                    setShowShippingForm(true);
                  }}
                >
                  Thay đổi địa chỉ
                </a>
              </div>
            ) : (
              <div>
                <p>
                <p>
                  {selectedAddress
                    ? `Địa chỉ được chọn: ${selectedAddress.receiver}, ${selectedAddress.phoneNumber}, ${selectedAddress.address}, ${selectedAddress.ward}, ${selectedAddress.district}, ${selectedAddress.city}`
                    : defaultAddress
                    ? `Địa chỉ mặc định: ${defaultAddress.receiver}, ${defaultAddress.phoneNumber}, ${defaultAddress.address}, ${defaultAddress.ward}, ${defaultAddress.district}, ${defaultAddress.city}`
                    : "Không có địa chỉ mặc định."}
                </p>
                </p>
                <button
                  className="a-change-address"
                  onClick={() => setShowShippingForm(true)}
                >
                  Thêm địa chỉ mới
                </button>
              </div>
            )}
          </div>

          {showShippingForm && (
            <div className="modal-overlay">
              <div className="modal-content">
                <div className="modal-header">
                  <h5>Thông tin giao hàng</h5>
                  <button
                    className="modal-close"
                    onClick={() => setShowShippingForm(false)}
                  >
                    <IoIosCloseCircle />
                  </button>
                </div>

                <div className="chooseAddress">
                  {allAddress.map((address) => (
                    <div key={address.id} className="">
                      <input
                        type="radio"
                        id={`address-${address.id}`}
                        name="selectedAddress"
                        value={address.id}
                        checked={
                          selectedAddress
                            ? selectedAddress.id === address.id
                            : defaultAddress?.id === address.id
                        }
                        onChange={() => handleAddressSelect(address.id)}
                      />
                      <label htmlFor={`address-${address.id}`} className="">
                        {` ${address.receiver}, ${address.phoneNumber}, ${address.address}, ${address.city}, ${address.district}`}
                      </label>
                    </div>
                  ))}
                </div>

                <div className="mb-3 d-flex justify-content-between">
                  <label htmlFor="name" className="form-label label-width">
                    Tên:
                  </label>
                  <input
                    type="text"
                    className="form-control input-width"
                    id="name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                  />
                </div>

                <div className="mb-3 d-flex justify-content-between">
                  <label htmlFor="phone" className="form-label label-width">
                    Số điện thoại:
                  </label>
                  <input
                    type="text"
                    className="form-control input-width"
                    id="phone"
                    value={phone}
                    onChange={(e) => setPhone(e.target.value)}
                  />
                </div>

                <div className="mb-3 d-flex justify-content-between">
                  <label htmlFor="province" className="form-label label-width">
                    Tỉnh/Thành:
                  </label>
                  <select
                    id="province"
                    className="form-control input-width"
                    value={selectedProvinceId} // Sử dụng selectedProvinceId làm giá trị cho dropdown
                    onChange={(e) => {
                      const selectedId = e.target.value;
                      if (selectedId) {
                        const selectedProvinceObject = provinces.find(
                          (prov) => prov.id === selectedId
                        );
                        if (selectedProvinceObject) {
                          setSelectedProvince(selectedProvinceObject.name);
                          setSelectedProvinceId(selectedId);
                        }
                      }
                    }}
                  >
                    <option value="">Chọn tỉnh/thành</option>
                    {provinces.map((province) => (
                      <option key={province.id} value={province.id}>
                        {province.name}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="mb-3 d-flex justify-content-between">
                  <label htmlFor="district" className="form-label label-width">
                    Quận/Huyện:
                  </label>
                  <select
                    id="district"
                    className="form-control input-width"
                    value={selectedDistrictId}
                    onChange={(e) => {
                      const selectedDistrict = districts.find(
                        (district) => district.id === e.target.value
                      )?.name;
                      const selectedId = e.target.value;
                      setSelectedDistrict(selectedDistrict);
                      setSelectedDistrictId(selectedId);
                    }}
                    disabled={!selectedProvinceId}
                  >
                    <option value="">Chọn quận/huyện</option>
                    {districts.map((district) => (
                      <option key={district.id} value={district.id}>
                        {district.name}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="mb-3 d-flex justify-content-between">
                  <label htmlFor="ward" className="form-label label-width">
                    Phường/Xã:
                  </label>
                  <select
                    id="ward"
                    className="form-control input-width"
                    value={selectedWardId}
                    onChange={(e) => {
                      const selectedWardName = wards.find(
                        (ward) => ward.id === e.target.value
                      )?.name;
                      const selectedId = e.target.value;
                      setSelectedWard(selectedWardName);
                      setSelectedWardId(selectedId); 
                    }}
                    disabled={!selectedDistrictId}
                  >
                    <option value="">Chọn phường/xã</option>
                    {wards.map((ward) => (
                      <option key={ward.id} value={ward.id}>
                        {ward.name}
                      </option>
                    ))}
                  </select>
                </div>

                <div className="mb-3 d-flex justify-content-between">
                  <label htmlFor="address" className="form-label label-width">
                    Địa chỉ:{" "}
                  </label>
                  <input
                    type="text"
                    className="form-control input-width"
                    id="address"
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                  />
                </div>

                <button className="btn save-address" onClick={saveAddress}>
                  Lưu địa chỉ mới
                </button>
                <button className="btn save-address" onClick={saveAddress}>
                  Đặt làm địa chỉ mặc định
                </button>
              </div>
            </div>
          )}

          <div className="payment-info">
            <h5>Phương thức thanh toán</h5>
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="paymentMethod"
                id="cashPayment"
                value="cash"
                onChange={handlePaymentMethodChange}
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
                id="vnpayPayment"
                value="vnpay"
                onChange={handlePaymentMethodChange}
              />
              <label className="form-check-label" htmlFor="vnpayPayment">
                Thanh toán trực tuyến
              </label>
            </div>
          </div>
        </div>

        {/* Phần bên phải chiếm 4/12 chiều rộng */}
        <div className="col-4 box boxOrder">
          <div>
            <div className="payment-container">
              <div className="roww">
                <span>Tạm tính</span>
                {Formatter(totalPrice)}
              </div>
              <div className="roww">
                <span>Giảm giá</span>
                <span>0</span>
              </div>
              <hr className="aaa" />
              <div className="roww">
                <span>Thành tiền</span>
                <span className="highlight-price">
                  {Formatter(totalPrice)}
                  </span>
              </div>
              <button className="btn-buy" onClick={handleOrder}>Đặt hàng</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ConfirmOrder;
