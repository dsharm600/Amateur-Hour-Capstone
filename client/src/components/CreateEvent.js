import { useState, useContext } from "react";
import { Form, Button, Col, Row } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';

import DatePicker from 'react-datepicker';

import "react-datepicker/dist/react-datepicker.css";
import 'bootstrap/dist/css/bootstrap.min.css';

import AuthContext from "../AuthContext";

function CreateEvent() {

  let navigate = useNavigate();

  const [title, setTitle] = useState('');
  const [eventAddress, setaddress] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [eventDate, setEventDate] = useState(new Date());
  const [capacity, setCapacity] = useState('');
  const [eventDescription, setEventDescription] = useState('');
  const [eventNotes, setEventNotes] = useState('');

  const [tagId1, setTag1] = useState(0);
  const [tagId2, setTag2] = useState(0);
  const [tagId3, setTag3] = useState(0);

  var tags = [];

  const [errors, setErrors] = useState([]);

  const authManager = useContext(AuthContext);

  //Handlers:

  const handleTitleChange = (event) => {
    setTitle(event.target.value);
  }

  const handleAddressChange = (event) => {
    setaddress(event.target.value);
  }

  const handleCityChange = (event) => {
    setCity(event.target.value);
  }

  const handleStateChange = (event) => {
    setState(event.target.value);
  }

  const handleCapacityChange = (event) => {
    setCapacity(event.target.value);
  }

  // Tags
  const handleTag1Change = (event) => {
    setTag1(event.target.value);
  }

  const handleTag2Change = (event) => {
    setTag2(event.target.value);
  }

  const handleTag3Change = (event) => {
    setTag3(event.target.value);
  }

  //Notes
  const handleEventDescriptionChange = (event) => {
    setEventDescription(event.target.value);
  }

  const handleEventNoteChange = (event) => {
    setEventNotes(event.target.value);
  }



  // const handleImageChange = (event) => {
  //   setImage(event.target.value);
  // }

  const handleSubmit = (event) => {

    if (event.capacity < 4) {
      setErrors(['Capacity must be greater than four.']);
      return;
    }

    if (tagId1 > 0) {
      tags.push({tagId:tagId1})
    }

    if (tagId2 > 0) {
      tags.push({tagId:tagId2})
    }

    if (tagId3 > 0) {
      tags.push({tagId:tagId3})
    }

    event.preventDefault();

    const newEvent = {

      host: authManager.user,
      eventDescription,
      eventNotes,
      eventAddress,
      city,
      state,
      eventDate,
      title,
      capacity,
      tags

    };

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('jwt_token')}`
      },
      body: JSON.stringify(newEvent)
    };

    fetch(`${window.EVENTS_BACKEND_URL}/event`, init)
      .then(response => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        }

        return Promise.reject('Something went wrong on the server.');
      })
      .then(json => {
        // if we have an id, we know this is successful
        if (json.eventId) {
          navigate('/');
        } else {
          // errors
          setErrors(json);
        }
      })
      .catch(err => console.error(err));

  }




  return (
    <>
      <div className="container">
        <h2 className="mt-5">Create Event</h2>

        <Form>

          <Form.Group className="mb-3" controlId="title">
            <Form.Label>Event Title</Form.Label>
            <Form.Control type="title" placeholder="Enter your event title here" required value={title} onChange={handleTitleChange} />
          </Form.Group>

          {/* Address fields: */}

          <Form.Group className="mb-3" controlId="address">
            <Form.Label>Event Address:</Form.Label>
            <Form.Control placeholder="1234 Main St" required value={eventAddress} onChange={handleAddressChange} />
          </Form.Group>

          {/* <Form.Group className="mb-3" controlId="formGridAddress2">
            <Form.Label>Address 2</Form.Label>
            <Form.Control placeholder="Apartment, studio, or floor" />
          </Form.Group> */}









          <Row className="mb-3">
            <Form.Group as={Col} controlId="city">
              <Form.Label>City</Form.Label >
              <Form.Control required value={city} onChange={handleCityChange} />
            </Form.Group>

            <Form.Group as={Col} controlId="state">
              <Form.Label>State</Form.Label>
              <Form.Select defaultValue="Choose..." required value={state} onChange={handleStateChange}>
                <option>Choose...</option>
                <option>AK</option><option>AL</option><option>AR</option><option>AZ</option><option>CA</option><option>CO</option><option>CT</option><option>DC</option><option>DE</option><option>FL</option><option>GA</option><option>
                  HI</option><option>IA</option><option>ID</option><option>IL</option><option>IN</option><option>KS</option><option>KY</option><option>LA</option><option>MA</option><option>MD</option><option>ME</option><option>
                  MI</option><option>MN</option><option>MO</option><option>MS</option><option>MT</option><option>NC</option><option>ND</option><option>NE</option><option>NH</option><option>NJ</option><option>NM</option><option>
                  NV</option><option>NY</option><option>OH</option><option>OK</option><option>OR</option><option>PA</option><option>RI</option><option>SC</option><option>SD</option><option>TN</option><option>TX</option><option>
                  UT</option><option>VA</option><option>VT</option><option>WA</option><option>WI</option><option>WV</option><option>WY</option>
              </Form.Select>
            </Form.Group>

            {/* <Form.Group as={Col} controlId="formGridZip">
              <Form.Label>Zip</Form.Label>
              <Form.Control />
            </Form.Group> */}
          </Row>

          {/* End Address Field */}

          <Row className="mb-3">
            {/* DATE AND TIME PICKER */}
            <Form.Group as={Col} className="mb-3" controlId="date">
              <Form.Label>Time and Date of Event</Form.Label>
              {/* <Form.Control
                type="date"
                name="duedate"
                placeholder="Due date"
                onChange={(date) => setEventDate(date)}
              /> */}
              <DatePicker
                selected={eventDate}
                onChange={(date) => setEventDate(date)}
                showTimeSelect
                timeIntervals={15}
                dateFormat="yyyy/MM/dd h:mm aa"
              />
            </Form.Group>

            <Form.Group as={Col} className="mb-3" controlId="capacity">
              <Form.Label>Capacity</Form.Label>
              <Form.Control type="number"
                required
                value={capacity}
                onChange={handleCapacityChange} />
            </Form.Group>

          </Row>

          {/* Tags */}
          <Row className="mb-3">
            <Form.Group as={Col} controlId="tag1">
              <Form.Label>Tag1</Form.Label>
              <Form.Select defaultValue="Choose..." onChange={handleTag1Change}>
                <option value='0'>Choose...</option>
                <option value='4'>Book Club</option>
                <option value='6'>Cards/ Games</option>
                <option value='5'>Collecting</option>
                <option value='7'>Cooking</option>
                <option value='9'>Group Exercises</option>
                <option value='8'>Hanging out</option>
                <option value='2'>Metal Working</option>
                <option value='11'>Other</option>
                <option value='3'>Pottery</option>
                <option value='10'>Study</option>
                <option value='12'>Outdoors</option>
                <option value='1'>Wood Working</option>

              </Form.Select>
            </Form.Group>
            <Form.Group as={Col} controlId="tag2">
              <Form.Label>Tag2</Form.Label>
              <Form.Select defaultValue="Choose..." onChange={handleTag2Change}>
                <option value='0'>Choose...</option>
                <option value='4'>Book Club</option>
                <option value='6'>Cards/ Games</option>
                <option value='5'>Collecting</option>
                <option value='7'>Cooking</option>
                <option value='9'>Group Exercises</option>
                <option value='8'>Hanging out</option>
                <option value='2'>Metal Working</option>
                <option value='11'>Other</option>
                <option value='3'>Pottery</option>
                <option value='10'>Study</option>
                <option value='12'>Outdoors</option>
                <option value='1'>Wood Working</option>
              </Form.Select>
            </Form.Group>
            <Form.Group as={Col} controlId="tag3">
              <Form.Label>Tag3</Form.Label>
              <Form.Select defaultValue="Choose..." onChange={handleTag3Change}>
                <option value='0'>Choose...</option>
                <option value='4'>Book Club</option>
                <option value='6'>Cards/ Games</option>
                <option value='5'>Collecting</option>
                <option value='7'>Cooking</option>
                <option value='9'>Group Exercises</option>
                <option value='8'>Hanging out</option>
                <option value='2'>Metal Working</option>
                <option value='11'>Other</option>
                <option value='3'>Pottery</option>
                <option value='10'>Study</option>
                <option value='12'>Outdoors</option>
                <option value='1'>Wood Working</option>
              </Form.Select>
            </Form.Group>
          </Row>


          <Form.Group className="mb-3" controlId="eventDescription">
            <Form.Label>Description</Form.Label>
            <Form.Control
              placeholder="Describe your event!"
              as="textarea"
              rows={3}
              required
              value={eventDescription}
              onChange={handleEventDescriptionChange} />
          </Form.Group>

          <Form.Group className="mb-3" controlId="eventNotes">
            <Form.Label>Event Note</Form.Label>
            <Form.Control
              placeholder="Here is the place to list requirements for your event such as price or materials needed."
              as="textarea"
              rows={3}
              value={eventNotes}
              onChange={handleEventNoteChange} />
          </Form.Group>


          {/* <Form.Group controlId="formFile" className="mb-3" >
            <Form.Label>Add an image for your event</Form.Label>
            <Form.Control type="file" />
          </Form.Group> */}

          <Button variant="success" type="submit" onClick={handleSubmit}>
            Create Event
          </Button>{' '}
          <Button variant="danger" type="submit" onClick={() => navigate('/')}>
            Cancel Create
          </Button>
        </Form>

      </div>

      {errors.length ? (<>
        <div className="alert alert-danger">
          <span>The following Errors occurred:</span>
          <ul>
            {errors.map((err, i) => <li key={i}>{err}</li>)}
          </ul>
        </div>
      </>) : null}
    </>
  )

}

export default CreateEvent;