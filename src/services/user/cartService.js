import axios from "./customize_axios";
export const processAddToCart = async (token, bookID, quantity) => {
  try {
    const response = await axios.post(
      "/api/cart",
      { bookID, quantity },
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      }
    );
    return response;
  } catch (error) {
    console.error("Error add to cart:", error);
    throw error;
  }
};

export const getDetailedCart = async (token) => {
  try {
    const response = await axios.get("/api/cart", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response)
    return response.data;
  } catch (error) {
    console.error("Error fetching cart details:", error);
    return null;
  }
};

export const removeItems = async (token, bookIDs) => {
  try {
    const response = await axios.delete("/api/cart", 
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        data: { bookIDs }  // Truyền dữ liệu bookIDs trong phần `data`
      }
    );
    console.log("Remove items response:", response);
    return response.data;
  } catch (error) {
    if (error.response) {
      console.error("Error removing items from cart, response status:", error.response.status);
      console.error("Error response data:", error.response.data);
      console.error("Error response headers:", error.response.headers);
    } else {
      console.error("Error removing items from cart:", error.message);
    }
    return null;
  }
};
