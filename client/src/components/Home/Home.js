import React, { useEffect, useState } from 'react';
import './Home.css';
import { Link } from "react-router-dom";

function Home() {
    // const [connected, setConnected] = useState([]);
    const [titles, setTitles] = useState([]);

    // useEffect(() => {
    //     const fetchConnectedData = async () => {
    //         fetch('/express/connected')
    //             .then(res => res.json())
    //             .then(res => setConnected(res.values))
    //             .then(console.log('fetched /express/connected'));
    //     }

    //     fetchConnectedData();
    // }, [connected[0]]);

    useEffect(() => {
        const fetchTitlesData = async () => {
            fetch('/express/titles')
                .then(res => res.json())
                .then(res => setTitles(res));
            console.log(titles);
        }

        fetchTitlesData();
    }, []);

    // function isConnected() {
    //     console.log('in isConnected', connected);
    //     if (connected[0]) {
    //         connected[0] = false;
    //     } else {
    //         return 'not';
    //     }
    // }

    return (
        <div className="Body">
            {/* <h2>Backend is {isConnected()} connected{console.log('connected is ', connected)}</h2> */}
            <h2>Featured:</h2>
            <ul>
                {
                    titles !== undefined &&
                    titles.length >= 0 &&
                    titles.map(series => {
                        return (
                            <li key={series.id}>
                                <Link to={'/series/' + series.id}>
                                    {series.title}
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
