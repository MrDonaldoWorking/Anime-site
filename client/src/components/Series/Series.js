import React, { useEffect, useState } from 'react';
import './Series.css';
import { useParams } from "react-router-dom";

function Series() {
    const { id } = useParams();
    const [series, setSeries] = useState({});

    useEffect(() => {
        const fetchSeriesData = async () => {
            fetch('/express/series/' + id)
                .then(res => res.json())
                .then(res => setSeries(res));
        }
        fetchSeriesData();
    }, []);

    return (
        <div>
            <h2>
                {series.title}
            </h2>
            <p>
                {series.description}
            </p>
        </div>
    );
}

export default Series;
