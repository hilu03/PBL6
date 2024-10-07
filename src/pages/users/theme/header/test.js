import "./style.scss"
import {  AiOutlineShoppingCart } from "react-icons/ai";
import { FaRegHeart, FaSearch, FaRegUser, FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";
// import { Formatter } from "utils/formatter";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState, useEffect } from "react";
import { getCategories } from "services/user/bookService";
import { BiCategory } from "react-icons/bi";
import { generateSlug } from "utils/createSlug";



const Test = () => {
    const token = localStorage.getItem("token")
    const name = localStorage.getItem("name")
    const [categories, setCategories] = useState([])

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

    return (
        <>
            <div className="top">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-2 col-md-2 col-sm-3 col-3">
                            <div className="logo">
                                <Link to=""><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOWmskQa6rP-i3q3n6NeG1i7-Dc9YqnHgm0g&s" alt="" /> </Link>  
                            </div>
                           
                        </div>
                        <div className="col-lg-1 col-md-1 d-none d-md-block">
                        <div className="category">
                <button type="button" >
                  <BiCategory />
                
                    <div className="submenu">
                      <ul>
                        {categories && categories.length > 0 ? (
                            categories.map((category) => (
                                <li key={category.id}>
                                    {" "}
                                    {/* Sử dụng ID duy nhất nếu có */}
                                    <Link to={`/listing/${generateSlug(category.name)}`}>{category.name}</Link>
                                    {/* Thay đổi theo thuộc tính thực tế */}
                                </li>
                            ))
                        ) : (
                            <li>No categories available</li> 
                        )}
                      </ul>
                    </div>
                  
                </button>
              </div>
                            
                        </div>
                        <div className="col-lg-6 col-md-6 col-sm-7 col-7">
                            <div className="search">
                                <input type="text" placeholder="Search...!!!" />
                                <button type="button">
                                    <FaSearch/>
                                </button>
                            </div>
                        </div>
                        <div className="col-lg-3 col-md-3 col-sm-2 col-2">
                            <div className="header_cart">
                                <ul className="ms-auto">
                                    <li>
                                        <Link to="" className="d-none d-md-block">
                                            <FaRegHeart/> <span>0</span>
                                        </Link>
                                    </li>

                                    <li>
                                        <Link to="">
                                            <AiOutlineShoppingCart/> <span>0</span>
                                        </Link>
                                    </li>
                                    <li className="signin d-none d-md-block">
                                        {token? (
                                             <button>
                                             {name}
                                             {/* <FaRegUser/> */}
                                             <div className="submenu">
                                               <ul>
                                                
                                                   <li> <a href="">Thông tin cá nhân</a></li>
                                                   <li> <a href="">Lịch sử mua hàng</a></li>
                                                   <li> <a href="">Đăng xuất</a></li>
                                                   {/* <li> <a href="">Thể loại sách 3</a></li> */}
                                                 
                                               </ul>
                                             </div>
                                           </button>
                                        ) : (
                                            <Link to="/login">
                                            Đăng nhập
                                            {/* <FaRegUser/> */}
                                            </Link>
                                        )}
                                        
                                    </li>

                                    <li>
                                        <Link to="" className="d-md-none">
                                            <FaBars />
                                        </Link>
                                    </li>
                                </ul>
                            
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            {/* {
                openCategory && <DropDownCategory/>
            } */}
            
        </>
    )
}

export default Test;