import React from "react";
import { shallow, mount } from '../test-setup';
import FormatAmountForm from './FormatAmountForm'

declare const tsConfig: any;
const mockfn = jest.fn();

describe("<FormatAmountForm />", () => {
    let wrapper: any;

    const props: any = {
        handleSubmit: mockfn,
    };

    beforeEach(() => {
        wrapper = shallow(<FormatAmountForm {...props} />);
    });

    it('defines the component', () => {
        wrapper = mount(<FormatAmountForm />);
        expect(wrapper).toBeDefined();
    });

    describe('defines format amount form fields', () => {
        it('renders currency field', () => {
            const currency = wrapper.find('[id="currency"]');

            expect(currency.prop('type')).toBe('text');
            expect(currency.prop('label')).toBe('Amount currency');
        });

        it('renders value field', () => {
            const currency = wrapper.find('[id="value"]');

            expect(currency.prop('type')).toBe('number');
            expect(currency.prop('label')).toBe('Amount value in minor units');
        });

        it('renders precision field', () => {
            const currency = wrapper.find('[id="precision"]');

            expect(currency.prop('type')).toBe('number');
            expect(currency.prop('label')).toBe('Amount precision');
        });
    });
});
