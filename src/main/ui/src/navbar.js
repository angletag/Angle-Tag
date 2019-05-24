import React from "react";
const NavBar = props => {
    return (
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <a class="navbar-brand" href="#">Angle Tag</a>
           
            <div class="navbar-collapse" id="navbarColor01">
                <div class="mr-auto">
                 <button class="btn btn-nav" type="submit">Search</button>
                 <button class="btn btn-nav" type="submit">Search</button>
                 <button class="btn btn-nav" type="submit">Search</button>
                </div>
                <div class="my-2 my-lg-0">
                <button class="btn btn-nav" type="button">
                    <i class="fas fa-bars"></i>
                </button>
                </div>
            </div>
        </nav>
        );
    }
        
export default NavBar;