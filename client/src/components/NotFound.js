import lost from "./images/lost.gif";
import { Link } from "react-router-dom";


function NotFound() {
  return (
    <div>
      <h1></h1>
      <div className="mt-5 container">
      <h1>Sorry, that URL doesn't exist</h1>

      <img src={lost} className = "confusedJohn"></img>
      <Link className="nav-link px-2 " to="/">Click here to return home.</Link>
  
      </div>
    </div>
  );
}

export default NotFound;