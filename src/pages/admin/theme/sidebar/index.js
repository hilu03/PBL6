import React, { useContext, useState } from "react"
import "./style.scss"
import { Link } from "react-router-dom"
import logo from "../../../../assets/users/transparent.png"
import { Button } from "@mui/material";
import { FaAngleRight, FaCartPlus, FaUserPlus } from "react-icons/fa";
import { FaBookBookmark, FaClipboardUser } from "react-icons/fa6";
import { RiDashboardHorizontalFill } from "react-icons/ri";
import { GiRoundStar } from "react-icons/gi";
import { MdFormatListBulletedAdd } from "react-icons/md";
import { TbBrandCashapp } from "react-icons/tb";
import { PiDiamondThin } from "react-icons/pi";
import { MyContext } from "../adminLayout";
const Sidebar = () => {
    const context = useContext(MyContext)

    const [activeTabs, setActiveTabs] = useState(0);
    const [isToggleSubmenu, setIsToggleSubmenu] = useState(true);

    const isOpenSubmenu = (index) => {
        if (activeTabs === index && isToggleSubmenu) {
            setIsToggleSubmenu(false);
        } 
        else {
            setActiveTabs(index);
            setIsToggleSubmenu(true);
        }
    };
    return (
        <div className="sidebar1 shadow">
            <div className="logoAdmin">
                <Link to="/admin">
                    <img src={logo} alt="" />
                </Link>
            </div>
            <div>
                <ul>
                    <li>
                        <Link to="/admin">
                            <Button 
                                className={`w-100 ${activeTabs===0  ? 'active' : ''}`}
                                onClick={() => isOpenSubmenu(0)}
                            >
                                <span className="icon"><RiDashboardHorizontalFill/></span>
                                Dashboard
                            </Button>
                        </Link>
                    </li>

                    <li>
                        <Button 
                            className={`w-100 ${activeTabs===1 ? 'active' : ''}`}
                            onClick={() => isOpenSubmenu(1)}
                        >
                            <span className="icon"><FaBookBookmark/></span>
                            Quản lý sách
                            <span className="arrow ms-auto"><FaAngleRight/></span>
                        </Button>

                        <div className={`submenuWrapper ${activeTabs===1 &&isToggleSubmenu===true ? 'openSubmenu' : 'closeSubmenu'}`}>
                            <ul className="submenu1">
                                <li className="mb-1 d-flex">
                                    <PiDiamondThin />
                                    <Link to="/admin/list-book/alls">Kho sách</Link>
                                </li>
                                <li className="mb-1 d-flex">
                                    <PiDiamondThin />
                                    <Link to="/admin/add-book">Thêm mới sách</Link>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li>
                        <Link to="/admin/list-category">
                            <Button 
                                className={`w-100 ${activeTabs===2  ? 'active' : ''}`}
                                onClick={() => isOpenSubmenu(2)}
                            >
                                <span className="icon"><MdFormatListBulletedAdd/></span>
                                Thể loại
                            </Button>
                        </Link>
                    </li>

                    <li>
                        <Button 
                            className={`w-100 ${activeTabs===3 &&isToggleSubmenu===true ? 'active' : ''}`}
                            onClick={() => isOpenSubmenu(3)}
                        >
                            <span className="icon"><FaClipboardUser/></span>
                            Tác giả
                            <span className="arrow ms-auto"><FaAngleRight/></span>
                        </Button>

                        <div className={`submenuWrapper ${activeTabs===3 &&isToggleSubmenu===true ? 'openSubmenu' : 'closeSubmenu'}`}>
                            <ul className="submenu1">
                            <li className="mb-1 d-flex">
                                    <PiDiamondThin />
                                    <Link to="#">Thêm mới tác giả</Link>
                                </li>
                                <li className="mb-1 d-flex">
                                    <PiDiamondThin />
                                    <Link to="#">Danh sách tác giả</Link>
                                </li>
                            </ul>
                        </div>
                    </li>

                    <li>
                        <Link to="/admin/list-order">
                            <Button 
                                className={`w-100 ${activeTabs===4 &&isToggleSubmenu===true ? 'active' : ''}`}
                                onClick={() => isOpenSubmenu(4)}
                            >
                                <span className="icon"><FaCartPlus/></span>
                                Đơn hàng
                            </Button>
                        </Link>
                    </li>

                    <li>
                        <Link>
                            <Button 
                                className={`w-100 ${activeTabs===5 &&isToggleSubmenu===true ? 'active' : ''}`}
                                onClick={() => isOpenSubmenu(5)}
                            >
                                <span className="icon"><FaUserPlus/></span>
                                Khách hàng
                                <span className="arrow ms-auto"><FaAngleRight/></span>
                            </Button>
                        </Link>
                    </li>

                    <li>
                        <Link>
                            <Button 
                                className={`w-100 ${activeTabs===6 &&isToggleSubmenu===true ? 'active' : ''}`}
                                onClick={() => isOpenSubmenu(6)}
                            >
                                <span className="icon"><GiRoundStar/></span>
                                Đánh giá
                                <span className="arrow ms-auto"><FaAngleRight/></span>
                            </Button>
                        </Link>
                    </li>

                    <li>
                        <Link>
                            <Button 
                                className={`w-100 ${activeTabs===7 &&isToggleSubmenu===true ? 'active' : ''}`}
                                onClick={() => isOpenSubmenu(7)}
                            >
                                <span className="icon"><TbBrandCashapp/></span>
                                Doanh thu
                                <span className="arrow ms-auto"><FaAngleRight/></span>
                            </Button>
                        </Link>
                    </li>

                </ul>
            </div>
        </div>

    )
}

export default Sidebar;