import React from "react";
const NavBar = props => {
    return (
        <nav class="navbar navbar-expand-lg navbar-light bg-light p-0">
            <a class="navbar-brand pl-3" href="#">Angle Tag</a>
           
            <div class="navbar-collapse" id="navbarColor01">
                <div class="mr-auto">
                 <button class="btn btn-nav" type="submit"><i class="far fa-folder-open"></i>&nbsp;Open</button>
                 <button class="btn btn-nav" type="submit"><i class="fas fa-code"></i>&nbsp;Format</button>
                 <button class="btn btn-nav" type="submit"><i class="fas fa-grip-lines"></i>&nbsp;Serialize</button>
                 <button class="btn btn-nav" type="submit"><i class="fas fa-grip-lines-vertical"></i>&nbsp;DeSerialize</button>
                 <button class="btn btn-nav" type="submit"><i class="far fa-check-circle"></i>&nbsp;Evaluate Xpath</button>
                 <button class="btn btn-nav" type="submit"><i class="fas fa-check"></i>&nbsp;Validate XML(XSD)</button>
                 <button class="btn btn-nav" type="submit"><i class="fas fa-exchange-alt"></i>&nbsp;XSLT Transform</button>
                 <button class="btn btn-nav" type="submit"><i class="fas fa-carrot"></i>&nbsp;Xquery Transform</button>

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