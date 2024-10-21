import React from 'react'
import "./style.scss"
import { IoIosCheckmarkCircle } from "react-icons/io";

const OrderSuccess = () => {
  return (
    <div className='order-success-container'>
      <div className="order-success-con">
        <button className='checkmark-icon'><IoIosCheckmarkCircle /></button>
      

        <h5>Cảm ơn bạn đã đặt hàng</h5>


        <div className="order-actions">
          <button className="btn-order-details">Xem chi tiết đơn hàng</button>
          <button className="btn-continue-shopping">Tiếp tục mua sắm</button>
        </div>

        </div>
    </div>
  )
}

export default OrderSuccess