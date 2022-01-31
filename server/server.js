const express = require('express');
const app = express();
const port = process.env.PORT || 5000;
const session = require('express-session');
const cookieParser = require('cookie-parser');

// CORS
app.use(function (req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:3000');
    res.setHeader('Access-Control-Allow-Credentials', 'true');
    res.setHeader('Cache-Control', 'no-store');
    next();
});

// Session
app.use(express.urlencoded( {extended: true }));
app.use(cookieParser());
app.use(session({
    secret: 'supersecretinformationveryhardtofind',
    resave: false,
    saveUninitialized: true,
    cookie: {
        secure: true,
        maxAge: 2 * 60 * 60 * 1000
    }
}));

app.listen(port, () => console.log(`Listening on port ${port}`));

app.get('/express/connected', (req, res) => {
    res.send({ values: [true] });
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

const animes = [
    { id: 0, title: 'Mushoku Tensei', image_url: 'https://aniwatcher.com/img/ser/fi/5226.jpg', description: 'A 34-year-old NEET loses his life saving a stranger in a traffic accident, only to wake up to find himself reborn as the baby Rudeus Greyrat in a new world filled with magic. The child of the adventurers Paul and Zenith Greyrat, his innate talent for magic is immediately recognized when he turns two, and the magic tutor Roxy Migurdia is brought to his home to refine him into a mage. With a tremendous amount of magical power and a wealth of knowledge from his original world, Rudeus seeks to fulfill his only desires from his previous life—to not repeat the mistakes of his past and die with no regrets. Follow Rudeus from infancy to adulthood, as he struggles to redeem himself in a wondrous yet dangerous world.' },
    { id: 1, title: 'Steins; Gate', image_url: 'https://aniwatcher.com/img/ser/fi/1741.jpg', description: 'The self-proclaimed mad scientist Rintarou Okabe rents out a room in a rickety old building in Akihabara, where he indulges himself in his hobby of inventing prospective "future gadgets" with fellow lab members: Mayuri Shiina, his air-headed childhood friend, and Hashida Itaru, a perverted hacker nicknamed "Daru." The three pass the time by tinkering with their most promising contraption yet, a machine dubbed the "Phone Microwave," which performs the strange function of morphing bananas into piles of green gel.\
\
    Though miraculous in itself, the phenomenon doesn\'t provide anything concrete in Okabe\'s search for a scientific breakthrough; that is, until the lab members are spurred into action by a string of mysterious happenings before stumbling upon an unexpected success—the Phone Microwave can send emails to the past, altering the flow of history.\
\
    Adapted from the critically acclaimed visual novel by 5pb. and Nitroplus, Steins;Gate takes Okabe through the depths of scientific theory and practicality. Forced across the diverging threads of past and present, Okabe must shoulder the burdens that come with holding the key to the realm of time.' },
    { id: 2, title: 'Overlord', image_url: 'https://aniwatcher.com/img/ser/fi/2987.jpg', description: 'The story begins with Yggdrasil, a popular online game which is quietly shut down one day; however, the protagonist Momonga decides to not log out. Momonga is then transformed into the image of a skeleton as "the most powerful wizard." The world continues to change, with non-player characters (NPCs) begining to feel emotion. Having no parents, friends, or place in society, this ordinary young man Momonga then strives to take over the new world the game has become.' }
];

const users = [
    { id: 0, login: 'Ghislane', password: '' },
    { id: 1, login: 'Viewer', password: '' }
];

app.get('/express/titles', (req, res) => {
    res.send(animes.map(anime => {
        return { id: anime.id, title: anime.title, image_url: anime.image_url };
    }));
});

app.get('/express/series/:id(\\d+)', function (req, res) {
    res.send(animes[req.params.id]);
});

app.post('/express/login', (req, res) => {
    const login = req.body.login;
    // const password = req.body.password;

    for (let user of users) {
        if (user.login == login/* && user.password == password*/) {
            req.session.userId = user.id;
            break;
        }
    }
    res.status(200);
    res.end();
});

app.get('/express/user', (req, res) => {
    if (req.session.userId === undefined) {
        res.send({});
    } else {
        if (users[req.session.userId] !== undefined) {
            res.send({
                id: users[req.session.userId].id,
                name: users[req.session.userId].login
            });
        } else {
            res.send({});
        }
    }
});