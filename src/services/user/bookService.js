import axios from "./customize_axios";

export const getHotBooks = async () => {
    try {
        const response = await axios.get('/api/books/hot-books');
        return response.data;
    } 
    catch (error) {
        console.error(error);
        return [];
    }
};

export const getBestSellerBooks = async () => {
    try {
        const response = await axios.get('/api/books/sale-books');
        return response.data;
    } 
    catch (error) {
        console.error(error);
        return [];
    }
};

export const getBookByID = async (bookID) => {
    try {
        const response = await axios.get(`/api/books/${bookID}`)
        return response.data
    } catch (error) {
        console.error(error);
        return [];
    }
}

export const getCategories = async () => {
    try {
      const response = await axios.get('/api/categories');
      return response.data; 
    }
    catch (error) {
        console.error(error);
        return [];
    }
};

export const getBookByCategoryID = async (categoryID, page) => {
    try {
        const response = await axios.get(`/api/books/category/${categoryID}?page=${page}`)
        return response.data
    } catch (error) {
        console.log(error)
        return []
    }
}

export const getAllBooks = async (page) => {
    try {
        const response = await axios.get(`/api/books?page=${page}`)
        return response.data
    } catch (error) {
        console.log(error)
        return []
    }
}

export const getBookRandomByCategory = async (categoryID) => {
    try {
        const response = await axios.get(`/api/books/random/${categoryID}`)
        return response.data
    } catch (error) {
        console.log(error)
        return []
    }
}

export const getTargets = async () => {
    try {
        const response = await axios.get('/api/targets')
        return response.data
    } catch (error) {
        console.log(error)
        return []
    }
}






