import React, { useEffect, useState } from 'react';
import logo from './logo.svg';
import './Footer.css';
import AccessService from '../../service/access.service';

function Footer() {
    const [streams, setStreams] = useState([])

    useEffect(() => {
        const fetchData = async () => {
            AccessService.getStreams()
                .then(res => setStreams(res.data))
        }
        fetchData();
    }, []);

    return (
        <div className="Footer">
            <div className="React-info">
                <img src={logo} className="App-logo" alt="logo" />
                <p>v{React.version}</p>
            </div>
            <div className="Streaming-services">
            <p>Streaming Services:</p>
                <ul>
                    {
                        streams !== undefined &&
                        streams.length >= 0 &&
                        streams.map(stream => {
                            return (
                                <li key={stream.name}>
                                    <a 
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
            </div>
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
