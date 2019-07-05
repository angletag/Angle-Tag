import React from "react";
const NavBar = props => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light p-0">
            <a className="navbar-brand pl-3" href="#">Angle Tag</a>
           
            <div className="navbar-collapse" id="navbarColor01">
                <div className="mr-auto">
                 <button className="btn btn-nav" onClick={props.openClick}><i className="far fa-folder-open"></i>&nbsp;Open</button>
                 <button className="btn btn-nav" ><i className="fas fa-code"></i>&nbsp;Format</button>
                 <button className="btn btn-nav" ><i className="fas fa-grip-lines"></i>&nbsp;Serialize</button>
                 <button className="btn btn-nav" ><i className="fas fa-grip-lines-vertical"></i>&nbsp;DeSerialize</button>
                 <button className="btn btn-nav" ><i className="far fa-check-circle"></i>&nbsp;Evaluate Xpath</button>
                 <button className="btn btn-nav" ><i className="fas fa-check"></i>&nbsp;Validate XML(XSD)</button>
                 <button className="btn btn-nav" ><i className="fas fa-exchange-alt"></i>&nbsp;XSLT Transform</button>
                 <button className="btn btn-nav" ><i className="fas fa-carrot"></i>&nbsp;Xquery Transform</button>
                
                </div>
                <div className="my-2 my-lg-0">
                <button className="btn btn-nav" type="button">
                    <i class="fas fa-sign-in-alt"></i> login
                </button>
                </div>
            </div>
        </nav>
        );
    }
        
export default NavBar;