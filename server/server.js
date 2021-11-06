const express = require('express');
const app = express();
const port = process.env.PORT || 5000;

app.listen(port, () => console.log(`Listening on port ${port}`));

app.get('/express/connected', (req, res) => {
    res.send(true);
});

app.get('/express/streams', (req, res) => {
    res.send({ 
        streams: [
            { href: 'https://www.crunchyroll.com/', name: 'Crunchyroll' },
            { href: 'https://www.funimation.com/', name: 'Funimation' },
            { href: 'https://www.netflix.com/', name: 'Netflix' }
        ]
    });
});