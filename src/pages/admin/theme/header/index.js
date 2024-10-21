import React, { useContext } from "react"
import "./style.scss"
import { MdMenuOpen } from "react-icons/md";
import { FiSearch } from "react-icons/fi";
import { TiMessage } from "react-icons/ti";
import { IoMdNotificationsOutline } from "react-icons/io";
import { useState } from "react";
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import PersonAdd from '@mui/icons-material/PersonAdd';
import Settings from '@mui/icons-material/Settings';
import Logout from '@mui/icons-material/Logout';
import { MyContext } from "../adminLayout";
import { MdOutlineMenu } from "react-icons/md";
const Header = () => {
    const context = useContext(MyContext);
    // const token = localStorage.getItem("token");
    const name = localStorage.getItem("name");
    const role = localStorage.getItem("role");

    const [anchorEl, setAnchorEl] = useState(null);
    const open = Boolean(anchorEl);
    const handleOpenMyAccDrop = (event) => {
        setAnchorEl(event.currentTarget);
    };
    const handleCloseMyAccDrop = () => {
        setAnchorEl(null);
    }
    return (
        <>
            <header>
                <div className="w-100 d-flex">
                    <div className="col-md-3 d-flex align-items-center part1">
                        <div onClick={() => context.setIsToggleSidebar(!context.isToggleSidebar)}>
                            {
                                context.isToggleSidebar===false ? <MdMenuOpen /> : <MdOutlineMenu/>
                            }
                        </div>
                        <div className="searchBox d-flex align-items-center">
                            <FiSearch className="me-1"/>
                            <input type="text" placeholder="Search!!!" />
                        </div>
                    </div>

                    <div className="col-md-9 d-flex align-items-center justify-content-end part2">
                        <TiMessage/>
                        <IoMdNotificationsOutline/>
                        <div className="myAccWrapper">
                            <div 
                                className="myAcc d-flex align-center-items"
                                onClick={handleOpenMyAccDrop}
                            >
                                <div className="userImg">
                                    <span className="rounded-circle">
                                        <img src="https://img.freepik.com/premium-photo/stylish-man-flat-vector-profile-picture-ai-generated_606187-310.jpg" alt="" />

                                    </span>
                                </div>

                                <div className="userInfo">
                                    {role==="admin" ? (
                                        <h6>Hi, {name}!</h6>
                                    ): (
                                        <h6>Hi, !</h6>
                                    )}
                                </div>
                            </div>

                            <Menu
                                anchorEl={anchorEl}
                                id="account-menu"
                                open={open}
                                onClose={handleCloseMyAccDrop}
                                onClick={handleCloseMyAccDrop}
                                slotProps={{
                                    paper: {
                                      elevation: 0,
                                      sx: {
                                        overflow: 'visible',
                                        filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
                                        mt: 1.5,
                                        '& .MuiAvatar-root': {
                                          width: 32,
                                          height: 32,
                                          ml: -0.5,
                                          mr: 1,
                                        },
                                        '&::before': {
                                          content: '""',
                                          display: 'block',
                                          position: 'absolute',
                                          top: 0,
                                          right: 14,
                                          width: 10,
                                          height: 10,
                                          bgcolor: 'background.paper',
                                          transform: 'translateY(-50%) rotate(45deg)',
                                          zIndex: 0,
                                        },
                                      },
                                    },
                                  }}
                                transformOrigin={{ horizontal: 'right', vertical: 'top' }}
                                anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
                            >
                                <MenuItem onClick={handleCloseMyAccDrop}>
                                    <ListItemIcon>
                                        <PersonAdd fontSize="small" />
                                    </ListItemIcon>
                                    Account
                                </MenuItem>
                                    <MenuItem onClick={handleCloseMyAccDrop}>
                                    <ListItemIcon>
                                        <Settings fontSize="small" />
                                    </ListItemIcon>
                                    Reset Password
                                </MenuItem>
                                <MenuItem onClick={handleCloseMyAccDrop}>
                                    <ListItemIcon>
                                        <Logout fontSize="small" />
                                    </ListItemIcon>
                                    Logout
                                </MenuItem>
                            </Menu>
                        </div>
                    </div>
                </div>
            </header>
        </>
    )
}

export default Header;