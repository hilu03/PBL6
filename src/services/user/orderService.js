import axios from "./customize_axios";

export const getProvinces = async () => {
  try {
    const response = await axios.get("https://esgoo.net/api-tinhthanh/1/0.htm");
    return response.data;
  } catch (error) {
    console.error("Error fetching provinces:", error);
    return [];
  }
};

export const getDistricts = async (provinceId) => {
  try {
    const response = await axios.get(
      `https://esgoo.net/api-tinhthanh/2/${provinceId}.htm`
    );
    return response.data;
  } catch (error) {
    console.error(
      `Error fetching districts for province ${provinceId}:`,
      error
    );
    return [];
  }
};

export const getWards = async (districtId) => {
  try {
    const response = await axios.get(
      `https://esgoo.net/api-tinhthanh/3/${districtId}.htm`
    );
    return response.data;
  } catch (error) {
    console.error(`Error fetching wards for district ${districtId}:`, error);
    return [];
  }
};

export const addNewAddress = async (
  token,
  receiver,
  phoneNumber,
  address,
  city,
  district,
  ward,
  isDefault
) => {
  try {
    const response = await axios.post(
      "/api/user/add-address",
      {
        receiver,
        phoneNumber,
        address,
        city,
        district,
        ward,
        isDefault,
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error(`Error adding address`, error);
    return [];
  }
};

export const getAllMyAddress = async (token) => {
  try {
    const response = await axios.get("/api/user/address", {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });
    console.log(response);
    return response.data;
  } catch (error) {
    console.error("Error fetching your address:", error);
    return null;
  }
};

export const createCodOrder = async (token, items, paymentMethodID, shippingAddressID) => {
  try {
    const response = await axios.post(
      "/api/order/create",
      {
        items, // Mảng chứa các item với bookID và quantity
        paymentMethodID, // Phương thức thanh toán
        shippingAddressID, // Địa chỉ giao hàng
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // Token gửi trong headers
        },
      }
    );

    console.log("Order created successfully:", response.data);
    return response.data;

  } catch (error) {
    if (error.response) {
      // Kiểm tra lỗi chi tiết từ server
      console.error("Error response data:", error.response.data);
      console.error("Error status:", error.response.status);
      console.error("Error headers:", error.response.headers);
    } else if (error.request) {
      // Yêu cầu được gửi nhưng không có phản hồi từ server
      console.error("No response received:", error.request);
    } else {
      // Một lỗi khác đã xảy ra
      console.error("Error setting up request:", error.message);
    }
    throw error;
  }
};


export const createPaymentLink = async (token, items, paymentMethodID, shippingAddressID) => {
  try {
    console.log("Creating order with data:", {
      token,
      items,
      paymentMethodID,
      shippingAddressID,
    });

    const response = await axios.post(
      "/api/order/create",
      {
        items, // Mảng chứa các item với bookID và quantity
        paymentMethodID, // Phương thức thanh toán
        shippingAddressID, // Địa chỉ giao hàng
      },
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // Token gửi trong headers
        },
      }
    );

    console.log("Order with online payment successfully:", response.data);
    return response.data;
  } catch {

  }
}