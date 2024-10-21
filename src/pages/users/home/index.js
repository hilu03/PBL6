import React from 'react';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import './style.scss';
import SliderComponent from 'component/user/slider';
import HotBook from 'component/user/hot_book';
import BestSellerBooks from 'component/user/best_seller';
import Ranking from 'component/user/ranking';

const HomePage = () => {
  return (
    <>
      <SliderComponent/>
      <HotBook/>
      <BestSellerBooks/>
      {/* <Ranking/> */}
    </>
  );
}

export default HomePage;