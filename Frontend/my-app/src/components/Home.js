import React, { useState } from 'react';

import { Link } from 'react-router-dom';

import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';

import Language from './Language';

import { orderString, callWaiterString, waiterServiceString, languageString, infoString, remainingTimeString } from '../constants/Strings';


function Home(props) {
  const [showLanguage, setShowLanguage] = useState(false);
	const callWaiter = () => {
		const tmp = document.getElementById('callWaiter');
    fetch('http://localhost:8080/callWaiter/3/' + (tmp.classList.contains('active_button') ? 'false' : 'true'), {
      method: 'POST'
    });
		if(tmp.classList.contains('active_button')) tmp.classList.remove('active_button');
		else tmp.classList.add('active_button');
  }
	
	const waiterService = () => {
		const tmp = document.getElementById('waiterService');
    fetch('http://localhost:8080/waiterService/3/' + (tmp.classList.contains('active_button') ? 'false' : 'true'), {
      method: 'POST'
    });
		if(tmp.classList.contains('active_button')) tmp.classList.remove('active_button');
		else tmp.classList.add('active_button');
  }

  return (
    <Container fluid={true} className="h-100 home">
      <Row className="h-100 justify-content-center align-items-center">
        <Col sm={12}>
          <Row>
            <Col sm={12} className="text-center bgap30">
              <h1 className="headline">Unser Logo</h1>
            </Col>
          </Row>

          <Row>
            <Col sm={6}>
              <Link to="/order">
								<div className="order_button rounded_border">
									<div className="centered">
										<h2 className="headline">{orderString[props.language]}</h2>
									</div>
								</div>
              </Link>
            </Col>

            <Col sm={6}>
              <Row>
                <Col sm={6}>
									<Link onClick={() => callWaiter()}>
										<div id="callWaiter" className={props.roomStatus.waiter_called ? 'secondary_button rounded_border active_button' : 'secondary_button rounded_border'}>
											<div className="centered">
												<h5 className="headline">{callWaiterString[props.language]}</h5>
											</div>
										</div>
									</Link>
                </Col>

                <Col sm={6}>
                  <Link onClick={() => setShowLanguage(true)}>
  									<div className="secondary_button rounded_border">
  										<div className="centered">
  											<h5 className="headline">{languageString[props.language]}</h5>
  										</div>
  									</div>
                  </Link>
                </Col>

                <Col sm={6}>
									<Link onClick={() => waiterService()}>
										<div id="waiterService" className={props.roomStatus.waiter_called ? 'secondary_button rounded_border active_button tgap30' : 'secondary_button rounded_border tgap30'}>
											<div className="centered">
												<h5 className="headline">{waiterServiceString[props.language]}</h5>
											</div>
										</div>
									</Link>
                </Col>

                <Col sm={6}>
									<div className="secondary_button rounded_border tgap30">
										<div className="centered">
											<h5 className="headline">{infoString[props.language]}</h5>
										</div>
									</div>
                </Col>
              </Row>
            </Col>
          </Row>

          <Row>
            <Col xs={12}>
							<div className="tgap30">
								<p className="text-center headline">
									{remainingTimeString[props.language]}: { prettyTime(props.roomStatus.time_remaining) }
								</p>
							</div>
            </Col>
          </Row>
        </Col>
      </Row>

      <Language show={showLanguage} handleClose={() => setShowLanguage(false)} language={props.language} setLanguage={props.setLanguage} />
    </Container>
  )
}

function prettyTime(ugly) {
	if(ugly === 0) return "00:00";
	if(ugly == null) return "00:00";

	var hour = Math.floor(ugly/60);
	if(hour < 10 && hour > 0) hour = "0" + hour;
	var minutes = ugly%60;
	if(minutes < 0) minutes *= -1;
	if(minutes < 10) minutes = "0" + minutes;
	return hour + ":" + minutes;
}

export default Home;
