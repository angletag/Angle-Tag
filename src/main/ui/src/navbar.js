import React from "react";
const NavBar = props => {
    return (
        <nav class="navbar navbar-expand-lg navbar-light bg-light p-0">
            <a class="navbar-brand pl-3" href="#">Angle Tag</a>
           
            <div class="navbar-collapse" id="navbarColor01">
                <div class="mr-auto">
                 <button class="btn btn-nav" type="submit">Open</button>
                 <button class="btn btn-nav" type="submit">Format</button>
                 <button class="btn btn-nav" type="submit">Serialize</button>
                 <button class="btn btn-nav" type="submit">DeSerialize</button>
                 <button class="btn btn-nav" type="submit">Evaluate Xpath</button>
                 <button class="btn btn-nav" type="submit">Validate XML(XSD)</button>
                 <button class="btn btn-nav" type="submit">Validate XML(Multi XSD)</button>
                 <button class="btn btn-nav" type="submit">XSLT Transform</button>
                 <button class="btn btn-nav" type="submit">Xquery Transform</button>

                </div>
                <div class="my-2 my-lg-0">
                <button class="btn btn-nav" type="button">
                    <i class="fas fa-bars"></i> login
                </button>
                </div>
            </div>
        </nav>
        );
    }
        
export default NavBar;