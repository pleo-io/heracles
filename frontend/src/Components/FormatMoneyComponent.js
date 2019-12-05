import React, {Component} from 'react'

class FormatMoneyComponent extends Component {


    state = {
        value: undefined
    }

    handleSubmit(event) {
        event.preventDefault()
        console.log('This is the current state: ', this.state.value)
    }

    handleChange(event){
        this.setState({
            value: event.target.value
        })
    }

    render(){
        return (
            <div>
                <div>
                    <h1>Format money</h1>
                </div>
                <form>
                    <input type="number" onChange={this.handleChange.bind(this)}/>
                    <button onClick={this.handleSubmit.bind(this)}>Send</button>
                </form>
            </div>
        )}
}

export default FormatMoneyComponent