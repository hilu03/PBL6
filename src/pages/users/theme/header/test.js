import "./style.scss"
import {  AiOutlineShoppingCart } from "react-icons/ai";
import { FaRegHeart, FaSearch, FaRegUser } from "react-icons/fa";
import { Link } from "react-router-dom";
// import { Formatter } from "utils/formatter";
import 'bootstrap/dist/css/bootstrap.min.css';
import { useState, useEffect } from "react";
import DropDownCategory from "component/user/dropdownCategory";
import { getCategory } from "services/user/bookService";
import { BiCategory } from "react-icons/bi";



const Test = () => {
    const [openCategory, setOpenCategory] = useState(false)
    const userID = localStorage.getItem("userID")
    const name = localStorage.getItem("name")
    const [categories, setCategories] = useState([])

    useEffect(() => {
        const fetchCategories = async () => {
          try {
            const response = await getCategory();
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
                        <div className="col-xl-2">
                            <div className="logo">
                                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOWmskQa6rP-i3q3n6NeG1i7-Dc9YqnHgm0g&s" alt="" />   
                            </div>
                           
                        </div>
                        <div className="col-xl-1">
                        <div className="category">
                <button type="button" >
                  <BiCategory />
                
                    <div className="submenu">
                      <ul>
                        {categories.map((category) => (
                          <li key={category.id}>
                            {" "}
                            {/* Sử dụng ID duy nhất nếu có */}
                            <a href="">{category.name}</a>{" "}
                            {/* Thay đổi theo thuộc tính thực tế */}
                          </li>
                        ))}
                      </ul>
                    </div>
                  
                </button>
              </div>
                            
                        </div>
                        <div className="col-xl-6">
                            <div className="search">
                                <input type="text" placeholder="Search...!!!" />
                                <button type="button">
                                    <FaSearch/>
                                </button>
                            </div>
                        </div>
                        <div className="col-xl-3">
                            <div className="header_cart">
                                <ul>
                                    <li>
                                        <Link to="">
                                            <FaRegHeart/> <span>0</span>
                                        </Link>
                                    </li>

                                    <li>
                                        <Link to="">
                                            <AiOutlineShoppingCart/> <span>0</span>
                                        </Link>
                                    </li>
                                    <li className="signin">
                                        {userID? (
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

                                    {/* <li>
                                        <Link to="" onClick={() => setOpenCategory(
                                        (prev) => !prev)}>
                                            <FaBars />
                                        </Link>
                                    </li> */}
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