import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { logout } from '../../Redux';
import './style.css';

class Navbar extends Component {
    constructor() {
        super();
        this.state = {
            adminClicked: false
        }
    }

    logout = () => {
        this.props.logout();
        this.props.history.push("/");
    }

    handleClick = () => {
        this.setState(prevState => {
            return {
                adminClicked: !prevState.adminClicked
            }
        })
    }

    render() {
        const origin = `${process.env.REACT_APP_bff_url}`;
        // const origin = "http://localhost:8000";
        const appIdLoginUrl = `${origin}/auth/login`;
        //const appIdSignUpUrl = `https://appid-oauth.ng.bluemix.net/oauth/v3/8abdaf29-b419-4f16-b8ae-7d81a6cc8508/authorization?client_id=a4ac6730-4606-421e-a3af-59224f10f9d2&response_type=sign_up&redirect_uri=${origin}/ibm/bluemix/appid/callback&scope=appid_default`;
        const appIdSignUpUrl = `${process.env.REACT_APP_app_id_sign_in}`;

        return (
            <nav>
                <div className="overlay"></div>
                <Link to="/" className="left">
                    <div className="logo"></div>
                    <h1>Cloud Fund</h1>
                </Link>
                { this.props.user.logged ?
                    <div className="right">
                        <h1>{this.props.user.loggedInAs.name}</h1>
                        <div className="divider">|</div>
                        { this.props.user.loggedInAs.role === "admin" ?
                            <div>
                                <h1 onClick={this.handleClick} className="highlight">Manage Donations</h1>
                                <div className="dropdownMenu" style={{display: this.state.adminClicked ? "block" : "none"}}>
                                    <Link to="/donations" onClick={this.handleClick}>Your Donations</Link>
                                    <Link to="/admin" onClick={this.handleClick} className="bottom">All Donations</Link>
                                </div>
                            </div>
                            :
                            <Link to="/donations">Manage Donations</Link>
                        }
                        <div className="divider">|</div>
                        <h1 onClick={() => this.logout()} className="highlight">Log Out</h1>
                    </div>
                    :
                    <div className="right">
                        <a href={appIdSignUpUrl}>Sign Up</a>
                        <div className="divider">|</div>
                        <a href={appIdLoginUrl}>Log In</a>
                    </div>
                }
            </nav>
        )
    }
}

export default withRouter(connect(state => state, { logout })(Navbar));
