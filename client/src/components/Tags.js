import { useNavigate, useParams } from 'react-router-dom';

import image1 from './images/wworking1080.jpg';
import image2 from './images/metalWorking1080.jpg';
import image3 from './images/pottery1080.jpg';
import image4 from './images/book1080.jpg';
import image5 from './images/coins1080.jpg';
import image6 from './images/boardgame1080.jpg';
import image7 from './images/cooking1080.jpg';
import image8 from './images/hanging1080.jpg';
import image9 from './images/running1080.jpg';
import image10 from './images/studying1080.jpg';
import image11 from './images/Outdoors1080.jpg';
import image12 from './images/other1080.jpg';

function About() {
  let navigate = useNavigate();

  const { tag } = useParams(false);


  return (

    <div className="py-5 container">

      <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
        <div className="col">
          <div className="card shadow-sm">
            <img src={image1} className="Image-1"></img>
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/1')}>Woodworking</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image2} className="Image-2"></img>
            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/2')}>Metal Working</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image3} className="Image-3"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/3')}>Pottery</button>
                </div>
                </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image4} className="Image-4"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/4')}>Book Club</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image5} className="Image-5"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/5')}>Collecting</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image6} className="Image-6"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/6')}>Cards/ Games</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image7} className="Image-7"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/7')}>Cooking</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image8} className="Image-8"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/8')}>Hanging Out</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image9} className="Image-9"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/9')}>Group Exercise</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image10} className="Image-10"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/10')}>Study</button>
              </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image11} className="Image-11"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/11')}>Outdoors</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="col">
          <div className="card shadow-sm">
            <img src={image12} className="Image-12"></img>

            <div className="card-body">
              <div className="d-flex justify-content-between align-items-center">
                <div className="btn-group">
                  <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/12')}>Other</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}



export default About;