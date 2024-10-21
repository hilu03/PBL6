import React, { useEffect } from "react"
import { useRef } from "react";
import { useState } from "react";
import "./style.scss"
import MenuItem from '@mui/material/MenuItem';
import { useTheme } from '@mui/material/styles';
import Select from '@mui/material/Select';
import OutlinedInput from '@mui/material/OutlinedInput';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import PriceInput from "component/admin/price_input";
import { Button } from "@mui/material";
import { getCategories, getTargets } from "services/user/bookService";

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
    disableScrollLock: true,
};
  
function getStyles(name, targets, theme) {
    return {
        fontWeight: targets.includes(name)
        ? theme.typography.fontWeightMedium
        : theme.typography.fontWeightRegular,
    };
}
const AddBook = () => {
    const theme = useTheme();
    const priceInputRef = useRef();
    const [categories, setCategories] = useState([]);
    const [targets, setTargets] = useState([])

    const [categoryVal, setCategoryVal] = useState(0);
    const [targetsVal, setTargetsVal] = useState([]);
    const [priceOrigin, setPriceOrigin] = useState('');
    const [imageUrl, setImageUrl] = useState('');

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

    //targets
    useEffect(() => {
        const fetchTargets = async () => {
            try {
                const response = await getTargets();
                setTargets(response.data); 
            } catch (error) {
                console.error("Error fetching categories:", error);
            }
        };

        fetchTargets();
    }, [])

    const handleInputChangeImg = (e) => {
        setImageUrl(e.target.value);
      };

    const handlePriceChange = (value) => {
        setPriceOrigin(value); 
    };

    const handleChangeCate = (event) => {
        setCategoryVal(event.target.value);
    };

    const handleChangeTarget = (event) => {
        const {
          target: { value },
        } = event;
        setTargetsVal(
          typeof value === 'string' ? value.split(',') : value,
        );
        console.log(value)
    };
  
    return (
        <form action="" className="form">
            <div className="card border-0 p-4 mt-5">
                <h5>Hình ảnh của sách</h5>
                <hr />

                <div className="form-group-add_book">
                    <h6>Hình ảnh</h6>
                    <input
                        type="text"
                        placeholder="Nhập URL hình ảnh"
                        value={imageUrl} 
                        onChange={handleInputChangeImg}
                    />
                    
                    {imageUrl && ( 
                        <div className="d-flex justify-content-center mt-3" style={{ height: '400px' }}>
                        <img
                            src={imageUrl}
                            alt="Link hình ảnh không hợp lệ"
                            style={{ 
                                maxWidth: '100%', 
                                height: '100%', 
                                objectFit: 'cover',
                            }}
                        />
                        </div>
                    )}
                </div>
            </div>

            <div className="card border-0 p-4 mt-4">
                <h5>Thông tin của sách</h5>
                <hr />

                <div className="form-group-add_book">
                    <h6>Tiêu đề</h6>
                    <input type="text" placeholder="Tên tiêu đề sách"/>
                </div>

                <div className="form-group-add_book">
                    <h6>Mô tả</h6>
                    <textarea rows={5} cols={10} placeholder="Mô tả sơ lược nội dung về sách..."/>
                </div>

                <div className="row">
                    <div className="col-6">
                        <div className="form-group-add_book">
                            <h6>Thể loại</h6>
                            <Select
                                className="custom-select"
                                value={categoryVal}
                                onChange={handleChangeCate}
                                displayEmpty
                                inputProps={{ 'aria-label': 'Without label' }}
                                MenuProps={{
                                    disableScrollLock: true,
                                    PaperProps: {
                                        style: {
                                            maxHeight: 200, 
                                            overflowY: 'auto',
                                        },
                                    },
                                }}
                                sx={{
                                    '&.MuiOutlinedInput-root': {
                                        // '& fieldset': {
                                        //     borderColor: 'red',
                                        // },
                                        '&:hover fieldset': {
                                            borderColor: '#9d2ff7',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#9d2ff7',
                                        },
                                    },
                                }}
                            >
                                <MenuItem disabled value={0}>
                                    <em>Thể loại</em>
                                </MenuItem>

                                {categories && categories.length > 0 ? (
                                    categories.map(category => (
                                        <MenuItem value={category.id}>{category.name}</MenuItem>
                                    ))
                                ) : (
                                    <MenuItem disabled>No categories available</MenuItem>
                                )}
                            </Select>
                        </div>
                    </div>

                    <div className="col-6">
                        <div className="form-group-add_book">
                            <h6>Đối tượng</h6>
                            <Select
                                className="custom-select"
                                multiple
                                displayEmpty
                                value={targetsVal}
                                onChange={handleChangeTarget}
                                input={<OutlinedInput />}
                                renderValue={(selected) => {
                                    if (selected.length === 0) {
                                        return <em>Chọn 1 hoặc nhiều đối tượng</em>;
                                    }
                                
                                    return selected.map(id => {
                                        const target = targets.find(t => t.id === id);
                                        return target ? target.name : '';
                                    }).join(', ');
                                }}
                                MenuProps={MenuProps}
                                // style={{ width: '100%', maxWidth: '500px' }}
                                sx={{
                                    '&.MuiOutlinedInput-root': {
                                        // '& fieldset': {
                                        //     borderColor: 'red',
                                        // },
                                        '&:hover fieldset': {
                                            borderColor: '#9d2ff7',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#9d2ff7',
                                        },
                                    },
                                }}
                                inputProps={{ 'aria-label': 'Without label' }}
                                >
                                <MenuItem disabled value="">
                                    <em>Chọn 1 hoặc nhiều đối tượng</em>
                                </MenuItem>
                                {targets && targets.length > 0 ? (
                                    targets.map((target) => (
                                        <MenuItem
                                            key={target.id}
                                            value={target.id}
                                            style={getStyles(target.name, targetsVal, theme)}
                                        >
                                            {target.name}
                                        </MenuItem>
                                    )) 
                                    ) : (
                                        <MenuItem disabled>No categories available</MenuItem>
                                    )
                                }
                            </Select>
                        </div>
                    </div>

                </div>

                <div className="row">
                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Tác giả</h6>
                            <input type="text" placeholder="Số lượng sách hiện có trong kho"/>
                        </div>
                    </div>

                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Nhà xuất bản</h6>
                            <input type="text" placeholder="Nhà xuất bản"/>
                        </div>
                    </div>
                </div>

                <div className="row mt-1">
                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Số lượng sách</h6>
                            <input type="number" placeholder="Số lượng sách hiện có trong kho" min="0"/>
                        </div>
                    </div>

                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Số trang</h6>
                            <input type="number" placeholder="Số trang sách" min="0"/>
                        </div>
                    </div>
                    
                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Định dạng</h6>
                            <input type="text" placeholder="Ex: bìa mềm, bìa cứng,..."/>
                        </div>
                    </div>
                </div>

                <div className="row">
                    <div className="col">
                        <h6 className="mb-0">Ngày phát hành</h6>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <DemoContainer components={['DatePicker']}>
                                <DatePicker 
                                    format="DD/MM/YYYY"
                                    sx={{
                                        width: '100%',
                                        height: 40,
                                        '& .MuiInputBase-input': {
                                            height: '40px',
                                            padding: '0px 10px',
                                            borderRadius: '8px'
                                        },
                                        '&:hover': {
                                            '& .MuiOutlinedInput-notchedOutline': {
                                            borderColor: '#9d2ff7',
                                            },
                                        },
                                    }}   
                                />
                            </DemoContainer>
                        </LocalizationProvider>
                    </div>


                    <div className="col">  
                        <div className="form-group-add_book">
                            <h6>Khuôn khổ</h6>
                            <input type="text" placeholder="11x13cm"/>
                        </div>

                    </div>
                </div>
            </div>

            <div className="card border-0 p-4 mt-4 mb-5">
                <h5>Thông tin về giá</h5>
                <hr />

                <div className="row">
                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Giá gốc</h6>
                            <PriceInput ref={priceInputRef} onChange={handlePriceChange} />
                        </div>
                    </div>
                    
                    <div className="col">
                        <div className="form-group-add_book">
                            <h6>Giá khi giảm giá</h6>
                            <PriceInput/>
                        </div>
                    </div>
                </div>
            </div>

            <Button className="addSubmit" type="submit">Thêm sách</Button>
        </form>
    )
}

export default AddBook;