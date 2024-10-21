import HotBook from 'component/user/hot_book';
import HomePage from './pages/users/home';
import MasterLayout from './pages/users/theme/masterLayout';
import {ROUTERS} from './utils/router'
import {Routes, Route} from 'react-router-dom'
import BestSellerBooks from 'component/user/best_seller';
import LoginForm from 'component/user/LoginRegister';
import RegisterForm from 'component/user/Register/RegisterForm';
import ProductDetails from 'pages/users/product_details';
import CartItems from 'pages/users/cartItems';
import CheckCode from 'component/user/checkCode';
import ConfirmOrder from 'pages/users/confirmOrder';
import ProductListing from 'pages/users/product_listing';
import Personal_infor from 'component/user/personal_infor';
import PersonalInfor from 'component/user/personal_infor';
import { MdOutlineReorder } from 'react-icons/md';
import OrderSuccess from 'component/user/order_success';
// import Payment from 'component/user/payment_online';

import { CartProvider } from "context/CartContext";
import Payment_Online from 'component/user/payment_online/Payment_Online';



const renderUserRouter = () => {
    const userRouters = [
        {
            path: ROUTERS.USER.HOME,
            component: <HomePage/>
        },
        
        {
            path: ROUTERS.USER.PROFILE,
            component: <HomePage/>
        },

        {
            path: ROUTERS.USER.HOT_BOOK,
            component: <HotBook/>
        },

        {
            path: ROUTERS.USER.BEST_SELLER_BOOK,
            component: <BestSellerBooks/>
        },

        {
            path: ROUTERS.USER.SIGNIN,
            component: <LoginForm/>
        },

        {
            path: ROUTERS.USER.REGISTER,
            component: <RegisterForm/>
        },

        {
            path: ROUTERS.USER.PRODUCT_DETAILS,
            component: <ProductDetails/>
        },

        {
            path: ROUTERS.USER.CARTITEMS,
            component: <CartItems/>
        },

        {
            path: ROUTERS.USER.CONFIRMORDER,
            component: <ConfirmOrder/>
        },
        {
            path: ROUTERS.USER.PRODUCT_LISTING,
            component: <ProductListing/>
        },
        {
            path: ROUTERS.USER.PERSONAL_INFOR,
            component: <PersonalInfor/>
        },
        {
            path: ROUTERS.USER.ORDER_SUCCESS,
            component: <OrderSuccess/>
        },
        {
            path: ROUTERS.USER.PAYMENT_ONLINE,
            component: <Payment_Online/>
        },
    ]

    return (
        <MasterLayout>
            <CheckCode/>
            <Routes>
                {
                    userRouters.map((item, key) => (
                        <Route key={key} path={item.path} element={item.component} />
                    ))
                }
            </Routes>
        </MasterLayout>

    )
}
const RouterCustom = () => {
    return renderUserRouter();
};

export default RouterCustom;