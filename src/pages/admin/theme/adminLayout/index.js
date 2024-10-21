import Sidebar from "../sidebar";
import "./style.scss"
import Header from "../header";
import { createContext, useEffect, useState } from "react";

const MyContext = createContext();
const AdminLayout = ({ children, ...props }) => {
  const [isToggleSidebar, setIsToggleSidebar] = useState(false);
  const values = {
    isToggleSidebar,
    setIsToggleSidebar
  }
  
  // useEffect(() => {
  //   alert(isToggleSidebar)
  // }, [isToggleSidebar])
  return (
    <>
      <MyContext.Provider value={values}>
        <div className={`sidebarWrapper1 ${isToggleSidebar===true ? 'toggle' : ''}`}>
          <Sidebar/>
        </div>
        <div className={`contentWrapper ${isToggleSidebar===true ? 'toggle' : ''}`}>
            <Header />
            <div className="children">
                {children}
            </div>
        </div>
      </MyContext.Provider>
    </>
  );
};

export default AdminLayout;
export {MyContext}
