import React, {Component} from 'react'
import {formatMoney} from '../requesters/formatMoney'
class FormatMoneyComponent extends Component {

    state = {
        value: undefined,
        received: undefined
    }

    async handleSubmit(event) {
        event.preventDefault()
        try {
            const res = await formatMoney({value: this.state.value})
            this.setState({received: res.data.value})
            console.log('Input: ', this.state.value)
            console.log('Output: ', res.data.value)
        } catch {
            this.setState({received: "Error"})
            console.log(`Error by sending input ${this.state.value}`)
        }
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