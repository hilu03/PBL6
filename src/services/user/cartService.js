import axios from './customize_axios';
export const processAddToCart = async (token, bookID, quantity) => {
 
    try {
      const response = await axios.post('/api/cart',
        { bookID, quantity },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );
      console.log(response)
      return response;
    } catch (error) {
      console.error('Error logging in:', error);
      throw error;
    }
  };