import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import axios from 'axios';

const Axios = axios.create({withCredentials: true});
const nodeWrapperUrl = `${process.env.REACT_APP_bff_url}`;
// const nodeWrapperUrl = "http://localhost:8000";
const isLoggedUrl = `${nodeWrapperUrl}/auth/logged`;
const logoutUrl = `${nodeWrapperUrl}/auth/logout`;

////////////////////////////////////
// Authentication Action Creators //
////////////////////////////////////
export const firstTimeAuthentication = () => {
    return dispatch => {
        Axios.get(isLoggedUrl)
            .then(response => {
                dispatch(authenticate(response.data));
            })
            .catch(err => {
                dispatch(authenticate(initialState));
            })
    }
}

export const isAuthenticated = () => {
    return dispatch => {
        Axios.get(isLoggedUrl)
            .then(response => {
                dispatch(checkAuthentication(response.data));
            })
            .catch(err => {
                dispatch(checkAuthentication(initialState));
            })
    }
}

export const logout = () => {
    return dispatch => {
        Axios.get(logoutUrl)
            .then(response => {
                dispatch(clearState());
            })
            .catch(err => {
                dispatch(clearState());
            })
    }
}

function clearState() {
    return {
        type: "LOGOUT"
    }
}

function authenticate(user) {
    return {
        type: "FIRST_AUTHENTICATION",
        logged: user.logged,
        loggedInAs: user.loggedInAs,
        redirectUri: user.redirectUri
    }
}

function checkAuthentication(user) {
    return {
        type: "AUTHENTICATE",
        logged: user.logged,
        loggedInAs: user.loggedInAs
    }
}

//////////////////////////////////
// Transactions Action Creators //
//////////////////////////////////
function setTransactions(transactions) {
    return {
        type: "SET_TRANSACTIONS",
        transactions
    }
}

function setSponsors(sponsors) {
    return {
        type: "SET_SPONSORS",
        sponsors
    }
}

function setTransactionResponseStatus(statusCode) {
    return {
        type: "SET_TRANSACTION_STATUS_CODE",
        statusCode
    }
}

function setSponsorResponseStatus(statusCode) {
    return {
        type: "SET_SPONSOR_STATUS_CODE",
        statusCode
    }
}

export function resetTransactionStatusCode() {
    return {
        type: "SET_TRANSACTION_STATUS_CODE",
        statusCode: null
    }
}

export function resetSponsorStatusCode() {
    return {
        type: "SET_SPONSOR_STATUS_CODE",
        statusCode: null
    }
}

export function loadAllTransactions() {
    return dispatch => {
        Axios.get(`${nodeWrapperUrl}/transactions/all`)
            .then(response => {
                dispatch(setTransactions(response.data));
            })
            .catch(err => {
                console.error(err);
                dispatch(setTransactionResponseStatus(500));
            })
    }
}

export function loadUserTransactions() {
    return dispatch => {
        Axios.get(`${nodeWrapperUrl}/transactions/manage`)
            .then(response => {
                dispatch(setTransactions(response.data));
            })
            .catch(err => {
                console.error(err);
                dispatch(setTransactionResponseStatus(500));
            })
    }
}

export function loadAllSponsors() {
    return dispatch => {
        Axios.get(`${nodeWrapperUrl}/sponsors/all`)
            .then(response => {
                dispatch(setSponsors(response.data));
            })
            .catch(err => {
                console.error(err);
                dispatch(setSponsorResponseStatus(500));
            })
    }
}

export function loadUserSponsors() {
    return dispatch => {
        Axios.get(`${nodeWrapperUrl}/sponsors/manage`)
            .then(response => {
                dispatch(setSponsors(response.data));
            })
            .catch(err => {
                console.error(err);
                dispatch(setSponsorResponseStatus(500));
            })
    }
}

export function addTransaction(transaction) {
    return dispatch => {
        Axios.post(`${nodeWrapperUrl}/donate`, transaction)
            .then(response => {
                dispatch(setTransactionResponseStatus(response.status));
            })
            .catch(err => {
                console.error(err);
                dispatch(setTransactionResponseStatus(500));
            })
    }
}

export function addSponsor(transaction) {
    return dispatch => {
        Axios.post(`${nodeWrapperUrl}/sponsor`, transaction)
            .then(response => {
                dispatch(setSponsorResponseStatus(response.status));
            })
            .catch(err => {
                console.error(err);
                dispatch(setSponsorResponseStatus(500));
            })
    }
}

export function deleteMonthlyDonation(transaction, months) {
    return dispatch => {
        const id = transaction.id;
        Axios.delete(`${nodeWrapperUrl}/transactions/${id}`)
            .then(response => {

                const transactionTemplate = {...transaction};
                delete transactionTemplate.id;
                transactionTemplate.type = "One-Time";
                const date = new Date(transaction.date);

                const axiosCalls = [];
                let newTransaction;

                for (let i = 0; i < months; i++) {
                    newTransaction = {...transactionTemplate};
                    newTransaction.date = date.getTime()
                    axiosCalls.push(Axios.post(`${nodeWrapperUrl}/sponsor`, newTransaction));
                    date.setMonth(date.getMonth() + 1);
                }

                Promise.all(axiosCalls)
                    .then(response => {
                        dispatch(loadUserTransactions());
                    })
                    .catch(err => {
                        console.error(err);
                        dispatch(setTransactionResponseStatus(500));
                    })
            })
            .catch(err => {
                console.error(err);
                dispatch(setTransactionResponseStatus(500));
            })
    }
}

export function deleteAdminTransaction(transactionId) {
    return dispatch => {
        Axios.delete(`${nodeWrapperUrl}/transactions/${transactionId}`)
            .then(response => {
                dispatch(loadAllTransactions());
            })
            .catch(err => {
                console.error(err);
            })
    }
}

export function clearRedirect() {
    return {
        type: "SET_REDIRECT_URI",
        redirectUri: null
    }
}

export function setRedirect(redirectUri) {
    return {
        type: "SET_REDIRECT_URI",
        redirectUri
    }
}

const initialState = {
    transactionsInfo: {
        transactions: [],
        statusCode: null
    },
    sponsorsInfo: {
        sponsors: [],
        statusCode: null
    },
    user: {
        logged: false,
        loggedInAs: {
            name: "",
            email: "",
            role: ""
        }
    },
    redirectUri: null
}

const reducer = (state = {...initialState}, action) => {
    switch (action.type) {
        case "FIRST_AUTHENTICATION":
            return {
                ...initialState,
                user: {
                    logged: action.logged,
                    loggedInAs: action.loggedInAs
                },
                redirectUri: action.redirectUri
            }
        case "AUTHENTICATE":
            return {
                ...state,
                user: {
                    logged: action.logged,
                    loggedInAs: action.loggedInAs
                },
                redirectUri: null
            }
        case "LOGOUT":
            return initialState;
        case "SET_TRANSACTIONS":
            return {
                ...state,
                transactionsInfo: {
                    ...state.transactionsInfo,
                    transactions: [...action.transactions]
                }
            };
        case "SET_SPONSORS":
            return {
                ...state,
                sponsorsInfo: {
                    ...state.sponsorsInfo,
                    sponsors: [...action.sponsors]
                }
            };
        case "SET_SPONSOR_STATUS_CODE":
            return {
                ...state,
                sponsorsInfo: {
                    sponsors: [],
                    statusCode: action.statusCode
                }
            };
        case "SET_TRANSACTION_STATUS_CODE":
            return {
                ...state,
                transactionsInfo: {
                    transactions: [],
                    statusCode: action.statusCode
                }
            };
        case "SET_REDIRECT_URI":
            return {
                ...state,
                redirectUri: action.redirectUri
            };
        default:
            return state;
    }
};

const composeEnhancer = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

export default createStore(
    reducer,
    composeEnhancer(applyMiddleware(thunk))
);
