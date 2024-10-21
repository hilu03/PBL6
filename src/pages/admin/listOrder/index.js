import React from "react"
import "./style.scss"
import { FaBasketShopping, FaRegClock } from "react-icons/fa6";
import { BsCartX } from "react-icons/bs";
import { TbTruckDelivery } from "react-icons/tb";

const OrderList = () => {
    return (
        <>
            <div className="OrderList">
                <div className="dashboardBoxWrapper d-flex mt-5">
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
                                <div className="avatar-md rounded color1">
                                    <TbTruckDelivery/>
                                </div>
                            </div>
                            <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                                <p>Đang vận chuyển</p>
                                <h3 className="m-0">1234</h3>
                            </div>
                        </div>
                    </div>

                    <div className="dashboardBox col">
                        <div className="row w-100 m-0">
                            <div className="col-lg-4 d-flex align-items-center">
                                <div className="avatar-md rounded color4">
                                    <FaRegClock />
                                </div>
                            </div>
                            <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                                <p>Chờ thanh toán</p>
                                <h3 className="m-0">1234</h3>
                            </div>
                        </div>
                    </div>

                    <div className="dashboardBox col">
                        <div className="row w-100 m-0">
                            <div className="col-lg-4 d-flex align-items-center">
                                <div className="avatar-md rounded color5">
                                    <BsCartX/>
                                </div>
                            </div>
                            <div className="col-lg-8 text-end flex-col align-items-center pe-3">
                                <p>Đơn hàng đã huỷ</p>
                                <h3 className="m-0">1234</h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}

export default OrderList;