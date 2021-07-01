import React, { useEffect, useState } from 'react';

import { useParams } from 'react-router-dom';

import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';


function AdminRoom(props) {
  let { id } = useParams();

  const [room, setRoom] = useState({'room': null, 'order': null, 'time_left': 0});
  const [pollingRefreshRate, setPollingRefreshRate] = useState(0);

  const fetchRoom = () => {
      fetch(`http://localhost:8080/getStatus`)
        .then(res => res.json())
        .then(res => setRoom(res[id]));
      setPollingRefreshRate(5000);
  }

  const renderOrders = () => {
    if (room.order) {
      return (
        <>
          {room.order.map((order, index) => (
            <p>
              Order Id: {order.id}
              Time: {order.d}
              {renderOrderItems(0)}
            </p>
          ))}
        </>
      )
    }
  }

  const renderOrderItems = (orderIndex) => {
    if (room.order && room.order[orderIndex].orders) {
      return (
        <>
        {room.order[orderIndex].orders.map((order, index) => (
          <>
            {order.item.name[0]}, {order.quantity}x{order.item.price} = {order.quantity * order.item.price} €
          </>
        ))}
        </>
      )
    }
  }

  useEffect(() => {
    const pollingRoom = setInterval(() => {
      fetchRoom();
    }, pollingRefreshRate);

    return () => clearInterval(pollingRoom);
  });

  let languageIndex = 0;
  if (props.language === 'en') {
    languageIndex = 1;
  } else if (props.language === 'cn') {
    languageIndex = 2;
  }


      console.log('test')

  return (
    <Container fluid={true} className="h-100 home">
      <Row className="h-100 justify-content-center align-items-center">
        <Col sm={12}>
          <Row>
            <Col sm={12} className="text-center bgap30">
              <h1 className="headline">Raum {id}</h1>
            </Col>
          </Row>

          <Row>
            <Col>
              <p>
                {renderOrders()}
              </p>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  )
}

export default AdminRoom;
