import Footer from "../footer";
import Header from "../header"
import Test from "../header/test";
import { useLocation } from 'react-router-dom';


// const MasterLayout = ({children, ...props}) => {
//     return (
//         <div {...props}>
//             <Test/>
//             {children}
//             <Footer/>
//         </div>
//     );
// }

const MasterLayout = ({ children, ...props }) => {
    const location = useLocation();

    const hideHeaderAndFooter = location.pathname === '/login' || location.pathname === '/register';

    return (
        <div {...props}>
            {/* Nếu không phải trang đăng nhập, hiển thị Header và Footer */}
            {!hideHeaderAndFooter && <Test />}
            <main>{children}</main>
            {!hideHeaderAndFooter && <Footer />}
        </div>
    );
};


export default MasterLayout;