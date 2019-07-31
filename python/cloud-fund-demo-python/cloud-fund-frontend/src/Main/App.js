import React, { Component } from 'react';
import Navbar from './Navbar';
import { Route, Switch, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { firstTimeAuthentication, clearRedirect } from '../Redux';
import HomePage from './HomePage';
import DonationsComponent from './DonationsComponent';
import DonationThankYou from './DonationsPages/DonationThankYou';
import SponsorThankYou from './DonationsPages/SponsorThankYou';
import Donate from './DonationsPages/Donate';
import Sponsor from './DonationsPages/Sponsor';
import BecomeASponsor from './DonationsPages/BecomeASponsor';
import ManageProfile from './Manage/Profile';
import Admin from './Manage/Admin';
import ProtectedRoute from './ProtectedRoute';

class App extends Component {
    componentDidMount() {
        if (!this.props.user || Object.keys(this.props.user).length === 0 || this.props.user.logged === false) {
            this.props.firstTimeAuthentication();
        }
    }

    donationPageRender = (info) => {
        return () => {
            return (
                <DonationsComponent
                    page={info.page}
                    pageName={info.pageName}
                    caption={info.caption}
                />
            );
        }
    }

    render() {
        if (this.props.redirectUri !== null) {
            const redirectUri = this.props.redirectUri;
            this.props.clearRedirect();
            this.props.history.push(redirectUri);
        }

        const donationPagesInfo = [
            {
                page: <DonationThankYou />,
                route: '/donate/thankyou',
                pageName: 'donationThankYou',
                caption: '',
                protected: false
            },
            {
                page: <SponsorThankYou />,
                route: '/sponsor/thankyou',
                pageName: 'sponsorThankYou',
                caption: '',
                protected: true
            },
            {
                page: <Donate />,
                route: '/donate',
                pageName: 'donate',
                caption: 'Help children in a disaster-stricken area',
                protected: false
            },
            {
                page: <Sponsor />,
                route: '/sponsor',
                pageName: 'sponsor',
                caption: 'Be a sponsor, help each month',
                protected: true
            },
            {
                page: <BecomeASponsor />,
                route: '/sponsor/register',
                pageName: 'becomeASponsor',
                caption: 'Be a sponsor, help each month',
                protected: false
            }
        ];

        const donationsPages = donationPagesInfo.map((info, index) => {
            return info.protected ?
                <ProtectedRoute exact path={`${info.route}`} render={this.donationPageRender(info)}/>
                :
                <Route exact path={`${info.route}`} render={this.donationPageRender(info)}/>
        });

        return (
            <div>
                <Navbar />
                <Switch>
                    <Route exact path='/' component={HomePage}/>
                    {donationsPages}
                    <ProtectedRoute exact path='/donations' component={ManageProfile}/>
                    <ProtectedRoute exact path='/admin' component={Admin}/>
                </Switch>
            </div>
        )
    }
}

export default withRouter(connect(state => state, { firstTimeAuthentication, clearRedirect })(App));
