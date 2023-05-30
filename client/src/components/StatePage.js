import React from 'react'
import { Col, Container, Row } from "react-bootstrap";
import { Link } from "react-router-dom";

function StatePage() {
    return (
        <div className="mt-4 container">
            <h4 className="mt-5">CLICK A STATE TO VIEW EVENTS IN THAT STATE </h4>

            <Container>
                <Row>
                    <Col>

                        <Link className="nav-link px-2" to="/states/AL/1">Alabama</Link>
                        <Link className="nav-link px-2" to="/states/AK/1">Alaska</Link>
                        <Link className="nav-link px-2" to="/states/AZ/1">Arizona</Link>
                        <Link className="nav-link px-2" to="/states/AR/1">Arkansas</Link>
                        <Link className="nav-link px-2" to="/states/CA/1">California</Link>
                        <Link className="nav-link px-2" to="/states/CO/1">Colorado</Link>
                        <Link className="nav-link px-2" to="/states/CT/1">Connecticut</Link>
                        <Link className="nav-link px-2" to="/states/DE/1">Delaware</Link>
                        <Link className="nav-link px-2" to="/states/FL/1">Florida</Link>
                        <Link className="nav-link px-2" to="/states/GA/1">Georgia</Link>
                        <Link className="nav-link px-2" to="/states/HI/1">Hawaii</Link>
                        <Link className="nav-link px-2" to="/states/ID/1">Idaho</Link>
                        <Link className="nav-link px-2" to="/states/IL/1">Illinois</Link>

                    </Col>
                    <Col>

                        <Link className="nav-link px-2" to="/states/IN/1">Indiana</Link>
                        <Link className="nav-link px-2" to="/states/IA/1">Iowa</Link>
                        <Link className="nav-link px-2" to="/states/KS/1">Kansas</Link>
                        <Link className="nav-link px-2" to="/states/KY/1">Kentucky</Link>
                        <Link className="nav-link px-2" to="/states/LA/1">Louisiana</Link>
                        <Link className="nav-link px-2" to="/states/ME/1">Maine</Link>
                        <Link className="nav-link px-2" to="/states/MD/1">Maryland</Link>
                        <Link className="nav-link px-2" to="/states/MA/1">Massachusetts</Link>
                        <Link className="nav-link px-2" to="/states/MI/1">Michigan</Link>
                        <Link className="nav-link px-2" to="/states/MN/1">Minnesota</Link>
                        <Link className="nav-link px-2" to="/states/MS/1">Mississippi</Link>
                        <Link className="nav-link px-2" to="/states/MO/1">Missouri</Link>
                        <Link className="nav-link px-2" to="/states/MT/1">Montana</Link>

                    </Col>
                    <Col>

                        <Link className="nav-link px-2" to="/states/NE/1">Nebraska</Link>
                        <Link className="nav-link px-2" to="/states/NV/1">Nevada</Link>
                        <Link className="nav-link px-2" to="/states/NH/1">New Hampshire</Link>
                        <Link className="nav-link px-2" to="/states/NJ/1">New Jersey</Link>
                        <Link className="nav-link px-2" to="/states/NM/1">New Mexico</Link>
                        <Link className="nav-link px-2" to="/states/NY/1">New York</Link>
                        <Link className="nav-link px-2" to="/states/NC/1">North Carolina</Link>
                        <Link className="nav-link px-2" to="/states/ND/1">North Dakota</Link>
                        <Link className="nav-link px-2" to="/states/OH/1">Ohio</Link>
                        <Link className="nav-link px-2" to="/states/OK/1">Oklahoma</Link>
                        <Link className="nav-link px-2" to="/states/OR/1">Oregon</Link>
                        <Link className="nav-link px-2" to="/states/PA/1">Pennsylvania</Link>

                    </Col>
                    <Col>

                        <Link className="nav-link px-2" to="/states/RI/1">Rhode Island</Link>
                        <Link className="nav-link px-2" to="/states/SC/1">South Carolina</Link>
                        <Link className="nav-link px-2" to="/states/SD/1">South Dakota</Link>
                        <Link className="nav-link px-2" to="/states/TN/1">Tennessee</Link>
                        <Link className="nav-link px-2" to="/states/TX/1">Texas</Link>
                        <Link className="nav-link px-2" to="/states/UT/1">Utah</Link>
                        <Link className="nav-link px-2" to="/states/VT/1">Vermont</Link>
                        <Link className="nav-link px-2" to="/states/VA/1">Virginia</Link>
                        <Link className="nav-link px-2" to="/states/WA/1">Washington</Link>
                        <Link className="nav-link px-2" to="/states/WV/1">West Virginia</Link>
                        <Link className="nav-link px-2" to="/states/WI/1">Wisconsin</Link>
                        <Link className="nav-link px-2" to="/states/WY/1">Wyoming</Link>

                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default StatePage