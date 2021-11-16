import React, { useEffect, useState } from 'react';
import './Home.css';
import { Link } from "react-router-dom";

import AccessService from '../../service/access.service';

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
            AccessService.getTitles()
                .then(res => setTitles(res.data));
            console.log("titles", titles);
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

    if (titles !== undefined && titles.length > 0) {
        return (
            <div className="Body">
                {/* <h2>Backend is {isConnected()} connected{console.log('connected is ', connected)}</h2> */}
                <h2>Featured:</h2>
                <ul>
                    {
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
    } else {
        return (
            <div className="Body">
                <h2>Couldn't load any titles</h2>
                <p>Please check connection</p>
                <h3>Also:</h3>
                <ul className="instruct">
                    <li>Insure you have installed PostgreSQL</li>
                    <li>
                        <p>In PostgreSQL:</p>
                        <code>CREATE DATABASE spring;</code>
                        <code>CREATE USER spring WITH PASSWORD 'spring-rest';</code>
                    </li>
                    <li>
                        <p>After successful Spring Boot in PostgreSQL: (Spring should add all neccessary info)</p>
                        <code>
                            postgres=# \c spring <br />
                            You are now connected to database "spring" as user "postgres".
                        </code>
                        <p>Show all tables:</p>
                        <code>
                            spring=# \d<br />
                                        List of relations<br />
                            .Schema | .... Name .... | . Type . | Owner  <br />
                            --------+----------------+----------+--------<br />
                            .public | roles ........ | table .. | spring<br />
                            .public | roles_id_seq . | sequence | spring<br />
                            .public | streams ...... | table .. | spring<br />
                            .public | streams_id_seq | sequence | spring<br />
                            .public | titles ....... | table .. | spring<br />
                            .public | titles_id_seq. | sequence | spring<br />
                            .public | user_roles ... | table .. | spring<br />
                            .public | users ........ | table .. | spring<br />
                            .public | users_id_seq . | sequence | spring<br />
                            (9 rows)
                        </code>
                        <p>Now see what in roles:</p>
                        <code>
                            spring=# select * from roles;<br />
                            . id | .. name  <br />
                            -----+------------<br />
                            .. 1 | ROLE_USER<br />
                            .. 2 | ROLE_ADMIN<br />
                            (2 rows)
                        </code>
                    </li>
                </ul>
            </div>
        );
    }
}

export default Home;
