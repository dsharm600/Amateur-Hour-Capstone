import React from 'react'
import { useContext, useEffect, useState } from "react";
import { useParams } from 'react-router-dom';
import { Form, Button, Col, Row } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';

function ViewEvent() {

    let navigate = useNavigate();


    const [events, setEvents] = useState([]);
    const { tagId } = useParams();

// fetch events by given tag 

    const findEvent = () => {
        fetch(`${window.EVENTS_BACKEND_URL}/tag/events/${tagId}`)

            .then(response => {
                if (response.status === 200) {
                    return response.json()
                }

                return Promise.reject('Something went wrong on the server');
            })
            .then(body => {
                setEvents(body);
            })
            .catch(err => console.error(err));
    }

    useEffect(() => {
        findEvent();
    }, []);

    return (
        <div className="p-4 container">
            {events.map((et) => (
                <tr key={et.eventId}>
                    <div className="p-2">
                        <div className="card shadow-sm">
                            <Button variant="outline-dark" type="submit" onClick={() => navigate(`/event/${et.eventId}`)} >
                                <Row>
                                    <h4>{et.title}</h4>
                                    
                                    <Col className="mb-3 text-left">
                                        <Col className="mb-3">
                                            <h4 className="featurette-heading">Date:</h4>
                                            {new Date(et.eventDate).toLocaleDateString()}
                                        </Col>

                                        <Col className="mb-3">
                                            <h4 className="featurette-heading">Time:</h4>
                                            {new Date(et.eventDate).toTimeString()}

                                        </Col>
                                    </Col>

                                    <Col className="mb-3 text-right">
                                        <h4 className="featurette-heading">Capacity:</h4>
                                        <p className="lead">{et.capacity}</p>
                                        <h4>Location:</h4>
                                        <p className="lead">{et.city}, {et.state}</p>
                                    </Col>

                                    {/* Could add a third column here with photos if we could figure out how to host images to aws bucket  */}

                                </Row>
                            </Button>
                        </div>
                    </div>
                </tr>
            ))}
        </div>
    )
}

export default ViewEvent;