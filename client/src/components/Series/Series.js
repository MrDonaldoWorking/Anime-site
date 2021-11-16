import React, { useEffect, useState } from 'react';
import './Series.css';
import { useParams } from "react-router-dom";
import AccessService from '../../service/access.service';

function Series() {
    const { id } = useParams();
    const [series, setSeries] = useState({});

    useEffect(() => {
        const fetchSeriesData = async () => {
            AccessService.getSeriesDescription(id)
                .then(res => setSeries(res.data[0]));
            console.log("series/", id, series);
        }
        fetchSeriesData();
    }, []);

    return (
        <div className="Info">
            <h2>
                {series.title}
            </h2>
            <img src={series.imageUrl} alt="series.logo"/>
            <p>
                {series.description}
            </p>
        </div>
    );
}

export default Series;
