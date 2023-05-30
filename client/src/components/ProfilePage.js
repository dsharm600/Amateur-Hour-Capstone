import React from 'react'
import AuthContext from "../AuthContext";
import "bootstrap/dist/css/bootstrap.min.css";

import { useState, useContext } from "react";
import { Form, Button, Col, Row } from "react-bootstrap";
import { Navigate, useNavigate } from 'react-router-dom';

function ProfilePage() {

    const authManager = useContext(AuthContext);
    const navigate = useNavigate;

    const [profile, setProfile] = useState('');

    const handleProfileChange = (event) => {
        setProfile(event.target.value);
    }


    const handleSubmitProfile = (event) => {
        setProfile(event.target.value);
    }
    

    return (
        <>
            <div>
                <div className="carousel slide" >

                    <svg width="100%" height="100%" ><rect width="100%" height="100%" fill="#777" /></svg>

                    <div className="mt-2 container">
                        <div className="carousel-caption text-start">
                            <h1>{authManager.user}</h1>
                        </div>

                    </div>
                </div>
                <div className='mt-5 container'>
                    <h4>Bio:</h4>
                    <Form.Group className="mb-3" controlId="eventProfile">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            placeholder="Tell others about yourself!"
                            as="textarea"
                            rows={3}
                            value={profile}
                            onChange={handleProfileChange} />
                    </Form.Group>
                    <Button variant="primary" type="submit" onClick={handleSubmitProfile}>
                        Update Your Bio
                    </Button>{' '}
                    <Button variant="secondary" type="submit" onClick={navigate('/')}>
                        Return Home
                    </Button>
                </div>

            </div>
        </>
    )
}

export default ProfilePage