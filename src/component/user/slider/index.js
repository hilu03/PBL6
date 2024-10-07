import React from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './style.scss';

const SliderComponent = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    adaptiveHeight: true,
    autoplay: true, 
    autoplaySpeed: 3000,
    pauseOnHover: false,
  };

  return (
    <div className="image-slider-container">
      <Slider {...settings}>
        <div>
          <img src="https://theme.hstatic.net/200000343865/1001052087/14/ms_banner_img4.jpg?v=1379" alt="" />
        </div>
        <div>
          <img src="https://theme.hstatic.net/200000343865/1001052087/14/ms_banner_img5.jpg?v=1385" alt="" />
        </div>
        <div>
          <img src="https://theme.hstatic.net/200000343865/1001052087/14/ms_banner_img4.jpg?v=1412" alt="" />
        </div>
        <div>
          <img src="https://theme.hstatic.net/200000343865/1001052087/14/ms_banner_img1.jpg?v=1412" alt="" />
        </div>
      </Slider>
    </div>
  );
}

export default SliderComponent;