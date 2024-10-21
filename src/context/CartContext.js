import React, { createContext, useState } from "react";
import { getDetailedCart } from "services/user/cartService";
import { processAddToCart } from "services/user/cartService";
import { useEffect } from "react";

export const CartContext = createContext();
export const CartProvider = ({ children }) => {
  const [cartCount, setCartCount] = useState(0);
  const token = localStorage.getItem("token");
  

  const fetchCartCount = async () => {
    // setCartCount(0); 
    try {
      const cartData = await getDetailedCart(token);
      if (cartData) {
        setCartCount(cartData.data.totalDistinctItems);
      }
    } catch (error) {
      console.error("Error fetching cart details:", error);
    }
  };

  // useEffect(() => {

  //   fetchCartCount(); 
  // }, []);

  const addToCart = async (bookID, quantity) => {
    try {
      const response = await processAddToCart(token, bookID, quantity);
      if (response) {
        await fetchCartCount(); 
      }
    } catch (error) {
      console.error("Error adding to cart:", error);
    }
  };

  return (
    <CartContext.Provider value={{ cartCount, addToCart, fetchCartCount }}>
      {children}
    </CartContext.Provider>
  );
};
