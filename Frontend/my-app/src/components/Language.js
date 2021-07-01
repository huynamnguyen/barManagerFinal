import React, { useEffect } from 'react';

import ListGroup from 'react-bootstrap/ListGroup';
import Modal from 'react-bootstrap/Modal';


function Language(props) {
  const languagePicker = (language) => {
    return language === props.language ? true : false;
  };

  useEffect(() => {
    languagePicker();
  });

  return (
    <Modal show={props.show} onHide={props.handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Sprache</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <ListGroup>
          <ListGroup.Item id="de" active={languagePicker('de')} onClick={() => props.setLanguage('de')}>Deutsch</ListGroup.Item>
          <ListGroup.Item id="en" active={languagePicker('en')} onClick={() => props.setLanguage('en')}>English</ListGroup.Item>
          <ListGroup.Item id="cn" active={languagePicker('cn')} onClick={() => props.setLanguage('cn')}>中文</ListGroup.Item>
        </ListGroup>
      </Modal.Body>
    </Modal>
  )
}

export default Language;
