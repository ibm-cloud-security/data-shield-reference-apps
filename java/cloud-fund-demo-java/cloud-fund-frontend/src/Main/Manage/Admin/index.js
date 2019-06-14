import React, { Component } from 'react';
import { loadAllTransactions, loadAllSponsors } from '../../../Redux';
import { connect } from 'react-redux';
import './style.css';
import '../style.css';
import AdminNavbar from './Navbar';

class Admin extends Component {
    componentDidMount() {
        window.scrollTo(0, 0);
        this.props.loadAllTransactions();
        this.props.loadAllSponsors();
    }

    amountOfMonthsThatHavePassed = (unformattedDate) => {
        const currentDate = new Date(Date.now());
        const date = new Date(unformattedDate);
        let months;
        months = (currentDate.getFullYear() - date.getFullYear()) * 12;
        months -= date.getMonth() + 1;
        months += currentDate.getMonth();
        return months <= 0 ? 0 : months;
    }

    mapSponsors = (donations) => {
        return donations.map((donation, index) => {
            const months = this.amountOfMonthsThatHavePassed(donation.date) + 1;
            const totalContribution = (months + 1) * donation.contribution;
            const contribution = `$${donation.contribution}/$${totalContribution}`

            const date = Date(donation.date).slice(4, 15);
            return (
                <tr>
                    <td>Monthly</td>
                    <td>{contribution}</td>
                    <td>{date}</td>
                </tr>
            );
        });
    }

    mapDonations = (donations) => {
        return donations.map((donation, index) => {
            let contribution = `$${donation.contribution}`;
            const date = Date(donation.date).slice(4, 15);
            return (
                <tr>
                    <td>One-Time</td>
                    <td>{contribution}</td>
                    <td>{date}</td>
                </tr>
            );
        });
    }

    render() {
        return (
            <div className="managePage">
                <AdminNavbar />
                <div className="decorationBar"></div>
                <div className="admin">
                    <h1>Recent Donations</h1>
                    <table>
                        <tr className="tableHeader">
                            <th>Amount/Total</th>
                            <th>Type</th>
                            <th>Date/Time</th>
                        </tr>
                        {this.mapSponsors(this.props.sponsorsInfo.sponsors)}
                        {this.mapDonations(this.props.transactionsInfo.transactions)}
                    </table>
                </div>
            </div>
        );
    }
}

export default connect(state => state, { loadAllTransactions, loadAllSponsors })(Admin);
