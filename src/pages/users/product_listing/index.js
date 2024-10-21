import React from "react";
import "./style.scss"
import { Link, useNavigate, useParams, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import  Slider  from "@mui/material/Slider";
import { Formatter } from "utils/formatter";
import Checkbox from '@mui/material/Checkbox';
import { getAllBooks, getBookByCategoryID, getCategories, getTargets } from "services/user/bookService";
import { Button } from "@mui/material";
import Box from '@mui/material/Box';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import { FaRegHeart } from "react-icons/fa";
import Pagination from '@mui/material/Pagination';
import { Discount } from "utils/discount";
import { generateSlug, findCategoryIdBySlug } from "utils/createSlug";
function valueText(value) {
    return `${value}°C`;
}
const labelCheckbox = { inputProps: { 'aria-label': 'Checkbox demo' } };

const ProductListing = () => {
    const { slug } = useParams(); // Lấy slug từ URL

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const page1 = searchParams.get('page') || 1;

    const [value, setValue] = useState([0, 1000000]); // filter price
    const [categories, setCategories] = useState([]);
    const [targets, setTargets] = useState([]);
    const [selectedCategoryId, setSelectedCategoryId] = useState(null); // click chọn category trong listing á
    const [books, setBooks] = useState([]);
    const [page, setPage] = useState(+page1);
    const [totalPage, setTotalPage] = useState(1);

    const [sortValue, setSortValue] = useState("1"); // giá trị chọn trong sort filter
    const navigate = useNavigate();

    // getBookByCategoryId
    useEffect(() => {
        const categoryId = findCategoryIdBySlug(categories, slug);
        if (categoryId) {
            setSelectedCategoryId(categoryId);
            setPage(+page1);
            const fetchBookByCategory = async () => {
                const response = await getBookByCategoryID(categoryId, page - 1);
                setBooks(response.data.content);
                setTotalPage(response.data.page.totalPages);
            };
            fetchBookByCategory();
        }
        else if (slug === "gosho") {
            const bookData = [
                {
                  "id": "1033632359",
                  "title": "Búp sen xanh (2020)",
                  "originalPrice": 72000.00,
                  "discountedPrice": 64800.00,
                  "imageLink": "https://product.hstatic.net/200000343865/product/bup-sen-xanh_bia_phien-ban-ky-niem-2020_7ed4b795a252443e899a875b19b69d0c.jpg",
                  "soldQuantity": 45,
                  "availableQuantity": 500
                },
                {
                  "id": "1033632371",
                  "title": "Thăng Long Kinh Kì - Kẻ Chợ - Thời Lê - Trịnh",
                  "originalPrice": 60000.00,
                  "discountedPrice": 30000.00,
                  "imageLink": "https://product.hstatic.net/200000343865/product/thang-long-kinh-ki-ke-cho-thoi-le-trinh_0a60c17a9ca04513a9e68728e53d7cbd.jpg",
                  "soldQuantity": 54,
                  "availableQuantity": 500
                },
                {
                  "id": "1033632411",
                  "title": "Những sư tử non",
                  "originalPrice": 40000.00,
                  "discountedPrice": 20000.00,
                  "imageLink": "https://product.hstatic.net/200000343865/product/nhung-su-tu-non_bia_fbf4f254e6744c6b975b50d61ce8c53d.jpg",
                  "soldQuantity": 52,
                  "availableQuantity": 500
                }
              ];
          
              // Cập nhật trạng thái với dữ liệu sách
              setBooks(bookData);
        } 
        else if (slug === "alls") {
            setSelectedCategoryId(0); // allBoooks
        }
        
        window.scrollTo(0, 0);
    }, [slug, page, page1, categories]);

    // getAllBooks
    useEffect(() => {
        if (selectedCategoryId === 0) {
          setPage(+page1);
          const fetchAllBooks = async () => {
            const response = await getAllBooks(page - 1);
            setBooks(response.data.content);
            setTotalPage(response.data.page.totalPages);
          };
    
          fetchAllBooks();
        }
      }, [selectedCategoryId, page1, page]);

    // getCategories
    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await getCategories();
                setCategories(response.data); 
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };

        fetchCategories();
    }, []);

    useEffect(() => {
        const fetchTargets = async () => {
            try {
                const response = await getTargets();
                setTargets(response.data); 
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };

        fetchTargets();
    }, [])

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleChangePage = (event, value) => {
        if (selectedCategoryId === 0) {
            setPage(value);
            navigate(`/listing/alls?page=${value}`);
        }
        else {
            const category = categories.find(category => category.id === selectedCategoryId);
            const slug = generateSlug(category.name);  
            setPage(value);
            navigate(`/listing/${slug}?page=${value}`);
        }
    };

    const handleCategoryClick = (id) => {
        if (id === 0) {
            setSelectedCategoryId(id);
            navigate(`/listing/alls`);
        }
        else {
            const category = categories.find(category => category.id === id);
            const slug = generateSlug(category.name); 
            setSelectedCategoryId(id);
            navigate(`/listing/${slug}`);
        }
    };

    const handleChangeSort = (event) => {
        setSortValue(event.target.value);
    };
    return (
        <>
            <div className="listingPage">
                <div className="container">
                    <div className="breadcrumb flex-column">
                        <h1>
                            {selectedCategoryId === 0 ? 'Tất cả sản phẩm' : categories.find(category => category.id === selectedCategoryId)?.name || 'Tất cả sản phẩm'}
                        </h1>
                        <ul className="list list-inline d-flex">
                            <li className="list-inline-items">
                                <Link to="/">Trang chủ</Link>
                            </li>
                            <li className="list-inline-items">
                                {/* <Link to="">Tất cả sản phẩm</Link> */}
                                {selectedCategoryId === 0 ? 'Tất cả sản phẩm' : categories.find(category => category.id === selectedCategoryId)?.name || 'Tất cả sản phẩm'}
                            </li>
                        </ul>
                    </div>
                </div>
            </div>


            <div className="listingData">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-3 sidebarWrapper">
                            <div className="sidebar">
                                <div className="card border-0 shadow">
                                    <h4>Thể loại</h4>
                                    <div className="cateList">
                                        {categories && categories.length > 0 ? (
                                            <>
                                            <div className="cateItem d-flex align-items-center"
                                                onClick={() => handleCategoryClick(0)}    
                                            >
                                                <h5 className="mb-0 me-3"
                                                    style={{
                                                        color: selectedCategoryId === 0 ? '#9d2ff7' : 'black',
                                                        fontWeight: selectedCategoryId === 0 ? 'bold' : 'normal'
                                                    }}
                                                >
                                                    Tất cả sản phẩm
                                                </h5>
                                                <span className="d-flex justify-content-center align-items-center rounded-circle ms-auto">
                                                {categories.reduce((acc, category) => acc + category.quantity, 0)}
                                                </span>
                                            </div>
                                            {categories.map((category) => (
                                                    
                                                    <div className="cateItem d-flex align-items-center"
                                                        key={category.id}
                                                        onClick={() => handleCategoryClick(category.id)}
                                                    >
                                                        <h5 className="mb-0 me-3" 
                                                            style={{
                                                                color: selectedCategoryId === category.id ? '#9d2ff7' : 'black',
                                                                fontWeight: selectedCategoryId === category.id ? 'bold' : 'normal'
                                                            }}
                                                        >{category.name}</h5>
                                                        <span className="d-flex justify-content-center align-items-center rounded-circle ms-auto">{category.quantity}</span>
                                                    </div>
                                           
                                            ))}
                                            </>
                                        ) : (
                                            <li>No categories available</li> 
                                        )}

                                    </div>
                                </div>

                                {/* Filter */}
                                <div className="card border-0 shadow">
                                    <h4>Lọc</h4>
                                    <h6 className="mb-0">Giá</h6>
                                    <Slider
                                        min={0}
                                        step={100000}
                                        max={1000000}
                                        getAriaLabel={() => 'Temperature range'}
                                        value={value}
                                        onChange={handleChange}
                                        valueLabelDisplay="auto"
                                        getAriaValueText={valueText}
                                        sx={{
                                            color: '#9d2ff7',
                                            '& .MuiSlider-thumb': {
                                                width: 16,  // Làm nhỏ thumb
                                                height: 16, // Làm nhỏ thumb
                                            },
                                            '& .MuiSlider-track': {
                                                height: 4,  // Làm mỏng track
                                            },
                                            // '& .MuiSlider-rail': {
                                            //     height: 4,  // Làm mỏng rail
                                            // },
                                        }}
                                    />
                                    <div className="d-flex pt-2 pb-2 priceRange">
                                        <span>
                                            Từ:<strong> {Formatter(value[0])}</strong>
                                        </span>
                                        <br/>
                                        <span className="ms-auto">
                                            Đến:<strong> {Formatter(value[1])}</strong>
                                        </span>
                                    </div>

                                    <div className="ageFilter">
                                        <h6>Độ tuổi</h6>
                                        <ul className="list list-inline">
                                            {targets && targets.length > 0 ? (
                                                targets.map(target => (
                                                    <li key={target.id}>
                                                         <label style={{ cursor: 'pointer' }}>
                                                        <Checkbox {...labelCheckbox} 
                                                            sx={{
                                                                '& .MuiSvgIcon-root': {
                                                                    fontSize: 20,  // Điều chỉnh kích thước của checkbox (icon)
                                                                },
                                                                '&.Mui-checked': {
                                                                    color: '#9d2ff7',  // màu sau khi check
                                                                },
                                                                }}
                                                        />
                                                        {target.name}
                                                        </label>
                                                    </li>
                                                ))

                                            ) : (
                                                <div></div>
                                            )}
                                            
                                        </ul>
                                    </div>

                                    <Button className="btn-filter">Filter</Button>
                                    
                                </div>
                            </div>
                        </div>

                        <div className="col-lg-9 rightContent">
                            <div className="sort d-flex align-items-center mt-3 mb-3 ms-3">
                                <h3>{(selectedCategoryId === 0 ? 'Tất cả sản phẩm' : categories.find(category => category.id === selectedCategoryId)?.name || 'Tất cả sản phẩm')}</h3>
                                <Box className="ms-auto" sx={{ mr:1.6, minWidth: 120 }}>
                                    <FormControl fullWidth>
                                        <InputLabel id="demo-simple-select-label">Sort</InputLabel >
                                        <Select
                                            labelId="demo-simple-select-label"
                                            id="demo-simple-select"
                                            value={sortValue}
                                            label="Age"
                                            onChange={handleChangeSort}
                                            MenuProps={{
                                                disableScrollLock: true,
                                            }}
                                            sx={{
                                                '&.MuiOutlinedInput-root': {
                                                    // '& fieldset': {
                                                    //     borderColor: 'blue'
                                                    // },
                                                    '&:hover fieldset': {
                                                        borderColor: 'blue',
                                                    },
                                                    '&.Mui-focused fieldset': {
                                                        borderColor: '#5E2E86',
                                                    },
                                                },
                                            }}
                                        >
                                        <MenuItem value="1">Mặc định</MenuItem>
                                        <MenuItem value="2">Giá tăng dần</MenuItem>
                                        <MenuItem value="3">Giá giảm dần</MenuItem>
                                        <MenuItem value="4">Bán chạy</MenuItem>
                                        </Select>
                                    </FormControl>
                                    </Box>
                            </div>
                            <div className="productRow ms-3">
                            {books && Array.isArray(books) && books.length > 0 ? (
                                books.map(book => (
                                    <div className="item1 productItem1">
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

                            </div>
                            
                            <div className="pagination justify-content-center">
                                <Pagination
                                    // key={selectedCategoryId}
                                    count={totalPage} 
                                    color="secondary"
                                    page={page} 
                                    onChange={handleChangePage}
                                />
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default ProductListing;