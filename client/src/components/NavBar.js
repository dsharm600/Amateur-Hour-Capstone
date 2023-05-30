
import { Navbar, Container, Nav, NavDropdown } from "react-bootstrap";
import { useContext } from "react";
import { Link } from "react-router-dom";
import AuthContext from "../AuthContext";
import "bootstrap/dist/css/bootstrap.min.css";

function NavBar() {

  const authManager = useContext(AuthContext);

  const handleLogout = () => {
    authManager.logout();
  }

  return (
    <div>
        <Navbar fixed="top" bg="dark" variant="dark">
          <Container>
            <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
              <li className="nav-item">
                <Link className="nav-link px-2 text-white" to="/">Amateur Hour</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-2 text-white" to="/tags">Tags</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-2 text-white" to="/states">Find Events in Your State</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-2 text-white" to="/create-event">Host an Event</Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link px-2 text-white" to="/about">About</Link>
              </li>
              {authManager.user ? (
                <li className="nav-item">
                  <button className="btn btn-secondary" type="button" onClick={handleLogout}>Logout</button>
                </li>
              )
                : (
                  <>
                    <li className="nav-item">
                      <Link className="nav-link px-2 text-white" to="/login">Login</Link>
                    </li>
                    <li className="nav-item">
                      <Link className="nav-link px-2 text-white" to="/signup">Sign-up</Link>
                      
                    </li>
                    
                  </>
                )}

            </ul>

            {authManager.user ? <span className="text-light"><Link className="nav-link px-2 blue" to={`/profile/${authManager.userId}`}>Hello, {authManager.user}</Link></span> : null}

          </Container>

        </Navbar>
    </div>
  );
}

export default NavBar;
