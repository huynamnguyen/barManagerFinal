import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './App.css';

import { BrowserRouter as Router,
         Switch,
         Route
 } from 'react-router-dom';

import Admin from './components/Admin';
import AdminRoom from './components/AdminRoom';
import Home from './components/Home';
import Order from './components/Order';


function App() {
  const [language, setLanguage] = useState('de');
  const [roomStatus, setRoomStatus] = useState({time_remaining: 0, current_balance: 0, waiter_called: false, waiter_server: false});
  const [pollingRefreshRate, setPollingRefreshRate] = useState(0);

  const fetchRoomStatus = () => {
      fetch('http://localhost:8080/getStatus/3')
        .then(res => res.json())
        .then(res => setRoomStatus(res));
      setPollingRefreshRate(5000);
  }

  useEffect(() => {
    const pollingRoomStatus = setInterval(() => {
      fetchRoomStatus();
    }, pollingRefreshRate);

    return () => clearInterval(pollingRoomStatus);
  });

  return (
    <Router>
      <Switch>
        <Route path="/admin/:id">
          <AdminRoom />
        </Route>
        <Route path="/admin">
          <Admin />
        </Route>
        <Route path="/order">
          <Order language={language} roomStatus={roomStatus} />
        </Route>
        <Route path="/">
          <Home language={language} setLanguage={setLanguage} roomStatus={roomStatus} />
        </Route>
      </Switch>
    </Router>
  );
}

export default App;
