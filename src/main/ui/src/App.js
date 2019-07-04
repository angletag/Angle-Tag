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
      theme: "",
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
        <NavBar>></NavBar>
        <div className="row mt-1">
          <div className="col-md-8">
          <div class="card text-white bg-secondary ">
              <div class="card-body p-1">
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
                height="75vh"
                />
              </div>
            </div>
            <div>Results</div>
          </div>
          <div className="col-md-4 pl-0">
            <div class="card text-white bg-info mb-1">
              <div class="card-body">
                <div class="form-group">
                  <label for="exampleTextarea">Xpath Evaluate</label>
                    <textarea class="form-control" id="exampleTextarea" rows="2"></textarea>
                 </div>
                 <div class="form-group">
                    <label for="exampleSelect1">Choose Xpath Version</label>
                    <select class="form-control" id="exampleSelect1">
                      <option>1</option>
                      <option>2</option>
                      <option>3</option>
                      <option>4</option>
                      <option>5</option>
                    </select>
                  </div>                
              </div>
            </div>
            <div class="card text-white bg-success mb-1">
              <div class="card-body">
                <div class="form-group">
                <label for="exampleInputFile">Select XSLT/Xquery for transform</label>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    {
                      //<small id="fileHelp" class="form-text">This is some placeholder block-level help text for the above input. It's a bit lighter and easily wraps to a new line.</small>
                    }
                 </div>
                 <div class="form-group">
                    <label for="exampleTextarea">Xslt Or Xquery Params</label>
                    <textarea class="form-control" id="exampleTextarea" rows="2"></textarea>
                    <small id="fileHelp" class="form-text">Ex)  param1=value1;param2=value2; (no single or double quotes)</small>
                  </div>
              </div>
            </div>
            <div class="card text-white bg-warning mb-1">
              <div class="card-body">
              <div class="form-group">
                <label for="exampleInputFile">Select XSD(S)</label>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                    <input type="file" class="form-control-file" id="exampleInputFile" aria-describedby="fileHelp"/>
                 </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default App;