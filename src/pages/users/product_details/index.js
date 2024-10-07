import React, { useContext } from "react";
import "./style.scss";
import { Rating } from "@mui/material";
import { Button } from "@mui/material";
import { Formatter } from "utils/formatter";
import { FormatDate } from "utils/formatDate";
import "bootstrap/dist/css/bootstrap.min.css";
import { useState, useEffect, useRef } from "react";
import { getBookByID } from "services/user/bookService";
import { Link, useParams, useNavigate } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./style.scss";
import InnerImageZoom from "react-inner-image-zoom";
import "react-inner-image-zoom/lib/InnerImageZoom/styles.css";
import { FaMinus, FaPlus } from "react-icons/fa";
import { CartContext } from "context/CartContext";
import { processAddToCart } from "services/user/cartService";

const ProductDetails = () => {
  const zoomSliderBig = useRef();
  const { id } = useParams(); // Lấy id từ URL
  const [book, setBook] = useState(null);
  const [quantityValue, setQuantityValue] = useState(1);
  const [estimateValue, setEstimateValue] = useState(0);
  const navigate = useNavigate();

  const { addToCart } = useContext(CartContext); // Lấy hàm addToCart từ context

  const handleAddToCart = async (e) => {
    const token = localStorage.getItem("token");
    const quantity = quantityValue; // lấy sl sách từ state
    try {
      const response = await processAddToCart(token, id, quantity);
      console.log("Book added to cart:", response);
    } catch (error) {
      console.error("Error adding book to cart:", error);
    }
  };

  const handleBuyHot = async (e) => {
    e.preventDefault();
    navigate(`/confirm-order/${id}/${quantityValue}`); 
  };

  useEffect(() => {
    const fetchBookDetails = async () => {
      const bookData = await getBookByID(id);
      setBook(bookData.data);
    };

    fetchBookDetails();
  }, [id]);

  useEffect(() => {
    if (book) {
      setEstimateValue(book.discountedPrice * quantityValue);
    }
  }, [book, quantityValue]);

  if (!book) {
    return <p>Book not found.</p>;
  }

  const minus = () => {
    setQuantityValue(Math.max(+quantityValue - 1, 1));
    setEstimateValue(+book.discountedPrice * quantityValue);
  };

  const plus = () => {
    setQuantityValue(Math.min(+quantityValue + 1, 999));
    setEstimateValue(+book.discountedPrice * quantityValue);
  };

  const handleChange = (e) => {
    const value = e.target.value;

    // nhập số với tối đa 3 kí tự
    if (/^\d*$/.test(value) && value.length <= 3) {
      setQuantityValue(+value);
    }
  };

  const settings = {
    dots: false,
    infinite: false,
    speed: 700,
    slidesToShow: 1,
    slidesToScroll: 1,
    fade: false,
    arrows: false,
  };

  return (
    <>
      <section className="productDetails section">
        <div className="container">
          <div className="row">
            <div className="col-md-4">
              <div className="productZoom">
                <Slider
                  {...settings}
                  className="zoomSliderBig"
                  ref={zoomSliderBig}
                >
                  <div className="item">
                    <InnerImageZoom
                      zoomType="hover"
                      zoomScale={1}
                      src={book.imageLink}
                    />
                  </div>
                </Slider>
              </div>
            </div>
            <div className="col-md-8">
              <ul className="list list-inline d-flex">
                <h2 className="hd text-uppercase">{book.title}</h2>
                <span className="badge bg-success">Còn hàng</span>
              </ul>
              <ul className="line list list-inline d-flex align-items-center">
                <li className="list-inline-item">
                  <div className="d-flex align-items-center">
                    <span className="me-2">Đã bán: </span>
                    <span className="sold">{book.soldQuantity}</span>
                  </div>
                </li>
                <li className="list-inline-item">
                  <div className="d-flex align-items-center">
                    <Rating
                      className="hi"
                      name="read-only"
                      value={5}
                      precision={0.5}
                      readOnly
                      size="small"
                    />
                    <span className="ms-2">1 đánh giá</span>
                  </div>
                </li>
              </ul>

              <div className="d-flex info line mt-1">
                <span className="curPrice text-danger">
                  {Formatter(book.discountedPrice)}
                </span>
                <span className="oriPrice">
                  {Formatter(book.originalPrice)}
                </span>
                <span className="save">
                  Wao tiết kiệm được tận{" "}
                  {Formatter(book.originalPrice - book.discountedPrice)}
                </span>
              </div>

              <div className="list-info align-items-center mt-2">
                <h6>Thông tin chi tiết</h6>
                <table>
                  <tr>
                    <td className="label">Tác giả:</td>
                    <td className="content">{book.authors.join(", ")}</td>
                  </tr>
                  <tr>
                    <td className="label">Thể loại:</td>
                    <td className="content">{book.category}</td>
                  </tr>
                  <tr>
                    <td className="label">Đối tượng:</td>
                    <td className="content">{book.targets}</td>
                  </tr>
                  <tr>
                    <td className="label">Khuôn khổ:</td>
                    <td className="content">{book.dimension}</td>
                  </tr>
                  <tr>
                    <td className="label">Số trang:</td>
                    <td className="content">{book.pages} trang</td>
                  </tr>
                  <tr>
                    <td className="label">Định dạng:</td>
                    <td className="content">{book.cover}</td>
                  </tr>
                  {book.datePublish ? (
                    <tr>
                      <td className="label">Ngày phát hành:</td>
                      <td className="content">
                        {FormatDate(book.datePublish)}
                      </td>
                    </tr>
                  ) : (
                    <tr></tr>
                  )}
                </table>
              </div>

              <div className="quantityDrop align-items-center mt-2">
                <div>
                  <span className="me-5">Số lượng: </span>
                  <Button className="ms-5" onClick={minus}>
                    <FaMinus />
                  </Button>
                  <input
                    type="text"
                    value={quantityValue}
                    onChange={handleChange}
                  />
                  <Button className="me-5" onClick={plus}>
                    <FaPlus />
                  </Button>
                </div>

                <div className="mt-2">
                  <span>Tạm tính: </span>
                  <span className="text-danger priceEstimate">
                    {Formatter(estimateValue)}
                  </span>
                </div>
              </div>

              <div className="buy d-flex align-items-center mt-3">
                <Button className="addToCart" onClick={handleAddToCart}>
                  Thêm vào giỏ hàng
                </Button>
                <Button className="addToOrder" onClick={handleBuyHot}>
                  Mua ngay cho nóng
                </Button>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default ProductDetails;
