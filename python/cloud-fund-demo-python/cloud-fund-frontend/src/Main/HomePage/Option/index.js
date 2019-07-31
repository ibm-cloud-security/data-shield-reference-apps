import React from 'react';
import { Link } from 'react-router-dom';
import './style.css';

const Option = props => {
    const descriptionList = props.description.map((description, index) => {
        return <p>{description}</p>;
    });

    return(
        <div className="option">
            <div className="sectionContainer">
                <div className="overlay"></div>
                <Link to={props.path} className="imageContainer">
                    <div className="caption">{props.caption}</div>
                    <div className="image" id={props.id}></div>
                </Link>
            </div>
            <div>
                {descriptionList}
            </div>
        </div>
    );
}

export default Option;
