import React, { useEffect, useState } from 'react';
import './Series.css';
import { useParams } from "react-router-dom";
import AccessService from '../../service/access.service';
import SessionService from '../../service/session.service';

function refresh() {
    window.location.reload()
}

function Comments(props) {
    if ("".length === 0) {
        return <h3>Feel free to start discussion!</h3>
    } else {
        return (
            <ul>
                {[""].map(comment => {
                    <li>
                        <div className="author">{comment.author.username}</div>
                        <div className="comment">{comment.comment}</div>
                    </li>
                })
                }
            </ul>
        );
    }
}

function Series() {
    const { id } = useParams();
    const [series, setSeries] = useState({});
    // const [user, setUser] = useState(undefined);

    useEffect(() => {
        const fetchSeriesData = async () => {
            AccessService.getSeriesDescription(id)
                .then(res => setSeries(res.data));
            console.log("series/", id, series);
        }

        fetchSeriesData();
    }, []);

    return (
        <div className="Series-container">
            <div className="Info">
                <h1>{series.title}</h1>
                <img src={series.imageUrl} alt="series.logo"/>
                <p>
                    {series.description}
                </p>
            </div>
            <Comments id={id} />
        </div>
    );
}

export default Series;
