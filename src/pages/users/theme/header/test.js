import "./style.scss";
import { AiOutlineShoppingCart } from "react-icons/ai";
import { FaRegHeart, FaSearch, FaRegUser, FaBars } from "react-icons/fa";
import { Link, useNavigate } from "react-router-dom";
// import { Formatter } from "utils/formatter";
import "bootstrap/dist/css/bootstrap.min.css";
import { useState, useEffect, useContext } from "react";
// import DropDownCategory from "component/user/dropdownCategory";
import { getCategories } from "services/user/bookService";
import { processLogout } from "services/user/userService";
import { BiCategory } from "react-icons/bi";
import logo from "../../../../assets/users/transparent.png";
import { CartContext } from "context/CartContext";
import { generateSlug } from "utils/createSlug";


const Test = () => {

  const token = localStorage.getItem("token");
  const name = localStorage.getItem("name");
  const role = localStorage.getItem("role");
  const [categories, setCategories] = useState([]);
  // const [cartCount, setCartCount] = useState(0);
  const navigate = useNavigate();
  const { cartCount } = useContext(CartContext);
  const { fetchCartCount } = useContext(CartContext); 



  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await getCategories();
        setCategories(response.data);
        await fetchCartCount();
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };
    fetchCategories();
  }, []);

 

  const handleLogout = async () => {
    const token = localStorage.getItem("token");
    if (token) {
      try {
        await processLogout(token);
        localStorage.removeItem("token");
        localStorage.removeItem("name");

        navigate("/login");
      } catch (error) {
        console.error("Logout failed:", error);
      }
    } else {
      console.error("No token found");
    }
  };

  return (
    <>
      <div className="top">
        <div className="container">
          <div className="row">
            <div className="col-lg-2 col-md-2 col-sm-3 col-3">
              <div className="logo">
                <Link to="">
                  <img src={logo} alt="" />
                </Link>
              </div>
            </div>
            <div className="col-lg-1 col-md-1 d-none d-md-block">
              <div className="category">
                <button type="button">
                  <BiCategory />

                  <div className="submenu">
                    <ul>
                      <li>
                        <Link to={`/listing/alls`}>Tất cả sản phẩm</Link>
                      </li>
                      {categories && categories.length > 0 ? (
                        categories.map((category) => (
                          <li key={category.id}>

                            <Link to={`/listing/${generateSlug(category.name)}`}>{category.name}</Link>
                           
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
                <button type="button"
                  onClick={() => {navigate('/books')}}
                >
                  <FaSearch />
                </button>
              </div>
            </div>
            <div className="col-lg-3 col-md-3 col-sm-2 col-2">
              <div className="header_cart">
                <ul className="ms-auto">
                  <li>
                  <Link to="" className="d-none d-md-block">
                      <FaRegHeart /> <span>0</span>
                    </Link>
                  </li>

                  <li>
                    <Link to="/cart-items">
                      <AiOutlineShoppingCart /> <span>{cartCount}</span>
                    </Link>
                  </li>
                  <li className="signin d-none d-md-block">
                    {role === "user" ? (
                      <button>
                        {name}
                        {/* <FaRegUser/> */}
                        <div className="submenu">
                          <ul>
                          <li>
  <Link to="/personal-infor">Thông tin cá nhân</Link>
</li>
                            <li>
                              <a href="">Lịch sử mua hàng</a>
                            </li>
                            <li>
                              <a href="#" onClick={handleLogout}>
                                Đăng xuất
                              </a>
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
  );
};

export default Test;
