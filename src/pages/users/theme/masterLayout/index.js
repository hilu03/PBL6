import Footer from "../footer";
import Header from "../header";
import Test from "../header/test";
import { useLocation } from "react-router-dom";
import { CartProvider } from "context/CartContext";

const MasterLayout = ({ children, ...props }) => {
  const location = useLocation();

  const hideHeaderAndFooter =
    location.pathname === "/login" || location.pathname === "/register";

  return (
    <CartProvider>
      <div {...props}>
        {!hideHeaderAndFooter && <Test />}
        <main>{children}</main>
        {!hideHeaderAndFooter && <Footer />}
      </div>
    </CartProvider>
  );
};

export default MasterLayout;
