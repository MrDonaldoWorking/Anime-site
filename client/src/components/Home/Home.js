import React, { useEffect, useState } from 'react';
import './Home.css';

function Home() {
    const [connected, setConnected] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            fetch('/express/connected')
            .then(res => setConnected(res))
            .then(console.log('fetched /express/connected'));
        }
        fetchData();
    }, []);

    return (
        <div className="Body">
            <h2>Backend is {connected ? '' : 'not'} connected</h2>
            <p>React version {React.version}</p>
            <h2>Featured:</h2>
            <ul>
                <li>Mushoku Tensei</li>
                <li>Steins; Gate</li>
                <li>Overlord</li>
            </ul>
        </div>
    );
}

export default Home;
