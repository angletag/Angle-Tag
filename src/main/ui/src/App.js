import React, { Component } from "react";
import AceEditor from "react-ace";
import "brace/mode/jsx";
import "brace/ext/language_tools";
import "brace/ext/searchbox";
import axios from "axios";
import NavBar from "./navbar";
import beautify from 'xml-beautifier';
import * as _ from "underscore";

const languages = [
  "xml",
];

const themes = [
  "monokai",
  "github",
  "tomorrow",
  "kuroir",
  "twilight",
  "xcode",
  "textmate",
  "solarized_dark",
  "solarized_light",
  "terminal",
  "tomorrow_night_eighties",
  "dracula",

];
languages.forEach(lang => {
  require(`brace/mode/${lang}`);
  require(`brace/snippets/${lang}`);
});

themes.forEach(theme => {
  require(`brace/theme/${theme}`);
});
/*eslint-disable no-alert, no-console */

const defaultValue = "<?xml version='1.0' encoding='UTF-8'?>"

const domain = {
  dev: process.env.REACT_APP_DOMAIN
}

class App extends Component {

  openClick() {
    this.refs.fileUploader.click();
  }

  onChange(newValue) {
    //console.log("change", newValue);
    this.setState({
      value: newValue
    });
  }

  onSelectionChange(newValue, event) {
    // console.log("select-change", newValue);
    //console.log("select-change-event", event);
  }

  onCursorChange(newValue, event) {
    // console.log("cursor-change", newValue);
    // console.log("cursor-change-event", event);
  }

  onValidate(annotations) {
    // console.log("onValidate", annotations);
  }

  setPlaceholder(e) {
    this.setState({
      placeholder: e.target.value
    });
  }
  setTheme(e) {
    this.setState({
      theme: e.target.value
    });
  }
  setMode(e) {
    this.setState({
      mode: e.target.value
    });
  }
  setBoolean(name, value) {
    this.setState({
      [name]: value
    });
  }
  setFontSize(e) {
    this.setState({
      fontSize: parseInt(e.target.value, 10)
    });
  }
  loadFromServer() {
    console.log("Calling from server");
    var myObj = this;
    console.log(myObj);
    axios.get("http://localhost:8080/api/xml/test", { responseType: 'text' }).then(
      res => {
        // console.log("load from server", res);
        myObj.setState({
          value: res.data
        });
      })
  }
  constructor(props) {
    super(props);
    this.state = {
      value: defaultValue,
      placeholder: "<?xml version='1.0' encoding='UTF-8'?>",
      theme: "xcode",
      mode: "xml",
      enableBasicAutocompletion: true,
      enableLiveAutocompletion: true,
      fontSize: 16,
      showGutter: true,
      showPrintMargin: true,
      highlightActiveLine: true,
      enableSnippets: false,
      showLineNumbers: true,
      result: "",
      xpathVersion:"1.0",
      xpath:"",
      xslt:"",
      output:"",
      param:""
    };
    this.setPlaceholder = this.setPlaceholder.bind(this);
    this.setTheme = this.setTheme.bind(this);
    this.setMode = this.setMode.bind(this);
    this.onChange = this.onChange.bind(this);
    this.setFontSize = this.setFontSize.bind(this);
    this.setBoolean = this.setBoolean.bind(this);

  }
  render() {
    return (
      <div>
        <NavBar
          openClick={() => this.openClick()}
          format={() => this.format()}
          serialize={()=>this.serialize()}
          deSerialize={()=>this.deserialize()}
          xpathEval={()=>this.evaluateXpath()}
          transformXslt={()=>this.transformXslt()}
          transformXquery={()=>this.transformXquery()}
          >

          </NavBar>
        <div className="row mt-1">
          <div className="col-md-8">
            <div className="card text-white ace-container ">
              <div className="card-body p-1">
                <AceEditor
                  placeholder={this.state.placeholder}
                  mode={this.state.mode}
                  theme={this.state.theme}
                  name="angleEditor"
                  onLoad={this.onLoad}
                  onChange={this.onChange}
                  onSelectionChange={this.onSelectionChange}
                  onCursorChange={this.onCursorChange}
                  onValidate={this.onValidate}
                  value={this.state.value}
                  fontSize={this.state.fontSize}
                  showPrintMargin={this.state.showPrintMargin}
                  showGutter={this.state.showGutter}
                  highlightActiveLine={this.state.highlightActiveLine}
                  setOptions={{
                    enableBasicAutocompletion: this.state.enableBasicAutocompletion,
                    enableLiveAutocompletion: this.state.enableLiveAutocompletion,
                    enableSnippets: this.state.enableSnippets,
                    showLineNumbers: this.state.showLineNumbers,
                    tabSize: 2
                  }}
                  width="100%"
                  height="85vh"
                  ref="aceEditor"
                />
              </div>
            </div>
            <div><small>(ctrl+z->undo);(ctrl+y->redo);(ctrl+x->cut);(ctrl+c->copy);(ctrl+p->paste)</small></div>
          </div>
          <div className="col-md-4 pl-0">
            <div className="card text-white bg-right-side mb-1 ">
              <div className="card-body">
                <div className="form-group">
                  <label >Xpath Evaluate&nbsp;</label>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="xpath-version" id="xpath-version-1" value="1.0" checked={this.state.xpathVersion === '1.0'} onChange={()=>this.xpathChange('1.0')}/>
                    <label className="form-check-label">verison 1</label>
                  </div>
                  <div className="form-check form-check-inline">
                    <input className="form-check-input" type="radio" name="xpath-version" id="xpath-version-2" value="2.0" checked={this.state.xpathVersion === '2.0'} onChange={()=>this.xpathChange('2.0')}/>
                    <label className="form-check-label">verison 2</label>
                  </div>
                  <button className="btn btn-success" data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">View Result</button>
                  <textarea className="form-control" id="xpath" rows="2" onChange={this.xpathHandler.bind(this)}  value={this.state.xpath}></textarea>
                </div>
              </div>
            </div>
            <div className="card text-white bg-right-side mb-1">
              <div className="card-body">
                <div className="form-group">
                  <label>Select XSLT/Xquery for transform</label> <button className="btn btn-success" data-toggle="modal" data-target="#showTransformResult" data-backdrop="static" data-keyboard="false">View Result</button>
                  <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp" onChange={this.readXsltFile.bind(this)} onClick={(event) => { event.target.value = null }}/>
                  {
                    //<small id="fileHelp" className="form-text">This is some placeholder block-level help text for the above input. It's a bit lighter and easily wraps to a new line.</small>
                  }
                </div>
                <div className="form-group">
                  <label>Xslt Or Xquery Params</label>
                  <textarea className="form-control" id="exampleTextarea" rows="2" value={this.state.param} onChange={this.handleParam.bind(this)}></textarea>
                  <small id="fileHelp" className="form-text">Ex)  param1=value1;param2=value2; (no single or double quotes)</small>
                </div>
              </div>
            </div>
            <div className="card text-white bg-right-side mb-1">
              <div className="card-body">
                <div className="form-group">
                  <label>Select XSD(S)</label>
                  <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp" />
                  <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp" />
                  <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp" />
                  <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp" />
                  <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <input type="file" id="file" ref="fileUploader" style={{ display: "none" }} onChange={this.readInputFile.bind(this)} onClick={(event) => { event.target.value = null }}/>
        <div className="modal fade" id="showTransformResult" role="dialog" ref="showTransformResult" >
          <div className="modal-dialog modal-dialog-centered model-liquid-xl" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Result</h5>
                <button type="button" className="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div className="modal-body">
                <textarea rows="20" className="form-control" value={this.state.output} readOnly></textarea>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }


  // Code for functionlity 
  format() {
    let selectedXml = this.state.value.trim();
    if (selectedXml === "") {
      alert("Please provide input xml");
      return false;
    }

    let self = this;
    if (selectedXml.includes("<xsl:stylesheet")) { // For xslt only
      let formatedData = beautify(selectedXml);
      //console.log(formatedData);
      self.setState({ value: formatedData });
    }
    else {
      let data = {
        xml: selectedXml
      }
      console.log(process.env.REACT_APP_DOMAIN);
      axios.post(domain.dev.concat("/api/xml/formatXML"),
        data,
        { responseType: "text" }
      ).then(response => {
        let formatedData = response.data;
        self.setState({ value: formatedData });
      }).catch(error => {
        console.log(error);
        alert("Error occured while format XML");
      });

    }
  }

  readInputFile(event) {
    let self = this;
    let fileReader = new FileReader();
    fileReader.readAsText(event.target.files[0], "UTF-8");
    fileReader.onload = () => {
      let xml = fileReader.result.trim()
      self.setState({ value: xml });
    }
  }

  deserialize() {
    let selectedXml = this.state.value.trim();
    if (selectedXml === "") {
      alert("Please provide input xml");
      return false;
    }
    let formatedXMl = _.unescape(selectedXml);
    this.setState({ value: formatedXMl });
  }

  serialize() {
    let selectedXml = this.state.value.trim();
    if (selectedXml === "") {
      alert("Please provide input xml");
      return false;
    }
    let formatedXMl = _.escape(selectedXml);
    this.setState({ value: formatedXMl });
  }

  xpathChange(v){
    this.setState({xpathVersion:v})
  }

  xpathHandler(e){
    this.setState({xpath:e.target.value})
  }

  handleParam(e){
    this.setState({param:e.target.value})
  }

  evaluateXpath() {
    let selectedXml = this.state.value.trim();
    if (selectedXml === "") {
      alert("Please provide input xml");
      return false;
    }
    let xpath = this.state.xpath;
    if (xpath === "") {
      alert("Please provide input xpath");
      return false;
    }
    let xpathV=this.state.xpathVersion
    let self = this;
    let data = {
      xml: selectedXml,
      xpathValue: xpath,
      version: xpathV
    }

    axios.post(domain.dev.concat("/api/xml/xpath"), data, { responseType: "text" }
    ).then(response => {
      console.log(response)
      let value = response.data;
      self.setState({ output: value });
    }).catch(error => {
      console.log(error);
      alert("Error occured while doing xpath evaluation");
    });
  }

  readXsltFile(event) {
    let self = this;
    let fileReader = new FileReader();
    fileReader.readAsText(event.target.files[0], "UTF-8");
    fileReader.onload = () => {
      let xml = fileReader.result.trim()
      self.setState({ xslt: xml });
    }
  }

  transformXslt() {
    let selectedXml = this.state.value
    if (selectedXml === "") {
      alert("Please provide input xml");
      return false;
    }
    let xpath = this.state.xslt
    if (xpath === "") {
      alert("Please provide input XSLT");
      return false;
    }
    let self = this;
    let data = {
      xml: selectedXml,
      xqueryValue: xpath,
      param: this.state.param
    }
    axios.post(domain.dev.concat("/api/xml/xslt"),
      data,
      { responseType: "text" }
    ).then(response => {
      let output = response.data;
      self.setState({ output:output });
    }).catch(error => {
      console.log(error);
      alert("Error occured while doing xslt transformation");
    });
  }

  transformXquery() {
   
    let selectedXml = this.state.value
    if (selectedXml === "") {
      alert("Please provide input xml");
      return false;
    }
    let xpath = this.state.xslt
    if (xpath === "") {
      alert("Please provide input Xquery");
      return false;
    }
    let self = this;

    let data = {
      xml: selectedXml,
      xqueryValue: xpath,
      param: this.state.param
    }
    axios.post(domain.dev.concat("/api/xml/xquery"),
      data,
      { responseType: "text" }
    ).then(response => {
      let output = response.data;
      self.setState({ output:output });
    }).catch(error => {
      console.log(error);
      alert("Error occured while doing xquery transformation");
    });
  }
}

export default App;