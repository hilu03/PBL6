import React from "react"
import "./style.scss"
import { BiDollar } from "react-icons/bi";
import { FaSackDollar, FaBasketShopping, FaBook} from "react-icons/fa6";
import { FaUser } from "react-icons/fa";

import { PieChart } from '@mui/x-charts';
import TickPlacementBars from "../barChart";

const Dashboard = () => {
    const bookSalesData = [
        { id: 'Sách A', value: 500, label: 'Văn học việt nam', color: 'red' }, 
        { id: 'Sách B', value: 450, label: 'Sách công cụ Đoàn - Đội', color: 'blue'},
        { id: 'Sách C', value: 300, label: 'Sách công cụ Đoàn - Đội',color: 'orange' },
        { id: 'Sách s', value: 500, label: 'Lịch sử - truyền thống' }, 
        { id: 'Sách x', value: 450, label: 'Lịch sử - truyền thống'},
    ];
    return (
        <>
            <div className="dashboard">
                <div className="dashboardBoxWrapper d-flex mt-5">
                    <div className="dashboardBox col">
                        <div className="row w-100 m-0">
                            <div className="col-lg-4 d-flex align-items-center">
                                <div className="avatar-md rounded color1">
                                    <FaSackDollar/>
                                </div>
                            </div>
                            <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                                <p>Tổng doanh thu</p>
                                <h3 className="m-0">1234</h3>
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
                                <p>Tổng đơn hàng</p>
                                <h3 className="m-0">1234</h3>
                            </div>
                        </div>
                    </div>

                    <div className="dashboardBox col">
                        <div className="row w-100 m-0">
                            <div className="col-lg-4 d-flex align-items-center">
                                <div className="avatar-md rounded color3">
                                    <FaBook />
                                </div>
                            </div>
                            <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                                <p>Tổng sản phẩm</p>
                                <h3 className="m-0">1234</h3>
                            </div>
                        </div>
                    </div>

                    <div className="dashboardBox col">
                        <div className="row w-100 m-0">
                            <div className="col-lg-4 d-flex align-items-center">
                                <div className="avatar-md rounded color4">
                                    <FaUser/>
                                </div>
                            </div>
                            <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                                <p>Tổng khách hàng</p>
                                <h3 className="m-0">1234</h3>
                            </div>
                        </div>
                    </div>
                </div>
                {/* chart */}
                {/* <PieChart
                    margin={{ top: 0, bottom: 10, left: 10, right:10 }}
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
                          padding: 0,
                        },
                      }}
                    height={200}
                />

                <TickPlacementBars/> */}
            </div>
        </>
    )
}

export default Dashboard;