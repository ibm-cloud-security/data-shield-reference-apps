import React from 'react';
import Option from './Option';
import { Link } from 'react-router-dom';
import './style.css';

const HomePage = () => {
    window.scrollTo(0, 0);

    const options = [
        {
            description: ["Drought. Famine. Monsoons. A crisis of catastrophic proportions in Bangladesh. Children are in desperate need around the world, and Cloud Fund is on the ground helping.", "Cloud Fund supports humanitarian relief work in 190 countries and territories. Your charitable donations to Cloud Fund support child survival and the development of youth worldwide."],
            caption: "Make a One-Time Donation",
            path: "/donate",
            id: "oneTimeDonationThumbnail"
        }, {
            description: ["Cloud Fund monthly donors are united by their commitment to a common mission: putting children first. Their steadfast support allows Cloud Fund to be there before, during and long after a conflict or disaster — building the foundations that every child needs to survive and thrive."],
            caption: "Become a Monthly Donor",
            path: "/sponsor/register",
            id: "monthlyDonorThumbnail"
        }
    ];

    const optionList = options.map((option, index) => {
        return <Option
                    description={option.description}
                    imgURL={option.imgURL}
                    key={index + option.name}
                    caption={option.caption}
                    path={option.path}
                    id={option.id}/>
    });

    return (
        <div className="homePage">
            <header>
                <div className="headerText">
                    <h1>Donate for Children in Need</h1>
                    <Link to="/donate">
                        <div className="buttonContainer">
                            <button>Donate Now</button>
                        </div>
                    </Link>
                </div>
                <div className="banner"></div>
            </header>
            <div>
                {optionList}
            </div>
        </div>
    );
}

export default HomePage;