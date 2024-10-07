import "./style.scss";
import { AiOutlineShoppingCart } from "react-icons/ai";
import { FaRegHeart, FaSearch, FaRegUser } from "react-icons/fa";
import { Link, useNavigate  } from "react-router-dom";
// import { Formatter } from "utils/formatter";
import "bootstrap/dist/css/bootstrap.min.css";
import { useState, useEffect, useContext } from "react";
// import DropDownCategory from "component/user/dropdownCategory";
import { getCategory } from "services/user/bookService";
import { processLogout } from "services/user/userService";
import { BiCategory } from "react-icons/bi";
import logo from "../../../../assets/users/transparent.png";
import { CartContext } from "context/CartContext";

const Test = () => {
//   const [openCategory, setOpenCategory] = useState(false);
  const token = localStorage.getItem("token");
  const name = localStorage.getItem("name");
  console.log(name, token);
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();
  const { cartCount } = useContext(CartContext);

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

  const handleLogout = async () => {
    const token = localStorage.getItem('token');
    if (token) {
      try {
        await processLogout(token);
        localStorage.removeItem('token');
        localStorage.removeItem('name');
        navigate('/login');
      } catch (error) {
        console.error('Logout failed:', error);
      }
    } else {
      console.error('No token found');
    }
  };

  return (
    <>
      <div className="top">
        <div className="container">
          <div className="row">
            <div className="col-xl-2">
              <div className="logo">
                <img src={logo} alt="" />
              </div>
            </div>
            <div className="col-xl-1">
              <div className="category">
                <button type="button">
                  <BiCategory />

                  <div className="submenu">
                    <ul>
                      {categories && categories.length > 0 ? (
                        categories.map((category) => (
                          <li key={category.id}>
                            {" "}
                            {/* Sử dụng ID duy nhất nếu có */}
                            <a href="">{category.name}</a>{" "}
                            {/* Thay đổi theo thuộc tính thực tế */}
                          </li>
                        ))
                      ) : (
                        <li>no</li>
                      )}
                    </ul>
                  </div>
                </button>
              </div>
            </div>
            <div className="col-xl-6">
              <div className="search">
                <input type="text" placeholder="Search...!!!" />
                <button type="button">
                  <FaSearch />
                </button>
              </div>
            </div>
            <div className="col-xl-3">
              <div className="header_cart">
                <ul>
                  <li>
                    <Link to="">
                      <FaRegHeart /> <span>0</span>
                    </Link>
                  </li>

                  <li>
                    <Link to="/cart-items">
                      <AiOutlineShoppingCart />  <span>{cartCount}</span>
                    </Link>
                  </li>
                  <li className="signin">
                    {token ? (
                      <button>
                        {name}
                        {/* <FaRegUser/> */}
                        <div className="submenu">
                          <ul>
                            <li>    
                              <a href="">Thông tin cá nhân</a>
                            </li>
                            <li>
                              <a href="">Lịch sử mua hàng</a>
                            </li>
                            <li>
                              <a href="#" onClick={handleLogout}>Đăng xuất</a>
                            </li>
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
  );
};

export default Test;
