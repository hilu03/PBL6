import axios from 'axios';
export const processLogin = async (email, password) => {
 
  console.log(password)
    try {
      const response = await axios.post('http://localhost:8080/api/login', { 
          email,
          password,

      });
      console.log(response)
      return response;
    } catch (error) {
      console.error('Error logging in:', error);
      throw error;
    }
  };
  
export const registerUser = async (fullName, email, phoneNumber, password) => {
    try {
      const response = await axios.post('http://localhost:8080/api/sign-up', { 
        fullName,
        phoneNumber,
        email,
        password,
        "role": "user"
      });
      return response; 
    } catch (error) {
      console.error('Error registering:', error);
      throw error; 
    }
  };