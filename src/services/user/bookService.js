import axios from 'axios';

export const getHotBooks = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/books/hot-books');
        return response.data;
    } 
    catch (error) {
        console.error(error);
        return [];
    }
};

export const getBestSellerBooks = async () => {
    try {
        const response = await axios.get('http://localhost:8080/api/books/sale-books');
        return response.data;
    } 
    catch (error) {
        console.error(error);
        return [];
    }
};

export const getBookByID = async (bookID) => {
    try {
        const response = await axios.get(`http://localhost:8080/api/books/${bookID}`)
        console.log(response.data)
        return response.data
    } catch (error) {
        console.error(error);
        return [];
    }
}

export const getCategory = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/categories');
      return response.data; 
    }
    catch (error) {
        console.error(error);
        return [];
    }
  };






