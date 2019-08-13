import React from "react";
const NavBar = props => {
    return (
        <nav className="navbar navbar-expand-lg navbar-light bg-light p-0">
            <a className="navbar-brand pl-3" href="#">Angle Tag</a>
           
            <div className="navbar-collapse" id="navbarColor01">
                <div className="mr-auto">
                <div className="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" className="btn dropdown-toggle btn-nav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i className="far fa-folder-open"></i>&nbsp;File
                    </button>
                    <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <button className="dropdown-item" onClick={props.openClick}>Open xm/xslt/xsd/xqy</button>
                        <button className="dropdown-item" onClick={props.saveToFile}>Save</button>
                    </div>
                </div>
                <div className="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" className="btn dropdown-toggle btn-nav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i className="fas fa-code"></i>&nbsp;Code
                    </button>
                    <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                    <button className="dropdown-item" onClick={props.format}>Format</button>
                    <button className="dropdown-item" onClick={props.serialize}>Serialize</button>
                    <button className="dropdown-item" onClick={props.deSerialize}>DeSerialize</button>
                    <button className="dropdown-item" onClick={props.xpathEval} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">Evaluate Xpath</button>
                    </div>
                </div>
                 
                 <div className="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" className="btn dropdown-toggle btn-nav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i className="fa fa-check"></i>&nbsp;XSD
                    </button>
                    <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <button className="dropdown-item" onClick={props.validateXML} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">Validate XML(Single xsd)</button>
                        <button className="dropdown-item" onClick={props.generateXML} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">Generate XML</button>
                        <button className="dropdown-item" onClick={props.viewInGraph}>View in Graph</button>
                        <button className="dropdown-item" onClick={props.validateMultiXML} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">Validate XML(Multiple xsd)</button>
                    </div>
                </div>
                <div className="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" className="btn dropdown-toggle btn-nav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i className="fas fa-exchange-alt"></i>&nbsp;Transform
                    </button>
                    <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                    <button className="dropdown-item" onClick={props.transformXslt} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">XSLT</button>
                    <button className="dropdown-item" onClick={props.transformXquery} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">Xquery</button>
                    </div>
                </div>
                
                </div>
                <div className="my-2 my-lg-0">
                <button className="btn btn-nav" type="button">
                    <i className="fas fa-sign-in-alt"></i> login
                </button>
                </div>
            </div>
        </nav>
        );
    }
        
export default NavBar;