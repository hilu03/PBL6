import { useState } from "react";
import "./style.scss"
import { AiOutlineFacebook, AiOutlineGlobal, AiOutlineInstagram, AiOutlineLinkedin, AiOutlineMail, AiOutlineShoppingCart, AiOutlineUser } from "react-icons/ai";
import { FaRegHeart, FaBars } from "react-icons/fa";
import { Link } from "react-router-dom";
import { Formatter } from "utils/formatter";
import { ROUTERS } from "utils/router";
const Header = () => {
    const [menus, setMenus] = useState([
        {
            name: "Trang chủ",
            path: ROUTERS.USER.HOME
        },
        {
            name: "Cửa hàng",
            path: ROUTERS.USER.PRODUCT
        },
        {
            name: "Sản phẩm",
            path: "",
            isShowSubmenu: false,
            child: {
                name: "",
                path: ""
            }
        },
        {
            name: "Bài viết",
            path: ROUTERS.USER.HOME
        },
        {
            name: "Liên hệ",
            path: ROUTERS.USER.HOME
        },
    ])

    return (
        // viền ở ngoài
        <>
            <div className="header_top">
                {/* trái, phải, tạo cái ở trong */}
                <div className="container"> 
                    <div className="row">
                        <div className="col-6 header_top_left">
                            <ul>
                                <li>
                                    <AiOutlineMail/>
                                    zkai@gmail.com
                                </li>
                                <li>Biết zkai là ai không? {Formatter(24121008)}</li>
                            </ul>
                        </div>
                        <div className="col-6 header_top_right">
                            <ul>
                                    <li>
                                        <a href="https://www.youtube.com/watch?v=DuzuhELUZw8" target="blank">
                                            <AiOutlineFacebook/>
                                        </a>
                                    </li>
                                <li>
                                    <Link to="">
                                        <AiOutlineInstagram/>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="">
                                        <AiOutlineLinkedin/>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="">
                                        <AiOutlineGlobal/>
                                    </Link>
                                </li>
                                <li>
                                    <Link to="">
                                        <AiOutlineUser/>
                                    </Link>
                                    <span>Đăng nhập</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div className="container">
                <div className="row">
                    {/* LOGO */}
                    <div className="col-xl-3">
                        <div className="header_logo">
                            <h1>LGK</h1>
                        </div>
                    </div>
                    {/* MENU */}
                    <div className="col-xl-6">
                        <nav className="header_menu">
                            <ul>
                                {/* nếu menus có tồn tại thì thực hiện hàm map
                                Hàm map giống như for. nó thực hiện vòng lặp 
                                ở đây truyền vào menu là phân tử của mảng menus và menuKey giống như index như 1 chỉ số của vòng lặp
                                */}
                                {menus?.map((menu, menuKey) => (
                                    <li key={menuKey} className={menuKey === 0 ? "active" : ""}>
                                        <Link to={menu.path}>{menu.name}</Link>
                                    </li>
                                ))}
                            </ul>
                        </nav>
                    </div>

                    <div className="col-xl-3">
                        <div className="header_cart">
                            {/* <div className="header_cart_price">
                                <span>{Formatter(2412)}</span>
                            </div> */}
                            <ul>
                                <li>
                                    <Link to="">
                                        <FaRegHeart/> <span>2</span>
                                    </Link>
                                </li>

                                <li>
                                    <Link to="">
                                        <AiOutlineShoppingCart/> <span>2</span>
                                    </Link>
                                </li>

                                <li>
                                    <Link to="">
                                        <FaBars />
                                    </Link>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Header;