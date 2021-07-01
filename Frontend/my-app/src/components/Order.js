import React, { useEffect,  useState } from 'react';

import Button from 'react-bootstrap/Button';
import Col from 'react-bootstrap/Col';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';

import { menuString, shoppingCartString, checkoutString, subtotalString, balanceString, remainingBalanceString, addString } from '../constants/Strings'


function Order(props) {
  const [items, setItems] = useState([]);
  const [shoppingCart, setShoppingCart] = useState([]);
  const [subtotal, setSubtotal] = useState(0);

  const fetchItems = () =>
    fetch('http://localhost:8080/getItems')
      .then(res => res.json())
      .then(res => setItems(res));

  const addItem = (item) => {
    for (let shoppingCartItemIndex in shoppingCart) {
      let shoppingCartCopy = shoppingCart;

      if (shoppingCart[shoppingCartItemIndex]['item']['id'] === item['id']) {
        shoppingCartCopy[shoppingCartItemIndex]['amount'] += 1;

        setShoppingCart(shoppingCartCopy);
        setSubtotal(calcSubtotal());

        return;
      }
    }

    setShoppingCart([...shoppingCart, {'item': item, 'amount': 1}]);
    setSubtotal(calcSubtotal());
  }

  const reduceItem = (item) => {
      for (let shoppingCartItemIndex in shoppingCart) {
        let shoppingCartCopy = shoppingCart;

        if (shoppingCart[shoppingCartItemIndex]['item']['id'] === item['id']) {
          if (shoppingCartCopy[shoppingCartItemIndex]['amount'] > 1) {
            shoppingCartCopy[shoppingCartItemIndex]['amount'] -= 1;
            setShoppingCart(shoppingCartCopy);
            setSubtotal(calcSubtotal());

            return;
          } else {
            shoppingCartCopy.splice(shoppingCartCopy.indexOf(shoppingCartItemIndex));
            setShoppingCart(shoppingCartCopy);
            setSubtotal(calcSubtotal());
          }
        }
      }
  }

  const checkout = () => {
    fetch('http://localhost:8080/order/3', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(shoppingCart)
      }).then(setShoppingCart([]))};

  const calcSubtotal = () => {
    let subtotal = 0;
    for (let shoppingCartItemIndex in shoppingCart) {
      subtotal += shoppingCart[shoppingCartItemIndex]['item']['price'] * shoppingCart[shoppingCartItemIndex]['amount'];
    }

    return subtotal;
  }

  useEffect(() => {
    fetchItems();
  });

  let languageIndex = 0;
  if (props.language === 'en') {
    languageIndex = 1;
  } else if (props.language === 'cn') {
    languageIndex = 2;
  }

  return (
    <Container fluid={true} className="h-100 home">
      <Row className="h-100 justify-content-center align-items-center">
        <Col xs={12}>
          <Row>
            <Col xs={12} className="text-center">
							<div className="red_bg bgap30">
								<h1 className="text-uppercase">{menuString[props.language]}</h1>
							</div>
            </Col>
          </Row>

          <Row>
            <Col sm={8}>
							<div className="hidden_scroll r2">
  							<div className="menu">
                  {items.map((item, index) => (
                    <Row key={index}>
                      <Col xs={6}>
                        <p>{item['name'][languageIndex]}<br />
                        {item['description'][languageIndex]}</p>
                      </Col>

                      <Col className="my-auto" xs={3}>
                        <p>
                         {item['price']}€
                       </p>
                      </Col>

                      <Col className="my-auto" xs={3}>
                        <Button onClick={() => addItem(item)}>{addString[props.language]}</Button>
                      </Col>
                    </Row>
                  ))}
  							</div>
							</div>
            </Col>

            <Col sm={4}>
							<Row className="title nm">
								<Col xs={12} className="align-middle my-auto">
                <div>
									<h5>{shoppingCartString[props.language]}</h5>
                  </div>
								</Col>
							</Row>
              <Row className="nm r1-top">
                <Col xs={12} className="cart_context">
                  {shoppingCart.map((item, index) => (
                    <Row key={index}>
                      <Col xs={6}>
                      <p>{item['item']['name'][0]}<br />{item['amount']*item['item']['price']}€</p>

                      </Col>
                      <Col xs={6}>
                        <Button onClick={() => reduceItem(item['item'])}>-</Button>
                        {item['amount']}
                        <Button onClick={() => addItem(item['item'])}>+</Button>
                      </Col>
                    </Row>
                  ))}
                </Col>


              <hr />

              <Row>
                <Col xs={8}>
                  {subtotalString[props.language]}
                </Col>
                <Col xs={4}>
                  {subtotal} €
                </Col>
              </Row>

              <Row>
                <Col xs={8}>
                  {balanceString[props.language]}
                </Col>
                <Col xs={4}>
                  {props.roomStatus.current_balance} €
                </Col>
              </Row>

              <hr />

              <Row>
                <Col xs={8}>
                  {remainingBalanceString[props.language]}
                </Col>
                <Col xs={4}>
                  {props.roomStatus.current_balance - subtotal} €
                </Col>
              </Row>
              </Row>
							<Button className="red_button tgap30 rock_bottom" onClick={() => checkout()}>{checkoutString[props.language]}</Button>
            </Col>
          </Row>
        </Col>
      </Row>
    </Container>
  )
}

export default Order;
