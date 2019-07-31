import React from 'react';
import './style.css';

const OneTimeThankYou = () => {
    const title = "Thank you for your monthly donation";
    const text = "Thank you for choosing to support the Cloud Fund. Your contribution helps children in a disaster-stricken area.  We're grateful for your generous support!";

    return (
        <div className="changingComponent sponsorThankYou">
            <h1>{title}</h1>
            <p>{text}</p>
        </div>
    );
}

export default OneTimeThankYou;
