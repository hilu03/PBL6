import React from "react";
import "./style.scss"
import { useState } from "react";
import { useEffect } from "react";
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
import { Category } from "@mui/icons-material";
import Top5CategoriesChart from "component/admin/barChart";
import { PieChart } from "@mui/x-charts";
import { FaSackDollar, FaBasketShopping, FaBook} from "react-icons/fa6";
import { FaUser } from "react-icons/fa";
import { TbCategoryPlus } from "react-icons/tb";

const CategoryList = () => {
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


    return (
        <div className="CategoryList">
            <div className="dashboardBoxWrapper d-flex mt-5">
                <div className="dashboardBox col">
                    <div className="row w-100 m-0">
                        <div className="col-lg-4 d-flex align-items-center">
                            <div className="avatar-md rounded color3">
                                <TbCategoryPlus />
                            </div>
                        </div>
                        <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                            <p>Tổng thể loại</p>
                            <h3 className="m-0">{categories.length}</h3>
                        </div>
                    </div>
                </div>

                <div className="dashboardBox col">
                    <div className="row w-100 m-0">
                        <div className="col-lg-4 d-flex align-items-center">
                            <div className="avatar-md rounded color2">
                                <FaBasketShopping/>
                            </div>
                        </div>  
                        <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                            <p>Tổng lượt bán</p>
                            <h3 className="m-0">1234</h3>
                        </div>
                    </div>
                </div>
            </div>
            
            <div className="card border-0 p-4 mt-5">
                <h5>Top 5 thể loại có nhiều lượt bán</h5>
                <hr />
                <Top5CategoriesChart className="barChart"/>
                {/* <PieChart
                    series={[
                        {
                            data: bookSalesData,
                            highlightScope: { fade: 'global', highlight: 'item' },
                            // faded: { innerRadius: 30, additionalRadius: -30, color: 'gray' },
                        },
                    ]}
                    slotProps={{
                        legend: {
                          direction: 'column',
                          position: { vertical: 'middle', horizontal: 'right' },
                          padding: 30,
                        },
                      }}
                    height={200}
                /> */}
            </div>
            <div className="card border-0 p-4 mt-5 mb-5">
                <div className="headCard d-flex">
                    <h5>Thể loại</h5>
                    <Link to="/admin/add-book" className="ms-auto"><Button >Thêm mới thể loại</Button></Link>
                </div>
                <hr />

                <div className="table-responsive mt-3">
                    <table className="table table-bordered v-align">
                        <thead className="thead-dark">
                            <tr>
                                <th>ID</th>
                                <th>THỂ LOẠI</th>
                                <th>SỐ LƯỢNG SÁCH</th>
                                <th>ĐƠN HÀNG</th>
                                <th>DOANH THU</th>
                                <th>ACTION</th>
                            </tr>
                        </thead>

                        <tbody>
                            {categories && categories.length > 0 ? (
                                categories.sort((a, b) => a.id - b.id)
                                    .map(category => (
                                    <tr key={category.id}>
                                        <td>{category.id}</td>
                                        <td>
                                            <div className="d-flex productBox">
                                                <h6>{category.name}</h6>
                                            </div>
                                        </td>
                                        <td>{category.quantity}</td>
                                        <td>500</td>
                                        <td>
                                            <span className="new text-danger">{Formatter(210000000)}</span>
                                        </td>
                                        <td>
                                            <div className="actions d-flex align-items-center justify-content-center">
                                                <Button className="secondary" color="secondary"><BsPencil /></Button>
                                                <Button className="error" color="error"> <RiDeleteBin6Line /></Button>
                                            </div>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    
                                </tr>
                            )}
                        </tbody>
                    </table>

                    <div className="d-flex tableFooter">
                        <Pagination className="ms-auto" count={1} color="secondary" />
                    </div>
                </div>
            </div>
        </div>
    )
}


export default CategoryList;
