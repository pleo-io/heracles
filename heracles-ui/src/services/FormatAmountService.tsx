import axios from "axios";
import uuid from "uuid/v4";
import Urls from "../resources/Urls";

declare const tsConfig: any;

export default class FormatAmountService {

    public static formatAmount(data: any = {}) {
        // const url = `${Urls.FORMAT_AMOUNT_URL}`; // fails test due to tsConfig not being found
        const url = "http://localhost:8002/api/v1/formatAmount"
        const headers = FormatAmountService.getHeaders()

        const instance = axios.create({
            headers: headers,
        });

        data.header = {
            groupId: headers.groupId,
            messageId: headers.messageId,
            timestamp:  headers.timestamp,
        };

        return instance.post(url, data)
    }

    private static getHeaders = () => {
        const headers = {
            "groupId": uuid(),
            "messageId" : uuid(),
            "timestamp" : new Date().toISOString(),
            "Authorization" : tsConfig.API_KEY,
            "Content-Type": "application/json",
            "sessionId": localStorage.getItem("sessionId"),
        };
        return headers;
    }
}
