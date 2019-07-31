import React from 'react';
import './style.css';

const Donations = props => {
    window.scrollTo(0, 0);

    return (
        <div className="donationsPage">
            <div className="decorationBar"></div>
            {props.page}
            <div className={`image ${props.pageName}Image`}>
                {props.caption !== "" && <h1 className="caption">{props.caption}</h1>}
            </div>
        </div>
    );
}

export default Donations;
