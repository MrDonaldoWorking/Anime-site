import React, { useEffect, useState } from 'react';
import './Footer.css';

function Footer() {
    const [streams, setStreams] = useState([])

    useEffect(() => {
        const fetchData = async () => {
            fetch('/express/streams')
                .then(res => res.json())
                .then(res => setStreams(res.streams))
                .then(console.log('fetched /express/streams'));
        }
        fetchData();
    }, []);

    return (
        <div className="Footer">
            <p>This is a footer!</p>
            <p>Streaming Services:</p>
            <ul>
                {
                    streams.length >= 0 &&
                    streams.map(stream => {
                        return (
                            <li><a 
                                    href={stream.href}
                                    target="_blank"
                                    rel="noopener noreferrer"
                                >
                                    {stream.name}
                                </a>
                            </li>
                        )
                    })
                }
            </ul>
            <p>
                Powered by <br />
                <a 
                    href="https://github.com/MrDonaldoWorking"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    MrDonaldo
                </a>
            </p>
        </div>
    );
}

export default Footer;
