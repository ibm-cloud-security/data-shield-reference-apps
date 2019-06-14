import React from 'react';
import { withRouter } from 'react-router-dom';
import './style.css';
import { connect } from 'react-redux';

const BecomeASponsor = props => {
    if (props.logged) {
        props.history.push("/sponsor");
    }

    const origin = `${process.env.REACT_APP_bff_url}`;
    // const origin = "http://localhost:8000";
    const appIdLoginUrl = `${origin}/auth/register`;
    const appIdSignUpUrl = `${process.env.REACT_APP_app_id_sign_in}`;

    return (
        <div className="changingComponent becomeASponsor">
            <p>Become a guardian for every child by making your first monthly gift.</p>
            <a href={appIdSignUpUrl}>
                <div className="buttonContainer">
                    <button>Register</button>
                </div>
            </a>
            <p>Already Registered? <a href={appIdLoginUrl}>Login</a></p>
        </div>
    );
}

export default withRouter(connect(state => state.user)(BecomeASponsor));
