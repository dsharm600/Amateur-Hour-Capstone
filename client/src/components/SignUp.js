import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Errors from "./Errors";

const DEFAULT_LOGIN = {
  username: '',
  email: '',
  password: '',
  confirmation: ''
}

function SignUp() {

  const [credentials, setCredentials] = useState(DEFAULT_LOGIN);
  const [errors, setErrors] = useState([]);

  const navigate = useNavigate();

  const handleChange = (event) => {
    // create a replacement
    const replacmentCredentials = { ...credentials };

    replacmentCredentials[event.target.name] = event.target.value;

    setCredentials(replacmentCredentials);
  }

  const handleSubmit = (event) => {
    event.preventDefault();

    if (credentials.password !== credentials.confirmation) {
      setErrors(['Password and Confirmation Password do not match']);
      return;
    }

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    }

    fetch(`${window.EVENTS_BACKEND_URL}/create_account`, init)
      .then(resp => {
        if (resp.status === 201) {
          return null;
        }

        if (resp.status === 400) {
          return resp.json();
        }

        return Promise.reject('Something went wrong on the server :)');
      })
      .then(json => {
        if (json) {
          setErrors(json)
        } else {
          navigate("/login");
        }


      })
      .catch(err => console.error(err));

  }

  return (
    <>
    <div className="vh-100 gradient-custom">
      <div className="container py-5 h-100">
        <form className="form-horizontal">
          <h2 className="mt-5">Sign Up</h2>
          <div className="pt-3 form-group col-md-6">
            <label htmlFor="username">Username:</label>
            <input className="form-control" type="text" id="username" name="username" placeholder="username" value={credentials.username} onChange={handleChange} ></input>
          </div>

          <div className="form-group col-md-6">
            <label htmlFor="email">Email:</label>
            <input className="form-control" type="email" id="email" name="email" placeholder="email" value={credentials.email} onChange={handleChange} ></input>
          </div>

          <div className="form-group col-md-6">
            <label htmlFor="password">Password:</label>
            <input className="form-control" type="password" id="password" name="password" placeholder="password" value={credentials.password} onChange={handleChange} ></input>
          </div>


          <div className="form-group col-md-6">
            <label htmlFor="confirmation">Confirmation Password:</label>
            <input className="form-control" type="password" id="confirmation" name="confirmation" placeholder="password" value={credentials.confirmation} onChange={handleChange} ></input>
          </div>

          <div className="form-group">
            <button type="submit" className="btn btn-outline-light btn-lg px-5" onClick={handleSubmit}>Sign Up</button>
          </div>
          <div className="text-center text-lg-start mt-4 pt-2">
            <p className="mb-0">Already have an account? <a href="login" className="text-white-50 fw-bold">login</a></p>
          </div>
        </form>
        <Errors errors={errors} />
      </div>
      </div>
    </>
  )
}

export default SignUp;
