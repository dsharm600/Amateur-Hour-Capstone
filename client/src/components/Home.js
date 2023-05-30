import image1 from './images/metalWorking1080.jpg';
import image2 from './images/coins1080.jpg';
import image3 from './images/running1080.jpg';
import image4 from './images/boardgame1080.jpg';
import image5 from './images/hanging1080.jpg';
import image6 from './images/studying1080.jpg';

import { useNavigate } from 'react-router-dom';

function Home() {
  const navigate = useNavigate();

  return (
    <main>
      <section className="py-5 text-center container">
        <div className="row py-lg-5">
          <div className="col-lg-6 col-md-8 mx-auto">
            <h1 className="fw-light">Find events in a city near you</h1>
            {/* <p className="lead text-muted">Enter your address to find events near you!</p> */}
            <p>
              <form className="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                <input type="search" className="form-control form-control-dark" placeholder="Search..." aria-label="Search"></input>
              </form>
              <a href="#" className="btn btn-primary my-2">Search</a>
              {/* <!-- <a href="#" className="btn btn-secondary my-2">Secondary action</a> --> */}
            </p>
          </div>
        </div>
      </section>

      <div className="album py-5 bg-light">
        <div className="container">

          
        <h2 className="fw-light text-center">FEATURED TAGS</h2>
          <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
            <div className="col">
              <div className="card shadow-sm">
                <img src={image1} className="Image-1"></img>
                <div className="card-body">
                  <p className="card-text">Create something new</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <div className="btn-group">
                      <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/2')}>Metal Working</button>

                      {/* <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button> */}
                    </div>
                    {/* <!-- <small className="text-muted">9 mins</small> --> */}
                  </div>
                </div>
              </div>
            </div>

            <div className="col">
              <div className="card shadow-sm">
                <img src={image2} className="Image-1"></img>

                <div className="card-body">
                  <p className="card-text">Share your passion for collecting with others just like you</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <div className="btn-group">
                      <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/5')}>Collecting</button>

                      {/* <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button> */}
                    </div>
                    {/* <!-- <small className="text-muted">9 mins</small> --> */}
                  </div>
                </div>
              </div>
            </div>

            <div className="col">
              <div className="card shadow-sm">
                <img src={image3} className="Image-1"></img>

                <div className="card-body">
                  <p className="card-text">Join a group exercise session.</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <div className="btn-group">
                      <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/9')}>Group Exercise</button>
                      {/* <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button> */}
                    </div>
                    {/* <!-- <small className="text-muted">9 mins</small> --> */}
                  </div>
                </div>
              </div>
            </div>
            <div className="col">
              <div className="card shadow-sm">
                <img src={image4} className="Image-1"></img>

                <div className="card-body">
                  <p className="card-text">Ruin your new friendships with game night</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <div className="btn-group">
                      <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/6')}>Cards/ Games</button>

                      {/* <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button> */}
                    </div>
                    {/* <!-- <small className="text-muted">9 mins</small> --> */}
                  </div>
                </div>
              </div>
            </div>
            <div className="col">
              <div className="card shadow-sm">
                <img src={image5} className="Image-1"></img>

                <div className="card-body">
                  <p className="card-text">Slow things down and just hang</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <div className="btn-group">
                      <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/8')}>Hanging Out</button>

                      {/* <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button> */}
                    </div>
                    {/* <!-- <small className="text-muted">9 mins</small> --> */}
                  </div>
                </div>
              </div>
            </div>

            <div className="col">
              <div className="card shadow-sm">
                <img src={image6} className="Image-1"></img>

                <div className="card-body">
                  <p className="card-text">See all of our communities great events</p>
                  <div className="d-flex justify-content-between align-items-center">
                    <div className="btn-group">
                      <button type="button" className="btn btn-sm btn-outline-secondary" onClick={() => navigate('/tag/10')}>Study</button>

                      {/* <button type="button" className="btn btn-sm btn-outline-secondary">Edit</button> */}
                    </div>
                    {/* <!-- <small className="text-muted">9 mins</small> --> */}
                  </div>
                </div>
              </div>
            </div>


          </div>
        </div>
      </div>

    </main>
  );
}

export default Home;
