import React from "react";
import "./style.scss";
import { Rating } from "@mui/material";
import Box from "@mui/material/Box";
import StarIcon from "@mui/icons-material/Star";
import { Button } from "@mui/material";
import { Formatter } from "utils/formatter";
import { FormatDate } from "utils/formatDate";
import "bootstrap/dist/css/bootstrap.min.css";
import { useState, useEffect, useRef } from "react";
import { getBookByID } from "services/user/bookService";
import { Link, useParams } from "react-router-dom";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import "./style.scss";
import InnerImageZoom from "react-inner-image-zoom";
import "react-inner-image-zoom/lib/InnerImageZoom/styles.css";
import { FaMinus, FaPlus } from "react-icons/fa";
import HotBook from "component/user/hot_book";

const labelsRating = {
  0.5: "Useless",
  1: "Useless+",
  1.5: "Poor",
  2: "Poor+",
  2.5: "Ok",
  3: "Ok+",
  3.5: "Good",
  4: "Good+",
  4.5: "Excellent",
  5: "Tuyệt",
};

const ProductDetails = () => {
  // giá trị mặc định của rating người dùng đánh giá
  const [valueRating, setValueRating] = React.useState(5);
  const [hover, setHover] = React.useState(-1);
  function getLabelText(value) {
    return `${value} Star${value !== 1 ? "s" : ""}, ${
      labelsRating[value.toString()]
    }`;
  }

  const zoomSliderBig = useRef();
  const { id } = useParams(); // Lấy id từ URL
  const [book, setBook] = useState(null); // lưu trữ sách
  const [quantityValue, setQuantityValue] = useState(1); // lưu trữ số lượng sách mà user chọn
  const [estimateValue, setEstimateValue] = useState(0); // lưu trữ giá tiền tạm tính
  const [activeTabs, setActiveTabs] = useState(0); // chuyển tab đánh giá với mô tả nội dung

  useEffect(() => {
    const fetchBookDetails = async () => {
      const response = await getBookByID(id);
      setBook(response.data);
    };
    window.scrollTo(0, 0);

    fetchBookDetails();
  }, [id]);

  // tính giá trị tạm tính
  useEffect(() => {
    if (book) {
      setEstimateValue(book.discountedPrice * quantityValue);
    }
  }, [book, quantityValue]);

  // đặt giá trị số lượng sách quay lại 1
  useEffect(() => {
    setQuantityValue(1);
  }, [id]);

  // đặt giá tab đổi là 0 (là mô tả nội dung đó hehe)
  useEffect(() => {
    setActiveTabs(0);
  }, [id]);

  useEffect(() => {
    setValueRating(5);
  }, [id]);

  if (!book) {
    return <p>Book not found.</p>;
  }

  const minus = () => {
    setQuantityValue(Math.max(+quantityValue - 1, 1));
    setEstimateValue(+book.discountedPrice * quantityValue);
  };

  // khi nhấn cộng thì tăng số lượng lên 1
  const plus = () => {
    setQuantityValue(Math.min(+quantityValue + 1, 999));
    setEstimateValue(+book.discountedPrice * quantityValue);
  };

  // lưu giá trị tự nhập vào ô số lượng sách
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
          {/* 1 */}
          <div className="row">
            <div className="col-lg-4 col-md-12">
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
                      loading="lazy"
                      src={book.imageLink}
                    />
                  </div>
                </Slider>
              </div>
            </div>
            <div className="col-lg-8 col-md-12">
              <ul className="list list-inline d-flex">
                <h2 className="hd text-uppercase">{book.title}</h2>
                <span className="stock badge bg-success">Còn hàng</span>
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
                <Button className="addToCart">Thêm vào giỏ hàng</Button>
                <Button className="addToOrder">Mua ngay cho nóng</Button>
              </div>
            </div>
          </div>

          {/* 2 */}
          <div className="card mt-5 p-5 detailsPageTabs mb-5">
            <div className="customTabs">
              <ul className="list list-inline d-flex">
                <li className="list-inline-items">
                  <Button
                    className={`${activeTabs === 0 && "active"}`}
                    onClick={() => {
                      setActiveTabs(0);
                    }}
                  >
                    Mô tả nội dung
                  </Button>
                </li>
                <li className="list-inline-items">
                  <Button
                    className={`${activeTabs === 1 && "active"}`}
                    onClick={() => {
                      setActiveTabs(1);
                    }}
                  >
                    Đánh giá
                  </Button>
                </li>
              </ul>
            </div>

            <br />

            {activeTabs === 0 && (
              <div className="tabContent">
                <p>{book.description}</p>
              </div>
            )}

            {activeTabs === 1 && (
              <div className="tabContent">
                <div className="row">
                  <div className="col-md-8">
                    <h5>Khách hàng đánh giá</h5>

                    <div className="card reviewsCard flex-row mb-3">
                      <div className="image">
                        <div className="rounded-circle">
                          <img
                            src="https://img.freepik.com/premium-photo/stylish-man-flat-vector-profile-picture-ai-generated_606187-310.jpg"
                            alt=""
                          />
                        </div>
                        <span>zkai</span>
                      </div>

                      <div className="info ps-5">
                        <div className="d-flex align-items-center">
                          <span className="datePublish">24/12/2003</span>
                          <div className="ps-3">
                            <Rating
                              name="read-only"
                              value={4}
                              precision={0.5}
                              readOnly
                              size="small"
                            />
                          </div>
                        </div>
                        <p>
                          Toẹt vời ông mặt trời ẻwtgergergerger
                        </p>
                      </div>
                    </div>

                    <div className="card reviewsCard flex-row">
                      <div className="image">
                        <div className="rounded-circle">
                          <img
                            src="https://img.freepik.com/premium-photo/stylish-man-flat-vector-profile-picture-ai-generated_606187-310.jpg"
                            alt=""
                          />
                        </div>
                        <span>pct</span>
                      </div>

                      <div className="info ps-5">
                        <div className="d-flex align-items-center">
                          <span className="datePublish">24/12/2003</span>
                          <div className="ps-3">
                            <Rating
                              name="read-only"
                              value={4}
                              precision={0.5}
                              readOnly
                              size="small"
                            />
                          </div>
                        </div>
                        <p>
                          Toẹt vời ông mặt trời good so good man! ô hô hô a ha ha. Đố report được tau
                        </p>
                      </div>
                    </div>
                    

                    <br />
                    <br />

                    <form action="" className="reviewForm">
                      <h5>Viết đánh giá</h5>
                      <br />
                      <div className="mb-3">
                        <Box
                          sx={{
                            width: 200,
                            display: "flex",
                            alignItems: "center",
                          }}
                        >
                          <Rating
                            name="hover-feedback"
                            value={valueRating}
                            precision={1}
                            getLabelText={getLabelText}
                            onChange={(event, newValue) => {
                              setValueRating(newValue);
                            }}
                            onChangeActive={(event, newHover) => {
                              setHover(newHover);
                            }}
                            emptyIcon={
                              <StarIcon
                                style={{ opacity: 0.55 }}
                                fontSize="inherit"
                              />
                            }
                          />
                          {valueRating !== null && (
                            <Box sx={{ ml: 2 }}>
                              {labelsRating[hover !== -1 ? hover : valueRating]}
                            </Box>
                          )}
                        </Box>
                      </div>
                      <div className="row">
                        <div className="col-md-6">
                          <h6>Tên</h6>
                          <div className="form-group">
                            <input
                              type="text"
                              className="form-control"
                              placeholder="Nhập tên bạn muốn hiển thị"
                            />
                          </div>
                        </div>

                        <div className="col-md-6">
                          <h6>Email</h6>
                          <div className="form-group">
                            <input
                              type="text"
                              className="form-control"
                              placeholder="Nhập email của bạn"
                            />
                          </div>
                        </div>
                      </div>

                      {/* <div className="form-group">
                          <input type="text" className="form-control" />
                      </div> */}

                      <div className="form-group">
                        <h6>Nội dung</h6>
                        <textarea
                          className="form-control"
                          placeholder="Viết nội dung đánh giá của bạn"
                        ></textarea>
                      </div>

                      <div className="form-group">
                        <Button className="submit">Gửi đánh giá</Button>
                      </div>
                    </form>
                  </div>

                  <div className="col-md-4 ps-3">
                    <h5>Tổng đánh giá</h5>
                    <div className="d-flex align-items-center">
                      <Rating
                        name="half-rating-read"
                        defaultValue={3.5}
                        precision={0.1}
                        readOnly
                      />
                      <strong className="ps-3">1 đánh giá</strong>
                    </div>

                    <div className="progressBarBox d-flex align-items-center">
                      <span className="me-auto">5 sao</span>
                      <div className="progress" style={{ width: "82%" }}>
                        <div
                          className="progress-bar"
                          style={{ width: "0%", background: "orange" }}
                        >
                          70%
                        </div>
                      </div>
                    </div>

                    <div className="progressBarBox d-flex align-items-center">
                      <span className="me-auto">4 sao</span>
                      <div className="progress" style={{ width: "82%" }}>
                        <div
                          className="progress-bar"
                          style={{ width: "100%", background: "orange" }}
                        >
                          100%
                        </div>
                      </div>
                    </div>

                    <div className="progressBarBox d-flex align-items-center">
                      <span className="me-auto">3 sao</span>
                      <div className="progress" style={{ width: "82%" }}>
                        <div
                          className="progress-bar"
                          style={{ width: "0%", background: "orange" }}
                        >
                          70%
                        </div>
                      </div>
                    </div>

                    <div className="progressBarBox d-flex align-items-center">
                      <span className="me-auto">2 sao</span>
                      <div className="progress" style={{ width: "82%" }}>
                        <div
                          className="progress-bar"
                          style={{ width: "0%", background: "orange" }}
                        >
                          70%
                        </div>
                      </div>
                    </div>

                    <div className="progressBarBox d-flex align-items-center">
                      <span className="me-auto">1 sao</span>
                      <div className="progress" style={{ width: "82%" }}>
                        <div
                          className="progress-bar"
                          style={{ width: "0%", background: "orange" }}
                        >
                          0%
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            )}
          </div>

          {/* 3 */}
          <HotBook />
        </div>
      </section>
    </>
  );
};

export default ProductDetails;
