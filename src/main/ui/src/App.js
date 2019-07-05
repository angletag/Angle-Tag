import React, { Component } from "react";
import AceEditor from "react-ace";
import "brace/mode/jsx";
import "brace/ext/language_tools";
import "brace/ext/searchbox";
import axios from "axios";
import NavBar from "./navbar";

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


class App extends Component {

  openClick(){
    this.refs.fileUploader.click();
  }

  onChange(newValue) {
    console.log("change", newValue);
    this.setState({
      value: newValue
    });
  }

  onSelectionChange(newValue, event) {
    console.log("select-change", newValue);
    console.log("select-change-event", event);
  }

  onCursorChange(newValue, event) {
    console.log("cursor-change", newValue);
    console.log("cursor-change-event", event);
  }

  onValidate(annotations) {
    console.log("onValidate", annotations);
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
        console.log("load from server", res);
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
      showLineNumbers: true
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
        <NavBar openClick={()=>this.openClick()}></NavBar>
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
                height="70vh"
                />
              </div>
            </div>
            <div>Results</div>
          </div>
          <div className="col-md-4 pl-0">
            <div className="card text-white bg-right-side mb-1 ">
              <div className="card-body">
                <div className="form-group">
                  <label >Xpath Evaluate&nbsp;</label>
                    <div className="form-check form-check-inline">
                      <input className="form-check-input" type="radio" name="xpath-version" id="xpath-version-1" value="1"/>
                      <label className="form-check-label">verison 1</label>
                    </div>
                    <div className="form-check form-check-inline">
                      <input className="form-check-input" type="radio" name="xpath-version" id="xpath-version-2" value="2"/>
                      <label className="form-check-label">verison 2</label>
                    </div>
                    <textarea className="form-control" id="xpath" rows="2"></textarea>
                 </div>              
              </div>
            </div>
            <div className="card text-white bg-right-side mb-1">
              <div className="card-body">
                <div className="form-group">
                <label>Select XSLT/Xquery for transform</label>
                    <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    {
                      //<small id="fileHelp" className="form-text">This is some placeholder block-level help text for the above input. It's a bit lighter and easily wraps to a new line.</small>
                    }
                 </div>
                 <div className="form-group">
                    <label>Xslt Or Xquery Params</label>
                    <textarea className="form-control" id="exampleTextarea" rows="2"></textarea>
                    <small id="fileHelp" className="form-text">Ex)  param1=value1;param2=value2; (no single or double quotes)</small>
                  </div>
              </div>
            </div>
            <div className="card text-white bg-right-side mb-1">
              <div className="card-body">
              <div className="form-group">
                <label>Select XSD(S)</label>
                    <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" className="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                 </div>
              </div>
            </div>
          </div>
        </div>
        <input type="file" id="file" ref="fileUploader" style={{display: "none"}}/>
      </div>
    );
  }
}

export default App;