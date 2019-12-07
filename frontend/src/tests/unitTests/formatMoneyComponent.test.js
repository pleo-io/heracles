import React from 'react';
import FormatMoneyComponent from "../../Components/FormatMoneyComponent"
import {shallow} from "../Enzyme"

let wrapper
beforeEach(()=>{
    wrapper = shallow(<FormatMoneyComponent/>)
})

test('Match snapshot formatMoney component', () => {
    expect(wrapper).toMatchSnapshot()
});

test("Should correctly set the state value on change", () => {
    const value = 123;
    wrapper.find('input').simulate('change', {
        target: {value}
    })
    expect(wrapper.state('value')).toBe(value)
})

