import React, { useEffect, useState } from 'react';
import './Home.css';
import { Link } from "react-router-dom";

function Home() {
    const [connected, setConnected] = useState(false);
    const [titles, setTitles] = useState([]);

    useEffect(() => {
        const fetchConnectedData = async () => {
            fetch('/express/connected')
            .then(res => setConnected(res))
            .then(console.log('fetched /express/connected'));
        }

        fetchConnectedData();
    }, []);

    useEffect(() => {
        const fetchTitlesData = async () => {
            fetch('/express/titles')
                .then(res => res.json())
                .then(res => setTitles(res));
            console.log(titles);
        }

        fetchTitlesData();
    }, []);

    return (
        <div className="Body">
            <h2>Backend is {connected ? '' : 'not'} connected</h2>
            <h2>Featured:</h2>
            <ul>
                {
                    titles !== undefined &&
                    titles.length >= 0 &&
                    titles.map(title => {
                        return (
                            <li key={title.id}>
                                <Link to={'/series/' + title.id}>
                                    {title.title}
                                </Link>
                            </li>
                        );
                    })
                }
            </ul>
        </div>
    );
}

export default Home;
