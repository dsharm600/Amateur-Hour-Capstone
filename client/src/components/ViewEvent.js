import React from 'react'
import { useEffect, useState, useContext } from "react";
import { useNavigate, useParams } from 'react-router-dom';
import { Form, Button, Col, Row, Container } from "react-bootstrap";
import AuthContext from "../AuthContext";

import 'bootstrap/dist/css/bootstrap.min.css';
import "react-datepicker/dist/react-datepicker.css";

function ViewEvent() {
  const navigate = useNavigate();


  const [event, setEvent] = useState([]);
  const { eventId } = useParams(false);

  const [errors, setErrors] = useState([]);
  const authManager = useContext(AuthContext);

  const [enrollment, setEnrollment] = useState([]);

  const [enrolled, setEnrolled] = useState(false);
  const [capacity, setCapacity] = useState({ maxCapacity: 0, currentCapacity: 0 });

  useEffect(() => {
    findEvent();
    findIfEnrolled();
    getCapacity();
  }, []);



  //GET capacity info

  const getCapacity = () => {
    fetch(`${window.EVENTS_BACKEND_URL}/rsvp/enrollment/${eventId}`)
      .then(response => {
        if (response.status === 200) {
          return response.json()
        }
        return Promise.reject('Something went wrong on the server');
      })
      .then(body => {
        setCapacity(body);
      })
      .catch(err => console.error(err));
  }


  //GET event info

  const findEvent = () => {
    fetch(`${window.EVENTS_BACKEND_URL}/event/${eventId}`)
      .then(response => {
        if (response.status === 200) {
          return response.json()
        }
        return Promise.reject('Something went wrong on the server');
      })
      .then(body => {
        setEvent(body);
      })
      .catch(err => console.error(err));
  }



  //GET user enrollment

  const findIfEnrolled = () => {
    fetch(`${window.EVENTS_BACKEND_URL}/rsvp/${eventId}/${authManager.userId}`)
      .then(response => {
        if (response.status === 200) {

          setEnrolled(true);
          return response.json();
        } else if (response.status === 404) {

          setEnrolled(false);
          return null;
        } else {
          return Promise.reject('Something went wrong on the server');
        }
      }).then(body => {
        if (body) {
          setEnrollment(body);
        }
      })
      .catch(err => console.error(err));
  }

  // handle enroll

  const handleEnroll = (event) => {
    event.preventDefault();


    if (capacity.maxCapacity === capacity.currentCapacity) {
      setErrors(['Sorry! Cannot enroll to this event at this time. The event is full.']);
      return;
    }


    const enroll = {
      appUserId: authManager.userId,
      eventId
    };

    const init = {

      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem("jwt_token")}`
      },
      body: JSON.stringify(enroll)
    };

    fetch(`${window.EVENTS_BACKEND_URL}/rsvp`, init)
      .then(response => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }
        return Promise.reject('Something went wrong on the server.');
      })
      .then(json => {
        if (json.eventId && json.appUserId) {
          setEnrolled(true);
          setCapacity(cap => { return { ...cap, currentCapacity: cap.currentCapacity + 1 } });

        } else {
          setErrors(json);
        }
      })
      .catch(err => console.error(err));
  }



  // handle Un-enroll

  const handleUnenroll = () => {
    const init = {
      method: 'DELETE',
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwt_token")}`
      }
    }

    fetch(`${window.EVENTS_BACKEND_URL}/rsvp/${eventId}/${authManager.userId}`, init)
      .then(response => {
        if (response.status === 204) {
          setEnrolled(false);
          setCapacity(cap => { return { ...cap, currentCapacity: cap.currentCapacity - 1 } });
          return;
        } else if (response.status === 404) {
          return;
        }

        return Promise.reject('Something went wrong :)');
      })
      .catch(err => console.error(err));
  }



  return (
    <>
      <div>
        <div className="carousel slide" >
          <svg width="100%" height="100%" ><rect width="100%" height="100%" fill="#777" /></svg>

          <div className="mt-2 container">
            <div className="carousel-caption text-start">
              <h1>{event.title}</h1>
              <h5>{event.host}</h5>

            </div>
          </div>
        </div>

        {event.cancelled === true ? (
        <>
          <div className="mt-5 container">
            <div className="alert alert-danger">
              <span>Sorry! This event has been cancelled!</span>
            </div>
          </div>
        </>
      ) : null}

        <div className="container">
          <div className="row featurette">
            <div className="col-md-7">
              <h4 className="description">Description:</h4>
              <p className="lead">{event.eventDescription}</p>

              <Row>
                <Col className="mb-3">
                  <h4 className="featurette-heading">Date:</h4>
                  {new Date(event.eventDate).toLocaleDateString()}
                </Col>

                <Col className="mb-3">
                  <h4 className="featurette-heading">Time:</h4>
                  {new Date(event.eventDate).toTimeString()}

                </Col>


                <Col className="mb-3">
                  <h4>Location:</h4>
                  <p className="lead">{event.eventAddress}<br></br>
                    {event.city}, {event.state}
                  </p>
                </Col>

                <Col className="mb-3">
                  <h4 className="featurette-heading">Spots Filled:</h4>
                  <p className="lead">{capacity.currentCapacity}/{capacity.maxCapacity}</p>
                </Col>
              </Row>

              <h4 className="featurette-heading">Notes:</h4>
              <p className="lead">{event.eventNotes}</p>


            </div>
            {/* <div className="col-md-5">
              <svg className="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#eee" /><text x="50%" y="50%" fill="#aaa" dy=".3em">500x500</text></svg>

            </div> */}

          </div>
          {authManager.user ? (
            <div>

              {event.cancelled === false ? (
                <>
                  {event.host == authManager.user ? (
                    <Button variant="info" type="submit" onClick={() => navigate(`/edit-event/${eventId}`)}>
                      Edit Your Event
                    </Button>
                  )
                    : (
                      <div>
                        {enrolled ? (
                          <Button variant="danger" type="submit" onClick={handleUnenroll}>
                            Unenroll
                          </Button>
                        )
                          : (
                            <Button variant="success" type="submit" onClick={handleEnroll}>
                              Enroll
                            </Button>
                          )}
                      </div>
                    )}
                </>
              ) : null}



            </div>
          )
            : (
              <>
                <p> Please LogIn or SignUp to Enroll in this event!</p>
              </>
            )}


        </div>
      </div>


      {errors.length ? (
        <>
          <div className="alert alert-danger">
            <span>The following Errors occurred:</span>
            <ul>
              {errors.map((err, i) => <li key={i}>{err}</li>)}
            </ul>
          </div>
        </>
      ) : null}

    </>
  )
}

export default ViewEvent;