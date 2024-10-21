import React from "react";
import { useState, useEffect } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import "./style.scss"
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import { Button } from "@mui/material";
import { getCategories, getTargets } from "services/user/bookService";
import { BsPencil } from "react-icons/bs";
import { RiDeleteBin6Line } from "react-icons/ri";
import { Formatter } from "utils/formatter";
import Pagination from '@mui/material/Pagination';
import { Link } from "react-router-dom";
import { findCategoryIdBySlug } from "utils/createSlug";
import { getBookByCategoryID } from "services/user/bookService";
import { generateSlug } from "utils/createSlug";
import { getAllBooks } from "services/user/bookService";
import { MyContext } from "../theme/adminLayout";
import { useContext } from "react";

const BookList = () => {
    const context = useContext(MyContext);
    const { slug } = useParams(); // Lấy slug từ URL

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const page1 = searchParams.get('page') || 1;

    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(+page1);
    const [totalPage, setTotalPage] = useState(1);
    const navigate = useNavigate();

    
    const [categoryVal, setCategoryVal] = useState(0); // category của select
    
    const [categories, setCategories] = useState([]);
    // categories
    useEffect(() => {
        const fetchCategories = async() => {
            try {
                const response = await getCategories();
                setCategories(response.data);
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };

        fetchCategories();
    },[])

    // getBookByCategoryId
    useEffect(() => {
        const categoryId = findCategoryIdBySlug(categories, slug);
        if (categoryId) {
            setCategoryVal(categoryId);
            setPage(+page1);
            const fetchBookByCategory = async () => {
                const response = await getBookByCategoryID(categoryId, page - 1);
                console.log(response.data)
                setBooks(response.data.content);
                setTotalPage(response.data.page.totalPages);
            };
            fetchBookByCategory();
        }
        else if (slug === "alls") {
            setCategoryVal(0); // allBoooks
        }
        
        window.scrollTo(0, 0);
    }, [slug, page, page1, categories]);

     // getAllBooks
     useEffect(() => {
        if (categoryVal === 0) {
          setPage(+page1);
          const fetchAllBooks = async () => {
            const response = await getAllBooks(page - 1);
            setBooks(response.data.content);
            setTotalPage(response.data.page.totalPages);
          };
    
          fetchAllBooks();
        }
      }, [categoryVal, page1, page]);

    const handleChangeCate = (event) => {
        setCategoryVal(event.target.value);
    };

    const handleChangePage = (event, value) => {
        if (categoryVal === 0) {
            setPage(value);
            navigate(`/admin/list-book/alls?page=${value}`);
        }
        else {
            const category = categories.find(category => category.id === categoryVal);
            const slug = generateSlug(category.name);  
            setPage(value);
            navigate(`/admin/list-book/${slug}?page=${value}`);
        }
    };

    const handleCategoryClick = (id) => {
        if (id === 0) {
            setCategoryVal(id);
            navigate(`/admin/list-book/alls`);
        }
        else {
            const category = categories.find(category => category.id === id);
            const slug = generateSlug(category.name); 
            setCategoryVal(id);
            navigate(`/admin/list-book/${slug}`);
        }
    };

    return (
        <div className="BookList">
            <div className="card border-0 p-4 mt-5">
                <div className="headCard d-flex">
                    <h5>Tất cả sản phẩm</h5>
                    <Link to="/admin/add-book" className="ms-auto"><Button >Thêm mới sách</Button></Link>
                </div>
                <hr />

                <div className="row cardFilters">
                    <h4>Thể loại</h4>
                    <div className="col-4">
                        <Select
                            className="custom-select"
                            value={categoryVal}
                            onChange={handleChangeCate}
                            displayEmpty
                            inputProps={{ 'aria-label': 'Without label' }}
                            MenuProps={{
                                disableScrollLock: true,
                                PaperProps: {
                                    style: {
                                        maxHeight: 200, 
                                        overflowY: 'auto',
                                    },
                                },
                            }}
                            sx={{
                                '&.MuiOutlinedInput-root': {
                                    // '& fieldset': {
                                    //     borderColor: 'red',
                                    // },
                                    '&:hover fieldset': {
                                        borderColor: '#9d2ff7',
                                    },
                                    '&.Mui-focused fieldset': {
                                        borderColor: '#9d2ff7',
                                    },
                                },
                            }}
                        >
                            <MenuItem disabled>
                                <em>Thể loại</em>
                            </MenuItem>

                            <MenuItem value={0} onClick={() => handleCategoryClick(0)} >Tất cả sản phẩm</MenuItem>

                            {categories && categories.length > 0 ? (
                                categories.map(category => (
                                    <MenuItem value={category.id} onClick={() => handleCategoryClick(category.id)}>{category.name}</MenuItem>
                                ))
                            ) : (
                                <MenuItem disabled>No categories available</MenuItem>
                            )}
                        </Select>
                    </div>
                </div>

                <div className="table-responsive mt-3">
                    <table className="table table-bordered v-align">
                        <thead className="thead-dark">
                            <tr>
                                <th>ID</th>
                                <th>SÁCH</th>
                                <th>THỂ LOẠI</th>
                                <th>TÁC GIẢ</th>
                                <th>GIÁ</th>
                                <th>KHO</th>
                                <th>ĐƠN HÀNG</th>
                                <th>ACTION</th>
                            </tr>
                        </thead>

                        <tbody>
                            {books && books.length > 0 ? (
                                books.map((book, index) => (
                                    <tr key={book.id}>
                                        <td>{index+1}</td>
                                        <td>
                                            <div className="d-flex productBox"
                                            style={{ width: context.isToggleSidebar ? '30rem' : '20rem' }}>
                                                <div className="imgWrapper1">
                                                    <div className="img">
                                                        <img className="w-100" src={book.imageLink} alt="" loading="lazy"/>
                                                    </div>
                                                </div>
                                                <h6>{book.title}</h6>
                                            </div>
                                        </td>
                                        <td>{categoryVal}</td>
                                        <td>Negi Haruba</td>
                                        <td>
                                            <span className="new text-danger">{Formatter(book.discountedPrice)}</span>
                                            <del className="old">{Formatter(book.originalPrice)}</del>
                                        </td>
                                        <td>{book.availableQuantity}</td>
                                        <td>500</td>
                                        <td>
                                            <div className="actions d-flex align-items-center">
                                                <Button className="secondary" color="secondary"><BsPencil /></Button>
                                                <Button className="error" color="error"> <RiDeleteBin6Line /></Button>
                                            </div>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr></tr>
                            )}
                        </tbody>
                    </table>

                    <div className="d-flex tableFooter">
                        <Pagination 
                            className="ms-auto" 
                            count={totalPage} 
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

export default BookList;