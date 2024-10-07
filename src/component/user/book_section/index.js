import React from 'react';
import "./style.scss"
import { Link, useNavigate } from 'react-router-dom';
import { Formatter } from 'utils/formatter';
import { FaRegHeart } from 'react-icons/fa';

import 'bootstrap/dist/css/bootstrap.min.css';
import { Button } from '@mui/material';

import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/navigation';
import { Navigation } from 'swiper/modules';

import { Discount } from 'utils/discount';

const BookSection = ({title, books, type}) => {
    const navigate = useNavigate();
    return (
        <>
            <div className="container">
                <div className="title">
                    <h3>{title}</h3>
                    <Link to={`/books/${type}`}>Xem tất cả</Link>
                </div>

                <div className="product_row w-100">
                <Swiper
                    slidesPerView={6} 
                    spaceBetween={10} 
                    navigation={true}
                    pagination = {{ clickable: true }}
                    modules={[Navigation]} 
                    className="mySwiper"
                    breakpoints={{
                        360: {
                            slidesPerView: 2, // Từ 768px đến 992px hiển thị 4 slides
                            spaceBetween: 10
                        },
                        768: {
                            slidesPerView: 4, // Từ 768px đến 992px hiển thị 4 slides
                            spaceBetween: 10
                        },
                        993: {
                            slidesPerView: 6, // Từ 992px trở lên hiển thị 6 slides
                            spaceBetween: 10
                        }
                    }}
                >

                    {books && Array.isArray(books) && books.length > 0 ? (
                            books.map(book => (
                                <SwiperSlide key={book.id} onClick={() => navigate(`/details/${book.id}`)}>
                                    <div className="item productItem">
                                        <div className="imgWrapper">
                                            <img src={book.imageLink} alt={book.title} className='w-100' />
                                            <span className="badge bg-danger">- {Discount(book.originalPrice, book.discountedPrice)}%</span>
                                            <div className='actions'>
                                                <Button
                                                    onClick={(e) => { e.stopPropagation();}}>
                                                    <FaRegHeart />
                                                </Button>
                                            </div>
                                            <h4>{book.title}</h4>
                                            <span className='text-success'>In Stock</span>
                                            <div className="d-flex justify-content-between">
                                                <span className="curPrice text-danger">{Formatter(book.discountedPrice)}</span>
                                                <span className="oriPrice">{Formatter(book.originalPrice)}</span>
                                            </div>
                                        </div>
                                    </div>
                                </SwiperSlide>
                            ))
                        ) : (
                            <p>No books available</p>
                        )}
                </Swiper>
                </div>
            </div>
        </>
    )
}

export default BookSection;