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
        } catch {
            this.setState({received: "Error"})
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
                {
                    this.state.received === ("Error" || undefined)
                        ? <h2 test-id={"Error"} style={{'color': '#b22222'}}>{this.state.received}</h2>
                        : this.state.received && <h2 test-id={"ok"}>{this.state.received}</h2>
                }
            </div>
        )}
}

export default FormatMoneyComponent