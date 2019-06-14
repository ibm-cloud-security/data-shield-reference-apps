import React, { Component } from 'react';
import { loadUserTransactions, loadUserSponsors, deleteMonthlyDonation, resetSponsorStatusCode, resetTransactionStatusCode } from '../../../Redux';
import { connect } from 'react-redux';
import './style.css';
import '../style.css';

class Donations extends Component {
    componentDidMount() {
        window.scrollTo(0, 0);
        this.props.resetSponsorStatusCode();
        this.props.resetTransactionStatusCode();
        this.props.loadUserTransactions();
        this.props.loadUserSponsors();
    }

    remainingMonths = (unformattedData) => {
        const date = new Date(unformattedData);
        let remainingMonths = 12 - (date.getMonth() + 1) +1;
        return remainingMonths;
    }

    deleteAndUpdate = (donation, months) => {
        this.props.deleteMonthlyDonation(donation, months);
        this.props.loadUserTransactions();
    }

    mapSponsors = (donations) => {
        return donations.map((donation, index) => {
            const months = this.remainingMonths(donation.date);
            const totalContribution = months * donation.contribution;
            const contribution = `$${donation.contribution}/$${totalContribution}`

            const date = Date(donation.date).slice(4, 15);
            return (
                <tr>
                    <td>Monthly</td>
                    <td>{contribution}</td>
                    <td>{date}</td>
                    <td className="buttonCell"><button className="blue">Send Invoice</button></td>
                    <td className="buttonCell">
                        <button className="red" onClick={() => this.deleteAndUpdate(donation, months)}>Stop Donation</button>
                    </td>
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
                    <td className="buttonCell"><button className="blue">Send Invoice</button></td>
                    <td className="buttonCell"></td>
                </tr>
            );
        });
    }

    render() {
        let errorMessage;
        if (this.props.transactionsInfo.statusCode >= 200 && this.props.transactionsInfo.statusCode < 300) {
            this.props.transactionsInfo.statusCode = null;
            this.props.history.push("/donate/thankyou");
        } else if (this.props.transactionsInfo.statusCode !== null) {
            errorMessage = "We were unable to process your request. Try again later.";
            window.scrollTo(0, document.body.scrollHeight);
        }

        return (
            <div className="managePage">
                <div className="decorationBar"></div>
                <div className="manageProfile">
                    <h1>Manage Donations</h1>
                    <table>
                        <tr className="tableHeader">
                            <th>Type</th>
                            <th>Amount/Total</th>
                            <th>Date</th>
                            <th></th>
                            <th></th>
                        </tr>
                        {this.mapSponsors(this.props.sponsorsInfo.sponsors)}
                        {this.mapDonations(this.props.transactionsInfo.transactions)}
                    </table>
                    <p className="errorMessage">{errorMessage}</p>
                    <h1>Thank you for your donations, they are making the world a better place.</h1>
                </div>
            </div>
        );
    }
}

export default connect(state => state, { loadUserTransactions, loadUserSponsors, deleteMonthlyDonation, resetSponsorStatusCode, resetTransactionStatusCode })(Donations)
