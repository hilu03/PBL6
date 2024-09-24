import React, { useRef } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './style.scss';
import InnerImageZoom from 'react-inner-image-zoom';
import 'react-inner-image-zoom/lib/InnerImageZoom/styles.css';
 
const ProductZoom = () => {
    const zoomSliderBig = useRef();
    const zoomSlider = useRef();
   
    const settings = {
        dots: false,
        infinite: false,
        speed: 700,
        slidesToShow: 1,
        slidesToScroll: 1,
        fade: false,
        arrows: false,
    };
 
    return (
        <>
            {/* <div className="container">
                <div className='row mt-2'>
                    <div className="col-md-3"> */}
                        <div className="productZoom">
                            <Slider {...settings} className='zoomSliderBig' ref={zoomSliderBig}>
                                <div className="item">
                                    <InnerImageZoom
                                        zoomType='hover' zoomScale={1}
                                        src={`https://product.hstatic.net/200000343865/product/ten-to-la-hy-vong_bia_9e8ce776f94c499ea570caf603a829b0_master.jpg`} />
                                </div>
                            </Slider>
                        </div>
                    {/* </div> */}
 
                    {/* <div className="col-md-9"></div> */}
                   
                {/* </div> */}
             {/* </div> */}
        </>
    )
}
 
export default ProductZoom;