import React, { useEffect,  useState } from 'react';

import { Link } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';


function Admin(props) {
  const [rooms, setRooms] = useState([]);
  const [pollingRefreshRate, setPollingRefreshRate] = useState(0);

  const fetchRooms = () => {
      fetch('http://localhost:8080/getStatus')
        .then(res => res.json())
        .then(res => setRooms(res));
      setPollingRefreshRate(5000);
  }

  useEffect(() => {
    const pollingRooms = setInterval(() => {
      fetchRooms();
    }, pollingRefreshRate);

    return () => clearInterval(pollingRooms);
  });

	const confirmCall = (room) => {
    fetch('http://localhost:8080/confirmCall/' + room, {
      method: 'POST'
    });
		var tmp = document.getElementById('confirm' + room);
		tmp.classList.remove('active');
  }

  let languageIndex = 0;
  if (props.language === 'en') {
    languageIndex = 1;
  } else if (props.language === 'cn') {
    languageIndex = 2;
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
            {rooms.map((room, index) => (
              <Col sm={6}>
									<div className={room.time_left === 0 ? 'admin_button rounded_border' : 'admin_button rounded_border_r'}>
											<div id={'confirm' + room.room.room } onClick={() => confirmCall( room.room.room )} className= { room.room.calledWaiter ? 'notification active call' : 'notification call'}>
												Kellner gerufen
											</div>
										<div className={ room.room.waiterService ? 'notification active w_service' : 'd-none'}>
											Kellner-Service
										</div>
										
										<Link to={"/admin/" + room.room.room}>
											<div className={room.time_left === 0 ? 'centered inactive_room' : 'centered active_room'}>
												<h3>
														Raum {room.room.room}
												</h3>
												<h3>
														{ prettyTime(room.time_left) }
												</h3>
											</div>
										</Link>
								</div>
              </Col>
            ))}
          </Row>
        </Col>
      </Row>
    </Container>
  )
}

// Function need to be made globally
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

export default Admin;
