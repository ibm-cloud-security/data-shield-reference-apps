import React from 'react';
import { connect } from "react-redux";
import { Route, Redirect } from "react-router-dom";

const ProtectedRoute = props => {
    const isAuthenticated = props.logged;
    const Component = props.component;
    const path = props.path;

    const renderMethod = props.render ? props.render : (props) => {
        return <Component {...props} />
    };

    return (
        isAuthenticated ?
            <Route path={path} render={renderMethod} /> :
            <Redirect to="/" />
    );
}

export default connect(state => state.user, {})(ProtectedRoute);
