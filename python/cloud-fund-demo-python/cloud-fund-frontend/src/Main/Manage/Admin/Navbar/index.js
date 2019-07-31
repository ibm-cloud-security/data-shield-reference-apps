import React from 'react';
import { Link, withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
import { logout } from '../../../../Redux';
import './style.css';

const AdminNavbar = props => {
    const logout = () => {
        props.logout();
        props.history.push("/");
    }

    return (
        <div className="adminNavbar">
            <h1 className="left">Cloud Fund Management</h1>
            <div className="right">
                <Link to="/">Home</Link>
                <div className="divider">|</div>
                <h1 onClick={() => logout()}>Log Out</h1>
            </div>
        </div>
    )
}

export default withRouter(connect(state => state.user, { logout })(AdminNavbar));
