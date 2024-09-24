import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import './style.scss'

const DropDownCategory = () => {
    return (
        <>
            <div className="flex flex-col dropDownCategory">
                <ul className="flex flex-col gap-4">
                    <li><a href="#=">Văn học tổng hợp</a></li>
                    <li><a href="#=">Văn học nước ngoài</a></li>
                    <li><a href="#=">văn học việt nam</a></li>
                </ul>
            </div>
        </>
    )
}

export default DropDownCategory;