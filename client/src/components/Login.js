import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "../AuthContext";
import Errors from "./Errors";

const DEFAULT_LOGIN = {
  username: '',
  password: ''
}

function Login() {

  const [credentials, setCredentials] = useState(DEFAULT_LOGIN);
  const [errors, setErrors] = useState([]);

  const authManager = useContext(AuthContext);
  const navigate = useNavigate(); // useHistory replaced by useNavigate in v6 of react-rounter-dom

  const handleChange = (event) => {
    // create a replacement
    const replacmentCredentials = { ...credentials };

    replacmentCredentials[event.target.name] = event.target.value;

    setCredentials(replacmentCredentials);
  }

  const handleSubmit = async (event) => {
    event.preventDefault();

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    }

    fetch(`${window.EVENTS_BACKEND_URL}/authenticate`, init)
      .then(resp => {
        console.log(resp);
        if (resp.status === 200) {
          return resp.json();
        }

        if (resp.status === 403) {
          return null;
        }

        return Promise.reject('Something went wrong on the server :)');
      })
      .then(json => {
        if (json) {
          authManager.login(json.jwt_token);
          navigate("/");
        } else {
          setErrors(['The supplied login information does not appear to be in our system.'])
        }


      })
      .catch(err => console.error(err));

  }
  return (
    <>
    <div className="vh-100 gradient-custom">
      <div className="container py-5 h-100">
        <form className="form-horizontal">
          <h2 className="mt-5">Login</h2>
          <div className="pt-3 form-group">
            <label htmlFor="username">Username:</label>
            <input className="form-control" type="text" id="username" name="username" placeholder="username" value={credentials.username} onChange={handleChange}></input>
          </div>
          <div className="form-group ">
            <label htmlFor="password">Password:</label>
            <input className="form-control" type="password" id="password" name="password" placeholder="password" value={credentials.password} onChange={handleChange}></input>
          </div>
          <div className="form-group">
            <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleSubmit}>Login</button>
          </div>
          <div className="text-center text-lg-start mt-4 pt-2">
            <p className="mb-0">Not a member? <a href="signup" className="text-white-50 fw-bold">Register</a></p>
          </div>
        </form>
        <Errors errors={errors} />
      </div>
      </div>
    </>
  );
}

export default Login;