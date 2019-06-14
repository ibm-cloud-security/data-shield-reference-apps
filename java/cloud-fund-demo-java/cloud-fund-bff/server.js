const express = require('express');
const app = express();
require('dotenv').config();
const morgan = require('morgan');
const bodyParser = require('body-parser');
const path = require('path');
const cors = require('cors');
const axios = require('axios');

const port = process.env.PORT || 8000;
const secret = process.env.SECRET || 'throw away local password';
app.use(express.static(path.join(__dirname + '../', 'react-frontend','..', 'build')));
app.use(bodyParser.json());
app.use(morgan('dev'));

const session = require("express-session");
const passport = require("passport");
const nconf = require("nconf");
const appID = require("bluemix-appid");

const helmet = require("helmet");
const express_enforces_ssl = require("express-enforces-ssl");
const cfEnv = require("cfenv");
const cookieParser = require("cookie-parser");

const WebAppStrategy = appID.WebAppStrategy;
const userAttributeManager = appID.UserAttributeManager;
const UnauthorizedException = appID.UnauthorizedException;

const isLocal = cfEnv.getAppEnv().isLocal;

const CALLBACK_URL = "/ibm/bluemix/appid/callback";
const UI_BASE_URL = process.env.frontend_url || "http://localhost:3000";
const BACKEND_URL = process.env.backend_url || "http://localhost:8500";
const BFF_URL = process.env.bff_url ||  "http://localhost:8000";
const TRANSACTIONS_URL = `${BACKEND_URL}/api/v1/transactions`;
const SPONSOR_URL = `${BACKEND_URL}/api/v1/store_card`;
const CARDS_URL = `${BACKEND_URL}/api/v1/get_cards`;

const config = getLocalConfig();
configureSecurity();

app.use(cors({credentials: true, origin: UI_BASE_URL}));

app.use(session({
    secret,
    resave: true,
    saveUninitialized: true,
      proxy: true,
      cookie: {
          httpOnly: true,
          secure: !isLocal,
          maxAge: 600000000
      }
}));

// Routes (CHANGE AS APPROPRIATE
app.use(passport.initialize());
app.use(passport.session());

let webAppStrategy = new WebAppStrategy(config);
passport.use(webAppStrategy);

userAttributeManager.init(config);

passport.serializeUser(function(user, cb) {
    cb(null, user);
});

passport.deserializeUser(function(obj, cb) {
    cb(null, obj);
});

// MY ROUTES

// Open Endpoints
app.get('/', (req, res) => {
    return res.status(200).send("OK");
});

app.post('/donate', (req, res) => {
    axios.post(TRANSACTIONS_URL, req.body)
        .then(response => {
            return res.sendStatus(response.status);
        })
        .catch(error => {
            console.log(error);
            return res.sendStatus(500);
        })
});

// Endpoints requiring authentication
app.get('/transactions/manage', (req, res) => {
    if (req.session[WebAppStrategy.AUTH_CONTEXT]) {
        axios.get(`${TRANSACTIONS_URL}?email=${req.user.email}`)
            .then(response => {
                return res.send(response.data);
            })
            .catch(error => {
                console.log(error);
                return res.sendStatus(500);
            })
    } else {
        res.status(401).send("Unauthorized");
    }
});

app.get('/transactions/all', (req, res) => {
    if (req.session[WebAppStrategy.AUTH_CONTEXT] && req.session[WebAppStrategy.AUTH_CONTEXT].identityTokenPayload.role === "admin") {
        axios.get(`${TRANSACTIONS_URL}`)
            .then(response => {
                return res.send(response.data);
            })
            .catch(error => {
                console.log(error);
                return res.status(500);
            })
    } else {
        res.status(401).send("Unauthorized");
    }
});

app.delete('/transactions/:id', isLoggedIn, (req, res) => {
    axios.delete(`${TRANSACTIONS_URL}/${req.params.id}`)
        .then(response => {
            return res.sendStatus(response.status);
        })
        .catch(error => {
            console.log(error);
            return res.sendStatus(500);
        })
});

app.post('/sponsor', isLoggedIn, (req, res) => {
    axios.post(SPONSOR_URL, req.body)
        .then(response => {
            return res.sendStatus(response.status);
        })
        .catch(error => {
            console.log(error);
            return res.sendStatus(500);
        })
});

app.get('/sponsors/manage', (req, res) => {
    if (req.session[WebAppStrategy.AUTH_CONTEXT]) {
        axios.get(`${CARDS_URL}?email=${req.user.email}`)
            .then(response => {
                return res.send(response.data);
            })
            .catch(error => {
                console.log(error);
                return res.sendStatus(500);
            })
    } else {
        res.status(401).send("Unauthorized");
    }
});

app.get('/sponsors/all', (req, res) => {
    if (req.session[WebAppStrategy.AUTH_CONTEXT] && req.session[WebAppStrategy.AUTH_CONTEXT].identityTokenPayload.role === "admin") {
        axios.get(`${CARDS_URL}`)
            .then(response => {
                return res.send(response.data);
            })
            .catch(error => {
                console.log(error);
                return res.status(500);
            })
    } else {
        res.status(401).send("Unauthorized");
    }
});

///////////////////////// AppID Routes //////////////////////////////
app.get('/auth/login', passport.authenticate(WebAppStrategy.STRATEGY_NAME, {successRedirect : UI_BASE_URL, forceLogin: true}));
app.get('/auth/register', passport.authenticate(WebAppStrategy.STRATEGY_NAME, {successRedirect : `${UI_BASE_URL}/sponsor`, forceLogin: true}));
app.get(CALLBACK_URL, passport.authenticate(WebAppStrategy.STRATEGY_NAME, {allowAnonymousLogin: false}));
app.get('/auth/logged', (req, res) => {
	const loggedInAs = {};

    let logged = false;
    if (req.session[WebAppStrategy.AUTH_CONTEXT]) {
        logged = true;
        loggedInAs.name = req.user.name;
        loggedInAs.email = req.user.email;
        loggedInAs.role = req.session[WebAppStrategy.AUTH_CONTEXT].identityTokenPayload.role || "donor";
    }

    res.send({
        logged,
		loggedInAs,
        redirectUri: loggedInAs.role === "admin" ? "/admin" : null
    })
});

app.get("/auth/logout", function(req, res, next) {
	WebAppStrategy.logout(req);
    res.send("OK");
});


function isLoggedIn(req, res, next) {
	if (req.session[WebAppStrategy.AUTH_CONTEXT]) {
        next();
    } else {
        res.status(401).send("Unauthorized");
    }
}

function getLocalConfig() {
	if (!isLocal) {
		return {};
	}
	let config = {};
	const localConfig = nconf.env().file('/etc/secret-volume/app_id_credentials').get();
	const requiredParams = ['clientId', 'secret', 'tenantId', 'oauthServerUrl', 'profilesUrl'];
	requiredParams.forEach(function (requiredParam) {
		if (!localConfig[requiredParam]) {
			console.log('When running locally, make sure to create a file *local-config.json* in the root directory.');
            console.log('See config-template.json for an example of what to retrieve from your App ID instance.');
			console.log(`Required parameter is missing: ${requiredParam}`);
			process.exit(1);
		}
		config[requiredParam] = localConfig[requiredParam];
	});
    config['redirectUri'] = `${BFF_URL}${CALLBACK_URL}`;
	return config;
}

function configureSecurity() {
	app.use(helmet());
	app.use(cookieParser());
	app.use(helmet.noCache());
	app.enable("trust proxy");
	if (!isLocal) {
		app.use(express_enforces_ssl());
	}
}

app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname + '../', 'react-frontend','..', 'build', 'index.html'));
});

app.listen(port, () => {
    console.log(`Server is running on port ${port}.`);
});
