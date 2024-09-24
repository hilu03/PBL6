import React from 'react';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './style.scss';
import SliderComponent from 'component/user/slider';
import HotBook from 'component/user/hot_book';
import BestSellerBooks from 'component/user/best_seller';

const HomePage = () => {
  return (
    <>
      <SliderComponent/>
      <HotBook/>
      <BestSellerBooks/>
    </>
  );
}

export default HomePage;