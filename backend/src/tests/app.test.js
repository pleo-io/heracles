const request =require("supertest")
const {app} = require("../app")

describe("Unit Test", () => {

    describe("Positive scenarios", () => {
        const arrayOfInputAndOutputs = [
            {input: 0, output: "0.00"},
            {input: 0.01, output: "0.01"},
            {input: 3, output: "3.00"},
            {input: 0.001, output: "0.00"},
            {input: "1e+0", output: "1.00"},
            {input: 1.16934234, output: "1.17"},
            {input: 1000, output: "1 000.00"},
            {input: 1000000, output: "1 000 000.00"},
            {input: 2000000000, output: "2 000 000 000.00"},
            {input: 20000000000, output: "20 000 000 000.00"},
        ]

        arrayOfInputAndOutputs.map((each)=>{
            test(`should return ${each.output} when input is ${each.input}`, async () => {
                const response = await request(app)
                    .post('/formatMoney')
                    .send({value: each.input})
                expect(response.status).toBe(200)
                expect(response.body.value).toBe(each.output)
            })
        })
    })

})