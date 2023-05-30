import Home from "./components/Home";
import About from "./components/About";
import Login from "./components/Login";
import SignUp from "./components/SignUp";
import ViewEvent from "./components/ViewEvent";
import CreateEvent from "./components/CreateEvent";
import Tags from "./components/Tags";
import ViewByTag from "./components/ViewByTag";
import StatePage from "./components/StatePage";
import EditEvent from "./components/EditEvent";
import ProfilePage from "./components/ProfilePage";
import ViewAllFromState from "./components/ViewFromState";

import NotFound from "./components/NotFound";
import NavBar from "./components/NavBar";

import { Navigate } from "react-router-dom"
import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import 'bootstrap/dist/css/bootstrap.min.css';

import AuthContext from "./AuthContext";
import jwtDecode from "jwt-decode";

const TOKEN = 'jwt_token';

function App() {
  const [init, setInit] = useState(false);
  const [authManager, setAuthManager] = useState({
    user: null,
    roles: null,
    login(token) {
      if (!this.user) {
        const userData = jwtDecode(token);
        localStorage.setItem(TOKEN, token);
        setAuthManager((prevState) => ({ ...prevState, user: userData.sub, roles: userData.authorities, userId: userData.userId}));
      }
    },
    logout() {
      if (this.user) {
        localStorage.removeItem(TOKEN);
        setAuthManager((prevState) => ({ ...prevState, user: null, roles: null, userId:null }));
      }
    },
    hasRole(role) {
      return this.roles && this.roles.split(',').includes(`ROLE_${role.toUpperCase()}`);
    }
  });

  useEffect(() => {
    const token = localStorage.getItem(TOKEN);
    if (token) {
      authManager.login(token);
    }
    setInit(true);
  }, []);

  return (

    <div className="App">
       {init ? 
      (<AuthContext.Provider value={authManager} >
        <BrowserRouter>
          <NavBar />

          <div className="pt-5 app-container">
            <Routes>
              <Route exact path='/' element={<Home />} />
              <Route path='/about' element={<About />} />
              <Route path='/states' element={<StatePage />} />
              <Route path='/login' element={authManager.user ? <Navigate to="/" replace/> : <Login />} />
              <Route path='/signup' element={authManager.user ? <Navigate to="/" replace/> : <SignUp />} />
              <Route path='/states/:state/:pageId' element={<ViewAllFromState />} />
              
              <Route path='/event/:eventId' element={<ViewEvent />} />
              <Route path='/create-event' element={authManager.user ? <CreateEvent /> : <Navigate to="/login" replace/>} />
              <Route path='/edit-event/:eventId' element={<EditEvent />} />
              
              <Route path='/tag/:tagId' element={<ViewByTag />} />
              <Route path='/profile/:userId' element={<ProfilePage />} />
              

              <Route path='/tags' element={<Tags />} />

              <Route path='/*' element={<NotFound />} />
            </Routes>
          </div>
        </BrowserRouter>
        </AuthContext.Provider>) : null}
    </div>
  );
}

export default App;