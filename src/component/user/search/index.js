import React, { useEffect } from "react";
import "./style.scss"
import { useNavigate, useLocation } from "react-router-dom";
import { useState } from "react";
import { FaRegHeart } from "react-icons/fa";
import { Button } from "@mui/material";
import Pagination from "@mui/material/Pagination";
import { Formatter } from "utils/formatter";
import { Discount } from "utils/discount";
const ProductSearch = () => {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search)
    const type = searchParams.get("type")

    const book = location.state?.book;

    const navigate = useNavigate();
    const [page, setPage] = useState(1);
    const [books, setBooks] = useState([])
    const handleChangePage = (event, value) => {
        setPage(value);
    };

    useEffect(() => {
        if (type === "hot-book" || "best-seller-book" || "randomBookByCate") {
            setBooks(book)
        }
        window.scrollTo(0, 0);
    }, [type, book])
    return (
        <div className="searchWrapper">
            <div className="container">
                <h2>Kết quả tìm kiếm: </h2>
                <div className="productRow">
                    {/* <div className="item2 productItem2 mb-3">
                        <div className="imgWrapper"
                            onClick={() => navigate(`/search`)}
                        >
                            <img src="https://product.hstatic.net/200000343865/product/4_80d9fdc5eab64df29885417daf6a0c45_94d704766c434cdf8b1074445fcd8ead_large.jpg" alt="{book.title} "className='w-100' loading="lazy"/>
                            <span className="badge bg-danger">- 21%</span>
                            <div className='actions'>
                                <Button
                                    onClick={(e) => { e.stopPropagation();}}>
                                    <FaRegHeart />
                                </Button>
                            </div>
                            <h4>Doraemon Plus Tập 4</h4>
                            <span className='text-success'>In Stock</span>
                            <div className="d-flex justify-content-between">
                                <span className="curPrice text-danger">100000</span>
                                <span className="oriPrice">10000</span>
                            </div>
                        </div>
                    </div> */}
                    {books && Array.isArray(books) && books.length > 0 ? (
                                books.map(book => (
                                    <div className="item2 productItem2 mb-3">
                                        <div className="imgWrapper"
                                            onClick={() => navigate(`/details/${book.id}`)}
                                        >
                                            <img src={book.imageLink} alt={book.title} className='w-100' loading="lazy"/>
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
                                ))
                            ) : (
                                <p>No books available</p>
                            )}
                    <div className="pagination d-flex justify-content-center w-100">
                        <Pagination
                            // key={selectedCategoryId}
                            count={10} 
                            color="secondary"
                            page={page} 
                            onChange={handleChangePage}
                        />
                    </div>

                </div>
            </div>
        </div>
    )
}

export default ProductSearch;