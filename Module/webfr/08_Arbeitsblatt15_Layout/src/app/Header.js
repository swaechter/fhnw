import React from 'react';
import {Jumbotron} from 'reactstrap';

const Header = ({title, subtitle}) => (
    <Jumbotron>
        <h1>{title}</h1>
        <h3>{subtitle}</h3>
    </Jumbotron>
)
export default Header;
