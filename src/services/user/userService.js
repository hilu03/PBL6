import axios from './customize_axios';
export const processLogin = async (email, password) => {
 
  console.log(password)
    try {
      const response = await axios.post('/api/login', { 
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
      const response = await axios.post('/api/sign-up', { 
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

  export const processLoginGG = async (code) => {
    try {
      const response = await axios.post(`/api/login/google?code=${code}`);
      console.log("Response from server:", response); 
      return response;
    } catch (error) {
      console.error('Error logging in:', error);
      throw error;
    }
  };


  export const processLogout = async (token) => {
    try {
      const response = await axios.post('/api/log-out', { 
        token
      });
      console.log(response)
      return response; 
     
    } catch (error) {
      console.error(error);
      throw error; 
    }
  };