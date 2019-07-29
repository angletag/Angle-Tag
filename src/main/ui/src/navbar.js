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
                        <button className="dropdown-item" onClick={props.saveClick}>Save</button>
                    </div>
                </div>
                 
                 <button className="btn btn-nav" onClick={props.format}><i className="fas fa-code"></i>&nbsp;Format</button>
                 <button className="btn btn-nav" onClick={props.serialize}><i className="fas fa-grip-lines"></i>&nbsp;Serialize</button>
                 <button className="btn btn-nav" onClick={props.deSerialize}><i className="fas fa-grip-lines-vertical"></i>&nbsp;DeSerialize</button>
                 <button className="btn btn-nav" onClick={props.xpathEval} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false"><i className="far fa-check-circle"></i>&nbsp;Evaluate Xpath</button>
                 <div className="btn-group" role="group">
                    <button id="btnGroupDrop1" type="button" className="btn dropdown-toggle btn-nav" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <i className="fa fa-check"></i>&nbsp;XSD
                    </button>
                    <div className="dropdown-menu" aria-labelledby="btnGroupDrop1">
                        <button className="dropdown-item" onClick={props.validateXML}>Validate XML(select single/Multiple xsd)</button>
                        <button className="dropdown-item" onClick={props.generateXML}>Generate XML</button>
                    </div>
                </div>
                 <button className="btn btn-nav" onClick={props.transformXslt} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false"><i className="fas fa-exchange-alt"></i>&nbsp;XSLT Transform</button>
                 <button className="btn btn-nav" onClick={props.transformXquery} data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false"><i className="fas fa-carrot"></i>&nbsp;Xquery Transform</button>
                
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