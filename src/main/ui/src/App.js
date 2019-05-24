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
        <div className="row">
          <div className="col-md-8 bg-white rounded ">
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
              width="95%"
              height="80vh"
            />
          </div>
          <div className="col-md-4">
            <div class="card text-white bg-info mb-3">
              <div class="card-body">
                <h4 class="card-title">Info card title</h4>
                <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default App;